package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UpdateBooking extends Simulation {

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
	object ProductList {
		val productList = exec(http("request_4")
			.get("/products")
			.headers(headers_0))
		.pause(16)
	}

	object BookingForm {
		val bookingForm= exec(http("request_5")
			.get("/bookings/new/2")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(11)
		.exec(http("request_6")
			.post("/bookings/save?id=2")
			.headers(headers_3)
			.formParam("numProductos", "6")
			.formParam("id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(9)

	}
	object BookingFormError {
		val bookingFormError= exec(http("request_5")
			.get("/bookings/new/2")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(11)
		.exec(http("request_6")
			.post("/bookings/save?id=2")
			.headers(headers_3)
			.formParam("numProductos", "6000")
			.formParam("id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(9)

	}

	val updateComplete = scenario("UpdateBooking").exec(Home.home,Login.login,ProductList.productList,BookingForm.bookingForm)
	val updateError = scenario("UpdateBookingError").exec(Home.home,Login.login,ProductList.productList,BookingFormError.bookingFormError)
	

	setUp(updateComplete.inject(rampUsers(1500) during (10 seconds)),
	updateError.inject(rampUsers(1500) during (10 seconds))
	).protocols(httpProtocol)
	.assertions(
            global.responseTime.max.lt(5000),
            global.responseTime.mean.lt(1000),
            global.successfulRequests.percent.gt(85)
        )
}