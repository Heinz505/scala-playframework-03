package controllers

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import scala.collection.mutable.ListBuffer
import models.{Book, User}

class BooksControllerSpec extends PlaySpec with Results {

  implicit val bookFormat: OFormat[Book] = Json.format[Book]
  implicit val userFormat: OFormat[User] = Json.format[User]

  val controller = new BooksController(Helpers.stubControllerComponents())

  "BooksController" should {

    "enumarate all books" in {
      val request = FakeRequest(GET, "/books")
      val result = controller.getAllBooks.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "enumerate books by year" in {
      val request = FakeRequest(GET, "/books/year/2010")
      val result = controller.getBooksByYear(2010).apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "add a new book to database" in {
      val newBook = Json.toJson(Book("New Book", "New Author", 2024))
      val request = FakeRequest(POST, "/books").withJsonBody(newBook)
      val result = controller.addBook.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "enumarate all users without passwords" in {
      val request = FakeRequest(GET, "/users")
      val result = controller.getAllUsers.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "add a new user to database" in {
      val newUser = Json.toJson(User("newuser", "password", 30, List("New Book")))
      val request = FakeRequest(POST, "/users").withJsonBody(newUser)
      val result = controller.addUser.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }
}
