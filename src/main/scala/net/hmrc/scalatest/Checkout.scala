package net.hmrc.scalatest

import net.hmrc.scalatest.model.{Apple, Item, Orange}

/**
  * Created by andrew on 24/08/16.
  */
object Checkout extends App {


  def checkout(items: List[Item]): String = {
    val total: Int = addUpItems(items)
    val discount: Int = calculateDiscount(items)
    val grandTotal = total - discount
    convertPenceToPounds(grandTotal)
  }

  def addUpItems(items: List[Item]): Int = {

    items match {
      case (item: Apple) :: remainingItems =>
        item.price + addUpItems(remainingItems)
      case (item: Orange) :: remainingItems =>
        item.price + addUpItems(remainingItems)
      case Nil =>
        0
      case _ =>
        println(s"Unexpected item has been scanned ${items.head.name}")
        throw new Exception(s"Unexpected item has been scanned: ${items.head.name}")
    }
  }

  def convertPenceToPounds(pence: Int): String = {
    val pounds: Double = pence.toDouble / 100
    f"£$pounds%1.2f"
  }

  def calculateDiscount(items: List[Item]): Int = {
    val apple: Apple = new Apple
    val orange: Orange = new Orange
    val appleCount = items.count(_.isInstanceOf[Apple])
    val discountedApples = appleCount / apple.discountTrigger
    val orangeCount = items.count(_.isInstanceOf[Orange])
    val discountedOranges = orangeCount / orange.discountTrigger
    (discountedApples * apple.discountAmount) + (discountedOranges * orange.discountAmount)
  }

}
