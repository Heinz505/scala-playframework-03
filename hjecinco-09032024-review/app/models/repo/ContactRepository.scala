package models.repo

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.slick._
import models.domain.{Contact, Contacts}
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcProfile
import scala.concurrent.{Future, ExecutionContext}

class ContactRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider, 
  cc: ControllerComponents
)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  import dbConfig.profile.api._
  val contacts = TableQuery[Contacts]

  def create(contact: Contact): Future[Contact] = {
    val insertQuery = contacts returning contacts += contact
    db.run(insertQuery)
  }

  def list(): Future[Seq[Contact]] = db.run(contacts.result)

  def findById(id: Int): Future[Option[Contact]] = db.run(contacts.filter(_.id === id).result.headOption)

  def update(contact: Contact): Future[Int] = {
    val query = contacts.filter(_.id === contact.id).update(contact)
    db.run(query)
  }

  def delete(id: Int): Future[Int] = {
    val query = contacts.filter(_.id === id).delete
    db.run(query)
  }
}
