package org.springframework.samples.petclinic.ui;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Then;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatingOfferListUITest {
	
  @LocalServerPort
  private int port;
  
  private String username;
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @BeforeEach
  public void setUp() throws Exception {
	  System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  @Test
	public void testList() throws Exception {
	  testMatingOfferListUITest("prueba").listadoDeMatingOffers();
	}
  
  @Test
	public void testListNotLoggedIn() throws Exception {
	  testMatingOfferListUITest("").paginaDeLogin();
	}

	private String passwordOf(String username) {
		return "prueba";
	}


  @Test
  public MatingOfferListUITest testMatingOfferListUITest(String username) throws Exception {
	  this.username=username;
	driver.get("http://localhost:"+port);
	driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
	if(username.contentEquals("prueba")) {
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys(username);
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys(passwordOf(username));
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//a[contains(@href, '/matingOffers')]")).click();
	}
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
  
  private void listadoDeMatingOffers() {
		assertEquals("MATING OFFERS", driver.findElement(By.xpath("//a[contains(@href, '/matingOffers')]")).getText());
	}
  
  @Then("Soy redireccionado a la página de login")
  private void paginaDeLogin() {
	    assertEquals("Sign in", driver.findElement(By.xpath("//button[@type='submit']")).getText());
	}

}
