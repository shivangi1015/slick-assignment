package com.example

import com.example.connection.{MySqlComponent, PostgresDbProvider, DBComponent}


case class Dependent(emp_id: Int,name: String,relationship: String,age: Option[Int])

trait DependentTable extends EmployeeTable with MySqlComponent {
  this: DBComponent =>
  import driver.api._

  class DependentTable(tag: Tag) extends Table[Dependent](tag, "employee_dependents"){
    val emp_id = column[Int]("emp_id")
    val name = column[String]("name",O.PrimaryKey)
    val relationship = column[String]("relationship")
    val age = column[Option[Int]]("age")
    def dependentEmployeeFK = foreignKey("dependent_employee_fk",emp_id,employeeTableQuery)(_.id)
    def * =(emp_id,name,relationship,age) <> (Dependent.tupled, Dependent.unapply)

  }

  val dependentTableQuery = TableQuery[DependentTable]


}

