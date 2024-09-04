import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._
import scala.concurrent.Future
import play.api.mvc._
import javax.inject.Inject

class UserControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting {

  "UserController" should {

    "valid user form submission is OK" in {
      val controller = inject[UserController]
      val request = FakeRequest(POST, "/user")
        .withFormUrlEncodedBody(
          "email" -> "test@example.com",
          "username" -> "validUsername",
          "password" -> "validPassword123"
        )
      
      val result: Future[Result] = controller.createUser.apply(request)
      
      status(result) mustBe OK
      contentAsString(result) mustBe "Form values received successfully"
    }

    "invalid user form submission is a BadRequest" in {
      val controller = inject[UserController]
      val request = FakeRequest(POST, "/user")
        .withFormUrlEncodedBody(
          "email" -> "test@example.com",
          "username" -> "",  // Invalid username
          "password" -> "short"  // Invalid password
        )
      
      val result: Future[Result] = controller.createUser.apply(request)
      
      status(result) mustBe BAD_REQUEST
      contentAsString(result) must include ("This is field is required")
      contentAsString(result) must include ("At least 8 characters for password")
    }
  }
}
