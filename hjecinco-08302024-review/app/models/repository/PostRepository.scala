package repositories

import models.Post
import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcProfile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val db = dbConfig.db

  private val posts = TableQuery[PostsTable]

  def create(post: Post): Future[Post] = db.run {
    (posts returning posts.map(_.id) into ((post, id) => post.copy(id = Some(id)))) += post
  }

  def list(): Future[Seq[Post]] = db.run {
    posts.sortBy(_.postAt.desc).result
  }

  // Define PostTable schema here
  private class PostsTable(tag: Tag) extends Table[Post](tag, "posts") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def postContent = column[String]("post_content")
    def userId = column[Long]("user_id")
    def postAt = column[Timestamp]("post_at")

    def * = (id.?, postContent, userId, postAt) <> ((Post.apply _).tupled, Post.unapply)
  }
}
