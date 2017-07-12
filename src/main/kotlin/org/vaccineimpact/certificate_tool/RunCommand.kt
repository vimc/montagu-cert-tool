package org.vaccineimpact.certificate_tool

import java.io.File
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDirectory: File? = null)
{
    this.split(" ").runCommand(workingDirectory)
}

fun List<String>.runCommand(workingDirectory: File? = null)
{
    val builder = ProcessBuilder(*this.toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
    if (workingDirectory != null)
    {
        builder.directory(workingDirectory)
    }
    val process = builder.start()
    process.waitFor(5, TimeUnit.MINUTES)
    if (process.exitValue() != 0)
    {
        throw CertToolError("Process terminated with exit code ${process.exitValue()}")
    }
}