package models

import play.api.libs.json.{Json, OFormat}

case class Book(title: String, author: String, yearPublished: Int)

object Book {
  implicit val bookFormat: OFormat[Book] = Json.format[Book]
}

