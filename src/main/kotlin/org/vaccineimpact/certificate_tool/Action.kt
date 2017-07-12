package org.vaccineimpact.certificate_tool

abstract class Action(val shortName: String)
{
    abstract fun run(args: List<String>)

    protected fun getArg(pos: Int, args: List<String>): String?
    {
        return args.drop(pos).firstOrNull()
    }

    companion object
    {
        val all = listOf(
            GenerateSelfSignedCertificate(),
            GenerateKeyPair()
        )
    }
}

fun String.toAction() = Action.all.singleOrNull { it.shortName == this }
        ?: throw UnknownOrMissingAction()