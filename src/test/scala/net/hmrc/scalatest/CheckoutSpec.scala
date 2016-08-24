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

    "Charge 6 apples at 360p" in {
      val items: List[String] = List.fill(6)("Apple")
      Checkout.addUpItems(items) mustEqual 360
    }

    "Charge 1 orange at 25p" in {
      val items: List[String] = List("Orange")
      Checkout.addUpItems(items) mustEqual 25
    }

    "Charge 2 oranges at 50p" in {
      val items: List[String] = List("Orange")
      Checkout.addUpItems(items) mustEqual 25
    }

    "Charge 6 oranges at 150p" in {
      val items: List[String] = List.fill(6)("Orange")
      Checkout.addUpItems(items) mustEqual 150
    }

    "Charge 1 apple and 1 orange at 85p" in {
      val items: List[String] = List("Orange", "Apple")
      Checkout.addUpItems(items) mustEqual 85
    }

    "Charge 4 apple and 14 orange at 85p" in {
      val apples: List[String] = List.fill(4)("Apple")
      val oranges: List[String] = List.fill(14)("Orange")
      val items: List[String] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 590
    }

    "Charge 24 apple and 8 orange at 85p" in {
      val apples: List[String] = List.fill(24)("Apple")
      val oranges: List[String] = List.fill(8)("Orange")
      val items: List[String] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 1640
    }

    "Charge 601 apples and 24 oranges at 36660p" in {
      val apples: List[String] = List.fill(601)("Apple")
      val oranges: List[String] = List.fill(24)("Orange")
      val items: List[String] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 36660
    }

    "Unexpected item throws error" in {
      val items: List[String] = List("Apple", "Orange", "Banana")
      Checkout.addUpItems(items) must throwA[Exception].like { case e => e.getMessage mustEqual "Unexpected item has been scanned: Banana"}
    }

  }

  "convertToPounds" should {
    "Convert 0p to £0.00" in {
      val pence: Int = 0
      Checkout.convertPenceToPounds(pence) mustEqual "£0.00"
    }

    "Convert 25p to £0.25" in {
      val pence: Int = 25
      Checkout.convertPenceToPounds(pence) mustEqual "£0.25"
    }

    "Convert 60p to £0.60" in {
      val pence: Int = 60
      Checkout.convertPenceToPounds(pence) mustEqual "£0.60"
    }

    "Convert 100p to £1.00" in {
      val pence: Int = 100
      Checkout.convertPenceToPounds(pence) mustEqual "£1.00"
    }

    "Convert 111p to £1.11" in {
      val pence: Int = 111
      Checkout.convertPenceToPounds(pence) mustEqual "£1.11"
    }

    "Convert 201p to £2.01" in {
      val pence: Int = 201
      Checkout.convertPenceToPounds(pence) mustEqual "£2.01"
    }

    "Convert 31444p to £314.44" in {
      val pence: Int = 31444
      Checkout.convertPenceToPounds(pence) mustEqual "£314.44"
    }
  }


  "Checkout" should {
    "Charge 1 apple with a readable output" in {
      val items: List[String] = List("Apple")
      Checkout.checkout(items) mustEqual "£0.60"
    }

    "Charge 1 orange with a readable output" in {
      val items: List[String] = List("Orange")
      Checkout.checkout(items) mustEqual "£0.25"
    }

    "Charge 5 apples and 1 orange with a readable output" in {
      val apples: List[String] = List.fill(5)("Apple")
      val oranges: List[String] = List("Orange")
      val items = apples ++ oranges
      Checkout.checkout(items) mustEqual "£2.05"
    }
  }

  "Calculate discount" should {
    "Be 0p for 1 apple" in {
      val items: List[String] = List("Apple")
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 60p for 2 apples" in {
      val items: List[String] = List("Apple")
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 60p for 3 apples" in {
      val items: List[String] = List("Apple")
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 0p for 1 orange" in {
      val items: List[String] = List.fill(1)("Orange")
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 0p for 2 oranges" in {
      val items: List[String] = List.fill(2)("Orange")
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 1070p for 44 oranges and 25 apples" in {
      val oranges: List[String] = List.fill(44)("Orange") // costs 1100p discount 350p
      val apples: List[String] = List.fill(25)("Apple") // costs 1500p discount 720p
      val items: List[String] = oranges ++ apples // total costs 2600p with discount of 1070p
      Checkout.calculateDiscount(items) mustEqual 1070
    }


  }

}
