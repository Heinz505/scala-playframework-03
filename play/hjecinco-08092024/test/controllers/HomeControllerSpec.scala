package controllers

import auth.AuthorizedAction
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._
import play.api.inject._
import scala.concurrent.ExecutionContext

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting {

  // Override the application for testing
  override def fakeApplication() = {
    // Define your test application configuration here
    GuiceApplicationBuilder()
      .overrides(bind[AuthorizedAction].to[FakeAuthorizedAction]) // Use a fake or mock implementation if needed
      .build()
  }

  implicit val ec: ExecutionContext = ExecutionContext.global

  "HomeController" should {

    "return OK for the createSession action" in {
      val controller = inject[HomeController]
      val result = controller.createSession(FakeRequest())
      status(result) mustBe OK
    }

    "return OK for the home action when authorized" in {
      val controller = inject[HomeController]
      val request = FakeRequest().withSession("email" -> "user@gmail.com")
      val result = controller.home(request)
      status(result) mustBe OK
      contentAsString(result) must include("You are logged in")
    }
  }
}
