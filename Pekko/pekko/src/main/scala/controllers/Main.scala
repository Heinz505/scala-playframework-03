package example

object ColoredPrint {
  def printlog(x: Any): Unit = {
    println(Console.YELLOW + x + Console.RESET)
  }
}
import ColoredPrint.{printlog}

import org.apache.pekko
import pekko.actor.{ Actor, ActorRef, ActorSystem, PoisonPill, Props }
import language.postfixOps
import scala.concurrent.duration._

object SimpleActorExample extends App {
  class SimpleActor extends Actor {
    def receive = {
      case s: String => printlog(s"String: $s")
      case i: Int => printlog(s"Int: $i")
    }

    def foo = printlog("Normal Method")
  }

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor")

  printlog("Before messages")
  actor ! "Hi there."
  printlog("After string")
  actor ! 42
  printlog("After Int")
  actor ! 'a'
  printlog("After char")

  system.terminate()
}