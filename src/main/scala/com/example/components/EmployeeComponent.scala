package com.example.components

import com.example.connection.DBComponent
import com.example.{Employee, EmployeeTable}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


/**
  * Created by knoldus on 11/3/17.
  */
trait EmployeeComponent extends EmployeeTable {

  this: DBComponent =>
  import driver.api
  val db = Database.forConfig("myPostgresDB")

  def create = db.run(employeeTableQuery.schema.create)

  def insert(emp: Employee) = db run {
    employeeTableQuery += emp
  }

  def delete(exp: Double) = {
    val query = employeeTableQuery.filter(x => x.experience === exp).delete
    db.run(query)
  }


  def update(id: Int, name: String) = {
    val query = employeeTableQuery.filter(_.id === id).map(_.name).update(name)
    db.run(query)
  }

  def getAll : Future[List[Employee]] = db run {
    employeeTableQuery.to[List].result
  }
  def upsert(emp : Employee) {
    val res: List[Employee] = Await.result(EmployeeComponent.getAll,Duration.Inf)
    val flag = res.map(x => if(x.id == emp.id) true else false)
    if(flag.contains(true)){
      val query = employeeTableQuery.filter(_.id === emp.id).map(x => (x.name,x.experience)).update((emp.name,emp.experience))
      db.run(query)

    }
    else{
      db run{
        employeeTableQuery += emp
      }
    }

  }
}


object EmployeeComponent extends EmployeeComponent with DBComponent