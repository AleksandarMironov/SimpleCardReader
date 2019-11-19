package com.exceptionteam17.simplecardreader.model

/**
 * More info about TLV:
 * https://en.wikipedia.org/wiki/Type-length-value
 * http://www.emvlab.org/tlvutils/
 */

data class TLV(
        var tag: EvmTag,
        var length: Int,
        var rawEncodedLengthBytes: ByteArray,
        var valueBytes: ByteArray){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TLV

        if (tag != other.tag) return false
        if (length != other.length) return false
        if (!rawEncodedLengthBytes.contentEquals(other.rawEncodedLengthBytes)) return false
        if (!valueBytes.contentEquals(other.valueBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + length
        result = 31 * result + rawEncodedLengthBytes.contentHashCode()
        result = 31 * result + valueBytes.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "TLV(tag=$tag, length=$length, rawEncodedLengthBytes=${rawEncodedLengthBytes.contentToString()}, valueBytes=${valueBytes.contentToString()})"
    }
}