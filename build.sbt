name := "zio"

description := "Zio Examples"

//scalaVersion := "2.11.1"
val circeVersion = "0.14.3"

libraryDependencies ++= Seq(
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "dev.zio" %% "zio" % "2.0.6"
libraryDependencies += "dev.zio" %% "zio-http" % "3.0.0-RC1"
libraryDependencies += "dev.zio" %% "zio-streams" % "2.0.6"
//libraryDependencies += "io.d11" %% "zhttp" % "2.0.0-RC11"