# Montagu Certificate Tool
This is a Kotlin application for performing various tasks relating to 
certificate management.

You can run it without cloning the repository like so:

    docker run montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master

## Generate self-signed certificate
To generate a self-signed certificate, stored in a Java Keystore, run

    docker run montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master gen-self-signed

This will prompt you for a password to encrypt the keystore with. Alternatively,
you can pass the password as an additional command line argument. In this latter
mode the keystore's encrypted contents are output to standard out, so you can
store it in a file like so:

    docker run montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master gen-self-signed my_password > some_keystore

## Extract unencrypted PEM files from keystore
If you already have a Java Keystore, but need to use the certificate with nginx
(as we do in the webapps) you can use the tool to extract the certificate and
the private key as unencrypted PEM files.

Be careful with these files - any spare copies should be deleted after they have
been copied into the docker container that is running the webapp.

Use it like so:

    mkdir workspace
    mv keystore workspace
    docker run montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master \
        -v $pwd/workspace:/workspace
        extract-as-pem /workspace/keystore /workspace
    # We now have workspace/certificate.pem and workspace/ssl_key.pem
    # Use these and then delete them
    rm -r workspace

The `-v` command makes the `workspace` folder available to the cert-tool's 
container at `/workspace`. This both allows us to pass in the keystore, and
provides a location for the cert-tool to write out the pem files.

Again, this will prompt for the keystore password interactively, but you can 
pass the password in as the last argument to run it non-interactively.
