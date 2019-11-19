package com.exceptionteam17.simplecardreader.model

data class EmvCardService(private val data: String) {

    var interchangeAndTechnology: String? = null

    var authorizationProcessing: String? = null

    var allowedServicesAndPinRequirements: String? = null

    init {
        if (data.length == 3) {
            interchangeAndTechnology = INTERCHANGE_AND_TECHNOLOGY[data.substring(0,1).toInt()]
            authorizationProcessing = AUTHORISATION_PROCESSING[data.substring(1,2).toInt()]
            allowedServicesAndPinRequirements = ALLOWED_SERVICES_AND_PIN_REQUIREMENTS[data.substring(2,3).toInt()]
        }
    }

    override fun toString(): String {
        return "EmvCardService(interchangeAndTechnology=$interchangeAndTechnology, authorizationProcessing=$authorizationProcessing, allowedServicesAndPinRequirements=$allowedServicesAndPinRequirements)"
    }

    companion object {
        //service code 1
        private val INTERCHANGE_AND_TECHNOLOGY = mapOf(
                1 to "Interchange: International interchange; Technology: None",
                2 to "Interchange: International interchange; Technology: Integrated circuit card",
                5 to "Interchange: National interchange; Technology: None",
                6 to "Interchange: National interchange; Technology: Integrated circuit card",
                7 to "Interchange: Private; Technology: None"
        )

        //service code 2
        private val AUTHORISATION_PROCESSING= mapOf(
                0 to "Authorisation: Normal",
                2 to "Authorisation: By issuer",
                4 to "Authorisation: By issuer unless explicit bilateral agreement applies"
        )

        //service code 3
        private val ALLOWED_SERVICES_AND_PIN_REQUIREMENTS = mapOf(
                0 to "Allowed services: No restrictions; Pin requirements: PIN required",
                1 to "Allowed services: No restrictions; Pin requirements: None",
                2 to "Allowed services: Goods and services only; Pin requirements: None",
                3 to "Allowed services: ATM only; Pin requirements: PIN required",
                4 to "Allowed services: Cash only; Pin requirements: None",
                5 to "Allowed services: Goods and services only; Pin requirements: PIN required",
                6 to "Allowed services: No restrictions; Pin requirements: Prompt for PIN if PED present",
                7 to "Allowed services: Goods and services only; Pin requirements: Prompt for PIN if PED present"
        )
    }
}