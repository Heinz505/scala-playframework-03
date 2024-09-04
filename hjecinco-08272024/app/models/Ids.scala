import slick.jdbc.PostgresProfile.api._
import java.util.UUID

class Ids(tag: Tag) extends Table[(UUID)](tag, "IDS") {
  def id = column[UUID]("ID", O.PrimaryKey)
  
  def * = id
}

val ids = TableQuery[Ids]
