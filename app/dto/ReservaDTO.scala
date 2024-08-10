package dto

import java.sql.Date

case class ReservaDTO (id : Int, idQuarto : Int, idHospede : Int, dataInicio : Date, dataFim : Date)
