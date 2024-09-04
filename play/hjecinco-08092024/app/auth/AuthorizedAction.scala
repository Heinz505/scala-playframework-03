package auth

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

class AuthorizedAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    request.session.get("email") match {
      case Some(_) => block(request)
      case None => Future.successful(Results.Unauthorized("Unauthorized"))
    }
  }
}
