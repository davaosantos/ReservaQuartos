ThisBuild / scalaVersion := "2.13.14"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """reservaQuartos""",
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play" % "2.8.19",         // Versão do Play Framework
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "com.typesafe.play" %% "play-slick" % "5.1.0",      // Integração do Play com Slick
      "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",  // HikariCP para gerenciamento de conexões
      "com.h2database" % "h2" % "2.1.210"  // Remova % Test para uso normal
    )
  )

enablePlugins(PlayScala)