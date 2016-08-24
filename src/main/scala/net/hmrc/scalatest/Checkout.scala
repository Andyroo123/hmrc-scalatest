package net.hmrc.scalatest

/**
  * Created by andrew on 24/08/16.
  */
object Checkout extends App {

  def checkout(items: List[String]): Int = {
    val itemPrices: List[Int] = items.map{ item =>
      60
    }

    itemPrices.sum
  }

}
