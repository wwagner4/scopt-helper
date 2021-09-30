package scopthelper

import scopt.OParser

object Main {

  sealed trait Mode

  case object Update extends Mode

  case class Insert(num: Int) extends Mode

  case class CliCfg(
                     foo: Int = -1,
                     libName: String = "",
                     mode: Mode = Update
                   )

  val builder = OParser.builder[CliCfg]
  val parser = {
    import builder.*
    OParser.sequence(
      programName("cli-test"),
      head("cli-test"),
      opt[Int]('f', "foo")
        .action((x, c) => c.copy(foo = x))
        .text("foo is an integer property"),
      opt[String]('l', name = "libName")
        .action((x, c) => c.copy(libName = x))
        .text("library name. string"),
      help('h', "help")
        .text("prints this usage text"),
      cmd("update")
        .action((_, c) => c.copy(mode = Update))
        .text("     update whatever"),
      cmd("insert")
        .action((_, c) => c.copy(mode = Insert(0)))
        .text("     insert is somthing to be inserted")
        .children(
          opt[Int]('n', "number")
            .action((x, c) => c.mode match {
              case i: Insert => c.copy(mode = i.copy(num = x))
              case _ => throw IllegalStateException()
            })
            .text("Number of inserts"),
        )
    )
  }

  def main(args: Array[String]): Unit = {
    // OParser.parse returns Option[Config]
    OParser.parse(parser, args, CliCfg()) match {
      case Some(config) =>
        println(config)
      case _ =>
      // arguments are bad, error message will have been displayed
    }
  }

  object Cooking {

    case class CookungData(
                            vagan: Boolean = false,
                            command: Command,
                          )

    trait Command

    case class Shopping(
                         number: Int = 0,
                         description: String = "",
                       ) extends Command

    case class Prepare(
                        numberOfPersons: Int = 1,
                        motto: String = ""
                      )

  }

}
