package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import auth.AuthorizedAction
import play.twirl.api.Html

@Singleton
class HomeController @Inject()(authorizedAction: AuthorizedAction, val controllerComponents: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController {

  def createSession = Action.async { implicit request =>
    Future.successful(Ok.withSession("email" -> "user@gmail.com"))
  }

  def home = authorizedAction.async { implicit request =>
    Future.successful(Ok("You are logged in"))
  }

  def index = Action { implicit request =>
    Ok(views.html.index())  // Call index without parameters
  }


  
}
