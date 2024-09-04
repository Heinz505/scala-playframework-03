package models.domain

import org.scalatestplus.play._
import play.api.test.Helpers._

class PokemonSpec extends PlaySpec {

  "Pokemon" should {
    "have properties that are correct" in {
      val pokemon = Pokemon(1, "bulbasaur", 7, 69, Seq("grass", "poison"))
      pokemon.name mustBe "bulbasaur"
      pokemon.id mustBe 1
      pokemon.height mustBe 7
      pokemon.weight mustBe 69
      pokemon.types must contain allOf ("grass", "poison")
    }
  }
}
