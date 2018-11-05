package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class mCheckResponseBody extends BaseSimulation {

  val scn = scenario("Video Games DB")
    .exec(http("Get game details")
      .get("videogames/1")
      .check(jsonPath("$.name").is("Resident Evil 4")))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
