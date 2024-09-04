package models

import play.api.libs.json.{Json, OFormat}

case class User(id: Option[Long], username: String, password: String, email: String)

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}