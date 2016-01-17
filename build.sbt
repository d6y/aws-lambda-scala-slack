scalaVersion := "2.11.7"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core"   % "1.1.0" % Provided,
  "com.amazonaws" % "aws-lambda-java-events" % "1.1.0" % Provided
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % "0.2.1",
  "io.circe" %% "circe-generic" % "0.2.1",
  "io.circe" %% "circe-parse" % "0.2.1"
)

libraryDependencies += "org.spire-math" %% "cats" % "0.3.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test


// https://github.com/sbt/sbt-assembly for merge strategy
// All the JAR files used by aws-lambda have META-INF files that we don't care about:
// Q: what happens if we leave them in?
// A: assembly requires files with same name to have same content, and error otherwise
// TODO: confirm above
mergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case _                             => MergeStrategy.first
   }
