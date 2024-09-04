/*

package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import repositories.TodoRepository
import models.Todo
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoController @Inject()(repo: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val todoFormat: Format[Todo] = Json.format[Todo]

  def listTodos() = Action.async {
    repo.list().map { todos =>
      Ok(Json.toJson(todos))
    }
  }

  def createTodo() = Action.async(parse.json) { request =>
    request.body.validate[Todo].map { todo =>
      repo.create(todo).map { createdTodo =>
        Created(Json.toJson(createdTodo))
      }
    }.getOrElse(Future.successful(BadRequest("Invalid data format")))
  }

  def updateTodo() = Action.async(parse.json) { request =>
    request.body.validate[Todo].map { todo =>
      repo.update(todo).map { _ =>
        Ok(Json.toJson(todo))
      }
    }.getOrElse(Future.successful(BadRequest("Invalid data format")))
  }

  def deleteTodo(id: Long) = Action.async {
    repo.delete(id).map { _ =>
      NoContent
    }
  }
}

*/