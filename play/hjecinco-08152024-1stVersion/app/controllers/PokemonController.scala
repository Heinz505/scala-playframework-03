package controllers

import javax.inject._
import play.api.mvc._
import services.PokemonService
import models.domain.Pokemon
import scala.concurrent.{ExecutionContext, Future}
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

@Singleton
class PokemonController @Inject()(
  cc: ControllerComponents,
  pokemonService: PokemonService
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  // Define form for capturing user input
  val searchForm: Form[String] = Form(
    single(
      "query" -> nonEmptyText
    )
  )

  // Render the search form view
  def searchFormView = Action { implicit request =>
    Ok(views.html.search(searchForm, None))
  }

  // Handle form submission
  def searchPokemon() = Action.async { implicit request =>
    searchForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.search(formWithErrors, None))),
      query => {
        pokemonService.getPokemon(query).map { pokemonOpt =>
          Ok(views.html.search(searchForm, pokemonOpt))
        }
      }
    )
  }
}
