package net.hmrc.scalatest.model

/**
  * Created by andrew on 24/08/16.
  */
class Apple extends Item {

  override val name: String = "Apple"

  override val price: Int = 60

  override val discountTrigger: Int = 2

  override val discountAmount: Int = price

}
