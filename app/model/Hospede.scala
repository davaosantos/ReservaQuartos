package model

import slick.jdbc.MySQLProfile.api._
import java.sql.Date

class Hospede(tag: Tag) extends Table[(Int, String)](tag, "HOSPEDE") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def nome = column[String]("NOME")

  def * = (id, nome)
}
