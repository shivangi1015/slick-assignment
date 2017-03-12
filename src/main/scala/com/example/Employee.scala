package com.example

import com.example.connection.{PostgresDbProvider, DBComponent}
import slick.jdbc.PostgresProfile.api._

/**
  * Created by knoldus on 10/3/17.
  */
case class Employee(id:Int,name:String,experience:Double)


trait EmployeeTable{


  private[example] class EmployeeTable(tag: Tag) extends Table[Employee](tag, "experienced_employee"){

    val id = column[Int]("id",O.PrimaryKey)
    val name = column[String]("name")
    val experience = column[Double]("experience")
    def * = (id,name,experience) <> (Employee.tupled, Employee.unapply)
  }

  val employeeTableQuery = TableQuery[EmployeeTable]
}









