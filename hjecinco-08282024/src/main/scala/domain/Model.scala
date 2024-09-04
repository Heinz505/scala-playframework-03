package domain

import slick.jdbc.H2Profile.api._
import java.util.UUID
import java.sql.Timestamp

// Enumeration types for Slick
object Status extends Enumeration {
  type Status = Value
  val ACTIVE, INACTIVE, DISABLED = Value
}

object Gender extends Enumeration {
  type Gender = Value
  val MALE, FEMALE, OTHER = Value
}

// Table definitions
class Accounts(tag: Tag) extends Table[(UUID, String, Status.Value, Timestamp)](tag, "ACCOUNTS") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def email = column[String]("EMAIL")
  def status = column[Status.Value]("STATUS")
  def createdAt = column[Timestamp]("CREATED_AT")

  def * = (id, email, status, createdAt)
}

class Profiles(tag: Tag) extends Table[(UUID, String, String, Gender.Value)](tag, "PROFILES") {
  def accountId = column[UUID]("ACCOUNT_ID", O.PrimaryKey)
  def firstName = column[String]("FIRST_NAME")
  def lastName = column[String]("LAST_NAME")
  def gender = column[Gender.Value]("GENDER")

  def * = (accountId, firstName, lastName, gender)

  def account = foreignKey("FK_ACCOUNTS", accountId, TableQuery[Accounts])(_.id, onDelete = ForeignKeyAction.Cascade)
}

class AccountAuthHistories(tag: Tag) extends Table[(UUID, Boolean, Timestamp)](tag, "ACCOUNT_AUTH_HISTORIES") {
  def accountId = column[UUID]("ACCOUNT_ID")
  def isSuccess = column[Boolean]("IS_SUCCESS")
  def attemptedAt = column[Timestamp]("ATTEMPTED_AT")
  
  def * = (accountId, isSuccess, attemptedAt)
  
  def account = foreignKey("FK_ACCOUNTS", accountId, TableQuery[Accounts])(_.id, onDelete = ForeignKeyAction.Cascade)
  
  def pk = primaryKey("PK_ACCOUNT_AUTH", (accountId, attemptedAt))
}
