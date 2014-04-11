## Local development

2) Start the Spark shell/console for interactive debugging

     sbt> console
     scala> import spark._
     scala> val sc = new SparkContext("local[4]", "MyDemo")
     scala> import com.typesafe.config.ConfigFactory
     scala> val config = ConfigFactory.parseFile(new java.io.File("rookery-job-server/config/staging2-sv2.conf"))
     scala> $organization$.$jobClassName$.runJob(sc, config)

## Run job on Job Server
## IMPORTANT: Do this in the newly created project

1) Run "sbt assembly" to generate a jar and submit it to a job server.

(NOTE: see the bin/ directory for scripts that do the below for you)


2) To upload the jar:

    curl --data-binary @target/$name;format="camel"$.jar <jobserverHost>:8090/jars/$name;format="camel"$


3) To start the job:

    curl --data-binary @sample-job.conf <jobserverHost>:8090/jobs?appName=\
    $name;format="camel"$&classPath=$organization$.$jobClassName$

    All jobs default to async now and return a jobId.
    For a syncronous job, add '&sync=true' to the end of the url

    NOTE: This does not start the job in a given context, you may want to add ?context=<contextName>

    To create a context:

        curl -d "" '<jobserverHost>:8090/contexts/<contextName>?numCores=12&memPerNode=2g'


4) Query job status:

    curl <jobserverHost>:8090/jobs/<jobId>


5) Other useful routes

    Shows all jobs that are / have been run
        curl <jobserverHost>:8090/jobs

    Show all contexts made
        curl <jobserverHost>:8090/contexts

    Show all jars
        curl <jobserverHost>:8090/jars
