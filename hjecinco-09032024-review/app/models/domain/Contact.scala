package models.domain

import slick.jdbc.PostgresProfile.api._

case class Contact(
  id: Int,
  firstName: String,
  middleName: Option[String],
  lastName: Option[String],
  phoneNumber: String,
  email: Option[String],
  groupId: Option[Long]
)

class Contacts(tag: Tag) extends Table[Contact](tag, "contacts") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("first_name")
  def middleName = column[Option[String]]("middle_name")
  def lastName = column[Option[String]]("last_name")
  def phoneNumber = column[String]("phone_number")
  def email = column[Option[String]]("email")
  def groupId = column[Option[Long]]("group_id")

  def * = (id.?, firstName, middleName, lastName, phoneNumber, email, groupId).mapTo[Contact]
}
