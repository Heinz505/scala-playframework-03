package repo

import domain._
import slick.jdbc.H2Profile.api._
import java.sql.Timestamp
import java.util.UUID
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration

class AccountRepository(db: Database) {
  private val accounts = TableQuery[Accounts]
  private val profiles = TableQuery[Profiles]
  private val accountAuthHistories = TableQuery[AccountAuthHistories]

  // #2 Insert data
  def insertData(account: (UUID, String, Status.Value, Timestamp),
                 profile: (UUID, String, String, Gender.Value),
                 authHistory: (UUID, Boolean, Timestamp)): DBIO[Unit] = {
    (accounts += account) andThen
    (profiles += profile) andThen
    (accountAuthHistories += authHistory)
  }

  // #3 Get all female users' accounts that have a profile
  def getFemaleUsersWithProfile(): DBIO[Seq[(UUID, String, Status.Value, Timestamp)]] = {
    val query = for {
      (a, p) <- accounts join profiles on (_.id === _.accountId)
      if p.gender === Gender.FEMALE
    } yield (a.id, a.email, a.status, a.createdAt)
    query.result
  }

  // #4 Update profile first name and last name
  def updateProfile(accountId: UUID, newFirstName: String, newLastName: String): DBIO[Int] = {
    profiles.filter(_.accountId === accountId)
      .map(p => (p.firstName, p.lastName))
      .update((newFirstName, newLastName))
  }

  // #5 Find profile by keyword in name
  def findProfileByNameKeyword(keyword: String): DBIO[Seq[(UUID, String, String, Gender.Value)]] = {
    profiles.filter(p => p.firstName.startsWith(keyword) || p.lastName.startsWith(keyword)).result
  }

  // #6 Get all accounts created between date range
  def getAccountsBetweenDates(startDate: Timestamp, endDate: Timestamp): DBIO[Seq[(UUID, String, Status.Value, Timestamp)]] = {
    accounts.filter(a => a.createdAt >= startDate && a.createdAt <= endDate).result
  }

  // #7 Count accounts created between date range
  def countAccountsBetweenDates(startDate: Timestamp, endDate: Timestamp): DBIO[Int] = {
    accounts.filter(a => a.createdAt >= startDate && a.createdAt <= endDate).length.result
  }

  // #8 Get profile by email
  def getProfileByEmail(email: String): DBIO[Option[(UUID, String, String, Gender.Value)]] = {
    val query = for {
      a <- accounts if a.email === email
      p <- profiles if p.accountId === a.id
    } yield (p.accountId, p.firstName, p.lastName, p.gender)
    query.result.headOption
  }

  // #9 Delete account by id
  def deleteAccountById(id: UUID): DBIO[Int] = {
    val deleteProfile = profiles.filter(_.accountId === id).delete
    val deleteAuthHistories = accountAuthHistories.filter(_.accountId === id).delete
    val deleteAccount = accounts.filter(_.id === id).delete

    deleteProfile andThen deleteAuthHistories andThen deleteAccount
  }
}
