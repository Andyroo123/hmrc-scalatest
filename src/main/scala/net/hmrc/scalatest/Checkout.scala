package net.hmrc.scalatest

/**
  * Created by andrew on 24/08/16.
  */
object Checkout extends App {

  def checkout(items: List[String]): String = {
    val total: Int = addUpItems(items)
    convertToPounds(total)
  }

  def addUpItems(items: List[String]): Int = {

    items match {
      case "Apple" :: remainingItems =>
        60 + addUpItems(remainingItems)
      case "Orange" :: remainingItems =>
        25 + addUpItems(remainingItems)
      case Nil =>
        0
    }
  }

  def convertToPounds(pence: Int): String = {
    val pounds: Double = pence.toDouble / 100
    f"£$pounds%1.2f"
  }

}
