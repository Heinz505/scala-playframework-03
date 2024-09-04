package actor

import org.apache.pekko.actor.Actor
import domain._

class Employee extends Actor {
  def receive: Receive = {
    case Work(client) =>
      sender() ! WorkCompleted(client)
    case Bonus(_) =>
      sender() ! Gratitude
    case Memo =>
      sender() ! Explanation
    case SalaryDeduction =>
      sender() ! ResignationLetter
  }
}