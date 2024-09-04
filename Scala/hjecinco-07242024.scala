//1.
Class shape:
  def isShape= "is a shape"

Class Circle(val Pi: Float = 3.1416) extends shape:
  def area(radius: Float): Float = 
    radius * radius * Pi
  def resize(radius: Float, factor: Float) =
    radius * factor
Class Rectangle extends shape:
  def area(h: Float, w: Float): Float = 
    h * w
  def resize(h: Float, w: Float, factor: Float) Float = 
    (h * factor)*(w * factor)

Class temperature:

trait C
  def isCelsius = "Celcius"

trait F
  def isFahrenheit = "Fahrenheit"

  var Celcius: Float = null
  var Fahrenheit: Float = null

  def TemperatureConverter(X: Float, what: Class)
    def convert(X: Float, what: Class)
      if what == C ||
        then (X - 32) * 5 / 9
      else if what == F
        then X * 9 / 5 + 32




