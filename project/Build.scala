import sbt._
import Keys._
import com.typesafe.sbt.osgi.SbtOsgi._
import com.typesafe.tools.mima.core._
import com.typesafe.tools.mima.plugin.MimaKeys._
import pl.project13.scala.sbt.SbtJmh._
import scodec.build.ScodecBuildSettings.autoImport._

object ScodecBuild extends Build {

  lazy val commonSettings = Seq(
    scodecModule := "scodec-bits",
    rootPackage := "scodec.bits",
    contributors ++= Seq(Contributor("mpilquist", "Michael Pilquist"), Contributor("pchiusano", "Paul Chiusano"))
  )

  lazy val root: Project = project.in(file(".")).aggregate(core, benchmark).settings(commonSettings: _*).settings(
    publishArtifact := false
  )

  lazy val core: Project = project.in(file("core")).
    settings(commonSettings: _*).
    settings(scodecPrimaryModule: _*).
    settings(
      scodecModule := "scodec-bits",
      rootPackage := "scodec.bits",
      libraryDependencies ++= Seq(
        "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
        "org.scalatest" %% "scalatest" % "2.1.3" % "test",
        "org.scalacheck" %% "scalacheck" % "1.11.3" % "test",
        "com.google.guava" % "guava" % "16.0.1" % "test",
        "com.google.code.findbugs" % "jsr305" % "2.0.3" % "test" // required for guava
      ),
      OsgiKeys.privatePackage := Nil,
      OsgiKeys.exportPackage := Seq("scodec.bits.*;version=${Bundle-Version}"),
      OsgiKeys.importPackage := Seq(
        """scala.*;version="$<range;[==,=+)>"""",
        "*"
      ),
      binaryIssueFilters ++= Seq(
        "scodec.bits.ByteVector.buffer",
        "scodec.bits.ByteVector.bufferBy",
        "scodec.bits.ByteVector.unbuffer",
        "scodec.bits.ByteVector.getImpl",
        "scodec.bits.BitVector#Bytes.depthExceeds",
        "scodec.bits.BitVector#Append.depthExceeds",
        "scodec.bits.BitVector#Suspend.depthExceeds",
        "scodec.bits.BitVector#Drop.depthExceeds",
        "scodec.bits.BitVector.depthExceeds",
        "scodec.bits.BitVector.depth",
        "scodec.bits.BitVector.unchunk",
        "scodec.bits.BitVector.align",
        "scodec.bits.BitVector.scodec$bits$BitVector$$reduceBalanced",
        "scodec.bits.BitVector#Drop.sizeIsAtMost",
        "scodec.bits.BitVector#Drop.sizeIsAtLeast",
        "scodec.bits.BitVector#Drop.sizeUpperBound",
        "scodec.bits.BitVector#Drop.sizeLowerBound",
        "scodec.bits.BitVector#Drop.scodec$bits$BitVector$_setter_$sizeUpperBound_=",
        "scodec.bits.BitVector#Drop.scodec$bits$BitVector$_setter_$sizeLowerBound_=",
        "scodec.bits.BitVector#Append.sizeIsAtMost",
        "scodec.bits.BitVector#Append.sizeIsAtLeast",
        "scodec.bits.BitVector#Append.sizeUpperBound",
        "scodec.bits.BitVector#Append.sizeLowerBound",
        "scodec.bits.BitVector#Append.scodec$bits$BitVector$_setter_$sizeUpperBound_=",
        "scodec.bits.BitVector#Append.scodec$bits$BitVector$_setter_$sizeLowerBound_=",
        "scodec.bits.BitVector#Chunks.sizeIsAtMost",
        "scodec.bits.BitVector#Chunks.sizeIsAtLeast",
        "scodec.bits.BitVector#Chunks.sizeUpperBound",
        "scodec.bits.BitVector#Chunks.sizeLowerBound",
        "scodec.bits.BitVector#Chunks.scodec$bits$BitVector$_setter_$sizeUpperBound_=",
        "scodec.bits.BitVector#Chunks.scodec$bits$BitVector$_setter_$sizeLowerBound_=",
        "scodec.bits.BitVector.sizeIsAtMost",
        "scodec.bits.BitVector.sizeIsAtLeast",
        "scodec.bits.BitVector.sizeUpperBound",
        "scodec.bits.BitVector.take",
        "scodec.bits.BitVector.sizeLowerBound",
        "scodec.bits.BitVector.drop",
        "scodec.bits.BitVector.scodec$bits$BitVector$_setter_$sizeUpperBound_=",
        "scodec.bits.BitVector.sizeLessThan",
        "scodec.bits.BitVector.scodec$bits$BitVector$_setter_$sizeLowerBound_=",
        "scodec.bits.BitVector.sliceToInt",
        "scodec.bits.BitVector.acquireThen",
        "scodec.bits.BitVector.sliceToLong",
        "scodec.bits.BitVector.sliceToLong$default$4",
        "scodec.bits.BitVector.sliceToInt$default$4",
        "scodec.bits.BitVector.sliceToLong$default$3",
        "scodec.bits.BitVector.consumeThen",
        "scodec.bits.BitVector.sliceToInt$default$3",
        "scodec.bits.BitVector.getByte",
        "scodec.bits.BitVector#Suspend.sizeIsAtMost",
        "scodec.bits.BitVector#Suspend.sizeIsAtLeast",
        "scodec.bits.BitVector#Suspend.sizeUpperBound",
        "scodec.bits.BitVector#Suspend.sizeLowerBound",
        "scodec.bits.BitVector#Suspend.scodec$bits$BitVector$_setter_$sizeUpperBound_=",
        "scodec.bits.BitVector#Suspend.scodec$bits$BitVector$_setter_$sizeLowerBound_=",
        "scodec.bits.BitVector#Bytes.sizeIsAtMost",
        "scodec.bits.BitVector#Bytes.sizeIsAtLeast",
        "scodec.bits.BitVector#Bytes.sizeUpperBound",
        "scodec.bits.BitVector#Bytes.sizeLowerBound",
        "scodec.bits.BitVector#Bytes.scodec$bits$BitVector$_setter_$sizeUpperBound_=",
        "scodec.bits.BitVector#Bytes.scodec$bits$BitVector$_setter_$sizeLowerBound_=",
        "scodec.bits.BitVector.toShort",
        "scodec.bits.BitVector.toShort$default$1",
        "scodec.bits.BitVector.toShort$default$2",
        "scodec.bits.BitVector.sliceToShort",
        "scodec.bits.BitVector.sliceToShort$default$3",
        "scodec.bits.BitVector.sliceToShort$default$4",
        "scodec.bits.ByteVector.toShort",
        "scodec.bits.ByteVector.toShort$default$1",
        "scodec.bits.ByteVector.toShort$default$2",
        "scodec.bits.ByteVector.toByte",
        "scodec.bits.ByteVector.toByte$default$1",
        "scodec.bits.BitVector.toByte",
        "scodec.bits.BitVector.toByte$default$1",
        "scodec.bits.BitVector.sliceToByte",
        "scodec.bits.BitVector.sliceToByte$default$3",
        "scodec.bits.BitVector.invertReverseByteOrder"
      ).map { method => ProblemFilters.exclude[MissingMethodProblem](method) },
      binaryIssueFilters +=
        // result type changed, but this method was private
        ProblemFilters.exclude[IncompatibleResultTypeProblem]("scodec.bits.BitVector#Append.sizeLowerBound")
  )

  lazy val benchmark: Project = project.in(file("benchmark")).dependsOn(core).settings(jmhSettings: _*).
    settings(commonSettings: _*).
    settings(
      publishArtifact := false,
      libraryDependencies ++=
        Seq("com.typesafe.akka" %% "akka-actor" % "2.3.5")
    )
}
