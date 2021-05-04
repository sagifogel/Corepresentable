name := "Corepresentable"

version := "0.1"

scalaVersion := "2.13.5"

lazy val kindProjector = "org.typelevel" % "kind-projector" % "0.11.3" cross CrossVersion.full

addCompilerPlugin(kindProjector)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.6.0"
)
