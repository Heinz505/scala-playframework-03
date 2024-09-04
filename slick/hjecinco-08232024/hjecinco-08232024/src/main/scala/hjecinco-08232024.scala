package tables

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.UUID

import slick.jdbc.H2Profile.api._

object Main {

	// Tables

	case class Accounts(
		id 	: UUID,
		email 	: String
	)

	class AccountsTable(tag: Tag) extends Table(UUID, String)(tag, "ACCOUNTS") {
		def id = column[UUID]("ID", O.PrimaryKey)
  		def email = column[String]("EMAIL")
  
  		def * = (id, email)
	}

	lazy val AccountsTable = TableQuery[AccountsTable]


	case class Profiles(
		accountId 	: UUID,
		firstName 	: String,
		lastName	: String,
		gender		: String
	)

	class ProfilesTable(tag: Tag) extends Table[(UUID, String, String, Char)](tag, "PROFILES") {
	  def accountId = column[UUID]("ACCOUNT_ID", O.PrimaryKey)
	  def firstName = column[String]("FIRST_NAME")
	  def lastName = column[String]("LAST_NAME")
	  def gender = column[Char]("GENDER", O.Length(1))

	  def * = (accountId, firstName, lastName, gender)

	  // Foreign key constraint
	  def account = foreignKey("FK_ACCOUNT", accountId, accounts)(_.id)
	}

	lazy val AccountsTable = TableQuery[AccountsTable]


	object ProfileRepo {
	  def createSchema(implicit db: Database): Future[Unit] = {
	    db.run(DBIO.seq(
	      profiles.schema.dropIfExists,
	      profiles.schema.create
	    ))
	  }

	  def add(profile: (UUID, String, String, Char))(implicit db: Database): Future[Int] = {
	    db.run(profiles += profile)
	  }

	  def findById(accountId: UUID)(implicit db: Database): Future[Option[(UUID, String, String, Char)]] = {
	    db.run(profiles.filter(_.accountId === accountId).result.headOption)
	  }

	  def filterByGender(gender: Char)(implicit db: Database): Future[Seq[(UUID, String, String, Char)]] = {
	    db.run(profiles.filter(_.gender === gender).result)
	  }

	  def get(implicit db: Database): Future[Seq[(UUID, String, String, Char)]] = {
	    db.run(profiles.result)
	  }
	}


	val db = Database.forConfig("h2mem1") 

  val account1ID = UUID.fromString("ef49bdf8-d9b8-40bf-9bc6-38fb3cd755a5")
  val account2ID = UUID.fromString("23ac66c0-f773-44fd-9990-6a01f561b89a")

  val accountOps = for {
    _ <- AccountRepo.createSchema
    _ <- AccountRepo.add(account1ID, "email1")
    account <- AccountRepo.findById(account1ID)
    _ <- AccountRepo.add(account2ID, "email2")
    accounts <- AccountRepo.get
  } yield {
    println(s"Accounts schema created & populated: [${accounts.map(transformAccountToString).mkString(", ")}]") 
  }

  val profileOps = for {
    _ <- ProfileRepo.createSchema
    _ <- ProfileRepo.add(account1ID, "firstName1", "lastName1", 'M')
    _ <- ProfileRepo.add(account2ID, "firstName2", "lastName2", 'F')
    profiles <- ProfileRepo.get
    profile <- ProfileRepo.findById(account1ID)
    profilesByGender <- ProfileRepo.filterByGender('M')
  } yield {
    println(s"Profiles schema created & populated: [${profiles.map(transformProfileToString).mkString(", ")}]") 
    profile match {
      case Some(p) => println(s"Found profile for account 1: ${transformProfileToString(p)}")
      case None => println(s"Account 1 profile not found")
    }
    println(s"Profiles by gender: ${profilesByGender.map(transformProfileToString).mkString(", ")}")
  }


  db.run(accountOps).onComplete(_ => db.run(profileOps).onComplete(_ => db.close()))


}