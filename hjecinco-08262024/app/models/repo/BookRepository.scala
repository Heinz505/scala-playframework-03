package models.repo

import models.domain.Book
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import java.util.UUID
import javax.inject.Inject

class BookRepository @Inject()(db: Database, profile: JdbcProfile)(implicit ec: ExecutionContext) {

  private val books = TableQuery[Books]

  def insert(booksList: Seq[Book]): Future[Unit] = {
    db.run(books ++= booksList).map(_ => ())
  }

  def getBooksByAuthorId(authorId: UUID): Future[Seq[String]] = {
    db.run(books.filter(_.authorId === authorId).map(_.title).result.map(_.map(_.reverse)))
  }

  def getBooksByYear(yearOpt: Option[Int]): Future[Seq[Book]] = {
    val query = yearOpt.map(year => books.filter(_.publicationYear === year)).getOrElse(books)
    db.run(query.result)
  }

  def updateBook(isbn: String, newTitle: String, newYear: Int, newPrice: BigDecimal): Future[Int] = {
    val query = books.filter(_.isbn === isbn).map(b => (b.title, b.publicationYear, b.price))
    db.run(query.update((newTitle, newYear, newPrice)))
  }

  def findBookByIsbn(isbn: String): Future[Option[Book]] = {
    db.run(books.filter(_.isbn === isbn).result.headOption)
  }

  def deleteBooksByAuthorId(authorId: UUID): Future[Int] = {
    val deleteAction = books.filter(_.authorId === authorId).delete
    val countAction = books.filter(_.authorId === authorId).length.result
    db.run(deleteAction andThen countAction)
  }

  def getBooksLimited(limit: Int = 10): Future[Seq[Book]] = {
    db.run(books.sortBy(_.publicationYear.asc).take(limit).result)
  }

  private class Books(tag: Tag) extends Table[Book](tag, "books") {
    def isbn = column[String]("isbn", O.PrimaryKey)
    def title = column[String]("title")
    def publicationYear = column[Int]("publication_year")
    def authorId = column[UUID]("author_id")
    def price = column[BigDecimal]("price")

    def * : ProvenShape[Book] = (isbn, title, publicationYear, authorId, price) <> (Book.tupled, Book.unapply)
  }
}
