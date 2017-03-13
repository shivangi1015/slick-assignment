package com.example

import com.example.components.{DependentComponent, ProjectComponent, EmployeeComponent}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by knoldus on 11/3/17.
  */
object DataBaseExecutor extends App{

    EmployeeComponent.create
    //  val innerRes = EmployeeComponent.insert(Employee(10,"shivangi",1))
    //  val res = innerRes.map(x => s"$x inserted").recover{
    //    case ex: Throwable => ex.getMessage
    //  }
    //  res.map(println(_))
    //  Thread.sleep(10000)
    EmployeeComponent.insert(Employee(1,"shivangi",1))

    EmployeeComponent.insert(Employee(2,"mahesh",1))

    EmployeeComponent.insert(Employee(3,"abc",4))
    EmployeeComponent.insert(Employee(4,"rahul",5))


    EmployeeComponent.delete(4)

    EmployeeComponent.update(10,"shiv")
    EmployeeComponent.insert(Employee(5,"shiv",5))
    EmployeeComponent.upsert(Employee(5,"shiva",6))
    EmployeeComponent.upsert(Employee(6,"shilpa",6))
    EmployeeComponent.upsert(Employee(6,"shilpi",6))



    //    val allEmployees = EmployeeComponent.getAll
    val res = Await.result(EmployeeComponent.getAll,Duration.Inf)
    println(res)


    ProjectComponent.create
    ProjectComponent.insert(Project(20,2,"codesquad"))
    ProjectComponent.insert(Project(10,10,"scalageek"))


    DependentComponent.create
    DependentComponent.insert(Dependent(1,"santosh","mother",None))
    DependentComponent.insert(Dependent(1,"ashok","father",Some(50)))

    Thread.sleep(1000)




  }



