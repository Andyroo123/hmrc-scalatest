package net.hmrc.scalatest

import org.specs2._

/**
  * Created by andrew on 24/08/16.
  */
class CheckoutSpec extends mutable.Specification {

  "Checkout" should {
    "Charge 1 apples at 60p" in {
      val items: List[String] = List("Apple")
      Checkout.checkout(items) mustEqual 60
    }

    "Charge 2 applies at 120p" in {
      val items: List[String] = List("Apple", "Apple")
      Checkout.checkout(items) mustEqual 120
    }

  }

}
