package domain

case class Post(id: Long, postText: String, userId: Long, imgUrl: Option[String])
