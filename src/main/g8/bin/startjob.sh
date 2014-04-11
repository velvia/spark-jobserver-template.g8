#!/bin/bash
# Bash script to start a job asynchronously
# NOTE: This does not start the job in a given context, you may want to add ?context=<contextName>
# Please set the JOBSERVER_HOSTNAME variable first

curl --data-binary @../sample-job.conf \
\${JOBSERVER_HOSTNAME}:8090/jobs?appName=$name;format="camel"$&classPath=$organization$.$jobClassName$
