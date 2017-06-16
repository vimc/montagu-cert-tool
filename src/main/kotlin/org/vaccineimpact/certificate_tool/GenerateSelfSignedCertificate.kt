package org.vaccineimpact.certificate_tool

import java.io.File

class GenerateSelfSignedCertificate : Action("gen-self-signed")
{
    override fun run(args: List<String>)
    {
        val usage = "Usage: cert-tool $shortName output_dir [keystore_password]"
        val outputPath = getArg(0, args) ?: throw CertToolError(usage)
        val settings = getPasswordSettings(1, args, "Enter new password for private key: ")
        val distinguishedName = "/C=GB/L=Location/O=Vaccine Impact Modelling Consortium/OU=Montagu/CN=montagu.vaccineimpact.org"
        val home = File(System.getProperty("user.home"))

        if (!outputPath.startsWith("/"))
        {
            throw CertToolError("outputPath must be an absolute path. It was $outputPath")
        }

        listOf(
                "openssl",
                "req", "-x509",
                "-newkey", "rsa:4096",
                "-sha256",
                "-subj", distinguishedName,
                "-days", "365",
                "-keyout", "$outputPath/ssl_key.pem",
                "-out", "$outputPath/certificate.pem",
                "-passout", "pass:${settings.password}"
        ).runCommand(home)

        println("\nWrote self-signed certificate to $outputPath")
    }
}