@main def greetStrings
	val greetStrings = new Array[String](3)
	greetStrings(0) = "Hello"
	greetStrings(1) = ", "
	greetStrings(2) = "world\n"

	for i <- 0 to 2 do
		print(greetStrings(i))