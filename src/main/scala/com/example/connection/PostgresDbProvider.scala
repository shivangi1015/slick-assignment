package com.example.connection


  import slick.driver.PostgresDriver



  trait PostgresDbProvider extends DBComponent{

    val driver = PostgresDriver

    import driver.api._

    val db: Database = Database.forConfig("myPostgresDB")

  }
