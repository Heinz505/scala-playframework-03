@main def main()=
	class Rational(n: Int, d: Int):
		require(d!=0)

		private val g = gcd(n.abs, d.abs)
		val numer = n/g
		val denom = d/g

		def this(n: Int) = this(n, 1)

		def add(that: Rational): Rational =
			Rational(
				numer * that.denom + that.numer * denom,
				denom * that.denom 
			)

		override def toString = s"$numer/$denom"

		private def gcd(a: Int, b: Int): Int = 
			if b == 0 then a else gcd(b, a % b)

	println(Rational(66/42))