package net.hmrc.scalatest.model

/**
  * Created by andrew on 24/08/16.
  */
trait Item {

  val name: String

  val price: Int

  val discountTrigger: Int

  val discountAmount: Int

}
