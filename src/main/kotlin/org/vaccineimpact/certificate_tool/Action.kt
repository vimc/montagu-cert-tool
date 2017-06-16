package org.vaccineimpact.certificate_tool

abstract class Action(val shortName: String)
{
    abstract fun run(args: List<String>)

    protected fun getPasswordSettings(argPos: Int, args: List<String>, prompt: String): PasswordSettings
    {
        var password = getArg(argPos, args)
        val interactive = (password == null)

        if (interactive)
        {
            print(prompt)
            password = System.console().readPassword().joinToString("")
        }
        return PasswordSettings(interactive, password!!)
    }

    protected fun getArg(pos: Int, args: List<String>): String?
    {
        return args.drop(pos).firstOrNull()
    }

    companion object
    {
        val all = listOf(
            GenerateSelfSignedCertificate()
            //RetrieveRealCertificate
        )
    }
}

data class PasswordSettings(val interactive: Boolean, val password: String)

fun String.toAction() = Action.all.singleOrNull { it.shortName == this }
        ?: throw UnknownOrMissingAction()