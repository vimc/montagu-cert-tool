# Montagu Certificate Tool
This is a Kotlin application for performing various tasks relating to 
certificate management.

You can run it without cloning the repository like so:

    docker run docker.montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master

## Generate self-signed certificate
To generate a self-signed certificate with OpenSSL run

    mkdir workspace
    docker run --rm \
        -v $PWD/workspace:/workspace \
        docker.montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master \
        gen-self-signed /workspace

This will output two files, `certificate.pem` and `ssl_key.pem`, to the local 
`workspace` folder. Be careful with these unencrypted files - any spare copies 
should be deleted after they have been copied into the docker container that is 
running the webapp.

    rm -r workspace

The `-v` command makes the `workspace` folder available to the cert-tool's 
container at `/workspace`. 

## Generate an RSA keypair
To generate an RSA keypair using OpenSSL run

    mkdir workspace
    docker run --rm \
        -v $PWD/workspace:/workspace \
        docker.montagu.dide.ic.ac.uk:5000/montagu-cert-tool:master \
        gen-keypair /workspace

This will output two files, `private_key.der` and `public_key.der`, to the
local `workspace` folder. Be careful with the unencrypted privat key - any
spare copies should be deleted. e.g.

    rm -r workspace
