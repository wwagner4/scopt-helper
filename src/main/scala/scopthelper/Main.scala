package scopthelper

import scopt.OParser

object Main {

  def main(args: Array[String]): Unit =
    Cooking.parse(args)

  object Cooking {

    def parse(args: Array[String]): Unit = {
      // OParser.parse returns Option[Config]
      OParser.parse(parser, args, CookingData()) match {
        case Some(shoppingData) =>
          println(shoppingData)
        case _ =>
          println("Something went wrong ???")
      }
    }

    case class CookingData(
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

    val builder = OParser.builder[CookingData]
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
                val s = c.asInstanceOf[Shopping]
                c.copy(command = s.copy(number = x))
              })
            ,
            opt[String]('d', "description")
              .text("Describes our ingrediences")
              .action((x, c) => {
                val s = c.asInstanceOf[Shopping]
                c.copy(command = s.copy(description = x))
              })
            ,
          )
        ,
        cmd("prepare")
          .text("lets prepare our meal")
          .action((_, c) => c.copy(command = Prepare()))
          .children(
            opt[Int]('n', "number-of-persons")
              .text("Number of persons")
              .action((x, c) => {
                val p = c.asInstanceOf[Prepare]
                c.copy(command = p.copy(numberOfPersons = x))
              })
            ,
            opt[String]('m', "motto")
              .text("Motto of what we are cooking. E.g. italien, ...")
              .action((x, c) => {
                val p = c.asInstanceOf[Prepare]
                c.copy(command = p.copy(motto = x))
              })
          )
        ,
      )
    }
  }
}
