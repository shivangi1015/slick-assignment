import com.example.components.EmployeeComponent

import com.example.Employee
import com.example.connection.H2DBComponent
import org.scalatest.AsyncFunSuite

class EmployeeSpec extends AsyncFunSuite{

  object emp extends EmployeeComponent with H2DBComponent

  test("Add new Employee ") {
    emp.insert(Employee(202,"shivangi",10)).map(x=>assert(x == 1))
  }

  test("update Employee record ") {
    emp.update(1,"shivangi").map(x=>assert(x == 1))
  }

  test("delete Employee ") {
    emp.delete(5).map(x=>assert(x == 1))
  }

  test("getting all employees"){
    emp.getAll.map(x => assert(x.size == 3))
  }

  test("upserting employees"){
    emp.upsert(Employee(202,"shiva",10)).map(x => assert(x == 1))
  }

//  test("sorting employees"){
//    emp.sortByEmployeeName.assert(x == true)
//  }





}