package com.exceptionteam17.simplecardreader.model.commandhelpers

import com.exceptionteam17.simplecardreader.model.EvmTag
import com.exceptionteam17.simplecardreader.model.EvmTag.TagValueTypeEnum
import com.exceptionteam17.simplecardreader.utils.BytesUtils.bytesToStringNoSpace

/**
 * http://www.emvlab.org/emvtags/all/
 * The coding of primitive context-specific class data objects in the ranges '80' to '9E' and '9F00' to '9F4F' is reserved for EMV
 * specification. The coding of primitive context-specific class data objects in the range '9F50' to '9F7F' is reserved for the
 * payment systems.
 */
object EmvTags {
    val AID_CARD = EvmTag("4f", TagValueTypeEnum.BINARY, "Application Identifier (AID) - card", "Identifies the application as described in ISO/IEC 7816-5")
    val SFI = EvmTag("88", TagValueTypeEnum.BINARY, "Short File Identifier (SFI)", "Identifies the SFI to be used in the commands related to a given AEF or DDF. The SFI data object is a binary field with the three high order bits set to zero")
    val TRACK_2_EQV_DATA = EvmTag("57", TagValueTypeEnum.BINARY, "Track 2 Equivalent Data", "Contains the data elements of track 2 according to ISO/IEC 7813, excluding start sentinel, end sentinel, and Longitudinal Redundancy Check (LRC)")
    val RESPONSE_MESSAGE_TEMPLATE_1 = EvmTag("80", TagValueTypeEnum.BINARY, "Response Message Template Format 1", "Contains the data objects (without tags and lengths) returned by the ICC in response to a command")
    val COMMAND_TEMPLATE = EvmTag("83", TagValueTypeEnum.BINARY, "Command Template", "Identifies the data field of a command message")
    val APPLICATION_FILE_LOCATOR = EvmTag("94", TagValueTypeEnum.BINARY, "Application File Locator (AFL)", "Indicates the location (SFI, range of records) of the AEFs related to a given application")
    val PDOL = EvmTag("9f38", TagValueTypeEnum.DOL, "Processing Options Data Object List (PDOL)", "Contains a list of terminal resident data objects (tags and lengths) needed by the ICC in processing the GET PROCESSING OPTIONS command")
    val CARDHOLDER_NAME = EvmTag("5f20", TagValueTypeEnum.TEXT, "Cardholder Name", "Indicates cardholder name according to ISO 7813")
    val TERMINAL_TRANSACTION_QUALIFIERS = EvmTag("9f66", TagValueTypeEnum.BINARY, "Terminal Transaction Qualifiers", "Provided by the reader in the GPO command and used by the card to determine processing choices based on reader functionality")
    val TRACK2_DATA = EvmTag("9f6b", TagValueTypeEnum.BINARY, "Track 2 Data", "Track 2 Data contains the data objects of the track 2 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC.")
    val KERNEL_IDENTIFIER = EvmTag("9f2a", TagValueTypeEnum.BINARY, "The value to be appended to the ADF Name in the data field of the SELECT command, if the Extended Selection Support flag is present and set to 1", "")

    private val tags = arrayListOf(

            // 7816-4 Interindustry data object for tag allocation authority
            EvmTag("06", TagValueTypeEnum.BINARY, "Object Identifier (OID)", "Universal tag for OID"),
            EvmTag("41", TagValueTypeEnum.NUMERIC, "Country Code", "Country code (encoding specified in ISO 3166-1) and optional national data"),
            EvmTag("42", TagValueTypeEnum.NUMERIC, "Issuer Identification Number (IIN)", "The number that identifies the major industry and the card issuer and that forms the first part of the Primary Account Number (PAN)"),
            // 7816-4 Interindustry data objects for application identification and selection,
            AID_CARD,
            EvmTag("50", TagValueTypeEnum.TEXT, "Application Label", "Mnemonic associated with the AID according to ISO/IEC 7816-5"),
            EvmTag("51", TagValueTypeEnum.BINARY, "File reference data element", "ISO-7816 Path"),
            EvmTag("52", TagValueTypeEnum.BINARY, "Command APDU", ""),
            EvmTag("53", TagValueTypeEnum.BINARY, "Discretionary data (or template)", ""),
            EvmTag("61", TagValueTypeEnum.BINARY, "Application Template", "Contains one or more data objects relevant to an application directory entry according to ISO/IEC 7816-5"),
            EvmTag("6f", TagValueTypeEnum.BINARY, "File Control Information (FCI) Template", "Set of file control parameters and file management data (according to ISO/IEC 7816-4)"),
            EvmTag("73", TagValueTypeEnum.BINARY, "Directory Discretionary Template", "Issuer discretionary part of the directory according to ISO/IEC 7816-5"),
            EvmTag("84", TagValueTypeEnum.BINARY, "Dedicated File (DF) Name", "Identifies the name of the DF as described in ISO/IEC 7816-4"),
            SFI,
            EvmTag("a5", TagValueTypeEnum.BINARY, "File Control Information (FCI) Proprietary Template", "Identifies the data object proprietary to this specification in the FCI template according to ISO/IEC 7816-4"),
            EvmTag("5f50", TagValueTypeEnum.TEXT, "Issuer URL", "The URL provides the location of the Issuerâ€™s Library Server on the Internet"),
            // EMV,
            TRACK_2_EQV_DATA,
            EvmTag("5a", TagValueTypeEnum.NUMERIC, "Application Primary Account Number (PAN)", "Valid cardholder account number"),
            EvmTag("70", TagValueTypeEnum.BINARY, "Record Template (EMV Proprietary)", "Template proprietary to the EMV specification"),
            EvmTag("71", TagValueTypeEnum.BINARY, "Issuer Script Template 1", "Contains proprietary issuer data for transmission to the ICC before the second GENERATE AC command"),
            EvmTag("72", TagValueTypeEnum.BINARY, "Issuer Script Template 2", "Contains proprietary issuer data for transmission to the ICC after the second GENERATE AC command"),
            EvmTag("77", TagValueTypeEnum.BINARY, "Response Message Template Format 2", "Contains the data objects (with tags and lengths) returned by the ICC in response to a command"),
            RESPONSE_MESSAGE_TEMPLATE_1,
            EvmTag("81", TagValueTypeEnum.BINARY, "Amount, Authorised (Binary)", "Authorised amount of the transaction (excluding adjustments)"),
            EvmTag("82", TagValueTypeEnum.BINARY, "Application Interchange Profile", "Indicates the capabilities of the card to support specific functions in the application"),
            COMMAND_TEMPLATE,
            EvmTag("86", TagValueTypeEnum.BINARY, "Issuer Script Command", "Contains a command for transmission to the ICC"),
            EvmTag("87", TagValueTypeEnum.BINARY, "Application Priority Indicator", "Indicates the priority of a given application or group of applications in a directory"),
            EvmTag("89", TagValueTypeEnum.BINARY, "Authorisation Code", "Value generated by the authorisation authority for an approved transaction"),
            EvmTag("8a", TagValueTypeEnum.TEXT, "Authorisation Response Code", "Code that defines the disposition of a message"),
            EvmTag("8c", TagValueTypeEnum.DOL, "Card Risk Management Data Object List 1 (CDOL1)", "List of data objects (tag and length) to be passed to the ICC in the first GENERATE AC command"),
            EvmTag("8d", TagValueTypeEnum.DOL, "Card Risk Management Data Object List 2 (CDOL2)", "List of data objects (tag and length) to be passed to the ICC in the second GENERATE AC command"),
            EvmTag("8e", TagValueTypeEnum.BINARY, "Cardholder Verification Method (CVM) List", "Identifies a method of verification of the cardholder supported by the application"),
            EvmTag("8f", TagValueTypeEnum.BINARY, "Certification Authority Public Key Index - card", "Identifies the certification authorityâ€™s public key in conjunction with the RID"),
            EvmTag("90", TagValueTypeEnum.BINARY, "Issuer Public Key Certificate", "Issuer public key certified by a certification authority"),
            EvmTag("91", TagValueTypeEnum.BINARY, "Issuer Authentication Data", "Data sent to the ICC for online issuer authentication"),
            EvmTag("92", TagValueTypeEnum.BINARY, "Issuer Public Key Remainder", "Remaining digits of the Issuer Public Key Modulus"),
            EvmTag("93", TagValueTypeEnum.BINARY, "Signed Static Application Data", "Digital signature on critical application parameters for SDA"),
            APPLICATION_FILE_LOCATOR,
            EvmTag("95", TagValueTypeEnum.BINARY, "Terminal Verification Results (TVR)", "Status of the different functions as seen from the terminal"),
            EvmTag("97", TagValueTypeEnum.BINARY, "Transaction Certificate Data Object List (TDOL)", "List of data objects (tag and length) to be used by the terminal in generating the TC Hash Value"),
            EvmTag("98", TagValueTypeEnum.BINARY, "Transaction Certificate (TC) Hash Value", "Result of a hash function specified in Book 2, Annex B3.1"),
            EvmTag("99", TagValueTypeEnum.BINARY, "Transaction Personal Identification Number (PIN) Data", "Data entered by the cardholder for the purpose of the PIN verification"),
            EvmTag("9a", TagValueTypeEnum.NUMERIC, "Transaction Date", "Local date that the transaction was authorised"),
            EvmTag("9b", TagValueTypeEnum.BINARY, "Transaction Status Information", "Indicates the functions performed in a transaction"),
            EvmTag("9c", TagValueTypeEnum.NUMERIC, "Transaction Type", "Indicates the type of financial transaction, represented by the first two digits of ISO 8583:1987 Processing Code"),
            EvmTag("9d", TagValueTypeEnum.BINARY, "Directory Definition File (DDF) Name", "Identifies the name of a DF associated with a directory"),
            CARDHOLDER_NAME,
            EvmTag("5f24", TagValueTypeEnum.NUMERIC, "Application Expiration Date", "Date after which application expires"),
            EvmTag("5f25", TagValueTypeEnum.NUMERIC, "Application Effective Date", "Date from which the application may be used"),
            EvmTag("5f28", TagValueTypeEnum.NUMERIC, "Issuer Country Code", "Indicates the country of the issuer according to ISO 3166"),
            EvmTag("5f2a", TagValueTypeEnum.TEXT, "Transaction Currency Code", "Indicates the currency code of the transaction according to ISO 4217"),
            EvmTag("5f2d", TagValueTypeEnum.TEXT, "Language Preference", "1â€“4 languages stored in order of preference, each represented by 2 alphabetical characters according to ISO 639"),
            EvmTag("5f30", TagValueTypeEnum.NUMERIC, "Service Code", "Service code as defined in ISO/IEC 7813 for track 1 and track 2"),
            EvmTag("5f34", TagValueTypeEnum.NUMERIC, "Application Primary Account Number (PAN) Sequence Number", "Identifies and differentiates cards with the same PAN"),
            EvmTag("5f36", TagValueTypeEnum.NUMERIC, "Transaction Currency Exponent", "Indicates the implied position of the decimal point from the right of the transaction amount represented according to ISO 4217"),
            EvmTag("5f53", TagValueTypeEnum.BINARY, "International Bank Account Number (IBAN)", "Uniquely identifies the account of a customer at a financial institution as defined in ISO 13616"),
            EvmTag("5f54", TagValueTypeEnum.MIXED, "Bank Identifier Code (BIC)", "Uniquely identifies a bank as defined in ISO 9362"),
            EvmTag("5f55", TagValueTypeEnum.TEXT, "Issuer Country Code (alpha2 format)", "Indicates the country of the issuer as defined in ISO 3166 (using a 2 character alphabetic code)"),
            EvmTag("5f56", TagValueTypeEnum.TEXT, "Issuer Country Code (alpha3 format)", "Indicates the country of the issuer as defined in ISO 3166 (using a 3 character alphabetic code)"),
            EvmTag("9f01", TagValueTypeEnum.NUMERIC, "Acquirer Identifier", "Uniquely identifies the acquirer within each payment system"),
            EvmTag("9f02", TagValueTypeEnum.NUMERIC, "Amount, Authorised (Numeric)", "Authorised amount of the transaction (excluding adjustments)"),
            EvmTag("9f03", TagValueTypeEnum.NUMERIC, "Amount, Other (Numeric)", "Secondary amount associated with the transaction representing a cashback amount"),
            EvmTag("9f04", TagValueTypeEnum.NUMERIC, "Amount, Other (Binary)", "Secondary amount associated with the transaction representing a cashback amount"),
            EvmTag("9f05", TagValueTypeEnum.BINARY, "Application Discretionary Data", "Issuer or payment system specified data relating to the application"),
            EvmTag("9f06", TagValueTypeEnum.BINARY, "Application Identifier (AID) - terminal", "Identifies the application as described in ISO/IEC 7816-5"),
            EvmTag("9f07", TagValueTypeEnum.BINARY, "Application Usage Control", "Indicates issuerâ€™s specified restrictions on the geographic usage and services allowed for the application"),
            EvmTag("9f08", TagValueTypeEnum.BINARY, "Application Version Number - card", "Version number assigned by the payment system for the application"),
            EvmTag("9f09", TagValueTypeEnum.BINARY, "Application Version Number - terminal", "Version number assigned by the payment system for the application"),
            EvmTag("9f0b", TagValueTypeEnum.TEXT, "Cardholder Name Extended", "Indicates the whole cardholder name when greater than 26 characters using the same coding convention as in ISO 7813"),
            EvmTag("9f0d", TagValueTypeEnum.BINARY, "Issuer Action Code - Default", "Specifies the issuerâ€™s conditions that cause a transaction to be rejected if it might have been approved online, but the terminal is unable to process the transaction online"),
            EvmTag("9f0e", TagValueTypeEnum.BINARY, "Issuer Action Code - Denial", "Specifies the issuerâ€™s conditions that cause the denial of a transaction without attempt to go online"),
            EvmTag("9f0f", TagValueTypeEnum.BINARY, "Issuer Action Code - Online", "Specifies the issuerâ€™s conditions that cause a transaction to be transmitted online"),
            EvmTag("9f10", TagValueTypeEnum.BINARY, "Issuer Application Data", "Contains proprietary application data for transmission to the issuer in an online transaction"),
            EvmTag("9f11", TagValueTypeEnum.NUMERIC, "Issuer Code Table Index", "Indicates the code table according to ISO/IEC 8859 for displaying the Application Preferred Name"),
            EvmTag("9f12", TagValueTypeEnum.TEXT, "Application Preferred Name", "Preferred mnemonic associated with the AID"),
            EvmTag("9f13", TagValueTypeEnum.BINARY, "Last Online Application Transaction Counter (ATC) Register", "ATC value of the last transaction that went online"),
            EvmTag("9f14", TagValueTypeEnum.BINARY, "Lower Consecutive Offline Limit", "Issuer-specified preference for the maximum number of consecutive offline transactions for this ICC application allowed in a terminal with online capability"),
            EvmTag("9f15", TagValueTypeEnum.NUMERIC, "Merchant Category Code", "Classifies the type of business being done by the merchant, represented according to ISO 8583:1993 for Card Acceptor Business Code"),
            EvmTag("9f16", TagValueTypeEnum.TEXT, "Merchant Identifier", "When concatenated with the Acquirer Identifier, uniquely identifies a given merchant"),
            EvmTag("9f17", TagValueTypeEnum.BINARY, "Personal Identification Number (PIN) Try Counter", "Number of PIN tries remaining"),
            EvmTag("9f18", TagValueTypeEnum.BINARY, "Issuer Script Identifier", "Identification of the Issuer Script"),
            EvmTag("9f1a", TagValueTypeEnum.TEXT, "Terminal Country Code", "Indicates the country of the terminal, represented according to ISO 3166"),
            EvmTag("9f1b", TagValueTypeEnum.BINARY, "Terminal Floor Limit", "Indicates the floor limit in the terminal in conjunction with the AID"),
            EvmTag("9f1c", TagValueTypeEnum.TEXT, "Terminal Identification", "Designates the unique location of a terminal at a merchant"),
            EvmTag("9f1d", TagValueTypeEnum.BINARY, "Terminal Risk Management Data", "Application-specific value used by the card for risk management purposes"),
            EvmTag("9f1e", TagValueTypeEnum.TEXT, "Interface Device (IFD) Serial Number", "Unique and permanent serial number assigned to the IFD by the manufacturer"),
            EvmTag("9f1f", TagValueTypeEnum.TEXT, "[Magnetic Stripe] Track 1 Discretionary Data", "Discretionary part of track 1 according to ISO/IEC 7813"),
            EvmTag("9f20", TagValueTypeEnum.TEXT, "[Magnetic Stripe] Track 2 Discretionary Data", "Discretionary part of track 2 according to ISO/IEC 7813"),
            EvmTag("9f21", TagValueTypeEnum.NUMERIC, "Transaction Time (HHMMSS)", "Local time that the transaction was authorised"),
            EvmTag("9f22", TagValueTypeEnum.BINARY, "Certification Authority Public Key Index - Terminal", "Identifies the certification authorityâ€™s public key in conjunction with the RID"),
            EvmTag("9f23", TagValueTypeEnum.BINARY, "Upper Consecutive Offline Limit", "Issuer-specified preference for the maximum number of consecutive offline transactions for this ICC application allowed in a terminal without online capability"),
            EvmTag("9f26", TagValueTypeEnum.BINARY, "Application Cryptogram", "Cryptogram returned by the ICC in response of the GENERATE AC command"),
            EvmTag("9f27", TagValueTypeEnum.BINARY, "Cryptogram Information Data", "Indicates the type of cryptogram and the actions to be performed by the terminal"),
            EvmTag("9f2d", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Certificate", "ICC PIN Encipherment Public Key certified by the issuer"),
            EvmTag("9f2e", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Exponent", "ICC PIN Encipherment Public Key Exponent used for PIN encipherment"),
            EvmTag("9f2f", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Remainder", "Remaining digits of the ICC PIN Encipherment Public Key Modulus"),
            EvmTag("9f32", TagValueTypeEnum.BINARY, "Issuer Public Key Exponent", "Issuer public key exponent used for the verification of the Signed Static Application Data and the ICC Public Key Certificate"),
            EvmTag("9f33", TagValueTypeEnum.BINARY, "Terminal Capabilities", "Indicates the card data input, CVM, and security capabilities of the terminal"),
            EvmTag("9f34", TagValueTypeEnum.BINARY, "Cardholder Verification (CVM) Results", "Indicates the results of the last CVM performed"),
            EvmTag("9f35", TagValueTypeEnum.NUMERIC, "Terminal Type", "Indicates the environment of the terminal, its communications capability, and its operational control"),
            EvmTag("9f36", TagValueTypeEnum.BINARY, "Application Transaction Counter (ATC)", "Counter maintained by the application in the ICC (incrementing the ATC is managed by the ICC)"),
            EvmTag("9f37", TagValueTypeEnum.BINARY, "Unpredictable Number", "Value to provide variability and uniqueness to the generation of a cryptogram"),
            PDOL,
            EvmTag("9f39", TagValueTypeEnum.NUMERIC, "Point-of-Service (POS) Entry Mode", "Indicates the method by which the PAN was entered, according to the first two digits of the ISO 8583:1987 POS Entry Mode"),
            EvmTag("9f3a", TagValueTypeEnum.BINARY, "Amount, Reference Currency", "Authorised amount expressed in the reference currency"),
            EvmTag("9f3b", TagValueTypeEnum.NUMERIC, "Application Reference Currency", "1â€“4 currency codes used between the terminal and the ICC when the Transaction Currency Code is different from the Application Currency Code; each code is 3 digits according to ISO 4217"),
            EvmTag("9f3c", TagValueTypeEnum.NUMERIC, "Transaction Reference Currency Code", "Code defining the common currency used by the terminal in case the Transaction Currency Code is different from the Application Currency Code"),
            EvmTag("9f3d", TagValueTypeEnum.NUMERIC, "Transaction Reference Currency Exponent", "Indicates the implied position of the decimal point from the right of the transaction amount, with the Transaction Reference Currency Code represented according to ISO 4217"),
            EvmTag("9f40", TagValueTypeEnum.BINARY, "Additional Terminal Capabilities", "Indicates the data input and output capabilities of the terminal"),
            EvmTag("9f41", TagValueTypeEnum.NUMERIC, "Transaction Sequence Counter", "Counter maintained by the terminal that is incremented by one for each transaction"),
            EvmTag("9f42", TagValueTypeEnum.NUMERIC, "Application Currency Code", "Indicates the currency in which the account is managed according to ISO 4217"),
            EvmTag("9f43", TagValueTypeEnum.NUMERIC, "Application Reference Currency Exponent", "Indicates the implied position of the decimal point from the right of the amount, for each of the 1â€“4 reference currencies represented according to ISO 4217"),
            EvmTag("9f44", TagValueTypeEnum.NUMERIC, "Application Currency Exponent", "Indicates the implied position of the decimal point from the right of the amount represented according to ISO 4217"),
            EvmTag("9f45", TagValueTypeEnum.BINARY, "Data Authentication Code", "An issuer assigned value that is retained by the terminal during the verification process of the Signed Static Application Data"),
            EvmTag("9f46", TagValueTypeEnum.BINARY, "ICC Public Key Certificate", "ICC Public Key certified by the issuer"),
            EvmTag("9f47", TagValueTypeEnum.BINARY, "ICC Public Key Exponent", "ICC Public Key Exponent used for the verification of the Signed Dynamic Application Data"),
            EvmTag("9f48", TagValueTypeEnum.BINARY, "ICC Public Key Remainder", "Remaining digits of the ICC Public Key Modulus"),
            EvmTag("9f49", TagValueTypeEnum.DOL, "Dynamic Data Authentication Data Object List (DDOL)", "List of data objects (tag and length) to be passed to the ICC in the INTERNAL AUTHENTICATE command"),
            EvmTag("9f4a", TagValueTypeEnum.BINARY, "Static Data Authentication Tag List", "List of tags of primitive data objects defined in this specification whose value fields are to be included in the Signed Static or Dynamic Application Data"),
            EvmTag("9f4b", TagValueTypeEnum.BINARY, "Signed Dynamic Application Data", "Digital signature on critical application parameters for DDA or CDA"),
            EvmTag("9f4c", TagValueTypeEnum.BINARY, "ICC Dynamic Number", "Time-variant number generated by the ICC, to be captured by the terminal"),
            EvmTag("9f4d", TagValueTypeEnum.BINARY, "Log Entry", "Provides the SFI of the Transaction Log file and its number of records"),
            EvmTag("9f4e", TagValueTypeEnum.TEXT, "Merchant Name and Location", "Indicates the name and location of the merchant"),
            EvmTag("9f4f", TagValueTypeEnum.DOL, "Log Format", "List (in tag and length format) of data objects representing the logged data elements that are passed to the terminal when a transaction log record is read"),
            EvmTag("bf0c", TagValueTypeEnum.BINARY, "File Control Information (FCI) Issuer Discretionary Data", "Issuer discretionary part of the FCI (e.g. O/S Manufacturer proprietary data)"),
            EvmTag("df60", TagValueTypeEnum.BINARY, "VISA Log Entry", ""),
            // '9F50' to '9F7F' are reserved for the payment systems (proprietary)
            // The following tags are specified in EMV Contactless (Book A)
            // The Track 1 Data may be present in the file read using the READ
            // RECORD command during a mag-stripe mode transaction. It is made up of
            // the following sub-fields:
            // +------------------------+--------------+--------------------+
            // | Data Field | Length | Format |
            // +------------------------+--------------+--------------------+
            // | Format Code | 1 | '42' |
            // | Primary Account Number | var up to 19 | digits |
            // | Field Separator | 1 | '5E' |
            // | Name | 2-26 | (see ISO/IEC 7813) |
            // | Field Separator | 1 | '5E' |
            // | Expiry Date | 4 | YYMM |
            // | Service Code | 3 | digits |
            // | Discretionary Data | var. | ans |
            // +------------------------+--------------+--------------------+
            // BER-TLV[56, 29 (raw 29), 42 xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx 5e 20 2f 5e xx xx xx xx 32 30 31 30 31 30 30 30
            // 30 30 30 30 30 30 30 30]
            // BER-TLV[56, 34 (raw 34), 42 XX XX XX XX XX XX XX XX XX XX XX XX XX XX XX XX 5e 20 2f 5e YY YY MM MM 32 30 31 30 30 30 30 30
            // 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30
            EvmTag("56", TagValueTypeEnum.BINARY, "Track 1 Data", "Track 1 Data contains the data objects of the track 1 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC."),
            TERMINAL_TRANSACTION_QUALIFIERS,
            // The Track 2 Data is present in the file read using the READ RECORD command
            // during a mag-stripe mode transaction. It is made up of the following
            // sub-fields (same as tag 57):
            //
            // +------------------------+-----------------------+-----------+
            // | Data Field | Length | Format |
            // +------------------------+-----------------------+-----------+
            // | Primary Account Number | var. up to 19 nibbles | n |
            // | Field Separator | 1 nibble | b ('D') |
            // | Expiry Date | 2 | n (YYMM) |
            // | Service Code | 3 nibbles | n |
            // | Discretionary Data | var. | n |
            // | Padding if needed | 1 nibble | b ('F') |
            // +------------------------+-----------------------+-----------+
            // 9f6b 13 BB BB BB BB BB BB BB BB dY YM M2 01 00 00 00 00 00 00 0f
            EvmTag("9f6b", TagValueTypeEnum.BINARY, "Track 2 Data", "Track 2 Data contains the data objects of the track 2 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC."),
            EvmTag("9f6e", TagValueTypeEnum.BINARY, "Visa Low-Value Payment (VLP) Issuer Authorisation Code", ""),
            // These are specified in EMV Contactless (Book B)
            EvmTag("9f29", TagValueTypeEnum.BINARY, "Indicates the card's preference for the kernel on which the contactless application can be processed", ""),
            EvmTag("9f2a", TagValueTypeEnum.BINARY, "The value to be appended to the ADF Name in the data field of the SELECT command, if the Extended Selection Support flag is present and set to 1", ""),
            // MasterCard Tags
            EvmTag("9f52", TagValueTypeEnum.BINARY, "Upper Cumulative Domestic Offline Transaction Amount", "Issuer specified data element indicating the required maximum cumulative offline amount allowed for the application before the transaction goes online."),
            // EMV Cap
            EvmTag("9f56", TagValueTypeEnum.BINARY, "?", ""),
            // Example: BER-TLV[9f6c, 02 (raw 02), 0001]
            EvmTag("9f6c", TagValueTypeEnum.BINARY, "Mag Stripe Application Version Number (Card)", "Must be personalized with the value 0x0001"),
            // Transaction log data
            EvmTag("df3e", TagValueTypeEnum.BINARY, "?", ""),
            // These are specified in EMV Contactless (Book C-2) "MasterCard"
            EvmTag("9f50", TagValueTypeEnum.BINARY, "Offline Accumulator Balance", "Represents the amount of offline spending available in the Card."),
            EvmTag("9f51", TagValueTypeEnum.BINARY, "DRDOL", "A data object in the Card that provides the Kernel with a list of data objects that must be passed to the Card in the data field of the RECOVER AC command"),
            EvmTag("9f53", TagValueTypeEnum.BINARY, "Transaction Category Code", ""),
            EvmTag("9f54", TagValueTypeEnum.BINARY, "DS ODS Card", ""),
            EvmTag("9f55", TagValueTypeEnum.BINARY, "Mobile Support Indicator", ""),
            EvmTag("9f5b", TagValueTypeEnum.BINARY, "DSDOL", ""),
            EvmTag("9f5c", TagValueTypeEnum.BINARY, "DS Requested Operator ID", ""),
            EvmTag("9f5d", TagValueTypeEnum.BINARY, "Application Capabilities Information", "Lists a number of card features beyond regular payment"),
            EvmTag("9f5e", TagValueTypeEnum.BINARY, "Data Storage Identifier", "Constructed as follows: Application PAN (without any 'F' padding) || Application PAN Sequence Number (+ zero padding)"),
            EvmTag("9f5f", TagValueTypeEnum.BINARY, "DS Slot Availability", ""),
            EvmTag("9f60", TagValueTypeEnum.BINARY, "CVC3 (Track1)", "The CVC3 (Track1) is a 2-byte cryptogram returned by the Card in the response to the COMPUTE CRYPTOGRAPHIC CHECKSUM command."),
            EvmTag("9f61", TagValueTypeEnum.BINARY, "CVC3 (Track2)", "The CVC3 (Track2) is a 2-byte cryptogram returned by the Card in the response to the COMPUTE CRYPTOGRAPHIC CHECKSUM command."),
            EvmTag("9f62", TagValueTypeEnum.BINARY, "Track 1 bit map for CVC3", "PCVC3(Track1) indicates to the Kernel the positions in the discretionary data field of the Track 1 Data where the CVC3 (Track1) digits must be copied"),
            EvmTag("9f63", TagValueTypeEnum.BINARY, "Track 1 bit map for UN and ATC", "PUNATC(Track1) indicates to the Kernel the positions in the discretionary data field of Track 1 Data where the Unpredictable Number (Numeric) digits and Application Transaction Counter digits have to be copied."),
            EvmTag("9f64", TagValueTypeEnum.BINARY, "Track 1 number of ATC digits", "The value of NATC(Track1) represents the number of digits of the Application Transaction Counter to be included in the discretionary data field of Track 1 Data"),
            EvmTag("9f65", TagValueTypeEnum.BINARY, "Track 2 bit map for CVC3", "PCVC3(Track2) indicates to the Kernel the positions in the discretionary data field of the Track 2 Data where the CVC3 (Track2) digits must be copied"),
            EvmTag("9f67", TagValueTypeEnum.BINARY, "Track 2 number of ATC digits", "The value of NATC(Track2) represents the number of digits of the Application Transaction Counter to be included in the discretionary data field of Track 2 Data"),
            EvmTag("9f69", TagValueTypeEnum.BINARY, "UDOL", ""),
            EvmTag("9f6a", TagValueTypeEnum.BINARY, "Unpredictable Number (Numeric)", ""),
            EvmTag("9f6d", TagValueTypeEnum.BINARY, "Mag-stripe Application Version Number (Reader)", ""),
            EvmTag("9f6f", TagValueTypeEnum.BINARY, "DS Slot Management Control", ""),
            EvmTag("9f70", TagValueTypeEnum.BINARY, "Protected Data Envelope 1", ""),
            EvmTag("9f71", TagValueTypeEnum.BINARY, "Protected Data Envelope 2", ""),
            EvmTag("9f72", TagValueTypeEnum.BINARY, "Protected Data Envelope 3", ""),
            EvmTag("9f73", TagValueTypeEnum.BINARY, "Protected Data Envelope 4", ""),
            EvmTag("9f74", TagValueTypeEnum.BINARY, "Protected Data Envelope 5", ""),
            EvmTag("9f75", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 1", ""),
            EvmTag("9f76", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 2", ""),
            EvmTag("9f77", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 3", ""),
            EvmTag("9f78", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 4", ""),
            EvmTag("9f79", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 5", ""),
            EvmTag("9f7c", TagValueTypeEnum.BINARY, "Merchant Custom Data", ""),
            EvmTag("9f7d", TagValueTypeEnum.BINARY, "DS Summary 1", ""),
            EvmTag("9f7f", TagValueTypeEnum.BINARY, "DS Unpredictable Number", ""),
            EvmTag("df4b", TagValueTypeEnum.BINARY, "POS Cardholder Interaction Information", ""),
            EvmTag("df61", TagValueTypeEnum.BINARY, "DS Digest H", ""),
            EvmTag("df62", TagValueTypeEnum.BINARY, "DS ODS Info", ""),
            EvmTag("df63", TagValueTypeEnum.BINARY, "DS ODS Term", ""),
            EvmTag("df8104", TagValueTypeEnum.BINARY, "Balance Read Before Gen AC", ""),
            EvmTag("df8105", TagValueTypeEnum.BINARY, "Balance Read After Gen AC", ""),
            EvmTag("df8106", TagValueTypeEnum.BINARY, "Data Needed", ""),
            EvmTag("df8107", TagValueTypeEnum.BINARY, "CDOL1 Related Data", ""),
            EvmTag("df8108", TagValueTypeEnum.BINARY, "DS AC Type", ""),
            EvmTag("df8109", TagValueTypeEnum.BINARY, "DS Input (Term)", ""),
            EvmTag("df810a", TagValueTypeEnum.BINARY, "DS ODS Info For Reader", ""),
            EvmTag("df810b", TagValueTypeEnum.BINARY, "DS Summary Status", ""),
            EvmTag("df810c", TagValueTypeEnum.BINARY, "Kernel ID", ""),
            EvmTag("df810d", TagValueTypeEnum.BINARY, "DSVN Term", ""),
            EvmTag("df810e", TagValueTypeEnum.BINARY, "Post-Gen AC Put Data Status", ""),
            EvmTag("df810f", TagValueTypeEnum.BINARY, "Pre-Gen AC Put Data Status", ""),
            EvmTag("df8110", TagValueTypeEnum.BINARY, "Proceed To First Write Flag", ""),
            EvmTag("df8111", TagValueTypeEnum.BINARY, "PDOL Related Data", ""),
            EvmTag("df8112", TagValueTypeEnum.BINARY, "Tags To Read", ""),
            EvmTag("df8113", TagValueTypeEnum.BINARY, "DRDOL Related Data", ""),
            EvmTag("df8114", TagValueTypeEnum.BINARY, "Reference Control Parameter", ""),
            EvmTag("df8115", TagValueTypeEnum.BINARY, "Error Indication", ""),
            EvmTag("df8116", TagValueTypeEnum.BINARY, "User Interface Request Data", ""),
            EvmTag("df8117", TagValueTypeEnum.BINARY, "Card Data Input Capability", ""),
            EvmTag("df8118", TagValueTypeEnum.BINARY, "CVM Capability - CVM Required", ""),
            EvmTag("df8119", TagValueTypeEnum.BINARY, "CVM Capability - No CVM Required", ""),
            EvmTag("df811a", TagValueTypeEnum.BINARY, "Default UDOL", ""),
            EvmTag("df811b", TagValueTypeEnum.BINARY, "Kernel Configuration", ""),
            EvmTag("df811c", TagValueTypeEnum.BINARY, "Max Lifetime of Torn Transaction Log Record", ""),
            EvmTag("df811d", TagValueTypeEnum.BINARY, "Max Number of Torn Transaction Log Records", ""),
            EvmTag("df811e", TagValueTypeEnum.BINARY, "Mag-stripe CVM Capability – CVM Required", ""),
            EvmTag("df811f", TagValueTypeEnum.BINARY, "Security Capability", ""),
            EvmTag("df8120", TagValueTypeEnum.BINARY, "Terminal Action Code – Default", ""),
            EvmTag("df8121", TagValueTypeEnum.BINARY, "Terminal Action Code – Denial", ""),
            EvmTag("df8122", TagValueTypeEnum.BINARY, "Terminal Action Code – Online", ""),
            EvmTag("df8123", TagValueTypeEnum.BINARY, "Reader Contactless Floor Limit", ""),
            EvmTag("df8124", TagValueTypeEnum.BINARY, "Reader Contactless Transaction Limit (No On-device CVM)", ""),
            EvmTag("df8125", TagValueTypeEnum.BINARY, "Reader Contactless Transaction Limit (On-device CVM)", ""),
            EvmTag("df8126", TagValueTypeEnum.BINARY, "Reader CVM Required Limit", ""),
            EvmTag("df8127", TagValueTypeEnum.BINARY, "TIME_OUT_VALUE", ""),
            EvmTag("df8128", TagValueTypeEnum.BINARY, "IDS Status", ""),
            EvmTag("df8129", TagValueTypeEnum.BINARY, "Outcome Parameter Set", ""),
            EvmTag("df812a", TagValueTypeEnum.BINARY, "DD Card (Track1)", ""),
            EvmTag("df812b", TagValueTypeEnum.BINARY, "DD Card (Track2)", ""),
            EvmTag("df812c", TagValueTypeEnum.BINARY, "Mag-stripe CVM Capability – No CVM Required", ""),
            EvmTag("df812d", TagValueTypeEnum.BINARY, "Message Hold Time", ""),
            EvmTag("ff8101", TagValueTypeEnum.BINARY, "Torn Record", ""),
            EvmTag("ff8102", TagValueTypeEnum.BINARY, "Tags To Write Before Gen AC", ""),
            EvmTag("ff8103", TagValueTypeEnum.BINARY, "Tags To Write After Gen AC", ""),
            EvmTag("ff8104", TagValueTypeEnum.BINARY, "Data To Send", ""),
            EvmTag("ff8105", TagValueTypeEnum.BINARY, "Data Record", ""),
            EvmTag("ff8106", TagValueTypeEnum.BINARY, "Discretionary Data", "")
    )

    /**
     * If the tag is not found, this method returns the "[UNHANDLED TAG]" containing 'tagBytes'
     */
    fun find(tagBytes: ByteArray): EvmTag {
        val tagString = bytesToStringNoSpace(tagBytes)
        var tag : EvmTag? = tags.find { it.tagIdString.equals(tagString, ignoreCase = true)}
        if (tag == null) {
            tag = EvmTag(tagString, TagValueTypeEnum.BINARY, "[UNKNOWN TAG]", "")
        }
        return tag
    }
}