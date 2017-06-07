package org.vaccineimpact.certificate_tool

import java.io.File

class ExportCertificateAsPEMFiles : Action("export-as-pem")
{
    override fun run(args: List<String>)
    {
        val usage = "Usage: cert-tool $shortName path_to_keystore output_dir [keystore_password]"

        val keystorePath = getArg(0, args) ?: throw CertToolError(usage)
        val outputPath = getArg(1, args) ?: throw CertToolError(usage)
        val settings = getPasswordSettings(2, args, "Enter keystore password: ")

        val home = File(System.getProperty("user.home"))

        convertKeystoreToPKCS12(keystorePath, settings, home)
        exportPKCS12ToSeparateFiles(keystorePath, outputPath, settings, home)
    }

    private fun convertKeystoreToPKCS12(keystorePath: String, settings: PasswordSettings, home: File)
    {
        ("keytool -importkeystore " +
                "-srckeystore $keystorePath " +
                "-destkeystore $keystorePath.p12 " +
                "-deststoretype PKCS12 " +
                "-srcalias api " +
                "-srcstorepass ${settings.password} " +
                "-deststorepass ${settings.password} " +
                "-noprompt").runCommand(home)
    }

    private fun exportPKCS12ToSeparateFiles(keystorePath: String, outputPath: String, settings: PasswordSettings, home: File)
    {
        "openssl pkcs12 -in $keystorePath.p12 -nokeys -out /$outputPath/certificate.pem -passin pass:${settings.password}"
                .runCommand(home)
        "openssl pkcs12 -in $keystorePath.p12 -nocerts -nodes -out /$outputPath/ssl_key.pem -passin pass:${settings.password}"
                .runCommand(home)
    }
}