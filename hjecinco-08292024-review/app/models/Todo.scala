package models

import java.sql.Timestamp
import slick.jdbc.H2Profile.api._

case class Todo(id: Option[Long], task: String, dueDate: Option[Timestamp], status: Boolean)

class Todos(tag: Tag) extends Table[Todo](tag, "TODO") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def task = column[String]("TASK")
  def dueDate = column[Option[Timestamp]]("DUE_DATE")
  def status = column[Boolean]("STATUS")

  def * = (id.?, task, dueDate, status).mapTo[Todo]
}
