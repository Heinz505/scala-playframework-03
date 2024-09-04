package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

case class User(id: Int, name: String)

@Singleton
class CustomController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  private def findUserById(id: Int): Future[Option[User]] = {
    val users = List(
      User(1, "John"),
      User(2, "William"),
      User(3, "James"),
      User(4, "Michael"),
      User(5, "Robert"),
      User(6, "David"),
      User(7, "Thomas"),
      User(8, "Charles"),
      User(9, "George"),
      User(10, "Joseph"))
    Future.successful(users.find(_.id == id))
  }

  def question1: Action[AnyContent] = Action { implicit request: Request[AnyContent] => Ok("Hello World").as("text/plain") }

  def question2: Action[AnyContent] = Action { implicit request: Request[AnyContent] => Ok("<html></html>").as("application/xml") }

  def question3: Action[AnyContent] = Action { implicit request: Request[AnyContent] => Ok("<html></html>").as("text/html") }

  def question4: Action[AnyContent] = Action { implicit request: Request[AnyContent] => Ok(views.html.customHtml()) }

  def question5: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val clientIp = request.remoteAddress
    Ok(clientIp).as("text/plain")
  }

  def question6: Action[JsValue] = Action(parse.json) { request: Request[JsValue] =>
    request.body.asOpt[JsValue] match {
      case Some(json) => Ok(json)
      case None => Ok("JSON body empty")
    }
  }

  def question7: Action[AnyContent] = Action.async { request =>
    val maybeUserId = request.body.asFormUrlEncoded.flatMap(_.get("user").flatMap(_.headOption)).flatMap(s => scala.util.Try(s.toInt).toOption)
    
    maybeUserId match {
      case Some(userId) =>
        findUserById(userId).map {
          case Some(user) =>
            Ok(user.toString).withSession("userId" -> userId.toString)
          case None =>
            NotFound
        }
      case None =>
        Future.successful(BadRequest("Invalid or user ID not found"))
    }
  }
}
