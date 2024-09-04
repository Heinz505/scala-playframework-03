package controllers

import javax.inject._
import play.api.mvc._
import models.repo.BookRepository
import models.domain.Book
import scala.concurrent.{ExecutionContext, Future}
import java.util.UUID

@Singleton
class BookController @Inject()(cc: ControllerComponents, bookRepo: BookRepository)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def insertBooks(): Action[AnyContent] = Action.async {
    val books = Seq(
      Book("1234567890", "Book One", 2020, UUID.randomUUID(), BigDecimal(19.99)),
      Book("0987654321", "Book Two", 2021, UUID.randomUUID(), BigDecimal(29.99))
    )
    bookRepo.insert(books).map(_ => Ok("Books inserted"))
  }

  def getBooksByAuthorId(authorId: UUID): Action[AnyContent] = Action.async {
    bookRepo.getBooksByAuthorId(authorId).map(titles => Ok(titles.mkString(", ")))
  }

  def getBooksByYear(yearOpt: Option[Int]): Action[AnyContent] = Action.async {
    bookRepo.getBooksByYear(yearOpt).map(books => Ok(books.mkString(", ")))
  }

  def updateBook(isbn: String, newTitle: String, newYear: Int, newPrice: BigDecimal): Action[AnyContent] = Action.async {
    bookRepo.updateBook(isbn, newTitle, newYear, newPrice).map(rowsAffected => Ok(s"Updated $rowsAffected row(s)"))
  }

  def findBookByIsbn(isbn: String): Action[AnyContent] = Action.async {
    bookRepo.findBookByIsbn(isbn).map {
      case Some(book) => Ok(book.toString)
      case None => NotFound("Book not found")
    }
  }

  def deleteBooksByAuthorId(authorId: UUID): Action[AnyContent] = Action.async {
    bookRepo.deleteBooksByAuthorId(authorId).map(remainingCount => Ok(s"Remaining books: $remainingCount"))
  }

  def getBooksLimited(): Action[AnyContent] = Action.async {
    bookRepo.getBooksLimited().map(books => Ok(books.mkString(", ")))
  }
}
