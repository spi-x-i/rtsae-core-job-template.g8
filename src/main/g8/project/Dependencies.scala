import sbt._

object Dependencies {

  object flink {
    lazy val version   = "$flink_version$"
    lazy val namespace = "org.apache.flink"
    lazy val scala     = namespace %% "flink-scala" % version % Provided
    lazy val streaming = namespace %% "flink-streaming-scala" % version % Provided
    lazy val kafka010  = namespace %% "flink-connector-kafka-0.10" % version
    lazy val testUtils = namespace %% "flink-test-utils" % version

    lazy val core = Seq(scala, streaming, kafka010)
  }

  object avro {
    lazy val avro   = "org.apache.avro"     % "avro"         % "1.8.1"
    lazy val avro4s = "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.1"
  }

  object scalatest {
    lazy val version   = "3.0.0"
    lazy val namespace = "org.scalatest"
    lazy val core      = namespace %% "scalatest" % version
  }

  object junit {
    lazy val version   = "4.12"
    lazy val namespace = "junit"
    lazy val junit     = namespace % "junit" % version
  }

  object junitInterface {
    lazy val version        = "0.11"
    lazy val namespace      = "com.novocode"
    lazy val junitInterface = namespace % "junit-interface" % version
  }

  object logging {
    lazy val `scala-logging` = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  }

  lazy val asm = "asm" % "asm" % "3.3.1" % Test //import to use ClosureCleaner in test
}
