package com.example

import com.example.components.{DependentComponent, ProjectComponent, EmployeeComponent}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration


object DataBaseExecutor extends App{

    EmployeeComponent.create

    EmployeeComponent.insert(Employee(1,"shivangi",1))

    EmployeeComponent.insert(Employee(2,"mahesh",1))

    EmployeeComponent.insert(Employee(3,"abc",4))
    EmployeeComponent.insert(Employee(4,"rahul",5))




    //    val allEmployees = EmployeeComponent.getAll
    EmployeeComponent.sortByEmployeeName()
    val res = Await.result(EmployeeComponent.getAll,Duration.Inf)
    println(res)


    ProjectComponent.create
    ProjectComponent.insert(Project(20,2,"codesquad"))
    ProjectComponent.insert(Project(5,10,"scalageek"))
    ProjectComponent.sortByProjectName()
    val projectList = Await.result(ProjectComponent.getAll,Duration.Inf)
    println(projectList)
//    val res3 = ProjectComponent.sortByProjectName()
//    println(res3)


    DependentComponent.create
    DependentComponent.insert(Dependent(1,"santosh","mother",None))
    DependentComponent.insert(Dependent(1,"ashok","father",Some(50)))
    DependentComponent.insert(Dependent(1,"krishna","grand mother",Some(68)))
    val dependentList = Await.result(DependentComponent.getAll,Duration.Inf)
  println(dependentList)

    print("Retired Dependents: ")
    val res1 = Await.result(DependentComponent.getRetiredDependents,Duration.Inf)
    println(res1)

    Thread.sleep(1000)




  }



