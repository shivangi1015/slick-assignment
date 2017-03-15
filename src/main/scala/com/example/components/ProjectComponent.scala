package com.example.components

import com.example.connection.{MySqlComponent, DBComponent}
import com.example.{Project, ProjectTable}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


trait ProjectComponent extends ProjectTable{

  this: DBComponent =>
  import driver.api._

  def create = db.run(projectTableQuery.schema.create)

  def insert(emp: Project) = db run {
    projectTableQuery += emp
  }

  def delete(pname: String) = {
    val query = projectTableQuery.filter(x => x.name === pname).delete
    db.run(query)
  }


  def update(id: Int, name: String) = {
    val query = projectTableQuery.filter(_.pid === id).map(_.name).update(name)
    db.run(query)
  }

  def getAll : Future[List[Project]] = db run {
 projectTableQuery.to[List].result
  }


  def upsert(proj : Project) ={
    val res: List[Project] = Await.result(ProjectComponent.getAll,Duration.Inf)
    val flag = res.map(x => if(x.emp_id == proj.emp_id) true else false)
    if(flag.contains(true)){
      val query = projectTableQuery.filter(_.pid === proj.pid).map(x => (x.emp_id,x.name)).update((proj.emp_id,proj.name))
      db.run(query)
    }
    else{
      db run{
        projectTableQuery += proj
      }
    }

  }

  def sortByProjectName() {
    val sortedNames = projectTableQuery.sortBy(x => x.name)
  }

  def leftJoin ={
    val innerJoin = for{
      (e,p) <- employeeTableQuery join projectTableQuery on(_.id === _.emp_id)
    }yield (e.name,p.name)

    db.run(innerJoin.to[List].result)
  }

  def rightJoin ={
    val innerJoin = for{
      (e,p) <- employeeTableQuery join projectTableQuery on(_.id === _.emp_id)
    }yield (e.name,p.name)

    db.run(innerJoin.to[List].result)
  }

}

object ProjectComponent extends ProjectComponent with MySqlComponent
