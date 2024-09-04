package models

import play.api.libs.json.{Json, OFormat}

case class User(
  username: String,
  password: String,
  age: Int,
  favoriteBookTitles: List[String]
)

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}
