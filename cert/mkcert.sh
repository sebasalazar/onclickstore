#!/bin/bash

JAVA_HOME=/srv/appserv/java-8-oracle
COMERCIO=597020000999


echo "Elimino Archivos Antiguos"
rm -rf *.crt *.jks

echo "Generación de KeyStore"
${JAVA_HOME}/bin/keytool -genkeypair -v -alias importkey -keystore importkey.jks -validity 3650 -keysize 1024 -keyalg RSA -storepass importkey -dname "CN=${COMERCIO}, OU=Desarrollo, O=EXPERTI, L=Santiago, S=Chile, C=CL" -keypass importkey

echo "Generación de PEM"
${JAVA_HOME}/bin/keytool -exportcert -rfc -keystore importkey.jks  -storepass importkey -alias importkey  > ${COMERCIO}.crt

echo "Creación de client.jks"
${JAVA_HOME}/bin/keytool -importkeystore -v -srckeystore importkey.jks -destkeystore client.jks -deststorepass importkey -srcstorepass importkey

echo "Done"
exit 0

