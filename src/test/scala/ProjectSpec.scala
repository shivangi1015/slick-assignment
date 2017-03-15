import com.example.Project
import com.example.components.ProjectComponent
import com.example.connection.H2DBComponent
import org.scalatest.AsyncFunSuite


class ProjectSpec extends AsyncFunSuite {

  object proj extends ProjectComponent with H2DBComponent

  test("Adding new Project ") {

    proj.insert(Project(2,6,"snake game")).map(x=>assert(x == 0))
  }

//  test("updating new record ") {
//    proj.update(1,"shivangi's Project").map(x=>assert(x == 1))
//  }
//
//  test("delete project ") {
//    proj.delete("scalageek").map(x=>assert(x == 1))
//  }
//
//  test("getting all projects"){
//    proj.getAll.map(x => assert(x.size == 3))
//  }
//
//  test("upserting projectss"){
//    proj.upsert(Project(3,7,"java Project")).map(x => assert(x == 1))
//  }

}
