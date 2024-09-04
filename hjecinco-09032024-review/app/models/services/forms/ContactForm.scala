package forms
import play.api.data._
import play.api.data.Forms._

case class ContactData(
  firstName: String,
  middleName: Option[String],
  lastName: Option[String],
  phoneNumber: String,
  email: Option[String]
)

object ContactData {
  def unapply(c: ContactData): Option[(String, Option[String], Option[String], String, Option[String])] = 
    Some((
      c.firstName, 
      c.middleName,
      c.lastName,
      c.phoneNumber,
      c.email
    ))
}

val contactForm = Form(
  mapping(
    "firstName" -> nonEmptyText(maxLength = 255),
    "middleName" -> optional(text(maxLength = 255)),
    "lastName" -> optional(text(maxLength = 255)),
    "phoneNumber" -> nonEmptyText(maxLength = 50),
    "email" -> optional(email)

  )(ContactData.apply)(ContactData.unapply)
)
