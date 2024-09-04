package actors

import org.apache.pekko.actor.{Actor, Props}
import org.apache.pekko.event.Logging

class MyActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
  }
}

object MyActor {
  def props: Props = Props[MyActor]
}
