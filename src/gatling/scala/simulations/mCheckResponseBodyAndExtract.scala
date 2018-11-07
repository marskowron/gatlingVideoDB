package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class mCheckResponseBodyAndExtract extends BaseSimulation {

  private val scn = scenario("Video Game DB")
    .exec(http("Get Specific game ID")
      .get("videogames")
      .check(status.is(200))
      .check(jsonPath("$[1].id").saveAs("gameId")))
    //debugging line below
    .exec { session => println(session); session }

    .exec(http("Get games details with parameter")
      .get("videogames/${gameId}")
      .check(status.is(200))
      .check(jsonPath("$.name").is("Gran Turismo 3"))
      .check(bodyString.saveAs("responseBody")))
    //wypisanie całego responsu do konsoli
    .exec { session => println(session("responseBody").as[String]); session }

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}