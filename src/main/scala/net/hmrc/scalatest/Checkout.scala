package net.hmrc.scalatest

/**
  * Created by andrew on 24/08/16.
  */
object Checkout extends App {

  final val APPLE = "Apple"
  final val ORANGE = "Orange"

  final val APPLE_PRICE = 60
  final val ORANGE_PRICE = 25

  final val APPLE_DISCOUNT_TRIGGER = 2
  final val ORANGE_DISCOUNT_TRIGGER = 3

  final val APPLE_DISCOUNT_AMOUNT = APPLE_PRICE
  final val ORANGE_DISCOUNT_AMOUNT = ORANGE_PRICE

  def checkout(items: List[String]): String = {
    val total: Int = addUpItems(items)
    val discount: Int = calculateDiscount(items)
    val grandTotal = total - discount
    convertPenceToPounds(grandTotal)
  }

  def addUpItems(items: List[String]): Int = {

    items match {
      case `APPLE` :: remainingItems =>
        APPLE_PRICE + addUpItems(remainingItems)
      case `ORANGE` :: remainingItems =>
        ORANGE_PRICE + addUpItems(remainingItems)
      case Nil =>
        0
      case _ =>
        println(s"Unexpected item has been scanned ${items.head}")
        throw new Exception(s"Unexpected item has been scanned: ${items.head}")
    }
  }

  def convertPenceToPounds(pence: Int): String = {
    val pounds: Double = pence.toDouble / 100
    f"£$pounds%1.2f"
  }

  def calculateDiscount(items: List[String]): Int = {
    val appleCount = items.count(_ equals APPLE)
    val discountedApples = appleCount / APPLE_DISCOUNT_TRIGGER
    val orangeCount = items.count(_ equals ORANGE)
    val discountedOranges = orangeCount / ORANGE_DISCOUNT_TRIGGER
    (discountedApples * APPLE_DISCOUNT_AMOUNT) + (discountedOranges * ORANGE_DISCOUNT_AMOUNT)
  }

}
