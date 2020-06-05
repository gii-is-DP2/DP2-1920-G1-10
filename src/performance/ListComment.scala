package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ListComment extends Simulation {

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

	val headers_4 = Map(
		"Accept" -> "*/*",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
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
	object Comments{
		val comments= exec(http("Comments")
			.get("/comments")
			.headers(headers_0))
		.pause(10)
	}
	object ListComment{
		val listComment = exec(http("List Comment")
			.get("/comments/1")
			.headers(headers_0))
		.pause(9)
	}

	val scn = scenario("ListComment").exec(Home.home,Login.login,Comments.comments,ListComment.listComment)
	val scn2 = scenario("ListCommentNotLogged").exec(Home.home,Comments.comments,ListComment.listComment)	
		


	setUp(scn.inject(rampUsers(100000) during (10 seconds)),
	scn2.inject(rampUsers(100000) during (10 seconds))
	).protocols(httpProtocol)
	.assertions(
            global.responseTime.max.lt(5000),
            global.responseTime.mean.lt(1000),
            global.successfulRequests.percent.gt(85)
        )
}