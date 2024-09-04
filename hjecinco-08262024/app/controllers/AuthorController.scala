package controllers

import javax.inject._
import play.api.mvc._
import models.repo.AuthorRepository
import models.domain.Author
import scala.concurrent.{ExecutionContext, Future}
import java.util.UUID

@Singleton
class AuthorController @Inject()(cc: ControllerComponents, authorRepo: AuthorRepository)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def createAuthor(firstName: String, lastName: String): Action[AnyContent] = Action.async {
    val author = Author(UUID.randomUUID(), firstName, lastName)
    authorRepo.insert(author).map(id => Ok(s"Author created with ID: $id"))
  }
}
