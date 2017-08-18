import sbt._
import Keys._
import Dependencies._

object Commons {

  val scalaVer = "$scala_version$"

  val settings: Seq[Def.Setting[_]] = Seq(
    scalaVersion := scalaVer,
    organization := "$organization$",
    resolvers ++= Seq(
      Opts.resolver.mavenLocalFile,
      "Radicalbit Repo" at "https://public-repo.radicalbit.io/maven/repository/internal/",
      "Radicalbit Snapshot" at "https://public-repo.radicalbit.io/maven/repository/snapshot/"
    ),
    libraryDependencies ++= flink.core ++ Operators.libs,
    parallelExecution in Test := false
  )
}
