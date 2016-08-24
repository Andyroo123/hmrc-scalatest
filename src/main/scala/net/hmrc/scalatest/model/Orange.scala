package net.hmrc.scalatest.model

/**
  * Created by andrew on 24/08/16.
  */
class Orange extends Item {

  override val name: String = "Orange"

  override val price: Int = 25

  override val discountTrigger: Int = 3

  override val discountAmount: Int = price

}
