import sbt.Keys._

addCommandAlias("verify", ";clean;coverage;test")

lazy val `$name$` = (project in file("."))
  .settings(Commons.settings: _*)
  .settings(
    name := "$name$",
    parallelExecution in Test := false,
    onLoad in Global := (Command.process("scalafmt", _: State)) compose (onLoad in Global).value,
    // make run command include the provided dependencies
    run in Compile := Defaults
      .runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))
      .evaluated,
    // exclude Scala library from assembly
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
    assemblyMergeStrategy in assembly := {
      {
        case PathList("org", "apache", "flink", xs @ _ *) => MergeStrategy.last
        case PathList("META-INF", xs @ _*) => MergeStrategy.discard
        case x =>
          val oldStrategy = (assemblyMergeStrategy in assembly).value
          oldStrategy(x)
      }
    },
    (stringType in avroConfig) := "String",
    wartremoverWarnings ++= Warts.unsafe,
    mainClass in assembly := Some("$organization$.Startup")
  )
