import slick.jdbc.PostgresProfile.api._
import java.util.UUID
import java.sql.Blob


class Keys(tag: Tag) extends Table[(UUID, Blob)](tag, "KEYS") {
  def userId = column[UUID]("USER_ID", O.PrimaryKey)
  def key = column[Blob]("KEY")

  def * = (userId, key)

  def userFk = foreignKey("fk_user", userId, users)(_.id, onDelete = ForeignKeyAction.Cascade)
}

val keys = TableQuery[Keys]