package controllers

import play.api.mvc._
import javax.inject._
import repository.UserRepository
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import domain.User

@Singleton
class UserController @Inject()(
  cc: ControllerComponents,
  val users: PostRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  import play.api.libs.json.Json

  def getPost(id: Long): Action[AnyContent] = Action.async {
    users.get.map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound
    }
  }

  // Other actions like create, update, delete
}