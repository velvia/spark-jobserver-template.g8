#!/bin/bash
# Bash script to upload the jar to the job server
# Please set the JOBSERVER_HOSTNAME variable first

curl --data-binary @../target/$name;format="camel"$.jar \
\${JOBSERVER_HOSTNAME}:8090/jars/$name;format="camel"$
