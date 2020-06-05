package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ListCitas extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.js""", """.*.ico""", """.*.css"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Proxy-Connection" -> "keep-alive")
	val headers_3 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")
	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "http://a.ssl-checking.com"
	object Home{
		val home = exec(http("home")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839351935&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(8)
	}
	object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("Logged")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "prueba")
        .formParam("password", "prueba")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }

	
	object MattingOfferList{
		val mattingOfferList =  exec(http("MattingOffer-list")
			.get("/matingOffers")
			.headers(headers_0)
			.resources(http("request_1")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590765761649&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(10)}
	object MattingOffer1{
		val mattingOffer1 = exec(http("MattingOffer1")
			.get("/pets/1/matingOffers/1")
			.headers(headers_0)
			.resources(http("request_10")
			.get(uri1 + "/?q=orBtpYxOEANPiYCfgbfzXa2LXgixcUkXboYhYb78ZXenra0aMSoyi7F20gSaEm_yisqXe4XInrc7LViW3WGBO0Xw6L9tFweTORwn3n50Ilt1C4acHrfak3Ze8VTTj0UAFAq9WyzgyLArH6qPvF10fGXirSIDYstoZ2S5Wfobg1-UjQrLWeWhkWp9-tdSzpAR2ErZ7PnU4TLb9UBY&t=1591112094685&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(14)
	}

	val scn = scenario("ListCitas")
	val pruebascn = scenario("prueba").exec(Home.home,
									Login.login,
									MattingOfferList.mattingOfferList,
									MattingOffer1.mattingOffer1

									)

setUp(
		pruebascn.inject(rampUsers(13000) during(100 seconds))
		).protocols(httpProtocol).assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95)			)
}