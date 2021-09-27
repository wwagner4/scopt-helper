package clitest
import scopt.OParser

import java.io.File

object Main {

  case class Config(
                     foo: Int = -1,
                     out: File = new File("."),
                     xyz: Boolean = false,
                     libName: String = "",
                     maxCount: Int = -1,
                     verbose: Boolean = false,
                     debug: Boolean = false,
                     mode: String = "",
                     files: Seq[File] = Seq(),
                     keepalive: Boolean = false,
                     jars: Seq[File] = Seq(),
                     kwargs: Map[String, String] = Map())

  val builder = OParser.builder[Config]
  val parser1 = {
    import builder.*
    OParser.sequence(
      programName("cli-test"),
      head("cli-test", "0.1"),
      // option -f, --foo
      opt[Int]('f', "foo")
        .action((x, c) => c.copy(foo = x))
        .text("foo is an integer property"),
      // more options here...
    )
  }

  def main(args: Array[String]): Unit = {
    // OParser.parse returns Option[Config]
    OParser.parse(parser1, args, Config()) match {
      case Some(config) =>
        println(config)
      case _ =>
      // arguments are bad, error message will have been displayed
    }
  }

}
