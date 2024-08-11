package service

import model._
import slick.jdbc.MySQLProfile.api._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.sql.Date
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReservaService @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val db = dbConfigProvider.get[JdbcProfile].db

  private val quartos = TableQuery[Quarto]
  private val reservas = TableQuery[Reserva]
  private val limpezas = TableQuery[Limpeza]
  def addRoom(numero: Int, descricao: Option[String], capacidade: Int): Future[Int] = {
    val action = (for {
      _ <- quartos += ((0, numero, descricao, capacidade))
    } yield 1).transactionally

    db.run(action)
  }

  def removeRoom(id: Int): Future[Int] = {
    val action = for {
      count <- quartos.filter(_.id === id).delete
    } yield count

    db.run(action)
  }

  def bookRoom(idQuarto: Int, idHospede: Int, dataInicio: Date, dataFim: Date): Future[Int] = {
    val action = for {
      conflictingReservations <- reservas.filter(r =>
        r.idQuarto === idQuarto &&
          ((r.dataInicio <= dataFim) && (r.dataFim >= dataInicio))
      ).result

      cleaningPeriods <- limpezas.filter(l =>
        l.idQuarto === idQuarto &&
          !((l.dataFim <= l.dataInicio) || (l.dataInicio >= l.dataFim))
      ).result

      if conflictingReservations.isEmpty && cleaningPeriods.isEmpty
      _ <- reservas += ((0, idQuarto, idHospede, dataInicio, dataFim))
    } yield 1

    db.run(action.transactionally)
  }

  def getOccupancy(date: Date): Future[Seq[(Int, Int, Int, Date, Date)]] = {
    val action = reservas.filter(r => r.dataInicio <= date && r.dataFim >= date).result
    db.run(action)
  }
}