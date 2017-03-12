package com.example.connection

import slick.jdbc.PostgresProfile

/**
  * Created by knoldus on 10/3/17.
  */
trait PostgresDbProvider extends DBComponent{

  val driver = PostgresProfile

  import driver.api._

  val db: Database = PostgresDB.connectionPool


    object PostgresDB{
      val connectionPool =Database.forConfig("database")

    }



}
