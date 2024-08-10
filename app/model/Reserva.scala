package model

import slick.jdbc.MySQLProfile.api._
import java.sql.Date

class Reserva(tag: Tag) extends Table[(Int, Int, Int, Date, Date)](tag, "RESERVA") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def idQuarto = column[Int]("ID_QUARTO")
  def idHospede = column[Int]("ID_HOSPEDE")
  def dataInicio = column[Date]("DATA_INICIO")
  def dataFim = column[Date]("DATA_FIM")

  def * = (id, idQuarto, idHospede, dataInicio, dataFim)

  def idQuartoFK = foreignKey("QUARTO_FK", idQuarto, TableQuery[Quarto])(_.id)
  def idHospedeFK = foreignKey("HOSPEDE_FK", idHospede, TableQuery[Hospede])(_.id)
}
