package net.hmrc.scalatest

import net.hmrc.scalatest.model.{Apple, Item, Orange}
import org.specs2._

/**
  * Created by andrew on 24/08/16.
  */
class CheckoutSpec extends mutable.Specification {

  val apple: Apple = new Apple
  val orange: Orange = new Orange

  "addUpItems" should {
    "Charge nothing at 0p" in {
      val items: List[Item] = List()
      Checkout.addUpItems(items) mustEqual 0
    }

    "Charge 1 apples at 60p" in {
      val items: List[Item] = List(apple)
      Checkout.addUpItems(items) mustEqual 60
    }

    "Charge 2 apples at 120p" in {
      val items: List[Item] = List(apple, apple)
      Checkout.addUpItems(items) mustEqual 120
    }

    "Charge 3 apples at 180p" in {
      val items: List[Item] = List(apple, apple, apple)
      Checkout.addUpItems(items) mustEqual 180
    }

    "Charge 6 apples at 360p" in {
      val items: List[Item] = List.fill(6)(apple)
      Checkout.addUpItems(items) mustEqual 360
    }

    "Charge 1 orange at 25p" in {
      val items: List[Item] = List(orange)
      Checkout.addUpItems(items) mustEqual 25
    }

    "Charge 2 oranges at 50p" in {
      val items: List[Item] = List(orange, orange)
      Checkout.addUpItems(items) mustEqual 50
    }

    "Charge 3 oranges at 75p" in {
      val items: List[Item] = List(orange, orange, orange)
      Checkout.addUpItems(items) mustEqual 75
    }

    "Charge 6 oranges at 150p" in {
      val items: List[Item] = List.fill(6)(orange)
      Checkout.addUpItems(items) mustEqual 150
    }

    "Charge 1 apple and 1 orange at 85p" in {
      val items: List[Item] = List(orange, apple)
      Checkout.addUpItems(items) mustEqual 85
    }

    "Charge 3 apples and 3 oranges at 255p" in {
      val apples: List[Item] = List.fill(3)(apple)
      val oranges: List[Item] = List.fill(3)(orange)
      val items: List[Item] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 255
    }

    "Charge 4 apple and 14 orange at 590p" in {
      val apples: List[Item] = List.fill(4)(apple)
      val oranges: List[Item] = List.fill(14)(orange)
      val items: List[Item] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 590
    }

    "Charge 24 apple and 8 orange at 85p" in {
      val apples: List[Item] = List.fill(24)(apple)
      val oranges: List[Item] = List.fill(8)(orange)
      val items: List[Item] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 1640
    }

    "Charge 601 apples and 24 oranges at 36660p" in {
      val apples: List[Item] = List.fill(601)(apple)
      val oranges: List[Item] = List.fill(24)(orange)
      val items: List[Item] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 36660
    }

/*    "Charge 601 apples and 24 oranges at 36660p" in {
      val apples: List[Item] = List.fill(600000001)(apple)
      val oranges: List[Item] = List.fill(20000004)(orange)
      val items: List[Item] = apples ++ oranges
      Checkout.addUpItems(items) mustEqual 36660
    }*/

    "Unexpected item throws error" in {
      class Banana extends Item {
        override val name: String = "Banana"
        override val price: Int = 10
        override val discountTrigger: Int = 1
        override val discountAmount: Int = 1
      }
      val banana: Banana = new Banana
      val items: List[Item] = List(apple, apple, banana)
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
      val items: List[Item] = List(apple)
      Checkout.checkout(items) mustEqual "£0.60"
    }

    "Charge 1 orange with a readable output" in {
      val items: List[Item] = List(orange)
      Checkout.checkout(items) mustEqual "£0.25"
    }

    "Charge 5 apples and 1 orange with a readable output" in {
      val apples: List[Item] = List.fill(5)(apple)
      val oranges: List[Item] = List(orange)
      val items = apples ++ oranges
      Checkout.checkout(items) mustEqual "£2.05"
    }

    "Charge 20 apples and 16 orange with a readable output" in {
      val apples: List[Item] = List.fill(20)(apple)
      val oranges: List[Item] = List.fill(16)(orange)
      val items = apples ++ oranges
      Checkout.checkout(items) mustEqual "£8.75"
    }
  }

  "Calculate discount" should {
    "Be 0p for 1 apple" in {
      val items: List[Item] = List(apple)
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 60p for 2 apples" in {
      val items: List[Item] = List.fill(2)(apple)
      Checkout.calculateDiscount(items) mustEqual 60
    }

    "Be 60p for 3 apples" in {
      val items: List[Item] = List.fill(3)(apple)
      Checkout.calculateDiscount(items) mustEqual 60
    }

    "Be 120p for 5 apples" in {
      val items: List[Item] = List.fill(5)(apple)
      Checkout.calculateDiscount(items) mustEqual 120
    }

    "Be 0p for 1 orange" in {
      val items: List[Item] = List.fill(1)(orange)
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 0p for 2 oranges" in {
      val items: List[Item] = List.fill(2)(orange)
      Checkout.calculateDiscount(items) mustEqual 0
    }

    "Be 25p for 4 oranges" in {
      val items: List[Item] = List.fill(4)(orange)
      Checkout.calculateDiscount(items) mustEqual 25
    }

    "Be 50p for 6 oranges" in {
      val items: List[Item] = List.fill(6)(orange)
      Checkout.calculateDiscount(items) mustEqual 50
    }

    "Be 75p for 10 oranges" in {
      val items: List[Item] = List.fill(10)(orange)
      Checkout.calculateDiscount(items) mustEqual 75
    }

    "Be 1070p for 14 oranges and 5 apples" in {
      val oranges: List[Item] = List.fill(14)(orange)
      val apples: List[Item] = List.fill(5)(apple)
      val items: List[Item] = oranges ++ apples
      Checkout.calculateDiscount(items) mustEqual 220
    }

    "Be 1070p for 44 oranges and 25 apples" in {
      val oranges: List[Item] = List.fill(44)(orange) // costs 1100p discount 350p
      val apples: List[Item] = List.fill(25)(apple) // costs 1500p discount 720p
      val items: List[Item] = oranges ++ apples // total costs 2600p with discount of 1070p
      Checkout.calculateDiscount(items) mustEqual 1070
    }


  }

}
