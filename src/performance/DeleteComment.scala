package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DeleteComment extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,en;q=0.9,en-GB;q=0.8")
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


object Home{
	val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
}

object LoginScreen{
	val loginScreen = exec(
		http("LoginScreen")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(23)
		.exec(
			http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}")
			).pause(8)
}

object CommentList{
	val commentList = exec(http("CommentList")
			.get("/comments")
			.headers(headers_0))
		.pause(8)
}

object DeleteComment{
	val deleteComment = exec(http("DeleteComment")
			.get("/comments/delete/1")
			.headers(headers_0))
		.pause(6)
}

	val deleteComment = scenario("DeleteComment").exec(Home.home, 
									LoginScreen.loginScreen, 
									CommentList.commentList, 
									DeleteComment.deleteComment)

	val deleteCommentNoLogin = scenario("DeleteCommentNoLogin").exec(Home.home, 
									CommentList.commentList)

	setUp(deleteComment.inject(rampUsers(5000) during (100 seconds)), deleteCommentNoLogin.inject(rampUsers(5000) during (100 seconds)))
	.protocols(httpProtocol).assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(85)
	)
}