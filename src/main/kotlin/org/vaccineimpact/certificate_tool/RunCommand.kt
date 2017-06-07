package org.vaccineimpact.certificate_tool

import java.io.File
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDirectory: File)
{
    this.split(" ").runCommand(workingDirectory)
}

fun List<String>.runCommand(workingDirectory: File)
{
    val process = ProcessBuilder(*this.toTypedArray())
            .directory(workingDirectory)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
    process.waitFor(5, TimeUnit.MINUTES)
    if (process.exitValue() != 0)
    {
        throw CertToolError("Process terminated with exit code ${process.exitValue()}")
    }
}