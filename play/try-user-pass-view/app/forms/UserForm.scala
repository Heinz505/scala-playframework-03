package forms

import play.api.data._
import play.api.data.Forms._
import models.User

object UserForm {

  // Define the registration form
  val registrationForm: Form[User] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply)(User.unapply)
  )

  // Define the login form
  val loginForm: Form[User] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply)(User.unapply)
  )
}
