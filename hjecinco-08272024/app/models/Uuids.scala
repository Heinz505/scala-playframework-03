import slick.jdbc.PostgresProfile.api._
import java.util.UUID

class Uuids(tag: Tag) extends Table[(Long, Long)](tag, "UUIDS") {
  def msb = column[Long]("MSB")
  def lsb = column[Long]("LSB")

  def * = (msb, lsb)

  def pk = primaryKey("pk_uuids", (msb, lsb))
}

val uuids = TableQuery[Uuids]
