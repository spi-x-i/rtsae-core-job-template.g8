import sbt._

object Dependencies {

  object flink {
    lazy val version   = "$flink_version$"
    lazy val namespace = "org.apache.flink"
    lazy val scala     = namespace %% "flink-scala" % version % Provided
    lazy val streaming = namespace %% "flink-streaming-scala" % version % Provided

    lazy val core = Seq(scala, streaming)
  }
}
