package org.vaccineimpact.certificate_tool

import java.io.File

class GenerateSelfSignedCertificate : Action("gen-self-signed")
{
    override fun run(args: List<String>)
    {
        val settings = getPasswordSettings(0, args, "Enter new keystore password: ")
        val distinguishedName = "CN=vaccineimpact.org, OU=Montagu, O=Vaccine Impact Modelling Consortium, L=London, C=GB"
        val home = File(System.getProperty("user.home"))

        listOf("keytool", "-genkeypair",
                "-dname", distinguishedName,
                "-keyalg", "RSA",
                "-alias", "api",
                "-keystore", "keystore",
                "-validity", "365",
                "-keysize", "2048",
                "-storepass", settings.password,
                "-keypass", settings.password
        ).runCommand(home)

        if (settings.interactive)
        {
            println("\nWrote self-signed certificate to $home/keystore.")
            println("Suggested next action:")
            println("sudo mv $home/keystore /etc/montagu/api/keystore")
        }
        else
        {
            "cat keystore".runCommand(home)
        }
    }
}