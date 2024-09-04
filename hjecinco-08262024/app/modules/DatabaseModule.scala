package modules

import com.google.inject.AbstractModule
import play.api.db.Database
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile

import scala.concurrent.ExecutionContext

class DatabaseModule extends AbstractModule {

  override def configure(): Unit = {
    // Bind the Slick database profile
    bind(classOf[JdbcProfile]).toInstance(PostgresProfile)
    
    // Bind the Slick Database instance
    bind(classOf[Database]).toProvider(classOf[DatabaseProvider])

    // Bind the ExecutionContext for database operations
    bind(classOf[ExecutionContext]).toProvider(classOf[ExecutionContextProvider])
  }
}
