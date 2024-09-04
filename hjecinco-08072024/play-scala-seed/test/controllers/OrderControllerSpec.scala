package controllers

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.libs.json._
import models.{Order, OrderItem}
import services.OrderService
import scala.concurrent.ExecutionContext.Implicits.global

class OrderControllerSpec extends PlaySpec with Results with GuiceOneAppPerTest {

  // Mock OrderService
  class MockOrderService extends OrderService(null) {
    override def placeOrder(order: Order): Future[String] = Future.successful("order12345")
    override def getOrderHistory(userId: Long): Future[Seq[Order]] = Future.successful(Seq.empty)
    override def getOrderDetails(orderId: String): Future[Option[Order]] = Future.successful(Some(Order(Some(orderId), 1L, Seq(OrderItem("prod123", 2, 50.00)))))
    override def cancelOrder(orderId: String): Future[Unit] = Future.successful(())
  }

  val mockOrderService = new MockOrderService
  val controller = new OrderController(stubControllerComponents(), mockOrderService)

  "OrderController" should {

    "place an order successfully" in {
      val orderJson = Json.parse("""{"userId": 1, "items": [{"productId": "prod123", "quantity": 2, "price": 50.00}]}""")
      val request = FakeRequest(POST, "/api/orders").withBody(orderJson).withHeaders("Content-Type" -> "application/json")
      val response = controller.placeOrder().apply(request)
      status(response) mustBe CREATED
      (contentAsJson(response) \ "message").as[String] mustBe "Order placed successfully"
    }

    "retrieve order history successfully" in {
      val request = FakeRequest(GET, "/api/orders/user/1")
      val response = controller.getOrderHistory(1).apply(request)
      status(response) mustBe OK
      contentAsJson(response) mustBe Json.arr()
    }

    "retrieve order details successfully" in {
      val request = FakeRequest(GET, "/api/orders/order12345")
      val response = controller.getOrderDetails("order12345").apply(request)
      status(response) mustBe OK
      (contentAsJson(response) \ "orderId").as[String] mustBe "order12345"
    }

    "fail to retrieve order details with invalid ID" in {
      val request = FakeRequest(GET, "/api/orders/invalidOrderId")
      val response = controller.getOrderDetails("invalidOrderId").apply(request)
      status(response) mustBe NOT_FOUND
      (contentAsJson(response) \ "message").as[String] mustBe "Order not found"
    }

    "cancel an order successfully" in {
      val request = FakeRequest(DELETE, "/api/orders/order12345")
      val response = controller.cancelOrder("order12345").apply(request)
      status(response) mustBe OK
      (contentAsJson(response) \ "message").as[String] mustBe "Order cancelled successfully"
    }
  }
}
