package org.springframework.samples.petclinic.ui;

import static org.junit.Assert.assertTrue;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentsListUITest {

	@LocalServerPort
	private int port;

	private String username;
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testListAsLoggedIn() throws Exception {
		as("owner1").listadoDeComentarios();
	}
	
	@Test
	public void testListAsNotLoggedIn() throws Exception {
		asNotLogged().inLogIn();
	}

	private CharSequence passwordOf(String username) {
		return "0wn3r";
	}

	private CommentsListUITest as(String username) {
		this.username = username;
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(passwordOf(username));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/comments')]")).click();
		return this;
	}

	private CommentsListUITest asNotLogged() {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(@href, '/comments')]")).click();
		return this;
	}
	
	private void listadoDeComentarios() {
		assertEquals("Comments", driver.findElement(By.xpath("//h2[@id='listadoComentarios']")).getText());
	}
	
	private void inLogIn() {
	    assertEquals("Sign in", driver.findElement(By.xpath("//button[@type='submit']")).getText());
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