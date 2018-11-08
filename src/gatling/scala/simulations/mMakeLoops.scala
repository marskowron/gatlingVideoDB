package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class mMakeLoops extends BaseSimulation {
  def getAllVideoGames() = {
    repeat(3) {
    exec(http("Get All Video Games")
      .get("videogames")
      .check(status.is(200)))
  }}

  def getSpecificVideoGame() = {
    repeat(5){
    exec(http("Get specific game")
      .get("videogames/1")
      .check(status.in(200 to 210)))
  }}

  val scn = scenario("Video Game DB")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificVideoGame())
    .pause(5)
    .exec(getAllVideoGames())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
