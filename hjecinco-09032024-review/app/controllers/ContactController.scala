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
        contact => {
          Future.successful(Redirect(routes.ContactController.listContacts()))
        }
      )
  }


  def updateContact(id: Long) = Action.async(parse.formUrlEncoded) { implicit request =>
    val firstName = request.body.get("first_name").flatMap(_.headOption)
    val middleName = request.body.get("middle_name").flatMap(_.headOption)
    val lastName = request.body.get("last_name").flatMap(_.headOption)
    val phoneNumber = request.body.get("phone_number").flatMap(_.headOption)
    val email = request.body.get("email").flatMap(_.headOption)

    val contact = Contact(Some(id), firstName, middleName, lastName, phoneNumber, email, None)

    contactRepo.update(id, contact).map { _ =>
      Redirect(routes.ContactController.listContacts())
    }
  }

  def deleteContact(id: Long) = Action.async {
    contactRepo.delete(id).map { _ =>
      Redirect(routes.ContactController.listContacts())
    }
  }
}
