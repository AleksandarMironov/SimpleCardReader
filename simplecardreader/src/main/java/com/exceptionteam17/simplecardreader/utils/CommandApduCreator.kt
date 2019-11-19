package com.exceptionteam17.simplecardreader.utils

/**
 * APDU - Smart card application protocol data unit
 * You can find more here:
 * https://en.wikipedia.org/wiki/Smart_card_application_protocol_data_unit
 * https://www.iso.org/standard/36134.html
 */
object CommandApduCreator {

    fun create(commandType: CommandType, data: ByteArray?, le: Int): ByteArray  {
        return toBytes(commandType, commandType.parameter1, commandType.parameter2, data, le)
    }

    fun create(commandType: CommandType, parameter1: Int, parameter2: Int, le: Int): ByteArray  {
        return toBytes(commandType, parameter1, parameter2, ByteArray(0), le)
    }

    private fun toBytes(commandType: CommandType,
                        parameter1: Int,
                        parameter2: Int,
                        data: ByteArray?,
                        le: Int
    ): ByteArray {

        var length = 5 // CLA, INS, P1, P2, .., LE

        if (data?.isNotEmpty() == true) {
            length += 1 // LC
            length += data.size // DATA
        }

        val apduCommand = ByteArray(length)

        apduCommand[0] = commandType.classByte.toByte()
        apduCommand[1] = commandType.instructionByte.toByte()
        apduCommand[2] = parameter1.toByte()
        apduCommand[3] = parameter2.toByte()

        var apduCommandArrIndex = 4

        if (data?.isNotEmpty() == true) {
            apduCommand[apduCommandArrIndex] = (data.size).toByte()
            apduCommandArrIndex++
            System.arraycopy(data, 0, apduCommand, apduCommandArrIndex, data.size)
            apduCommandArrIndex += data.size
        }
        apduCommand[apduCommandArrIndex] = (apduCommand[apduCommandArrIndex] + le.toByte()).toByte() // LE

        return apduCommand
    }

    //Enum which define all EMV apdu commands
    enum class CommandType(
            val classByte: Int,
            val instructionByte: Int,
            val parameter1: Int,
            val parameter2: Int) {

        SELECT(0x00, 0xA4, 0x04, 0x00),

        READ_RECORD(0x00, 0xB2, 0x00, 0x00),

        GPO(0x80, 0xA8, 0x00, 0x00),

        GET_DATA(0x80, 0xCA, 0x00, 0x00);

    }
}