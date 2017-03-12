package com.example.connection

import slick.jdbc.JdbcProfile

/**
  * Created by knoldus on 10/3/17.
  */
trait DBComponent {

  val driver: JdbcProfile

  import driver.api._

  def db: Database

}
