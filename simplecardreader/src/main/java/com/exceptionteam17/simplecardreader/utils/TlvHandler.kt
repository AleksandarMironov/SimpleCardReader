package com.exceptionteam17.simplecardreader.utils

import com.exceptionteam17.simplecardreader.model.commandhelpers.EmvTags
import com.exceptionteam17.simplecardreader.model.EvmTag
import com.exceptionteam17.simplecardreader.model.TLV
import com.exceptionteam17.simplecardreader.model.TagAndLength
import com.exceptionteam17.simplecardreader.utils.BytesUtils.byteArrayToInt
import com.exceptionteam17.simplecardreader.utils.BytesUtils.matchBitByBitIndex
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList

/**
 * List of utils methods to manipulate TLV
 * More info:
 * https://en.wikipedia.org/wiki/Type-length-value
 * http://www.emvlab.org/tlvutils/
 */
object TlvHandler {
    private fun readTagIdBytes(stream: ByteArrayInputStream): ByteArray {
        val tagBAOS = ByteArrayOutputStream()
        val tagFirstOctet = stream.read().toByte()
        tagBAOS.write(tagFirstOctet.toInt())
        // Find TAG bytes
        val mask = 0x1F.toByte()
        if (tagFirstOctet.toInt() and mask.toInt() == mask.toInt()) { // EMV book 3, Page 178 or Annex B1 (EMV4.3)
            // Tag field is longer than 1 byte
            val nextOctet = stream.read()
            while (nextOctet >= 0){
                val tlvIdNextOctet = nextOctet.toByte()
                tagBAOS.write(tlvIdNextOctet.toInt())
                if (!matchBitByBitIndex(tlvIdNextOctet.toInt(), 7) ||
                        (matchBitByBitIndex(tlvIdNextOctet.toInt(), 7) && tlvIdNextOctet.toInt() and 0x7f == 0)) {
                    break
                }
            }
        }
        return tagBAOS.toByteArray()
    }

    private fun readTagLength(stream: ByteArrayInputStream): Int { // Find LENGTH bytes
        var length = stream.read()
        when {
            length < 0 -> return -1

            // 127 0111 1111 -> short length form
            // 128 1000 0000
            // indefinite form is not specified in ISO7816-4, but we include it here for completeness
            length <= 128 -> return length

            else -> { // long length form
                val numberOfLengthOctets = length and 127 // turn off 8th bit
                length = 0
                for (i in 0 until numberOfLengthOctets) {
                    val nextLengthOctet = stream.read()
                    if (nextLengthOctet < 0) {  //"EOS when reading length bytes"
                        return -1
                    }
                    length = length shl 8
                    length = length or nextLengthOctet
                }
                return length
            }
        }
    }

    private fun getNextTLV(stream: ByteArrayInputStream): TLV? {
        removeMeaninglessBytesFromTlvStreem(stream)

        //Error parsing data. Available bytes < 2
        if (stream.available() < 2) {
            return null
        }

        val tagIdBytes = readTagIdBytes(stream)

        // We need to get the raw length bytes.
        // Use quick and dirty workaround
        stream.mark(0)
        val posBefore = stream.available()

        // Now parse the lengthbyte(s)
        // This method will read all length bytes. We can then find out how many bytes was read.
        var length = readTagLength(stream) // Decoded

        // Now find the raw (encoded) length bytes
        val posAfter = stream.available()
        stream.reset()
        val lengthBytes = ByteArray(posBefore - posAfter)
        if (lengthBytes.size < 1 || lengthBytes.size > 4) { //Number of length bytes must be from 1 to 4
            return null
        }
        stream.read(lengthBytes, 0, lengthBytes.size)
        val rawLength = byteArrayToInt(lengthBytes)
        val valueBytes: ByteArray
        val tag = EmvTags.find(tagIdBytes)

        // Find VALUE bytes
        if (rawLength == 128) { // 1000 0000
            stream.mark(0)
            var prevOctet = 1
            var curOctet: Int
            var len = 0
            while (true) {
                len++
                curOctet = stream.read()

                //"Error parsing data. TLV length byte indicated indefinite length, but EOS was reached before 0x0000 was found
                if (curOctet < 0) {
                    return null
                }
                if (prevOctet == 0 && curOctet == 0) {
                    break
                }
                prevOctet = curOctet
            }
            len -= 2
            valueBytes = ByteArray(len)
            stream.reset()
            stream.read(valueBytes, 0, len)
            length = len

        } else {
            if (stream.available() < length || length < 0) {
                return null
            }
            // definite form
            valueBytes = ByteArray(length)
            stream.read(valueBytes, 0, length)
        }

        clearTrailingMeaninglessBytes(stream)

        return TLV(tag, length, lengthBytes, valueBytes)
    }

    /**
     *  ISO/IEC 7816 uses neither '00' nor 'FF' as tag value.
     *  Before, between, or after TLV-coded data objects,
     *  '00' or 'FF' bytes without any meaning may occur
     *  (for example, due to erased or modified TLV-coded data objects).
     *  This function removes them.
     */
    private fun removeMeaninglessBytesFromTlvStreem(stream: ByteArrayInputStream): ByteArrayInputStream{
        //Error parsing data. Available bytes < 2
        if (stream.available() < 2) {
            return stream
        }

        // ISO/IEC 7816 uses neither '00' nor 'FF' as tag value.
        // Before, between, or after TLV-coded data objects,
        // '00' or 'FF' bytes without any meaning may occur
        // (for example, due to erased or modified TLV-coded data objects).
        stream.mark(0)

        // peekInt == 0xffffffff indicates EOS
        var peekInt = stream.read()
        var peekByte = peekInt.toByte()

        while (peekInt != -1 && (peekByte == 0xFF.toByte() || peekByte == 0x00.toByte())) {

            // Current position
            stream.mark(0)
            peekInt = stream.read()
            peekByte = peekInt.toByte()
        }

        // Reset back to the last known position without 0x00 or 0xFF
        stream.reset()
        return stream
    }

    private fun clearTrailingMeaninglessBytes(stream: ByteArrayInputStream): ByteArrayInputStream{

        // Remove any trailing 0x00 and 0xFF
        stream.mark(0)
        var peekInt = stream.read()
        var peekByte = peekInt.toByte()
        while (peekInt != -1 && (peekByte == 0xFF.toByte() || peekByte == 0x00.toByte())) {
            stream.mark(0)
            peekInt = stream.read()
            peekByte = peekInt.toByte()
        }

        // Reset back to the last known position without 0x00 or 0xFF
        stream.reset()

        return stream
    }

    /**
     * Method used to parser Tag and length
     */
    fun parseTagAndLength(data: ByteArray?): List<TagAndLength> {
        val tagAndLengthList: MutableList<TagAndLength> = ArrayList()
        if (data != null) {
            val stream = ByteArrayInputStream(data)
            while (stream.available() > 1) {
                val tag = EmvTags.find(readTagIdBytes(stream))
                val tagValueLength = readTagLength(stream)
                tagAndLengthList.add(TagAndLength(tag, tagValueLength))
            }
        }
        return tagAndLengthList
    }

    /**
     * Method used to get the list of TLV corresponding to tags specified in parameters
     */
    fun getlistTLV(data: ByteArray?, vararg tags: EvmTag?): List<TLV?> {
        val list: MutableList<TLV?> = ArrayList()
        if(data != null) {
            val stream = ByteArrayInputStream(data)
            while (stream.available() > 0) {
                val tlv = getNextTLV(stream)
                if(tlv != null) {
                    if (tags.contains(tlv.tag)) {
                        list.add(tlv)
                    } else if (tlv.tag.isConstructed) {
                        list.addAll(getlistTLV(tlv.valueBytes, *tags))
                    }
                }
            }
        }
        return list
    }

    /**
     * Method used to get Tag value
     */
    fun getValue(data: ByteArray?, vararg tags: EvmTag?): ByteArray? {
        var ret: ByteArray? = null
        if (data != null) {
            val stream = ByteArrayInputStream(data)
            while (stream.available() > 0) {
                val tlv = getNextTLV(stream)
                if(tlv != null) {
                    if (tags.contains(tlv.tag)) {
                        return tlv.valueBytes
                    } else if (tlv.tag.isConstructed) {
                        ret = getValue(tlv.valueBytes, *tags)
                        if (ret != null) {
                            break
                        }
                    }
                }
            }
        }
        return ret
    }

    /**
     * Method used to get length of all Tags
     */
    fun getTotalLength(list: List<TagAndLength>?): Int {
        var totalLength = 0
        if (list != null) {
            for ((_, length) in list) {
                totalLength += length
            }
        }
        return totalLength
    }
}