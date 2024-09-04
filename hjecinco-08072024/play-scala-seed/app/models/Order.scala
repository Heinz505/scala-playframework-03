package models

import play.api.libs.json.{Json, OFormat}

case class Order(id: Option[String], userId: Long, items: Seq[OrderItem])

case class OrderItem(productId: String, quantity: Int, price: Double)

object Order {
  implicit val orderItemFormat: OFormat[OrderItem] = Json.format[OrderItem]
  implicit val orderFormat: OFormat[Order] = Json.format[Order]
}