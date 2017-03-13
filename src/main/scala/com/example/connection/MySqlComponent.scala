package com.example.connection

import slick.driver.MySQLDriver

trait MySqlComponent extends DBComponent{
  val driver = MySQLDriver
  import driver.api._

  val db = Database.forConfig("mySqlDb")

}
