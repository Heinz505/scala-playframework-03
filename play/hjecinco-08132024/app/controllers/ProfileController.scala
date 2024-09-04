package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Profile

@Singleton
class ProfileController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val namePattern = ".*\\d+.*"

  val profileForm: Form[Profile] = Form(
    mapping(
      "firstName" -> nonEmptyText(maxLength = 250).verifying("Must not contain digits", !_.matches(namePattern)),
      "middleName" -> optional(text(maxLength = 250).verifying("Must not contain digits", !_.matches(namePattern))),
      "lastName" -> nonEmptyText(maxLength = 250).verifying("Must not contain digits", !_.matches(namePattern)),
      "age" -> number(min = 0)
    )(Profile.apply)(Profile.unapply)
  )

  def showProfileForm: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.profile(profileForm))
  }

  def createProfile(): Action[AnyContent] = Action { implicit request =>
    profileForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.profile(formWithErrors)),
      profile => Ok("Form values received successfully") // You might want to save `profile` to a database here.
    )
  }
}
