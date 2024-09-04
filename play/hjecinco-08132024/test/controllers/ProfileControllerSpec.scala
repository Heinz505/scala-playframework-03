import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._
import scala.concurrent.Future
import play.api.mvc._
import javax.inject.Inject

class ProfileControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting {

  "ProfileController" should {

    "valid profile form submission is OK" in {
      val controller = inject[ProfileController]
      val request = FakeRequest(POST, "/profile")
        .withFormUrlEncodedBody(
          "firstName" -> "John",
          "middleName" -> "Paul",
          "lastName" -> "Doe",
          "age" -> "30"
        )
      
      val result: Future[Result] = controller.createProfile.apply(request)
      
      status(result) mustBe OK
      contentAsString(result) mustBe "Form values received successfully"
    }

    "invalid profile form submission is a BadRequest" in {
      val controller = inject[ProfileController]
      val request = FakeRequest(POST, "/profile")
        .withFormUrlEncodedBody(
          "firstName" -> "John123",  // Invalid first name
          "middleName" -> "Paul",
          "lastName" -> "Doe",
          "age" -> "30"
        )
      
      val result: Future[Result] = controller.createProfile.apply(request)
      
      status(result) mustBe BAD_REQUEST
      contentAsString(result) must include ("First name should not have numbers")
    }
  }
}
