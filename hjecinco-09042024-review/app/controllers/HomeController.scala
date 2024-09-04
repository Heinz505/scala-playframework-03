package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.cache._
import play.api.cache.AsyncCacheApi
import scala.concurrent.{Future,ExecutionContext}
import scala.concurrent.duration._
import play.api.cache.Cached

import play.api.libs.ws.WSClient
import play.api.libs.ws.JsonBodyReadables._
import play.api.libs.ws.JsonBodyWritables._

import play.api.libs.json._

import play.api.data._
import play.api.i18n._


case class Ability (name:String,url:String)
object Ability{
  given Format[Ability] = Json.format[Ability]
 
}
case class Move (name:String,url:String)
object Move{
  given Format[Move] = Json.format[Move]
 
}

@Singleton
class HomeController @Inject()(ws: WSClient, cache: AsyncCacheApi, cc: MessagesControllerComponents)(using ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val urlPoke = "https://pokeapi.co/api/v2/pokemon"

  def request(url: String) = ws.url(url).get().map(_.body)

  def index() = Action {
    Ok(views.html.index())
  }

  def pokemon(id: String) = Action.async {
    val cacheKey = s"pokemon-$id"
    
    cache.getOrElseUpdate(cacheKey, 10.minutes) {
      request(urlPoke + s"/${id.toLowerCase}").map { response =>
        val strJson = Json.parse(response)
        val abilityLength = ((strJson \ "abilities").toString.split("ability").toList.length - 1) / 2
        val abilityList = (0 until abilityLength).map { x =>
          (strJson \ "abilities" \ x \ "ability").as[Ability]
        }.toList.map(_.name.toLowerCase.split(' ').map(_.capitalize).mkString(" "))
        val name = (strJson \ "forms" \ 0 \ "name").as[String]
        val height = (strJson \ "height").as[Int]
        val weight = (strJson \ "weight").as[Int]
        val pic = (strJson \ "sprites" \ "front_default").as[String]
        val moveList = (0 until abilityLength).map { x =>
          (strJson \ "moves" \ x \ "move").as[Move]
        }.toList.map(_.name.toLowerCase.split(' ').map(_.capitalize).mkString(" "))

        Ok(views.html.details(name, weight, height, pic, abilityList, moveList))
      }.recover {
        case e: Exception => NotFound(views.html.details(err = s"Pokemon with name/id ${id} not found"))
      }
    }
  }
}

