package models

import play.api.libs.json.Json

/**
  * Created by Orange on 20/06/2016.
  */
case class Employee(empName:String, email:String)

  object Employee {

    var employees = Set(
      Employee("John Matthews", "john.m@abc.com"),
      Employee("Bob Johnson", "bob.j@abc.com"),
      Employee("Lily Brown", "lily.b@abc.com"),
      Employee("Louise Green", "louise.g@abc.com")
    )

    implicit val employeeFormat = Json.format[Employee]

    def findAll = employees.toList

/*    def add(newEmployee: Employee): Unit = {
      employees =  employees + newEmployee
    }*/

  }


