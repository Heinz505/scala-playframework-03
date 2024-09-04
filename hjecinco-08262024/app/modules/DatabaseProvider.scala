package modules

import com.google.inject.{Inject, Provider}
import play.api.db.{Database => PlayDatabase}
import slick.jdbc.JdbcProfile
import javax.inject.Singleton

@Singleton
class DatabaseProvider @Inject()(playDatabase: PlayDatabase, profile: JdbcProfile) extends Provider[PlayDatabase] {
  override def get(): PlayDatabase = playDatabase
}
