package controllers

import javax.inject._
import models.{Book, User}
import play.api.libs.json.{Json, JsValue}
import play.api.mvc._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

@Singleton
class BooksController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  private val books = ListBuffer(
    Book("Harry Potter and The Sorcerer's Stone", "J.K. Rowling", 1997),
    Book("Hunger Games", "Suzanne Collins", 2008),
    Book("Catching Fire", "Suzanne Collins", 2010),
    Book("Mockingjay", "Suzanne Collins", 2010),
    Book("Meditations", "Marcus Aurelius", 2011),
    Book("The Plague", "Albert Camus", 1947),
    Book("White Nights", "Fyodor Dostoevsky", 1848)
  )

  private val users = ListBuffer(
    User("jsmith", "p@55w0rd", 21, List("Harry Potter", "Hunger Games", "Catching Fire")),
    User("mkjones", "p@55w0rd", 28, List("Harry Potter", "Six of Crows", "Dune")),
    User("jdoe", "p@55w0rd", 25, List("Hunger Games", "Mockingjay", "White Nights"))
  )

  def getAllBooks: Action[AnyContent] = Action {
    Ok(Json.toJson(books.toList))
  }

  def getBooksByYear(year: Int): Action[AnyContent] = Action {
    val filteredBooks = books.filter(_.yearPublished == year).toList
    Ok(Json.toJson(filteredBooks))
  }

  def addBook: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Book].fold(
      errors => BadRequest(Json.obj("message" -> "Invalid Book format")),
      book => {
        books += book
        Ok(Json.toJson(books.toList))
      }
    )
  }

  def getAllUsers: Action[AnyContent] = Action {
    val usersWithoutPassword = users.map(user => user.copy(password = ""))
    Ok(Json.toJson(usersWithoutPassword.toList))
  }

  def addUser: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[User].fold(
      errors => BadRequest(Json.obj("message" -> "Invalid User format")),
      user => {
        users += user
        Ok(Json.toJson(users.toList.map(u => u.copy(password = ""))))
      }
    )
  }
}
