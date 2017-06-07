package org.vaccineimpact.certificate_tool

open class UserError(message: String) : Exception(message)

class UnknownOrMissingAction : UserError(
        "Please provide an action from " + Action.all.map { it.shortName }.joinToString()
)