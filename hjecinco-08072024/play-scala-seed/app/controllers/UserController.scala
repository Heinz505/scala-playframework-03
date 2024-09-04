package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.Json
import services.UserService
import models.User
import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def registerUser(): Action[JsValue] = Action.async(parse.json) { request =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      user => {
        userService.registerUser(user).map(id => Created(Json.obj("message" -> "User registered successfully", "userId" -> id)))
      }
    )
  }

  def updateUser(userId: Long): Action[JsValue] = Action.async(parse.json) { request =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      user => {
        userService.updateUser(userId, user).map(_ => Ok(Json.obj("message" -> "User updated successfully")))
      }
    )
  }

  def loginUser(): Action[JsValue] = Action.async(parse.json) { request =>
    val loginInfo = for {
      username <- (request.body \ "username").asOpt[String]
      password <- (request.body \ "password").asOpt[String]
    } yield (username, password)

    loginInfo match {
      case Some((username, password)) =>
        userService.findUser(username, password).map {
          case Some(user) => Ok(Json.obj("message" -> "Login successful", "token" -> "mock-jwt-token"))
          case None => Unauthorized(Json.obj("message" -> "Invalid username or password"))
        }
      case None => Future.successful(BadRequest(Json.obj("message" -> "Username and password required")))
    }
  }

  def logoutUser(): Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("message" -> "Logout successful"))
  }
}
