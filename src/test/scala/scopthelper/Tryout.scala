package scopthelper

import scopthelper.Main.Starts.{ConfigWithId, table}

object Tryout {

  case class Entry(id: String, description: String) extends ConfigWithId

  val entries = Seq(
    Entry("a", "Some text"),
    Entry("a1", "Some text text text"),
    Entry("o", "Some text"),
    Entry("x", "Some text"),
  )

  @main
  def main(): Unit = {
    println()
    println(table(entries, withHeader = true))
    println()
    println(table(entries, withHeader = false))
  }

}
