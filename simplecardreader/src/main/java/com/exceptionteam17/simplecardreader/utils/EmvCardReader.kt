package com.exceptionteam17.simplecardreader.utils

import android.nfc.tech.IsoDep
import com.exceptionteam17.simplecardreader.model.commandhelpers.EmvCardScheme
import com.exceptionteam17.simplecardreader.model.commandhelpers.StatusEnum
import com.exceptionteam17.simplecardreader.model.commandhelpers.StatusEnum.Companion.isEqual
import com.exceptionteam17.simplecardreader.model.commandhelpers.EmvTags
import com.exceptionteam17.simplecardreader.model.ApplicationFileLocator
import com.exceptionteam17.simplecardreader.model.EmvCard
import com.exceptionteam17.simplecardreader.model.constructByteValue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * Emv Parser
 * Class used to read and parse EMV card
 */
class EmvCardReader(private val isoDep: IsoDep) {

    /**
     * Card data
     */
    private val card: EmvCard = EmvCard()

    /**
     * Method used to read public data from EMV card
     */
    fun readEmvCard(): EmvCard { // use PSE first
        if (!readPpseAndPseDirectory()) { // Find with AID
            readWithAID()
        }
        return card
    }

    /**
     * Method used to parse File Control Information (FCI) Proprietary Template
     */
    private fun parseFCIProprietaryTemplate(inputData: ByteArray?): ByteArray? { // Get SFI
        var data = TlvHandler.getValue(inputData, EmvTags.SFI)
        // Check SFI
        if (data != null) {
            val sfi = BytesUtils.byteArrayToInt(data)
            data = transceive(CommandApduCreator.create(CommandApduCreator.CommandType.READ_RECORD, sfi, sfi shl 3 or 4, 0))
            // If LE is not correct
            if (isEqual(data, StatusEnum.STATUS_6C)) {
                data = transceive(CommandApduCreator.create(CommandApduCreator.CommandType.READ_RECORD, sfi, sfi shl 3 or 4, data!![data.size - 1].toInt()))
            }
            return data
        }
        return inputData
    }

    /**
     * Read EMV card with Payment System Environment or Proximity Payment System Environment
     */
    private fun readPpseAndPseDirectory(): Boolean {
        return if (readPpseDirectory()){
            true
        } else {
            readPseDirectory()
        }
    }

    private fun readPpseDirectory(): Boolean{
        var data = transceive(CommandApduCreator.create(CommandApduCreator.CommandType.SELECT, PPSE_DIRECTORY, 0))
        var isSuccessful = false

        if (isCommandSucceed(data)) { // Parse FCI Template
            data = parseFCIProprietaryTemplate(data)
            // Extract application label
            var isExtractPublicDataSucceed = false
            if (isCommandSucceed(data)) { // Get Aids
                val aids = getAids(data)
                for (aid in aids) {
                    isExtractPublicDataSucceed = extractPublicData(aid)

                    if(isExtractPublicDataSucceed && isSuccessful && card.secondCardNumber.isNotEmpty()){
                        break
                    } else if(isExtractPublicDataSucceed){
                        isSuccessful = true
                        card.isNfcLocked = false
                    }
                }
            }
        }

        return isSuccessful
    }

    private fun readPseDirectory(): Boolean {
        var data = transceive(CommandApduCreator.create(CommandApduCreator.CommandType.SELECT, PSE_DIRECTORY, 0))
        if (isCommandSucceed(data)) {
            data = parseFCIProprietaryTemplate(data)
            if (isCommandSucceed(data)) {
                val aids = getAids(data)
                for (aid in aids) {
                    if (extractPublicData(aid)) {
                        card.isNfcLocked = false
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Method used to get the aid list, if the Kernel Identifier is defined,
     * this value need to be appended to the ADF Name in the data field of the SELECT command.
     * @return the Aid to select
     */
    private fun getAids(inputData: ByteArray?): List<ByteArray> {
        val ret: MutableList<ByteArray> = ArrayList()
        val listTlv = TlvHandler.getlistTLV(inputData, EmvTags.AID_CARD, EmvTags.KERNEL_IDENTIFIER)
        for (tlv in listTlv) {
            if (tlv?.tag === EmvTags.KERNEL_IDENTIFIER && ret.size > 1) {
                val joinedArray = ByteArray(ret[ret.size - 1].size + tlv.valueBytes.size)
                System.arraycopy(ret[ret.size - 1], 0, joinedArray, 0, ret[ret.size - 1].size)
                System.arraycopy(tlv.valueBytes, 0, joinedArray, ret[ret.size - 1].size, tlv.valueBytes.size)

                ret.add(joinedArray)
            } else {
                ret.add(tlv!!.valueBytes)
            }
        }
        return ret
    }

    /**
     * Read EMV card with AID
     */
    private fun readWithAID() { // Test each card from know EMV AID
        for (type in EmvCardScheme.values()) {
            if (extractPublicData(type.aidByte)) {
                return
            }
        }
    }

    /**
     * Select application with AID or RID
     * byte array containing AID or RID
     * @return response byte array
     */
    private fun selectAID(aid: ByteArray?): ByteArray? {
        return transceive(CommandApduCreator.create(CommandApduCreator.CommandType.SELECT, aid, 0))
    }

    /**
     * Read public card data from parameter AID
     * @param aid
     * card AID in bytes
     * @return true if succeed false otherwise
     */
    private fun extractPublicData(aid: ByteArray?): Boolean {
        var ret = false
        // Select AID
        val data = selectAID(aid)
        // check response
        if (isCommandSucceed(data)) { // Parse select response
            ret = parse(data)
        }
        return ret
    }

    /**
     * Method used to parse EMV card
     */
    private fun parse(selectResponse: ByteArray?): Boolean {
        var ret = false
        // Get PDOL
        val pdol = TlvHandler.getValue(selectResponse, EmvTags.PDOL)
        // Send GPO Command
        var gpo = getGetProcessingOptions(pdol)
        // Check empty PDOL
        if (!isCommandSucceed(gpo)) {
            gpo = getGetProcessingOptions(null)
            // Check response
            if (!isCommandSucceed(gpo)) {
                return false
            }
        }
        // Extract commons card data (number, expire date, ...)
        if (extractCommonsCardData(gpo)) { // Extract log entry
            ret = true
        }
        return ret
    }

    /**
     * Method used to extract commons card data
     * gpo = global processing options response
     */
    private fun extractCommonsCardData(gpo: ByteArray?): Boolean {
        var ret = false
        // Extract data from Message Template 1
        var data = TlvHandler.getValue(gpo, EmvTags.RESPONSE_MESSAGE_TEMPLATE_1)

        if (data != null) {
            data = data.copyOfRange(1, data.size-1)
        } else { // Extract AFL data from Message template 2
            ret = card.setTrack2Data(gpo)
            if (!ret) {
                data = TlvHandler.getValue(gpo, EmvTags.APPLICATION_FILE_LOCATOR)
            } else {
                extractCardHolderName(gpo)
            }
        }
        if (data != null) { // Extract Afl
            val listApplicationFileLocator = extractAfl(data)
            // for each AFL

            for ((sfi, firstRecord, lastRecord) in listApplicationFileLocator) { // check all records
                for (index in firstRecord..lastRecord) {
                    var info = transceive(CommandApduCreator.create(CommandApduCreator.CommandType.READ_RECORD, index, sfi shl 3 or 4, 0))
                    if (isEqual(info, StatusEnum.STATUS_6C)) {
                        info = transceive(CommandApduCreator.create(CommandApduCreator.CommandType.READ_RECORD, index, sfi shl 3 or 4, info!![info.size - 1].toInt()))
                    }
                    // Extract card data
                    if (isCommandSucceed(info)) {
                        extractCardHolderName(info)
                        if (card.setTrack2Data(info)) {
                            return true
                        }
                    }
                }
            }
        }
        return ret
    }

    /**
     * Extract list of application file locator from Afl response
     * AFL data
     * @return list of AFL
     */
    private fun extractAfl(afl: ByteArray?): List<ApplicationFileLocator> {
        val list: MutableList<ApplicationFileLocator> = ArrayList()
        val bai = ByteArrayInputStream(afl)
        while (bai.available() >= 4) {
            list.add(ApplicationFileLocator(bai.read() shr 3, bai.read(), bai.read(), bai.read() == 1))
        }
        return list
    }

    /**
     * Extract card holder lastname and firstname (if exist)
     */
    private fun extractCardHolderName(cardData: ByteArray?) {
        val cardHolderByte = TlvHandler.getValue(cardData, EmvTags.CARDHOLDER_NAME)
        if (cardHolderByte != null) {
            card.holderName = String(cardHolderByte)
        }
    }

    /**
     * Method used to create GPO command and execute it
     */
    private fun getGetProcessingOptions(pdolData: ByteArray?): ByteArray? { // List Tag and length from PDOL
        val listOptions = TlvHandler.parseTagAndLength(pdolData)
        val out = ByteArrayOutputStream()
        try {
            out.write(EmvTags.COMMAND_TEMPLATE.tagIdBytes) // COMMAND
            out.write(TlvHandler.getTotalLength(listOptions)) // ADD total length
            for (tagAndLength in listOptions) {
                out.write(tagAndLength.constructByteValue())
            }

        } catch (ignored: IOException) { }

        return transceive(CommandApduCreator.create(CommandApduCreator.CommandType.GPO, out.toByteArray(), 0))
    }

    private fun transceive(command: ByteArray?) = try {
        isoDep.transceive(command)// send command to emv card
    } catch (e: IOException) {
        null
    }

    companion object {

        val PPSE_DIRECTORY = "2PAY.SYS.DDF01".toByteArray()

        val PSE_DIRECTORY = "1PAY.SYS.DDF01".toByteArray()

        /**
         * Method used to check if the last command return SW1SW2 == 9000 - succeed
         * @return true if the status is 9000 false otherwise
         */
        fun isCommandSucceed(lastCommandData: ByteArray?): Boolean {
            return isEqual(lastCommandData, StatusEnum.STATUS_9000)
        }
    }
}