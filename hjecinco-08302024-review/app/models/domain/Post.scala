package models

import java.sql.Timestamp

case class Post(id: Option[Long], postContent: String, userId: Long, postAt: Timestamp)
