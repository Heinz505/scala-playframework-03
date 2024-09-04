package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._
import services.PokemonService
import scala.concurrent.Future
import models.domain.Pokemon
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import play.api.mvc._

class PokemonControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  // Mock the PokemonService
  val mockPokemonService = mock(classOf[PokemonService])
  val controller = new PokemonController(stubControllerComponents(), mockPokemonService)

  "PokemonController" should {

    "display the search form" in {
      val result = controller.searchFormView.apply(FakeRequest(GET, "/search"))
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
    }

    "be able to give results from searh for a valid Pokemon query" in {
      val pokemon = Pokemon(1, "bulbasaur", 7, 69, Seq("grass", "poison"))
      when(mockPokemonService.getPokemon("bulbasaur")).thenReturn(Future.successful(Some(pokemon)))

      val result = controller.searchPokemon().apply(FakeRequest(POST, "/search").withFormUrlEncodedBody("query" -> "bulbasaur"))
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include ("bulbasaur")
    }

    "will have a not found if Pokemon query is invalid" in {
      when(mockPokemonService.getPokemon("invalid")).thenReturn(Future.successful(None))

      val result = controller.searchPokemon().apply(FakeRequest(POST, "/search").withFormUrlEncodedBody("query" -> "invalid"))
      status(result) mustBe NOT_FOUND
    }
  }
}
