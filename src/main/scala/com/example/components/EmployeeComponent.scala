package com.example.components

import com.example.connection.{MySqlComponent, DBComponent}
import com.example.{Employee, EmployeeTable}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import slick.dbio.DBIOAction


trait EmployeeComponent extends EmployeeTable {

  this: DBComponent =>
  import driver.api._

  def create = db.run(employeeTableQuery.schema.create)

  def insert(emp: Employee) = db run {
    employeeTableQuery += emp
  }

  def delete(exp: Double) = {
    val query1 = employeeTableQuery.filter(x => x.experience === exp).delete
//    val query2 = query1.delete
//    db.run(query1.andThen(query2).cleanUp(query1))
//    println("Deleted query"+query1.toString)
    db.run(query1)
  }


  def update(id: Int, name: String) = {
    val query = employeeTableQuery.filter(_.id === id).map(_.name).update(name)
    db.run(query)
  }

  def getAll : Future[List[Employee]] = db run {
    employeeTableQuery.to[List].result
  }
  def upsert(emp : Employee)={
    val a: Future[Int] = db.run(employeeTableQuery.insertOrUpdate(emp))
    a
  }

  def sortByEmployeeName = {
    val sortedNames = employeeTableQuery.sortBy(x => x.name)
    true
  }

  def addingMultipleAtOnce(emp1 : Employee,emp2 : Employee) ={
    val ins1 = employeeTableQuery += emp1
    val ins2 = employeeTableQuery += emp2
    db.run(ins1 andThen(ins2).cleanUp{
      case None => ins2
      case _ => ins2
    })


  }
}


object EmployeeComponent extends EmployeeComponent with MySqlComponent