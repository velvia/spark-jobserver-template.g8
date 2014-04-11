import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object Dependencies {
  lazy val sparkDeps = Seq(
    "org.apache.spark" %% "spark-core" % "0.9.1" % "provided",
    "ooyala.cnd" %% "job-server" % "0.3.0.9" % "provided"
  )

  lazy val coreTestDeps = Seq(
    "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  )

  val repos = Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    "JCenter Bintray" at "http://jcenter.bintray.com/"
  )
}


object SparkJobBuild extends Build {
  import Dependencies._

  lazy val myProject = Project(
    id  = "$name;format="norm"$",
    base = file("."),
    settings = Defaults.defaultSettings ++ dirSettings ++ sparkAssemblySettings ++ Seq(
      scalaVersion   := "2.10.4",
      resolvers      ++= repos,
      jarName in assembly := "$name;format="camel"$.jar",
      ivyXML :=
        <dependencies>
          <exclude module="log4j"/>
          <exclude module="slf4j-log4j12"/>
          <exclude module="slf4j-simple"/>
        </dependencies>,
      libraryDependencies ++= sparkDeps ++ coreTestDeps
    ) ++ sbtRunSettings
  )

  lazy val dirSettings = Seq(
    unmanagedSourceDirectories in Compile <<= Seq(baseDirectory(_ / "src" )).join,
    unmanagedSourceDirectories in Test <<= Seq(baseDirectory(_ / "test" )).join
  )

  // Settings for building fat jars that work with Spark
  lazy val sparkAssemblySettings = assemblySettings ++ Seq(
    // Redefine just the run task to use Compile deps so spark will launch correctly
    run in Compile <<= Defaults.runTask(fullClasspath in Compile,
                                        mainClass in (Compile, run), runner in (Compile, run)),
    runMain in Compile <<= Defaults.runMainTask(fullClasspath in Compile,
                                                runner in (Compile, runMain)),
    assembleArtifact in packageScala := false   // scala-library causes problems for Spark
  )

  // Settings for "sbt run" local/dev testing
  lazy val sbtRunSettings = Seq(
    fork := true,
    fork in Test := false,    // Don't make the tests use the settings below !!
    parallelExecution in Test := false,
    javaOptions ++= Seq("-XX:MaxPermSize=1g", "-Xmx2g",
      "-Dconfig.file=config/dev.conf", "-Djava.net.preferIPv4Stack=true",
      // Below settings are for running Spark with Mesos
      "-Djava.library.path=/opt/local/lib", "-Dspark.home=/usr/lib/spark")
  )
}
