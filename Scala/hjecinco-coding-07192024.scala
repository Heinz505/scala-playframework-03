trait moveWalk:
  def moveByWalk = "walk"

trait moveSwim:
  def moveBySwim = "swim"

Class Animal extends moveWalk, moveSwim:
  def performActivities = moveWalk.moveByWalk

Class Dog extends Animal, moveWalk, moveSwim:
  def performActivities = moveWalk.moveByWalk

val animal = new Animal()
animal.performActivities()

trait shape:
  def isShape= "is a shape"

Class Circle(val Pi: Float = 3.1416) extends shape:
  def area(radius: Float): Float = 
    radius * Pi

Class Rectangle extends shape:
  def area(l: Float, w: Float): Float = 
    l * moveWalk

val circle = new Circle(5.0)
val rectangle = new Rectangle(3.0, 4.0)


@main def test =
  // Create an instance of Animal and call the performActivities method
  val animal = new Animal()
  animal.performActivities() // should print "Walking" and "Swimming" respectively

  // Create instances of different shapes and calculate their areas using polymorphism
  val circle = new Circle(5.0)
  val rectangle = new Rectangle(3.0, 4.0)
