package models

/**
  * Created by Orange on 20/06/2016.
  */
case class Employee(
 eno:Int, empName:String, email:String)

  //Below object class works as s data access object for the class
  object Employee {

    var employees = Set(
      Employee(1, "John Matthews", "john.m@abc.com"),
      Employee(2, "Bob Johnson", "bob.j@abc.com"),
      Employee(3, "Lily Brown", "lily.b@abc.com"),
      Employee(4, "Louise Green", "louise.g@abc.com")
    )

    override def toString(): String =   {
      val lastEmployee = employees.last

      var itemsAsString = lastEmployee.productIterator.toList.map(_.toString).filter(_.nonEmpty)

      itemsAsString.mkString(", ")

    }

    def reset : Unit = employees = employees.empty

    def add(newEmployee: Employee): Unit = {
      employees =  employees + newEmployee
    }
    def findAll = employees.toList.sortBy(_.eno)

    def findByEno(eno: Int) = employees.find(_.eno == eno)
  }


