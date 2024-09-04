package auth

import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

class FakeAuthorizedAction(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    block(request) // Just pass through the request for testing
  }
}
