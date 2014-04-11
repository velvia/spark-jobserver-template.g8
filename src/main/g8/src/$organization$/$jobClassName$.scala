package $organization$

import org.apache.spark.SparkContext
import spark.jobserver.{SparkJob}
import com.typesafe.config.Config

/**
 * An example of a Spark job that can be submitted to the job server.
 * You can
 * 2) Start the Spark shell/console for interactive debugging
 *
 *      sbt> console
 *      scala> import spark._
 *      scala> val sc = new SparkContext("local[4]", "MyDemo")
 *      scala> import com.typesafe.config.ConfigFactory
 *      scala> val config = ConfigFactory.parseString("""input.string = ["cassandra"]""")
 *      scala> $organization$.$jobClassName$.runJob(sc, config)
 *
 * 3) Run "sbt assembly" to generate a jar and submit it to a job server.
 *
 */
object $jobClassName$ extends SparkJob {
  /**
   * This is the method that needs to be filled in with the Spark transformations to run the job.
   * @param sc the SparkContext
   * @return the value from the transform, typically the result of a collect() or take() method
   */
  def runJob(sc: SparkContext, config: Config): Any = {
    // Just do something dummy
    sc.parallelize(Seq(1,2,3,4)).collect()
  }
}
