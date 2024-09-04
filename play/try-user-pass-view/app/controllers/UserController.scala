package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import play.api.data._
import play.api.data.Forms._
import forms.UserForm
import models.User
import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  // Define the registration form using Play Forms API
  val registrationForm: Form[User] = UserForm.registrationForm

  // Define the login form using Play Forms API
  val loginForm: Form[User] = UserForm.loginForm

  // Action to display the registration page
  def register() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.register(registrationForm))
  }

  // Action to handle the registration form submission
  def registerSubmit() = Action { implicit request: Request[AnyContent] =>
    registrationForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.register(formWithErrors)),
      userData => {
        // Handle successful registration
        Redirect(routes.UserController.dashboard()).flashing("success" -> "Registration successful!")
      }
    )
  }

  // Action to display the login page
  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login(loginForm))
  }

  // Action to handle the login form submission
  def loginSubmit() = Action { implicit request: Request[AnyContent] =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      userData => {
        // Handle successful login
        Redirect(routes.UserController.dashboard()).flashing("success" -> "Login successful!")
      }
    )
  }

  // Action to display the dashboard page
  def dashboard() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.dashboard())
  }

  // Optional: Action to handle logout
  def logout() = Action { implicit request: Request[AnyContent] =>
    // Clear the session or handle logout logic here
    Redirect(routes.UserController.login()).flashing("success" -> "You have been logged out.")
  }
}
