import slick.jdbc.PostgresProfile.api._
import java.util.UUID
import java.sql.Blob


class Users(tag: Tag) extends Table[(UUID, String, String)](tag, "USERS") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def email = column[String]("EMAIL")
  def nickname = column[String]("NICKNAME")

  def * = (id, email, nickname)

  def uniqueEmail = index("idx_email", email, unique = true)
}

val users = TableQuery[Users]


