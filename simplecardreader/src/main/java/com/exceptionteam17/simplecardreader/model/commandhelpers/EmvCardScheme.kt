package com.exceptionteam17.simplecardreader.model.commandhelpers

import com.exceptionteam17.simplecardreader.utils.BytesUtils

/**
 * Class used to define all supported NFC EMV cards. http://en.wikipedia.org/wiki/Europay_Mastercard_Visa
 */
enum class EmvCardScheme(val scheme: String,  aids: String) {

    UNKNOWN("", ""),
    VISA("VISA",  "A0 00 00 00 03"), //"^4[0-9]{6,}$"
    VISA2("VISA",  "A0 00 00 00 03 10 10"), //"^4[0-9]{6,}$"
    VISA3("VISA",  "A0 00 00 00 98 08 48"), //"^4[0-9]{6,}$"
    NAB_VISA("VISA",  "A0 00 00 00 03"), //"^4[0-9]{6,}$"
    NAB_VISA2("VISA",   "A0 00 00 03"), //"^4[0-9]{6,}$"
    NAB_VISA3("VISA",  "A0 00 00 00 03 10 10"), //"^4[0-9]{6,}$"
    NAB_VISA4("VISA",  "A0 00 00 00 98 08 48"), //"^4[0-9]{6,}$"
    MASTER_CARD("Master card",  "A0 00 00 00 04"), //"^5[1-5][0-9]{5,}$"
    MASTER_CARD2("Master card",   "A0 00 00 00 05"), //"^5[1-5][0-9]{5,}$"
    AMERICAN_EXPRESS("American express",  "A0 00 00 00 25"), //"^3[47][0-9]{5,}$"
    CB("CB",  "A0 00 00 00 42"),
    LINK("LINK",  "A0 00 00 00 29"),
    JCB("JCB",  "A0 00 00 00 65"), //"^(?:2131|1800|35[0-9]{3})[0-9]{3,}$"
    DANKORT("Dankort",  "A0 00 00 01 21 10 10"),
    COGEBAN("CoGeBan",  "A0 00 00 01 41 00 01"),
    DISCOVER("Discover",  "A0 00 00 01 52 30 10"), //"(6011|65|64[4-9]|622)[0-9]*"
    BANRISUL("Banrisul",  "A0 00 00 01 54"),
    SPAN("Saudi Payments Network",  "A0 00 00 02 28"),
    INTERAC("Interac",  "A0 00 00 02 77"),
    ZIP("Discover Card",  "A0 00 00 03 24"),
    UNIONPAY("UnionPay",  "A0 00 00 03 33"), //"^62[0-9]{14,17}"
    EAPS("Euro Alliance of Payment Schemes",  "A0 00 00 03 59"),
    VERVE("Verve",  "A0 00 00 03 71"),
    TENN("The Exchange Network ATM Network",  "A0 00 00 04 39"),
    RUPAY("Rupay",  "A0 00 00 05 24 10 10"),
    PRO100("PRO100",  "A0 00 00 04 32 00 01"),
    ZKA("ZKA",  "D2 76 00 00 25 45 50 01 00"),
    BANKAXEPT("Bankaxept",  "D5 78 00 00 02 10 10"),
    BRADESCO("BRADESCO",  "F0 00 00 00 03 00 01"),
    MIDLAND("Midland",  "A0 00 00 00 24 01"),
    PBS("PBS",  "A0 00 00 01 21 10 10"),
    ETRANZACT("eTranzact",  "A0 00 00 04 54"),
    GOOGLE("Google",  "A0 00 00 04 76 6C"),
    INTER_SWITCH("InterSwitch",  "A0 00 00 03 71 00 01");

    /**
     * array of Aid in byte
     */
    val aidByte: ByteArray = BytesUtils.byteArrayFromString(aids)
}