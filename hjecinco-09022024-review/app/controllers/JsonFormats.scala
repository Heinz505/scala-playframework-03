import domain.{User, Post}
import play.api.libs.json._

object JsonFormats {
  implicit val userFormat: Format[User] = Json.format[User]
  implicit val postFormat: Format[Post] = Json.format[Post]
}
