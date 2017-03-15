import com.example.Project
import com.example.components.ProjectComponent
import com.example.connection.H2DBComponent
import org.scalatest.AsyncFunSuite


class ProjectSpec extends AsyncFunSuite {

  object proj extends ProjectComponent with H2DBComponent

  test("testing creation of project table"){
    assert(proj.create == true)
  }

  test("Adding new Project ") {

    proj.insert(Project(2,2,"snake game")).map(x=>assert(x == 1))
  }

  test("updating new record ") {
    proj.update(103,"shivangi's Project").map(x=>assert(x == 1))
  }

  test("deleting project ") {
    proj.delete("scalageek").map(x=>assert(x == 1))
  }

  test("upserting projectss"){
    proj.upsert(Project(3,7,"java Project")).map(x => assert(x == 1))
  }

  test("getting all projects"){
    proj.getAll.map(x => assert(x.size == 3))
  }

  test("sort by project name "){
    assert(proj.sortByProjectName == true)
  }

  test("Testing left join"){
    proj.leftJoin.flatMap(x => assert(x.size == 3))
  }

  test("Testing right join"){
    proj.rightJoin.flatMap(x => assert(x.size == 3))
  }

}
