package controllers

import dto._
import play.api.libs.json._
import play.api.mvc._
import service.ReservaService

import java.sql.Date
import java.time.LocalDate
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReservaController @Inject()(val controllerComponents: ControllerComponents, reservaService: ReservaService)(implicit ec: ExecutionContext) extends BaseController {

  implicit val quartoFormat: Format[QuartoDTO] = Json.format[QuartoDTO]
  implicit val hospedeFormat: Format[HospedeDTO] = Json.format[HospedeDTO]
  implicit val reservaFormat: Format[ReservaDTO] = Json.format[ReservaDTO]

  def addRoom(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[QuartoDTO].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      quarto => reservaService.addRoom(quarto.numero, quarto.descricao, quarto.capacidade).map { _ =>
        Created(s"Room added with number ${quarto.numero}")
      }
    )
  }

  def removeRoom(id: Int): Action[AnyContent] = Action.async {
    reservaService.removeRoom(id).map { count =>
      if (count > 0) Ok(s"Room $id removed")
      else NotFound("Room not found")
    }
  }

  def bookRoom(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[ReservaDTO].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      reserva => reservaService.bookRoom(reserva.idQuarto, reserva.idHospede, reserva.dataInicio, reserva.dataFim).map { _ =>
        Created(s"Reservation made for room ${reserva.idQuarto}")
      }
    )
  }

  def getOccupancy(date: String): Action[AnyContent] = Action.async {
    try {
      val localDate = LocalDate.parse(date)
      val sqlDate = Date.valueOf(localDate)
      reservaService.getOccupancy(sqlDate).map { occupancy =>
        if (occupancy.nonEmpty) Ok(Json.toJson(occupancy))
        else NoContent
      }
    } catch {
      case _: Exception => Future.successful(BadRequest("Invalid date format. Please use YYYY-MM-DD."))
    }
  }
}
