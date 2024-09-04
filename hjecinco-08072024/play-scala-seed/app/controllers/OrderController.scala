package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.Json
import services.OrderService
import models.{Order, OrderItem}
import scala.concurrent.ExecutionContext

@Singleton
class OrderController @Inject()(cc: ControllerComponents, orderService: OrderService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def placeOrder(): Action[JsValue] = Action.async(parse.json) { request =>
    val orderResult = request.body.validate[Order]
    orderResult.fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      order => {
        orderService.placeOrder(order).map(id => Created(Json.obj("message" -> "Order placed successfully", "orderId" -> id)))
      }
    )
  }

  def getOrderHistory(userId: Long): Action[AnyContent] = Action.async {
    orderService.getOrderHistory(userId).map(orders => Ok(Json.toJson(orders)))
  }

  def getOrderDetails(orderId: String): Action[AnyContent] = Action.async {
    orderService.getOrderDetails(orderId).map {
      case Some(order) => Ok(Json.toJson(order))
      case None => NotFound(Json.obj("message" -> "Order not found"))
    }
  }

  def cancelOrder(orderId: String): Action[AnyContent] = Action.async {
    orderService.cancelOrder(orderId).map(_ => Ok(Json.obj("message" -> "Order cancelled successfully")))
  }
}
