package org.springframework.samples.petclinic.ui;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewCitaUITest {

	@LocalServerPort
	private int port;
	

	private int CitaAlInicio;
	private String username;
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		// String pathToGeckoDriver = "C:\\Users\\felix\\Documents";
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testNewCitaIfNotMyOffer() throws Exception {
		as("prueba").LaNuevaCitaSeEncuentraEnLaTabla();
	}
	@Test
	public void testNewCitaIfMyOffer() throws Exception {
		as("owner1").muestraPagerror();
	}


	

	private CharSequence passwordOf(String username) {
        CharSequence res = "prueba";
        if (username.equals("owner1")) {
            res = "0wn3r";
        }
        return res;
    }
	 
	private NewCitaUITest as(String username) {
		this.username = username;
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(passwordOf(username));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/matingOffers')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Entrar')]")).click();
		CitaAlInicio = contarCitas();
		driver.findElement(By.xpath("//a[contains(text(),'Add\n				cita')]")).click();
		if(!username.toUpperCase().equals(driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText()))
		{
			driver.findElement(By.xpath("//form[@id='cita']/div/select")).click();
		    new Select(driver.findElement(By.xpath("//select[@id='Pet2Id']"))).selectByVisibleText("Leo");
		    driver.findElement(By.xpath("//select[@id='Pet2Id']/option[2]")).click();
			    driver.findElement(By.id("place")).click();
			    driver.findElement(By.id("place")).clear();
			    driver.findElement(By.id("place")).sendKeys("hola");
			    driver.findElement(By.id("dateTime")).click();
			    driver.findElement(By.linkText("30")).click();
			    driver.findElement(By.xpath("//button[@type='submit']")).click();
			driver.findElement(By.xpath("//a[contains(@href, '/matingOffers')]")).click();
			driver.findElement(By.xpath("//a[contains(text(),'Entrar')]")).click();
		}
			
		return this;
	}
	private int contarCitas() {
		WebElement tablaCitas = driver.findElement(By.xpath("//table[@id='TablaCitas']"));
		
		List<WebElement> filasDetablaCitas = tablaCitas.findElements(By.tagName("tr"));
		return filasDetablaCitas.size() - 1;
	}
	public void LaNuevaCitaSeEncuentraEnLaTabla() {
		assertTrue(contarCitas() > CitaAlInicio);
		
	}
	private void muestraPagerror() {
		assertEquals("Something happened...", driver.findElement(By.xpath("//h2[@id='oops']")).getText());
		
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
