package controllers

import play.api.mvc._
import javax.inject._
import repository.PostRepository
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import domain.Post

@Singleton
class PostController @Inject()(
	cc: ControllerComponents,
	val posts: PostRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  import play.api.libs.json.Json

  def getPost(id: Long): Action[AnyContent] = Action.async {
    posts.get.map {
      case Some(post) => Ok(Json.toJson(post))
      case None => NotFound
    }
  }

  // Other actions like create, update, delete
}
