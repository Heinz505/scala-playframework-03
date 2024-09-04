//1.
trait MyTrait[T]{
	val value: T 
}

class A extends MyTrait[String]{
	override val value: String = "abc"
}

class B extends MyTrait[Int]{
	override val value: Int = 10
}

//2.

trait Functor Map()

def functorForOption: Functor


“import Literal.*
    def valueOfLiteral[T](lit: Literal[T]): T =
    lit match
  		case IntLit(n) => n
  		case LongLit(m) => m
  		case CharLit(c) => c
  		case FloatLit(f) => f
  		case DoubleLit(d) => d
  		case BooleanLit(b) => b
  		case StringLit(s) => s

