package models

import java.sql.Timestamp

case class User(id: Option[Long], username: String, password: String)
