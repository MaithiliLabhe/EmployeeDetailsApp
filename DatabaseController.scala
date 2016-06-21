package controllers

import javax.inject.Inject
import models.Employee
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{Action, Controller}
import reactivemongo.play.json.collection.JSONCollection
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.play.json._
import scala.concurrent.Future

/**
  * Created by Orange on 21/06/2016.
  */

class DatabaseController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def collection: JSONCollection = db[JSONCollection]("employees")

  implicit val modelFormat = Json.format[Employee]

  def addEmployee = Action { implicit request =>
    val jsonBody: Option[JsValue] = request.body.asJson
    jsonBody.map { json =>
      val employee = json.validate[Employee]
      employee.fold(errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
      },
        employee => {
          collection.insert(employee) map {
            lastError =>
              Created
          }
          Ok(Json.obj("status" -> "OK", "message" -> ("User added into the system")))
        })
    }.getOrElse(BadRequest(Json.obj("status" -> "KO")))
  }

  def getAllEmployees = Action.async {
    val cursor: Cursor[Employee] = collection.find(Json.obj()).cursor[Employee]
    val futureEmployeesList: Future[List[Employee]] = cursor.collect[List]()
    futureEmployeesList.map(response => Ok(Json.toJson(response)))
  }
}