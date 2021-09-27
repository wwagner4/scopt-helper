package clitest

object Main {

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) println("no arguments")
    else {
      val argStr = args.mkString(", ")
      println(s"Called main with arguments: $argStr")
    }
  }

}
