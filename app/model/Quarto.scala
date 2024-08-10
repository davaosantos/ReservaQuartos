package model

import slick.jdbc.MySQLProfile.api._

class Quarto(tag: Tag) extends Table[(Int, Int, Option[String], Int)](tag, "QUARTO") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def numero = column[Int]("NUMERO", O.Unique)
  def descricao = column[Option[String]]("DESCRICAO")
  def capacidade = column[Int]("CAPACIDADE")

  def * = (id, numero, descricao, capacidade)
}
