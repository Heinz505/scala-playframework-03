package models.repo

import akka.actor.ActorSystem
import javax.inject._
import play.api.cache.AsyncCacheApi
import play.api.libs.ws._
import models.domain.Pokemon
import play.api.Logger
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

@Singleton
class PokemonRepository @Inject()(
  ws: WSClient,
  cache: AsyncCacheApi
)(implicit ec: ExecutionContext, actorSystem: ActorSystem) {

  private val baseUrl = "https://pokeapi.co/api/v2/pokemon/"
  private val logger = Logger(this.getClass)

  def fetchPokemon(idOrName: String): Future[Option[Pokemon]] = {
    cache.getOrElseUpdate(s"pokemon:$idOrName") {
      ws.url(s"$baseUrl$idOrName").get().map { response =>
        response.status match {
          case 200 =>
            val json = response.json
            Some(Pokemon(
              (json \ "id").as[Int],
              (json \ "name").as[String],
              (json \ "height").as[Int],
              (json \ "weight").as[Int],
              (json \ "types").as[Seq[JsValue]].map(t => (t \ "type" \ "name").as[String])
            ))
          case _ =>
            logger.warn(s"Failed to fetch Pokemon data for $idOrName")
            None
        }
      }
    }
  }
}
