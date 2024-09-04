package repositories

import models.Comment
import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcProfile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val db = dbConfig.db

  private val comments = TableQuery[CommentsTable]

  def create(comment: Comment): Future[Comment] = db.run {
    (comments returning comments.map(_.id) into ((comment, id) => comment.copy(id = Some(id)))) += comment
  }

  def findByPostId(postId: Long): Future[Seq[Comment]] = db.run {
    comments.filter(_.postId === postId).result
  }

  // Define CommentTable schema here
  private class CommentsTable(tag: Tag) extends Table[Comment](tag, "comments") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def commentContent = column[String]("comment_content")
    def postId = column[Long]("post_id")
    def commentAt = column[Timestamp]("comment_at")

    def * = (id.?, commentContent, postId, commentAt) <> ((Comment.apply _).tupled, Comment.unapply)
  }
}
