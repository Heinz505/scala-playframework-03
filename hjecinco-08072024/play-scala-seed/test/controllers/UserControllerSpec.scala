package controllers

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.libs.json._
import models.User
import services.UserService
import scala.concurrent.ExecutionContext.Implicits.global

class UserControllerSpec extends PlaySpec with Results with GuiceOneAppPerTest {

  // Mock UserService
  class MockUserService extends UserService(null) {
    override def registerUser(user: User): Future[Long] = Future.successful(1L)
    override def updateUser(id: Long, user: User): Future[Unit] = Future.successful(())
    override def findUser(username: String, password: String): Future[Option[User]] = Future.successful(Some(User(Some(1L), username, password, "test@example.com")))
  }

  val mockUserService = new MockUserService
  val controller = new UserController(stubControllerComponents(), mockUserService)

  "UserController" should {

    "register a user successfully" in {
      val userJson = Json.parse("""{"username": "testuser", "password": "Test@1234", "email": "testuser@example.com"}""")
      val request = FakeRequest(POST, "/api/register").withBody(userJson).withHeaders("Content-Type" -> "application/json")
      val response = controller.registerUser().apply(request)
      status(response) mustBe CREATED
      contentType(response) mustBe Some("application/json")
      (contentAsJson(response) \ "message").as[String] mustBe "User registered successfully"
    }

    "update user information successfully" in {
      val userJson = Json.parse("""{"email": "newemail@example.com"}""")
      val request = FakeRequest(PUT, "/api/user/1").withBody(userJson).withHeaders("Content-Type" -> "application/json")
      val response = controller.updateUser(1).apply(request)
      status(response) mustBe OK
      (contentAsJson(response) \ "message").as[String] mustBe "User updated successfully"
    }

    "login user successfully" in {
      val loginJson = Json.parse("""{"username": "testuser", "password": "Test@1234"}""")
      val request = FakeRequest(POST, "/api/login").withBody(loginJson).withHeaders("Content-Type" -> "application/json")
      val response = controller.loginUser().apply(request)
      status(response) mustBe OK
      (contentAsJson(response) \ "message").as[String] mustBe "Login successful"
    }

    "fail login with invalid credentials" in {
      val loginJson = Json.parse("""{"username": "testuser", "password": "WrongPassword"}""")
      val request = FakeRequest(POST, "/api/login").withBody(loginJson).withHeaders("Content-Type" -> "application/json")
      val response = controller.loginUser().apply(request)
      status(response) mustBe UNAUTHORIZED
      (contentAsJson(response) \ "message").as[String] mustBe "Invalid username or password"
    }

    "logout user successfully" in {
      val request = FakeRequest(POST, "/api/logout")
      val response = controller.logoutUser().apply(request)
      status(response) mustBe OK
      (contentAsJson(response) \ "message").as[String] mustBe "Logout successful"
    }
  }
}
