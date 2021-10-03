package scopthelper

import scopt.OParser

object Main {

  def main(args: Array[String]): Unit =
    Starts.parse(args)

  object Starts {

    trait Selectable1 {
      def id: String

      def description: String
    }

    case class WilsonClass(
                            id: String,
                            description: String,
                            fullDescription: String,
                          ) extends Selectable1

    val wilsonClasses = Seq(
      WilsonClass("sd", "Subdwarf", "Sometimes denoted by 'sd', is a star with luminosity class VI under the Yerkes spectral classification system. They are defined as stars with luminosity 1.5 to 2 magnitudes lower than that of main-sequence stars of the same spectral type. On a Hertzsprung–Russell diagram subdwarfs appear to lie below the main sequence."),
      WilsonClass("d", "Dwarf", "A dwarf is a star of relatively small size and low luminosity. Most main sequence stars are dwarf stars. The term was originally coined in 1906 when the Danish astronomer Ejnar Hertzsprung noticed that the reddest stars—classified as K and M in the Harvard scheme—could be divided into two distinct groups. They are either much brighter than the Sun, or much fainter"),
      WilsonClass("sg", "Subgiant", "A subgiant is a star that is brighter than a normal main-sequence star of the same spectral class, but not as bright as giant stars. The term subgiant is applied both to a particular spectral luminosity class and to a stage in the evolution of a star."),
      WilsonClass("g", "Giant", "A giant star is a star with substantially larger radius and luminosity than a main-sequence (or dwarf) star of the same surface temperature.[1] They lie above the main sequence (luminosity class V in the Yerkes spectral classification) on the Hertzsprung–Russell diagram and correspond to luminosity classes II and III. The terms giant and dwarf were coined for stars of quite different luminosity despite similar temperature or spectral type by Ejnar Hertzsprung about 1905"),
      WilsonClass("c", "Supergiant", "Supergiants are among the most massive and most luminous stars. Supergiant stars occupy the top region of the Hertzsprung–Russell diagram with absolute visual magnitudes between about −3 and −8. The temperature range of supergiant stars spans from about 3,400 K to over 20,000 K."),
    )

    case class StarsConfig(
                            wilsonClassId: String = ""
                          )

    def table(selectables: Seq[Selectable1], withHeader: Boolean) = {
      val headerId = "id"
      val headerDescription = "description"

      def maxlen(strings: Iterable[String]): Int = strings.map(_.length).max

      def line(len: Int): String = Seq.fill(len)('-').mkString

      val lenId = math.max(maxlen(selectables.map(_.id)), headerId.length)
      val lenDesc = math.max(maxlen(selectables.map(_.description)), headerDescription.length)
      if withHeader then {
        val formatString = s"%-${lenId}s | %-${lenDesc}s"
        val header = Seq(formatString.format("id", "description"))
        val sepa = Seq(line(lenId + 1) + "+" + line(lenDesc + 1))
        val lines = selectables.map(e => formatString.format(e.id, e.description))
        (sepa ++ header ++ sepa ++ lines).mkString("\n")
      } else {
        val formatString = s"%-${lenId}s : %-${lenDesc}s"
        val lines = selectables.map(e => formatString.format(e.id, e.description))
        lines.mkString("\n")
      }
    }


    val builder = OParser.builder[StarsConfig]
    val parser = {
      import builder.*
      OParser.sequence(
        programName("Wilson classification"),
        head("Show the details of the wilson classification"),
        arg[String]("id")
          .required()
          .action((x, c) => c.copy(wilsonClassId = x))
          .text(s"Must be one of the wilson classes\n${table(wilsonClasses, withHeader = true)}")
          .validate { x =>
            if wilsonClasses.map(_.id).contains(x)
            then success
            else failure(s"id must be one of the following: ${wilsonClasses.map(_.id).mkString(", ")}")
          },
        help('h', "help").text("prints this usage text"),
      )
    }

    def parse(args: Array[String]): Unit = {

      def wilsonClassFromId(id: String): WilsonClass = {
        val tuples = wilsonClasses.map(wc => (wc.id, wc))
        val wilsonMap = tuples.toMap
        wilsonMap(id)
      }

      OParser.parse(parser, args, StarsConfig()) match {
        case Some(config) =>
          val wc = wilsonClassFromId(config.wilsonClassId)
          println("-----------------------------------------------------------------------------")
          println(wc.id)
          println(wc.description)
          println(wc.fullDescription.take(30) + "...")
          println("-----------------------------------------------------------------------------")
        case _ =>
      }
    }

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
