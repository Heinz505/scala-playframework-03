package services

import javax.inject._
import models.repo.PokemonRepository
import models.domain.Pokemon
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PokemonService @Inject()(pokemonRepository: PokemonRepository)(implicit ec: ExecutionContext) {

  def getPokemon(idOrName: String): Future[Option[Pokemon]] = {
    pokemonRepository.fetchPokemon(idOrName)
  }
}
