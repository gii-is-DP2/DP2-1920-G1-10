package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DeleteProduct extends Simulation {

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

	object LoginAsOwner {
    val loginAsOwner = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("Logged")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "owner1")
        .formParam("password", "0wn3r")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }

  object LoginAsAdmin {
    val loginAsAdmin = exec(
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

	object ProductListBefore{
		val productListBefore = exec(http("ProductListBefore")
			.get("/products")
			.headers(headers_0))
		.pause(8)
	}

	object Delete{
		val delete = exec(http("DeleteProduct")
			.get("/products/delete/1")
			.headers(headers_0)
		).pause(20)
	}

	object Error{
		val error = exec(http("Error")
			.get("/products/delete/1")
			.headers(headers_0)
			.resources(http("request_14")
			.get("/resources/images/oops.gif")
			.headers(headers_2))
			).pause(20)
	}

	val ownerScn = scenario("OwnerDeleteProducts").exec(
		Home.home,
		LoginAsOwner.loginAsOwner,
		ProductListBefore.productListBefore,
		Error.error
	)

	val adminScen = scenario("AdminDeleteProducts").exec(
		Home.home,
		LoginAsAdmin.loginAsAdmin,
		ProductListBefore.productListBefore,
		Delete.delete
	)

	setUp(
		ownerScn.inject(rampUsers(350) during (100 seconds)),
		adminScen.inject(rampUsers(350) during (100 seconds)))
		.protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(85)
		)
}