package scopthelper

import scopt.OParser

object Main {

  def main(args: Array[String]): Unit =
    Cooking.parse(args)

  object Starts {


    /*
    */

    case class StarsConfig(

                          )


  }

  object Cooking {

    def parse(args: Array[String]): Unit = {
      OParser.parse(parser, args, CookingConfig()) match {
        case Some(config) =>
          println(s"Cooking config is: $config")
        case _ =>
      }
    }

    case class CookingConfig(
                              vegan: Boolean = false,
                              command: Command = Shopping(),
                            )

    trait Command

    case class Shopping(
                         number: Int = 0,
                         description: String = "",
                       ) extends Command

    case class Prepare(
                        numberOfPersons: Int = 1,
                        motto: String = ""
                      ) extends Command

    val builder = OParser.builder[CookingConfig]
    val parser = {
      import builder.*
      OParser.sequence(
        programName("cooking"),
        head("Lets do some shopping and then prepare a delicious meal"),
        opt[Unit]('v', "vegan")
          .action((_, c) => c.copy(vegan = true))
          .text("if present the only vegan ingrediens"),
        help('h', "help").text("prints this usage text"),
        cmd("shopping")
          .text("lets go shopping for our meal")
          .action((_, c) => c.copy(command = Shopping()))
          .children(
            opt[Int]('n', "number-of-persons")
              .text("Number of persons")
              .action((x, c) => {
                c.copy(command = c.command.asInstanceOf[Shopping].copy(number = x))
              }),
            opt[String]('d', "description")
              .text("Describes our ingrediences")
              .action((x, c) => {
                c.copy(command = c.command.asInstanceOf[Shopping].copy(description = x))
              }),
          ),
        cmd("prepare")
          .text("lets prepare our meal")
          .action((_, c) => c.copy(command = Prepare()))
          .children(
            opt[Int]('n', "number-of-persons")
              .text("Number of persons")
              .required()
              .validate(x => if x > 0 then success else failure("Number of persons must be more than zero"))
              .action((x, c) => {
                c.copy(command = c.command.asInstanceOf[Prepare].copy(numberOfPersons = x))
              }),
            opt[String]('m', "motto")
              .text("Motto of what we are cooking. E.g. italien, ...")
              .action((x, c) => {
                c.copy(command = c.command.asInstanceOf[Prepare].copy(motto = x))
              }),
          ),
      )
    }
  }
}
