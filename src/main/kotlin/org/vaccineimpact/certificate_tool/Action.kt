package org.vaccineimpact.certificate_tool

enum class Actions(val shortName: String)
{
    GenerateSelfSignedCertificate("gen-self-signed"),
    ExportCertificateAsPEMFiles("export-as-pem"),
    //RetrieveRealCertificate
}