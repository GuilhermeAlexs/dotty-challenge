name := "DottChallangeScala"

version := "1.0"

mainClass in assembly := Some("com.dotty.challenge.Main")
assemblyJarName in assembly := "dotty-challenge-v1.jar"

scalaVersion := "2.13.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % Test
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0"
