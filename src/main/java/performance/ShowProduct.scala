package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ShowProduct extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.css""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,it-IT;q=0.6,it;q=0.5,en-US;q=0.4")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
			.pause(10)
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
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")        
			.formParam("_csrf", "${stoken}")
		).pause(142)
	}

	object ProductList{
		val productList = exec(http("ProductList")
			.get("/products")
			.headers(headers_0))
		.pause(8)
	}

	object ProductDetails{
		val productDetails = exec(http("ProductDetails")
			.get("/products/2")
			.headers(headers_0))
		.pause(10)
	}

	val ownerScn = scenario("ownerShowProduct").exec(
		Home.home,
		Login.login,
		ProductList.productList,
		ProductDetails.productDetails
	)

	val notLoggedScn = scenario("notLoggedShowProducts").exec(
		Home.home,
		ProductList.productList,
		Login.login,
		ProductList.productList
	)

	setUp(
		ownerScn.inject(rampUsers(500) during (100 seconds)),
		notLoggedScn.inject(rampUsers(500) during (100 seconds)))
		.protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(85)
		)
}