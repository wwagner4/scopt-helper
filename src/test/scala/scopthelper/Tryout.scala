package scopthelper

import scopthelper.Main.Starts.{Selectable1, table}

object Tryout {

  case class Entry(id: String, description: String) extends Selectable1

  val entries = Seq(
    Entry("a", "Some text"),
    Entry("a1", "Some text text text"),
    Entry("o", "Some text"),
    Entry("x", "Some text"),
  )


  @main
  def main(): Unit = {
    println(table(entries)(withHeader = true))
    println()
    println()
    println(table(entries)(withHeader = false))
  }

}
