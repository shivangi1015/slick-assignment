package com.example

import com.example.connection.{MySqlComponent, PostgresDbProvider, DBComponent}

/**
  * Created by knoldus on 10/3/17.
  */
case class Employee(id:Int,name:String,experience:Double)


trait EmployeeTable{
  this: DBComponent =>
  import driver.api._

  private[example] class EmployeeTable(tag: Tag) extends Table[Employee](tag, "experienced_employee"){

    val id = column[Int]("id",O.PrimaryKey)
    val name = column[String]("name")
    val experience = column[Double]("experience")
    def * = (id,name,experience) <> (Employee.tupled, Employee.unapply)
  }

  val employeeTableQuery = TableQuery[EmployeeTable]
}









