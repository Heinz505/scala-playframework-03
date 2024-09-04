package actor

import org.apache.pekko.actor.{ Actor, ActorRef }
import domain._
import domain.Rating._

class Employer(employee: ActorRef) extends Actor {
  def receive: Receive = {
    case ProjectPlan =>
      employee ! Work(sender())
    case WorkCompleted(client) =>
      client ! ProjectCompleted
    case rating: Rating =>
      rating match {
        case Excellent =>
          employee ! Bonus(5000)
        case Good =>
          employee ! Bonus(4000)
        case Average =>
          employee ! Bonus(3000)
        case Bad =>
          employee ! Memo
        case NeverAgain =>
          employee ! SalaryDeduction
      }
  }
}
