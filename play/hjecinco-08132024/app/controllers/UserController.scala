package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.User

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val userForm: Form[User] = Form(
    mapping(
      "email" -> email,
      "username" -> nonEmptyText(maxLength = 100),
      "password" -> nonEmptyText(minLength = 8, maxLength = 250)
    )(User.apply)(User.unapply)
  )

  def showUserForm: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.user(userForm))
  }

  def createUser(): Action[AnyContent] = Action { implicit request =>
    userForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.user(formWithErrors)),
      user => Ok("Form values received successfully") // You might want to save `user` to a database here.
    )
  }
}
