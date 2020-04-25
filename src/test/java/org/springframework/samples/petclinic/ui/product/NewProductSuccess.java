package org.springframework.samples.petclinic.ui.product;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NewProductSuccess {

		private WebDriver driver;
		private String baseUrl;
		private boolean acceptNextAlert = true;
		private StringBuffer verificationErrors = new StringBuffer();

		@BeforeEach
		public void setUp() throws Exception {
			String pathToGeckoDriver = "C:\\Users\\felix\\Documents";
			System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
			driver = new FirefoxDriver();
			baseUrl = "https://www.google.com/";
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

		@Test
		public void testCreaciNNuevoProducto() throws Exception {
			driver.get("http://localhost:8080/");
			driver.findElement(By.linkText("Login")).click();
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys("admin1");
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("4dm1n");
			driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
			driver.findElement(By.linkText("Products")).click();
			driver.findElement(By.linkText("New Product")).click();
			driver.findElement(By.id("name")).click();
			driver.findElement(By.id("name")).clear();
			driver.findElement(By.id("name")).sendKeys("Champú");
			driver.findElement(By.id("description")).clear();
			driver.findElement(By.id("description")).sendKeys("Champú antipulgas");
			driver.findElement(By.id("price")).clear();
			driver.findElement(By.id("price")).sendKeys("20");
			driver.findElement(By.id("stock")).clear();
			driver.findElement(By.id("stock")).sendKeys("30");
			driver.findElement(By.id("urlImage")).clear();
			driver.findElement(By.id("urlImage"))
					.sendKeys("https://images-na.ssl-images-amazon.com/images/I/81%2BPaitQc4L._AC_SY355_.jpg");
			driver.findElement(By.xpath("//button[@type='submit']")).click();
		}

//		@AfterEach
//		public void tearDown() throws Exception {
//			driver.quit();
//			String verificationErrorString = verificationErrors.toString();
//			if (!"".equals(verificationErrorString)) {
//				fail(verificationErrorString);
//			}
//		}

		private boolean isElementPresent(By by) {
			try {
				driver.findElement(by);
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		}

		private boolean isAlertPresent() {
			try {
				driver.switchTo().alert();
				return true;
			} catch (NoAlertPresentException e) {
				return false;
			}
		}

		private String closeAlertAndGetItsText() {
			try {
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				if (acceptNextAlert) {
					alert.accept();
				} else {
					alert.dismiss();
				}
				return alertText;
			} finally {
				acceptNextAlert = true;
			}
		}
	}
