package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingCreateUITest {

	@LocalServerPort
	private int port;

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
	public void crearBookingTestCase() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("practica");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("prueba1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		driver.findElement(By.linkText("Book")).click();
		driver.findElement(By.id("numProductos")).click();
		driver.findElement(By.id("numProductos")).clear();
		driver.findElement(By.id("numProductos")).sendKeys("4");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		try {
			Assert.assertEquals("Champú Para Perros",
					this.driver.findElement(By.linkText("Champú Para Perros")).getText());
		} catch (Error e) {
			this.verificationErrors.append(e.toString());
		}
	}

	@Test
	public void FalloCantidadSuperiorTestCase() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("practica");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("prueba1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/bookings')]")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		driver.findElement(By.linkText("Products")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'Book')])[2]")).click();
		driver.findElement(By.id("numProductos")).click();
		driver.findElement(By.id("numProductos")).clear();
		driver.findElement(By.id("numProductos")).sendKeys("21");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		try {
			Assert.assertEquals("Something happened...", driver.findElement(By.xpath("//h2")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

	}

	@Test
	public void ListaBookingSinLoginTestCase() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		try {
			Assert.assertEquals("Please sign in", driver.findElement(By.xpath("//h2")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
