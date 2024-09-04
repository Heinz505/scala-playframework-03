import slick.jdbc.PostgresProfile.api._
import java.sql.Blob
import java.time.LocalDate

class Employees(tag: Tag) extends Table[(Int, String, Option[String], String, Option[LocalDate], String, Option[Blob])](tag, "EMPLOYEES") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("FIRST_NAME")
  def middleNameInitial = column[Option[String]]("MIDDLE_NAME_INITIAL")
  def lastName = column[String]("LAST_NAME")
  def hiredOn = column[Option[LocalDate]]("HIRED_ON")
  def email = column[String]("EMAIL")
  def photo = column[Option[Blob]]("PHOTO")

  def * = (id, firstName, middleNameInitial, lastName, hiredOn, email, photo)

  def uniqueEmail = index("idx_email", email, unique = true)
  def idxHiredOn = index("IDX_HIRED_ON", hiredOn)
}

val employees = TableQuery[Employees]
