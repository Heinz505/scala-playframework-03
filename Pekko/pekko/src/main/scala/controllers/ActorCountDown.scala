package example

import example.ColoredPrint.{printlog}

import org.apache.pekko
import pekko.actor.{ Actor, ActorRef, ActorSystem, PoisonPill, Props }
import language.postfixOps
import scala.concurrent.duration._

object ActorCountDown extends App {
	case class StartCounting(n: Int, other: ActorRef)
	case class CountDown(n: Int)

	class CountDownActor extends Actor {
		def receive = {
			case StartCounting(n, other) => {
				printlog(n)
				other ! CountDown(n-1)
			}
			case CountDown(n) => {
				printlog(self.path)
				if (n>0) {
					printlog(n)
					sender ! CountDown(n-1)
				} else {
					context.system.terminate()
				}
			}
		}

		def foo = printlog("Normal Method")
	}

	val system = ActorSystem("CountDownSystem")
	val actor1 = system.actorOf(Props[CountDownActor], "CountDown1")
	val actor2 = system.actorOf(Props[CountDownActor], "CountDown2")

	actor1 ! StartCounting(10, actor2)

}