package com.example.components

import com.example.connection.{MySqlComponent, DBComponent}
import com.example.{Project, ProjectTable}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


trait ProjectComponent extends ProjectTable{

  this: DBComponent =>
  import driver.api._

  def create = {
    db.run(
    projectTableQuery.schema.create)
  true
}

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

  def sortByProjectName ={
    val sortedNames = projectTableQuery.sortBy(x => x.name)
    true
  }

  def leftJoin ={
    val innerJoin = for{
      (e,p) <- employeeTableQuery join projectTableQuery on(_.id === _.emp_id)
    }yield (e.name,p.name)
//    println(innerJoin)
    db.run(innerJoin.to[List].result)
  }

  def rightJoin ={
    val innerJoin = for{
      (e,p) <- employeeTableQuery join projectTableQuery on(_.id === _.emp_id)
    }yield (e.name,p.name)
   println(innerJoin)
    db.run(innerJoin.to[List].result)
  }

//  def count ={
//    db.run((projectTableQuery.length)
//  }

  def addMultipleProjects(pr1: Project,pr2: Project) =
  {
    val ins1 = projectTableQuery += pr1

    val ins2 = projectTableQuery += pr2
    val res = ins1 andThen ins2
    db.run(res)
  }

//  def getByPname(pname : String) ={
//
//  }

}

object ProjectComponent extends ProjectComponent with MySqlComponent
