package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CreateBooking extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.ico""", """.*.css""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")	

	val headers_6 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	object ProductList{
		val productList = exec(http("ProductList")
			.get("/products")
			.headers(headers_0))
		.pause(11)
	}

	object Home{
		val home= exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)
	}

	object Login{
    	val login = exec(
        http("Login")
            .get("/login")
            .headers(headers_0)
            .check(css("input[name=_csrf]", "value").saveAs("stoken"))
        ).pause(23)
        .exec(
            http("Logged")
            .post("/login")
            .headers(headers_3)
            .formParam("username", "prueba1")
            .formParam("password", "practica")
            .formParam("_csrf", "${stoken}")
        ).pause(8)
	}

	object BookForm{
		val bookForm = exec(http("BookForm")
			.get("/bookings/new/5")
			.headers(headers_0)
			  .check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(16)
		.exec(http("BookFormComplete")
			.post("/bookings/save?id=5")
			.headers(headers_2)
			.formParam("numProductos", "40")
			.formParam("id", "")
			.formParam("_csrf", "${stoken}")
		).pause(21)
	}
	object BookFormError{
		val bookFormError = exec(http("BookForm")
			.get("/bookings/new/5")
			.headers(headers_0)
			  .check(css("input[name=_csrf]", "value").saveAs("stoken"))
			  ).pause(16)
		
		.exec(http("BookFormError")
			.post("/bookings/save?id=1")
			.headers(headers_2)
			.formParam("numProductos", "9000")
			.formParam("id", "")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_6")
			.get("/resources/images/oops.gif")
			.headers(headers_6)))
		.pause(12)
	}
	val FirstScn = scenario("CreateBooking").exec(Home.home,Login.login,ProductList.productList,BookForm.bookForm)
	val SecondScn = scenario("CreateBookingError").exec(Home.home,Login.login,ProductList.productList,BookFormError.bookFormError)	

	setUp(FirstScn.inject(rampUsers(1000) during (100 seconds)),
	SecondScn.inject(rampUsers(1000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
            global.responseTime.max.lt(5000),
            global.responseTime.mean.lt(1000),
            global.successfulRequests.percent.gt(85)
        )
}