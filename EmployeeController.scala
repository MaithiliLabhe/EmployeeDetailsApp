package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import models.Employee
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.libs.json.Json
import play.api.libs.ws._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by Orange on 20/06/2016.
  */
class EmployeeController @Inject() (ws:WSClient) extends Controller{

 private val employeeForm: Form[Employee] = Form (
     mapping (
       "empName" -> nonEmptyText,
       "email" -> nonEmptyText
     ) (Employee.apply)(Employee.unapply)
  )

  def save = Action { implicit request =>
    val newEmployee = employeeForm.bindFromRequest.get
    val futureResponse: Future[WSResponse] = ws.url("http://localhost:9000/addEmployee").post(Json.toJson(newEmployee))
    Redirect(routes.EmployeeController.list())
  }

  def list = Action.async{
    ws.url(s"http://localhost:9000/getAllEmployees").get() map {
      response => Ok(response.body)
    }
  }

    def newEmployee = Action { implicit request =>
      Ok(views.html.employee.editEmployee(employeeForm, Employee.findAll))
    }



}
