/** Definition */
name := "scoobi"

organization := "com.nicta"

version := "0.6.2-cdh4-fs-j"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "javassist" % "javassist" % "3.12.1.GA",
  "org.apache.avro" % "avro-mapred" % "1.7.4" classifier "hadoop2",
  "org.apache.avro" % "avro" % "1.7.4",
  "org.apache.hadoop" % "hadoop-client" % "2.0.0-mr1-cdh4.0.1",
  "org.apache.hadoop" % "hadoop-core" % "2.0.0-mr1-cdh4.0.1",
  "com.thoughtworks.xstream" % "xstream" % "1.4.3" intransitive(),
  "org.scalaz" %% "scalaz-core" % "7.0.5",
  "org.specs2" %% "specs2" % "2.0" % "optional",
  "com.chuusai" %% "shapeless" % "1.2.4",
  "org.specs2" % "classycle" % "1.4.1"% "test",
  "org.scalacheck" %% "scalacheck" % "1.11.1" % "test",
  "org.scala-tools.testing" % "test-interface" % "0.5" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
  "org.mockito" % "mockito-all" % "1.9.0" % "optional",
  "org.pegdown" % "pegdown" % "1.0.2" % "test",
  "junit" % "junit" % "4.7" % "test",
  "org.apache.commons" % "commons-math" % "2.2" % "test",
  "org.apache.commons" % "commons-compress" % "1.0" % "test"
)

(sourceGenerators in Compile) <+= (sourceManaged in Compile) map GenWireFormat.gen

resolvers ++= Seq("nicta's avro" at "http://nicta.github.com/scoobi/releases",
                  "cloudera" at "https://repository.cloudera.com/content/repositories/releases",
                  "sonatype" at "http://oss.sonatype.org/content/repositories/snapshots")

/** Compilation */
scalacOptions ++= Seq("-deprecation", "-unchecked")

/** Testing */
testOptions := Seq(Tests.Filter(s => s.endsWith("Spec") ||
                                     Seq("Index", "All", "UserGuide", "ReadMe").exists(s.contains)))

fork in Test := true

/** Publishing */
publishTo <<= version { v: String =>
  Some("thirdparty-releases" at "http://nexus.prod.foursquare.com/nexus/content/repositories/thirdparty/")
}

credentials ++= {
  val sonatype = ("Sonatype Nexus Repository Manager", "nexus.prod.foursquare.com")
    def loadMavenCredentials(file: java.io.File) : Seq[Credentials] = {
      xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
          val host = (s \ "id").text
          val realm = if (host == sonatype._2) sonatype._1 else "Unknown"
          Credentials(realm, host, (s \ "username").text, (s \ "password").text)
          })
    }
  val ivyCredentials   = Path.userHome / ".ivy2" / ".credentials"
    val mavenCredentials = Path.userHome / ".m2"   / "settings.xml"
    (ivyCredentials.asFile, mavenCredentials.asFile) match {
      case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
        case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
        case _ => Nil
    }
}

credentials ++= {
  val sonatype = ("Sonatype Nexus Repository Manager", "nexus.prod.foursquare.com")
    def loadMavenCredentials(file: java.io.File) : Seq[Credentials] = {
      xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
          val host = (s \ "id").text
          val realm = if (host == sonatype._2) sonatype._1 else "Unknown"
          Credentials(realm, host, (s \ "username").text, (s \ "password").text)
          })
    }
  val ivyCredentials   = Path.userHome / ".ivy2" / ".credentials"
    val mavenCredentials = Path.userHome / ".m2"   / "settings.xml"
    (ivyCredentials.asFile, mavenCredentials.asFile) match {
      case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
        case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
        case _ => Nil
    }
}

credentials += Credentials(Path.userHome / ".ivy_credentials")

publishMavenStyle := true

publishArtifact in Test := false

publishArtifact in packageDoc := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>http://nicta.github.com/scoobi</url>
  <licenses>
    <license>
      <name>Apache 2.0</name>
      <url>http://www.opensource.org/licenses/Apache-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>http://github.com/NICTA/scoobi</url>
    <connection>scm:http:http://NICTA@github.com/NICTA/scoobi.git</connection>
  </scm>
  <developers>
    <developer>
      <id>blever</id>
      <name>Ben Lever</name>
      <url>http://github.com/blever</url>
    </developer>
     <developer>
      <id>espringe</id>
      <name>Eric Springer</name>
      <url>http://github.com/espringe</url>
    </developer>
    <developer>
      <id>etorreborre</id>
      <name>Eric Torreborre</name>
      <url>http://etorreborre.blogspot.com/</url>
    </developer>
  </developers>
)


