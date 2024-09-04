package models

import java.sql.Timestamp

case class Comment(id: Option[Long], commentContent: String, postId: Long, commentAt: Timestamp)
