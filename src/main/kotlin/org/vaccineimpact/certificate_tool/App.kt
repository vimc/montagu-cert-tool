package org.vaccineimpact.certificate_tool

fun main(args: Array<String>)
{
    try
    {
        App().run(args)
    }
    catch (e: CertToolError)
    {
        println(e.message)
    }
}

class App
{
    fun run(args: Array<String>)
    {
        val action = args.firstOrNull()?.toAction()
                ?: throw UnknownOrMissingAction()

        action.run(args.drop(1))
    }
}