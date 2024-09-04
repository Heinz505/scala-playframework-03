import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MainClass {
  def main(args: Array[String]): Unit = {

    def f1: Future[Int] = Future.successful(1)
    def f2: Future[Option[Int]] = Future.successful(Some(10))
    def f3: Future[Option[Int]] = Future.successful(None)
    def f4: Future[Seq[Int]] = Future.successful(1 to 10)
    def f5: Future[Seq[Int]] = Future.successful(Nil)
    def f6: Future[Option[Seq[Int]]] = Future.successful(Some(1 to 10))
    def f7: Future[Option[Seq[Int]]] = Future.successful(None)
    def f8: Future[Seq[Option[Int]]] = Future.successful(Seq(Some(100), Some(1000), None))
    def f9: Future[Option[Future[Int]]] = Future.successful(Some(Future.successful(1)))
    def f10: Future[Seq[Future[Int]]] = Future.successful((1 to 10).map(Future.successful))

    //1.
    def sumF1F2: Future[Int] = for {
      value1 <- f1
      value2Opt <- f2
    } yield value2Opt.getOrElse(0) + value1

    //2.
    def sumF1F2F3: Future[Int] = for {
      value1 <- f1
      value2Opt <- f2
      value3Opt <- f3
    } yield value1 + value2Opt.getOrElse(0) + value3Opt.getOrElse(0)

    //3.
    def sumF4: Future[Int] = f4.map(_.sum)


    //4.
    def sumF4F5: Future[Int] = for {
      seq4 <- f4
      seq5 <- f5
    } yield (seq4 ++ seq5).sum

   


    //Some print to see on terminal
    println("Main")
  }
}

