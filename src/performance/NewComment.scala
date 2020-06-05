package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class NewComment extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.css""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,it-IT;q=0.6,it;q=0.5,en-US;q=0.4",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,it-IT;q=0.6,it;q=0.5,en-US;q=0.4",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,it-IT;q=0.6,it;q=0.5,en-US;q=0.4",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Accept-Language" -> "es",
		"Content-Type" -> "text/plain;charset=UTF-8",
		"Origin" -> "file://",
		"Sec-Fetch-Mode" -> "cors",
		"Sec-Fetch-Site" -> "cross-site",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) GitKraken/7.0.0 Chrome/78.0.3904.130 Electron/7.1.13 Safari/537.36",
		"authorization" -> "token bcb5c2f61eefbc23280cb49d53058a56afff0823")

	val headers_7 = Map(
		"Accept" -> "*/*",
		"Pragma" -> "no-cache",
		"User-Agent" -> "git/2.0 (libgit2 0.28.0)")

    val uri1 = "https://api.github.com/graphql"
    val uri3 = "https://github.com/gii-is-DP2/DP2-1920-G1-10.git/info/refs"

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
        .formParam("username", "owner1")
        .formParam("password", "0wn3r")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
	}
	object Products{
		val products = exec(http("Products")
			.get("/products")
			.headers(headers_0))
		.pause(12)
	}

	object NewComment{
		val newComment = exec(http("request_5")
			.get("/comments/new/1")
			.headers(headers_0))
		.pause(17)
		.exec(http("request_6")
			.post(uri1)
			.headers(headers_6)
			.body(RawFileBody("dp2/createproduct/0006_request.txt"))
			.resources(http("request_7")
			.get(uri3 + "?service=git-upload-pack")
			.headers(headers_7)))
		.pause(1)
		.exec(http("request_8")
			.post("/comments/save?id=1")
			.headers(headers_3)
			.formParam("descripcion", "prueba")
			.formParam("email", "pruena@prueba.com")
			.formParam("id", "")
    	    .formParam("_csrf", "c3caebc0-b1fe-41c3-9fc6-d4f4fca2c04f")
		).pause(35)
	}

	object Error{
		val error = exec(http("request_5")
			.get("/comments/new/1")
			.headers(headers_0))
		.pause(17)
		exec(http("Error")
			.post(uri1)
			.headers(headers_6)
			.body(RawFileBody("dp2/createproduct/0011_request.txt"))
			.resources(http("request_12")
			.get(uri3 + "?service=git-upload-pack")
			.headers(headers_7),
            http("request_13")
			.post("/comments/save?id=1")
			.headers(headers_3)
			.formParam("descripcion", "jladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbasjladsnlkasbdilasbdasildbasildbsailudsabasliudbasiludasbiuaslbdiasdbas")
			.formParam("email", "prueba@prueba.com")
			.formParam("id", "")
    	    .formParam("_csrf", "c3caebc0-b1fe-41c3-9fc6-d4f4fca2c04f")
			)).pause(7)
	}

	val success = scenario("NewComment").exec(
		Home.home,
		Login.login,
		Products.products,
		NewComment.newComment,
		Products.products
		)
	val Failure = scenario("NewCommentError").exec(
		Home.home,
		Login.login,
		Products.products,
		Error.error
	)

	setUp(
		success.inject(rampUsers(1000) during (100 seconds)),
		Failure.inject(rampUsers(1000) during (100 seconds)))
		.protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(85)
		)
}