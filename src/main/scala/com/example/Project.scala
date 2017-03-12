package com.example
import slick.jdbc.PostgresProfile.api._

case class Project(emp_id : Int,pid:Int,name :String)


trait ProjectTable extends EmployeeTable{

  private[example] class ProjectTable(tag: Tag) extends Table[Project](tag, "new_project_table"){

    val pid = column[Int]("pid",O.PrimaryKey)
    val emp_id = column[Int]("emp_id")
    val name = column[String]("name")
    def employeeProjectFK = foreignKey(
      "employee_project_fk",emp_id,employeeTableQuery)(_.id)
    def * = (pid,emp_id,name) <> (Project.tupled, Project.unapply)
  }

  val projectTableQuery = TableQuery[ProjectTable]
}


