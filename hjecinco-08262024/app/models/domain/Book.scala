package models.domain

import java.util.UUID

case class Book(isbn: String, title: String, publicationYear: Int, authorId: UUID, price: BigDecimal)
