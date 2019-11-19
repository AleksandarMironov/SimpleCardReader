package com.exceptionteam17.simplecardreader.model

import com.exceptionteam17.simplecardreader.model.commandhelpers.EmvTags
import com.exceptionteam17.simplecardreader.utils.BytesUtils

data class TagAndLength(
        val tag: EvmTag,
        val length: Int)

fun TagAndLength.constructByteValue(): ByteArray {
    val returnValue = ByteArray(this.length)

    //Terminal Transaction Qualifiers, (Tag '9F66')
    var terminalQualValue: ByteArray? = null
    if(this.tag === EmvTags.TERMINAL_TRANSACTION_QUALIFIERS){

        terminalQualValue = ByteArray(4)

        //Set Contactless EMV Mode Supported
        terminalQualValue[0] = BytesUtils.setBit(terminalQualValue[0], 5, true)

        //Set Reader Is Offline Only
        terminalQualValue[0] = BytesUtils.setBit(terminalQualValue[0], 3, true)
    }

    terminalQualValue?.let{System.arraycopy(terminalQualValue, 0, returnValue, 0, Math.min(terminalQualValue.size, returnValue.size))}

    return returnValue
}