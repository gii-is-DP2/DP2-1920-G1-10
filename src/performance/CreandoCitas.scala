package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CreandoCitas extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.ico""", """.*.js""", """.*.css"""), WhiteList())
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
	object Login{
		val login = exec(http("login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/login")
			.headers(headers_3),
            http("request_4")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839360267&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(14).exec(http("logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "prueba")
			.formParam("password", "prueba")
			.formParam("_csrf", "c3caebc0-b1fe-41c3-9fc6-d4f4fca2c04f")
			.resources(http("request_6")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839374795&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(10)
	}
	
	object MattingOfferList{
		val mattingOfferList = exec(http("MattingOffer-list")
			.get("/matingOffers")
			.headers(headers_0)
			.resources(http("request_1")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590765761649&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(10)
	}
		object MattingOffer1{
		val mattingOffer1 = exec(http("mattingOffer1")
			.get("/pets/1/matingOffers/1")
			.headers(headers_0)
			.resources(http("request_10")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839402865&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(12)
	}
	object NewcitaForm{
		val newcitaForm = exec(http("newcitaForm")
			.get("/pets/1/matingOffers/1/citas/new")
			.headers(headers_0)
			.resources(http("request_12")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839415419&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(21).exec(http("citaCreadaMatList")
			.post("/pets/1/matingOffers/1/citas/new")
			.headers(headers_5)
			.formParam("Pet2.id", "15")
			.formParam("place", "hola")
			.formParam("dateTime", "2020/05/31")
			.formParam("petId", "")
			.formParam("citaId", "")
			.formParam("status", "PENDING")
			.formParam("_csrf", "aa126c53-91e3-46fd-910b-6fc2dd3a46b1")
			.resources(http("request_14")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839437146&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(31)
	}
	object Mating2{
		val mating2 = exec(http("mating2")
			.get("/pets/2/matingOffers/2")
			.headers(headers_0)
			.resources(http("request_16")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839468695&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(10)
	}
		object EditCitas{
		val editCitas = exec(http("EditCitas")
			.get("/pets/2/citas/edit/2")
			.headers(headers_0)
			.resources(http("request_18")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839479404&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(10).exec(http("CitaEditada")
			.post("/pets/2/citas/edit/2")
			.headers(headers_5)
			.formParam("status", "ACCEPTED")
			.formParam("petId", "2")
			.formParam("citaId", "2")
			.formParam("_csrf", "aa126c53-91e3-46fd-910b-6fc2dd3a46b1")
			.resources(http("request_20")
			.get(uri1 + "/?q=dRklS2nv3t3vmyy91xYE16-gj42vfeHhkZyRfsR_ae_B9skIDqYHYXYERSDtdRN-oD0mhCbHahawr0afKSGyHopLxHBhUBfe9brl1-_LK5fSKmikAYArRO7F968QGB99HX4V7gMebEBPv6LjYwRN9ut6R1KcI7osAMd3-q2VdvZ9hwFud5SWZC3ynfZlIJbMZLG5KBo9zeMF0yg1&t=1590839490069&ti=undefined&tu=undefined")
			.headers(headers_1)))
		.pause(6)
	}




	val scn = scenario("CreandoCitas")
		
	val pruebascn = scenario("prueba").exec(Home.home,
									Login.login,
									MattingOfferList.mattingOfferList,
									MattingOffer1.mattingOffer1,
									NewcitaForm.newcitaForm,

									)
	val ownerscn = scenario("owner").exec(Home.home,
									Login.login,
									MattingOfferList.mattingOfferList,
									Mating2.mating2,
									EditCitas.editCitas
									)
					

		
	setUp(
		pruebascn.inject(rampUsers(50000) during(10 seconds)),
		ownerscn.inject(rampUsers(50000) during(10 seconds))
		).protocols(httpProtocol)
}
