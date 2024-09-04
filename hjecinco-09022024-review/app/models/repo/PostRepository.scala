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
class PostRepository @Inject()(
	protected val dbConfigProvider: DatabaseConfigProvider,
	cc: ControllerComponents
)(implicit ec: ExecutionContext)
extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

	import dbConfig.profile.api._

	class PostsTable(tag: Tag) extends Table[Post](tag, "POST") {
	  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
	  def postText = column[String]("POSTTEXT")
	  def userId = column[Long]("ID_USER")
	  def imgUrl = column[Option[String]]("IMGURL")

	  def * = (id, postText, userId, imgUrl).mapTo[Post]
	}

	val posts = TableQuery[PostsTable]

	def get = {
		def action = {
			posts.filter(_.id === id).result.headOption
		}
		db.run(action)
	}
}

