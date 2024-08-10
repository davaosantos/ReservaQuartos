package model

import slick.jdbc.MySQLProfile.api._
import java.sql.Date

class Limpeza(tag: Tag) extends Table[(Int, Int, java.sql.Timestamp, java.sql.Timestamp)](tag, "LIMPEZA") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def idQuarto = column[Int]("ID_QUARTO")
  def dataInicio = column[java.sql.Timestamp]("DATA_INICIO")
  def dataFim = column[java.sql.Timestamp]("DATA_FIM")

  def * = (id, idQuarto, dataInicio, dataFim)

  def idQuartoFK = foreignKey("QUARTO_FK", idQuarto, TableQuery[Quarto])(_.id)
}
