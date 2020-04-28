package org.springframework.samples.petclinic.ui;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Then;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatingOfferCreateUITest {
	
  @LocalServerPort
  private int port;
  
  private String nombreOferta = "New Offer Success";
  private String textoError = "Something happened...";
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String username;

  @BeforeEach
  public void setUp() throws Exception {
	  System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
    @Test
	public void testNewOfferAsOwner() throws Exception {
	  testMatingOfferCreateUITest("prueba").laOfertaHaSidoCreada();
	}

	@Test
	public void testNewOfferASNoPets() throws Exception {
		testMatingOfferCreateUITest("pruebaNoPets").paginaDeError();
	}
	
	private String passwordOf(String username) {
		if (username.equals("prueba")) {
			return "prueba";
		}else {
		return "pruebaNoPets";	
		}
	}

  @Test
  public MatingOfferCreateUITest testMatingOfferCreateUITest(String username){
    driver.get("http://localhost:" +port);
    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys(username);
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys(passwordOf(username));
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//a[contains(@href, '/matingOffers')]")).click();
    driver.findElement(By.xpath("//a[contains(@href, '/matingOffers/new')]")).click();
    if(username.equals("prueba")) {
    	driver.findElement(By.id("PetId")).click();
    new Select(driver.findElement(By.id("PetId"))).selectByVisibleText("pruebaFem");
    driver.findElement(By.xpath("//option[@value='15']")).click();
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys(nombreOferta);
    }
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    
    return this;
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
 
  @Then("La oferta ha sido añadida")
	public void laOfertaHaSidoCreada() {
		assertTrue(driver.findElement(By.xpath("//table[@id='matingOffersTable']")).getText().contains(nombreOferta));
	}

	@Then("Soy redireccionado a la página de error")
	private void paginaDeError() {
		assertEquals(driver.findElement(By.xpath("//h2[@id='oops']")).getText(), textoError);
	}
}

