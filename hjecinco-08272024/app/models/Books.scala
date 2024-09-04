import slick.jdbc.PostgresProfile.api._
import java.time.LocalDate

class Books(tag: Tag) extends Table[(Int, String, String, String, Option[LocalDate], Option[BigDecimal])](tag, "BOOKS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def isbn = column[String]("ISBN")
  def title = column[String]("TITLE")
  def author = column[String]("AUTHOR")
  def publishedOn = column[Option[LocalDate]]("PUBLISHED_ON")
  def price = column[Option[BigDecimal]]("PRICE")

  def * = (id, isbn, title, author, publishedOn, price)
  
  def uniqueIsbn = index("idx_isbn", isbn, unique = true)
}

val books = TableQuery[Books]
