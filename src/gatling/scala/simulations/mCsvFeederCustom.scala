package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class mCsvFeederCustom extends BaseSimulation {

  private val  idNumber = (1 to 10).iterator

  def getNextGameId() ={Map("gameId" -> idNumber.next())}

  private val customFeeder = Iterator.continually(getNextGameId())

  def getSpecificVideoGame() = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("get Games details")
          .get("videogames/${gameId}")
          .check(status.is(200)))
        .pause(1)
    }
  }

  private val scn = scenario("Video Game DB")
    .exec(getSpecificVideoGame())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf.inferHtmlResources())
}
