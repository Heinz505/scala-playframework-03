package models.repo

import models.domain.Author
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import java.util.UUID
import javax.inject.Inject

class AuthorRepository @Inject()(db: Database, profile: JdbcProfile)(implicit ec: ExecutionContext) {

  private val authors = TableQuery[Authors]

  def insert(author: Author): Future[UUID] = {
    val query = (authors returning authors.map(_.id)) += author
    db.run(query)
  }

  private class Authors(tag: Tag) extends Table[Author](tag, "authors") {
    def id = column[UUID]("id", O.PrimaryKey)
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")

    def * : ProvenShape[Author] = (id, firstName, lastName) <> (Author.tupled, Author.unapply)
  }
}
