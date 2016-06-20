package controllers

import play.api.mvc.{Action, Controller}
import models.Employee
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}

/**
  * Created by Orange on 20/06/2016.
  */
class EmployeeController extends Controller{

 private val employeeForm: Form[Employee] = Form (
     mapping (
       "eno" -> number.verifying("validation.eno.duplicate", Employee.findByEno(_).isEmpty),
       "empName" -> nonEmptyText,
       "email" -> nonEmptyText
     ) (Employee.apply)(Employee.unapply)
  )

  def list = Action { implicit request =>
    val employees = Employee.findAll
    Ok(views.html.employee.list1(employees))
  }

    def newEmployee = Action { implicit request =>
      Ok(views.html.employee.list(employeeForm, Employee.findAll))
    }

  def save = Action { implicit Request =>
    val newEmployee = employeeForm.bindFromRequest()
    newEmployee.fold(
      hasErrors = { form =>
       Redirect (routes.EmployeeController.list())
     },
      success = { newEmployee =>
        Employee.add(newEmployee)
        Redirect (routes.EmployeeController.list())
      }
    )
  }

}
