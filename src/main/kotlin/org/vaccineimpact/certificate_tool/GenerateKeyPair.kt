package org.vaccineimpact.certificate_tool

import java.io.File

class GenerateKeyPair : Action("gen-keypair")
{
    override fun run(args: List<String>)
    {
        val usage = "Usage: cert-tool $shortName output_dir"
        val outputPath = getArg(0, args) ?: throw CertToolError(usage)
        if (!outputPath.startsWith("/"))
        {
            throw CertToolError("outputPath must be an absolute path. It was $outputPath")
        }
        val home = File(System.getProperty("user.home"))

        // Generate a 2048-bit RSA private key (this does not go in the output folder)
        "openssl genrsa -out private_key.pem 2048".runCommand()

        // Convert private Key to PKCS#8 format (so Java can read it)
        "openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out $outputPath/private_key.der -nocrypt".runCommand()
        println("\nWrote private key to $outputPath/private_key.der")

        // Output public key portion in DER format (so Java can read it)
        "openssl rsa -in private_key.pem -pubout -outform DER -out $outputPath/public_key.der".runCommand()
        println("Wrote public key to $outputPath/public_key.der")

        // Output public key portion in PEM format (so Caddy can read it)
        "openssl rsa -in private_key.pem -pubout -outform PEM -out $outputPath/public_key.pem".runCommand()
        println("Wrote public PEM key to $outputPath/public_key.pem")
    }
}
