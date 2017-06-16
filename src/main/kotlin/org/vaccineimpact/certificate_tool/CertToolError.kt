package org.vaccineimpact.certificate_tool

open class CertToolError(message: String) : Exception(message)

class UnknownOrMissingAction : CertToolError(
        "Please provide an action from: " + Action.all.map { it.shortName }.joinToString()
)