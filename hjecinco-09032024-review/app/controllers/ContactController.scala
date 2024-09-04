package controllers

import javax.inject._
import models.domain.Contact
import forms._
import play.api.i18n._
import models.repo.ContactRepository
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.JdbcProfile


@Singleton
class ContactController @Inject()(
  cc: ControllerComponents,
  contactRepo: ContactRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  def listContacts() = Action.async {
    contactRepo.list().map { contacts =>
      Ok(views.html.contact_list(contacts))
    }
  }

//   def viewContact(id: Long) = Action.async {
//     contactRepo.findById(id).map { c =>
//       c match {
//         case Some(contact) => Ok(views.html.contact_form(contactForm))
//         case None => NotFound("Contact not found")
//       }
//     }
//   }

  def addContact = Action.async { implicit request =>
    contactForm
      .bindFromRequest()
      .fold(
        formWithErrors => {
          Future.successful(BadRequest(views.html.contact_form(formWithErrors)))
          
        },
        data => {
          val firstName = data.firstName
          val middleName = data.middleName
          val lastName = data.lastName
          val phoneNumber = data.phoneNumber
          val email = data.email

          val contact = Contact(0, firstName, middleName, lastName, phoneNumber, email, None)

          contactRepo.create(contact) map { _ =>
            Redirect(routes.ContactController.listContacts())
          }
        }
      )
  }

  def updateContact(id: Int) = Action.async(parse.formUrlEncoded) { implicit request =>
    contactForm
      .bindFromRequest()
      .fold(
        formWithErrors => {
          Future.successful(BadRequest(views.html.contact_form(formWithErrors)))
          
        },
        data => {
          val firstName = data.firstName
          val middleName = data.middleName
          val lastName = data.lastName
          val phoneNumber = data.phoneNumber
          val email = data.email

          val contact = Contact(0, firstName, middleName, lastName, phoneNumber, email, None)

          contactRepo.update(contact) map { _ =>
            Redirect(routes.ContactController.listContacts())
          }
        }
      )
  }

  def deleteContact(id: Int) = Action.async {
    contactRepo.delete(id).map { _ =>
      Redirect(routes.ContactController.listContacts())
    }
  }
}
