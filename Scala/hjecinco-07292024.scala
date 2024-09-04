#1 Using Scala's `givens` and `using`, complete the implementation for the JsonSerializer
object which contains the method `toJson`. This method can serialize a `Profile` or a `User` object to a JSON string.
  - The `toJson` method for a `Profile` returns the following value:
    "{"id": "${johnProfile.id.toString}","name": "John Doe", "age": 30}"
  - The `toJson` metho for a `User` returns the following value:
    "{"email": "${user.email}", "username": "${user.username}", "id_profile": ${user.idProfile}}"
*/

trait JsonSerializable[A]:
  def serialize(value: A): String

class Profile(val id: UUID, val name: String, val age: Int)

object Profile:
  def apply(name: String, age: Int): Profile = new Profile(UUID.randomUUID, name, age)

case class User(email: String, username: String, idProfile: UUID)

object JsonSerializer:
  def toJson[A](value: A): String = Profile(name: String, age: Int)

/*
#2 Write the code to make the test code below execute successfully.
  - Create the necessary classes for the test cases.
  - To convert Kilogram to Gram, multiply the value by 1000
  - To convert Gram to Kilogram, divide the value by 1000
  - Hint: Scala has a built-in feature to easily convert between types
*/

// DO NOT CHANGE
@main def test =
  // 1
  val johnProfile = Profile("John Doe", 30)
  val johnUser = User("jdoe@gmail.com", "jdoe", johnProfile.id)

  val obtainedSerializedProfile = JsonSerializer.toJson(johnProfile)
  val expectedSerilizedProfile = s"""{"id": "${johnProfile.id}","name": "John Doe", "age": 30}"""
  println(s"obtained: $obtainedSerializedProfile")
  println(s"expected: $expectedSerilizedProfile")
  assert(obtainedSerializedProfile == expectedSerilizedProfile)

  val obtainedSerializedUser = JsonSerializer.toJson(johnUser)
  val expectedSerilizedUser = s"""{"email": "jdoe@gmail.com", "username": "jdoe", "id_profile": ${johnProfile.id}}"""
  println(s"obtained: $obtainedSerializedUser")
  println(s"expected: $expectedSerilizedUser")
  assert(obtainedSerializedUser == expectedSerilizedUser)

  // 2
  val testCases1: List[(Kilogram, Gram)] = List(
    (Kilogram(1.0), Gram(1000.0)), // (argument value, expected result value)
    (Kilogram(1.6), Gram(1600.0)),
    (Kilogram(2.89), Gram(2890.0))
  )

  val testCases2: List[(Gram, Kilogram)] = List(
    (Gram(100.0), Kilogram(0.1)), // (argument value, expected result value)
    (Gram(3.0), Kilogram(0.003)),
    (Gram(19.8), Kilogram(0.0198))
  )
  
  testCases1.foreach(t => {
    val obtained: Gram = t._1
    val expected = t._2
    println(s"obtained: $obtained")
    println(s"expected: $expected")
    assert(obtained == expected)
  })

  testCases2.foreach(t => {
    val obtained: Kilogram = t._1
    val expected = t._2
    println(s"obtained: $obtained")
    println(s"expected: $expected")
    assert(obtained == expected)
  })