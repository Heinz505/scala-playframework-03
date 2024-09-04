package example

import example.ColoredPrint.{printlog}

import org.apache.pekko.actor.{ Actor, ActorRef, ActorSystem, PoisonPill, Props }
import language.postfixOps
import scala.concurrent.duration._

case object Ping
case object Pong

object PingPong extends App {

  class Pinger extends Actor {
    var countDown = 100

    def receive = {
      case Pong =>
        printlog(s"${self.path} received pong, count down $countDown")

        if (countDown > 0) {
          countDown -= 1
          sender() ! Ping
        } else {
          sender() ! PoisonPill
          self ! PoisonPill
        }
    }
  }

  class Ponger(pinger: ActorRef) extends Actor {
    def receive = {
      case Ping =>
        printlog(s"${self.path} received ping")
        pinger ! Pong
    }
  }

  val system = ActorSystem("pingpong")

  val pinger = system.actorOf(Props[Pinger](), "pinger")

  val ponger = system.actorOf(Props(classOf[Ponger], pinger), "ponger")

  import system.dispatcher
  system.scheduler.scheduleOnce(500 millis) {
    ponger ! Ping
  }
}