package repositories

import models.{Todo, Todos}
import slick.jdbc.H2Profile.api._
import scala.concurrent.{Future, ExecutionContext}

class TodoRepository(db: Database)(implicit ec: ExecutionContext) {
  private val todos = TableQuery[Todos]

  def create(todo: Todo): Future[Todo] = {
    val action = todos.returning(todos.map(_.id)).into((todo, id) => todo.copy(id = Some(id))) += todo
    db.run(action)
  }

  def list(): Future[Seq[Todo]] = db.run(todos.result)

  def findById(id: Long): Future[Option[Todo]] = db.run(todos.filter(_.id === id).result.headOption)

  def update(todo: Todo): Future[Int] = db.run(todos.filter(_.id === todo.id.get).update(todo))

  def delete(id: Long): Future[Int] = db.run(todos.filter(_.id === id).delete)
}
