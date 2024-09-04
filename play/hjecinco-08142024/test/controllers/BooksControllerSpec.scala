// test/controllers/BooksControllerSpec.scala
package controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import scala.concurrent.ExecutionContext
import models.{Book, User}

class BooksControllerSpec extends PlaySpec with Results {

  // Initialize the ActorSystem and Materializer
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)
  implicit val ec: ExecutionContext = system.dispatcher

  // Define implicit formats for JSON conversion
  implicit val bookFormat: OFormat[Book] = Json.format[Book]
  implicit val userFormat: OFormat[User] = Json.format[User]

  // Initialize the controller with the necessary components
  val controller = new BooksController(Helpers.stubControllerComponents())

  "BooksController" should {

    "should be all the books" in {
      val request = FakeRequest(GET, "/books")
      val result = controller.getAllBooks.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "give books by year" in {
      val request = FakeRequest(GET, "/books/year/2010")
      val result = controller.getBooksByYear(2010).apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "able to add a new book entry" in {
      val newBook = Json.toJson(Book("New Book", "New Author", 2024))
      val request = FakeRequest(POST, "/books").withJsonBody(newBook)
      val result = controller.addBook.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "should be all users without passwords" in {
      val request = FakeRequest(GET, "/users")
      val result = controller.getAllUsers.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "able to add a new user" in {
      val newUser = Json.toJson(User("newuser", "password", 30, List("New Book")))
      val request = FakeRequest(POST, "/users").withJsonBody(newUser)
      val result = controller.addUser.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }
}
