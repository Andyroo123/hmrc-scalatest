package net.hmrc.scalatest

import org.specs2._

/**
  * Created by andrew on 24/08/16.
  */
class CheckoutSpec extends mutable.Specification {

  "addUpItems" should {
    "Charge nothing at 0p" in {
      val items: List[String] = List()
      Checkout.addUpItems(items) mustEqual 0
    }

    "Charge 1 apples at 60p" in {
      val items: List[String] = List("Apple")
      Checkout.addUpItems(items) mustEqual 60
    }

    "Charge 2 apples at 120p" in {
      val items: List[String] = List("Apple", "Apple")
      Checkout.addUpItems(items) mustEqual 120
    }

    "Charge 1 orange at 25p" in {
      val items: List[String] = List("Orange")
      Checkout.addUpItems(items) mustEqual 25
    }

    "Charge 2 oranges at 50p" in {
      val items: List[String] = List("Orange")
      Checkout.addUpItems(items) mustEqual 25
    }

    "Charge 1 apple and 1 orange at 85p" in {
      val items: List[String] = List("Orange", "Apple")
      Checkout.addUpItems(items) mustEqual 85
    }

    "Charge 601 apples and 24 oranges at 36660p" in {
      val apples: List[String] = List.fill(601)("Apple")
      val oranges: List[String] = List.fill(24)("Orange")
      val items: List[String] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 36660
    }

  }

  "convertToPounds" should {
    "Convert 0p to £0.00" in {
      val pence: Int = 0
      Checkout.convertToPounds(pence) mustEqual "£0.00"
    }

    "Convert 25p to £0.25" in {
      val pence: Int = 25
      Checkout.convertToPounds(pence) mustEqual "£0.25"
    }

    "Convert 60p to £0.60" in {
      val pence: Int = 60
      Checkout.convertToPounds(pence) mustEqual "£0.60"
    }

    "Convert 100p to £1.00" in {
      val pence: Int = 100
      Checkout.convertToPounds(pence) mustEqual "£1.00"
    }

    "Convert 111p to £1.11" in {
      val pence: Int = 111
      Checkout.convertToPounds(pence) mustEqual "£1.11"
    }

    "Convert 201p to £2.01" in {
      val pence: Int = 201
      Checkout.convertToPounds(pence) mustEqual "£2.01"
    }

    "Convert 31444p to £314.44" in {
      val pence: Int = 31444
      Checkout.convertToPounds(pence) mustEqual "£314.44"
    }
  }


  "Checkout" should {
    "Charge 1 apple with a readable output" in {
      val items: List[String] = List("Apple")
      Checkout.checkout(items) mustEqual "£0.60"
    }
  }

}
