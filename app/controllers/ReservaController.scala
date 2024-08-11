package controllers

import dto._
import play.api.libs.json._
import play.api.mvc._
import service.ReservaService

import java.time.LocalDate
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReservaController @Inject()(val controllerComponents: ControllerComponents, reservaService: ReservaService)(implicit ec: ExecutionContext) extends BaseController {

  implicit val quartoFormat: Format[QuartoDTO] = Json.format[QuartoDTO]
  implicit val hospedeFormat: Format[HospedeDTO] = Json.format[HospedeDTO]
  implicit val reservaFormat: Format[ReservaDTO] = Json.format[ReservaDTO]

  def adicionarQuarto(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[QuartoDTO].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      quarto => reservaService.adicionaInventario(quarto.numero, quarto.descricao, quarto.capacidade).map { _ =>
        Created(s"Quarto adicionado , numero :  ${quarto.numero}")
      }
    )
  }

  def removerQuarto(id: Int): Action[AnyContent] = Action.async {
    reservaService.removeQuarto(id).map { count =>
      if (count > 0) Ok(s"Room $id Removido")
      else NotFound("Quarto não encontrado")
    }
  }

  def reservarQuarto(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[ReservaDTO].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      reserva => reservaService.reservaQuarto(reserva.idQuarto, reserva.idHospede, reserva.dataInicio, reserva.dataFim).map { _ =>
        Created(s"Reserva criada para o quarto: ${reserva.idQuarto}")
      }
    )
  }

  def verificaOcupacao(date: String): Action[AnyContent] = Action.async {
    try {
      val localDate = LocalDate.parse(date)
      reservaService.verificaOcupacao(localDate).map { isOccupied =>
        if (isOccupied) Ok(Json.toJson("Ocupado"))
        else Ok(Json.toJson("Disponível"))
      }
    } catch {
      case _: Exception => Future.successful(BadRequest("Formato de data inválido. Use YYYY-MM-DD."))
    }
  }

}
