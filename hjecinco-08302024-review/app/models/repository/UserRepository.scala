package repositories

import models.User
import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcProfile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val db = dbConfig.db

  private val users = TableQuery[UsersTable]

  def create(user: User): Future[User] = db.run {
    (users returning users.map(_.id) into ((user, id) => user.copy(id = Some(id)))) += user
  }

  def findByUsername(username: String): Future[Option[User]] = db.run {
    users.filter(_.username === username).result.headOption
  }

  // Define UserTable schema here
  private class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")

    def * = (id.?, username, password) <> ((User.apply _).tupled, User.unapply)
  }
}