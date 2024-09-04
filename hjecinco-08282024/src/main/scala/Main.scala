import slick.jdbc.H2Profile.api._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  // Create a database connection
  val db = Database.forConfig("slick.db")

  // Instantiate repository
  val accountRepo = new repo.AccountRepository(db)

  // Sample usage
  val accountId = UUID.randomUUID()
  val profileId = UUID.randomUUID()
  val now = new Timestamp(System.currentTimeMillis())

  val account = (accountId, "test@example.com", domain.Status.ACTIVE, now)
  val profile = (profileId, "John", "Doe", domain.Gender.MALE)
  val authHistory = (accountId, success = true, now)

  val action = accountRepo.insertData(account, profile, authHistory)
  val result = db.run(action)

  Await.result(result, Duration.Inf)
  println("Data inserted")
}
