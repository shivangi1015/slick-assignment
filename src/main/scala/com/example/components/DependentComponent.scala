package com.example.components

import com.example.connection.DBComponent
import com.example.{Dependent, DependentTable}
import slick.dbio.Effect.Read
import slick.sql.FixedSqlStreamingAction

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


trait DependentComponent extends DependentTable{

  this: DBComponent =>
  import driver.api._
  def create = db.run(dependentTableQuery.schema.create)

  def insert(dependent : Dependent) = db run{
    dependentTableQuery += dependent
  }

  def delete(relationStatus: String ) {
    val query = dependentTableQuery.filter((x => x.relationship === relationStatus)).delete
    db.run(query)
}


  def update(id: Int,relationshipStatus: String): Unit = {
     val query = dependentTableQuery.filter(x => x.emp_id === id).map(_.relationship).update(relationshipStatus)
    db.run(query)
  }

  def getAll : Future[List[Dependent]] = db run {
    dependentTableQuery.to[List].result
  }


  def upsert(dep : Dependent) {
    val res: List[Dependent] = Await.result(DependentComponent.getAll,Duration.Inf)
    val flag = res.map(x => if(x.name == dep.name) true else false)
    if(flag.contains(true)){
      val query = dependentTableQuery.filter(_.name === dep.name).map(x => (x.relationship,x.age)).update((dep.relationship,dep.age))
      db.run(query)
    }
    else{
      db run{
        dependentTableQuery += dep
      }
    }

  }

  def getRetiredDependents = db run{
    dependentTableQuery.to[List].filter(_.age > 60).result
  }
}

object DependentComponent extends DependentComponent
