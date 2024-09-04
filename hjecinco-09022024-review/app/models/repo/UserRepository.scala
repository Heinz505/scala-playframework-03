package repository

import play.api._
import play.api.mvc._
import javax.inject._
import play.api.db.slick._

import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcProfile
import domain.Post

import scala.concurrent.ExecutionContext

@Singleton
class UserRepository @Inject()(
	protected val dbConfigProvider: DatabaseConfigProvider,
	cc: ControllerComponents
)(implicit ec: ExecutionContext)
extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

	import dbConfig.profile.api._

	class UsersTable(tag: Tag) extends Table[User](tag, "USER") {
	  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
	  def username = column[String]("USERNAME")
	  def password = column[String]("PASSWORD")

	  def * = (id, username, password).mapTo[User]
	}

  	val users = TableQuery[UsersTable]

	def get = {
		def action = {
			posts.filter(_.id === id).result.headOption
		}
		db.run(action)
	}
}
