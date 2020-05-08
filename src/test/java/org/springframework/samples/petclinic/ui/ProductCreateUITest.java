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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Then;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCreateUITest {

	@LocalServerPort
	private int port;

	private int productosAlInicio;
	private String nombreProducto = "Comida para perros";
	private String textoError = "Something happened...";

	private String username;
	private WebDriver driver;
	private String baseUrl;
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
	public void testNewProductAsAdmin() throws Exception {
		as("admin1").elNuevoProductoSeEncuentraEnLaTabla();
	}

	@Test
	public void testNewProductAsOwner() throws Exception {
		as("owner1").paginaDeError();
	}

	private CharSequence passwordOf(String username) {
		CharSequence res = "4dm1n";
		if (username.equals("owner1")) {
			res = "0wn3r";
		}
		return res;
	}

	private ProductCreateUITest as(String username) {
		this.username = username;
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(passwordOf(username));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/products')]")).click();
		productosAlInicio = contarProductos();
		driver.findElement(By.xpath("//a[contains(@href, '/products/new')]")).click();
		if (username.equals("admin1")) {
			driver.findElement(By.id("name")).click();
			driver.findElement(By.id("name")).clear();
			driver.findElement(By.id("name")).sendKeys(nombreProducto);
			driver.findElement(By.id("description")).clear();
			driver.findElement(By.id("description")).sendKeys("Nutrientes necesarios para tu mascota");
			driver.findElement(By.id("price")).clear();
			driver.findElement(By.id("price")).sendKeys("15");
			driver.findElement(By.id("stock")).clear();
			driver.findElement(By.id("stock")).sendKeys("30");
			driver.findElement(By.id("urlImage")).clear();
			driver.findElement(By.id("urlImage"))
					.sendKeys("https://www.tupienso.com/image/data/satisfaction/satisfaction-adult-medium.jpg");
			driver.findElement(By.xpath("//button[@type='submit']")).click();
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

	private int contarProductos() {
		WebElement tablaProductos = driver.findElement(By.xpath("//table[@id='productsTable']"));
		List<WebElement> filasDetablaProductos = tablaProductos.findElements(By.tagName("tr"));
		return filasDetablaProductos.size() - 1;
	}

	@Then("El nuevo producto se añade al resto")
	public void elNuevoProductoSeEncuentraEnLaTabla() {
		assertTrue(contarProductos() > productosAlInicio);
		assertTrue(driver.findElement(By.xpath("//table[@id='productsTable']")).getText().contains(nombreProducto));
	}

	@Then("Soy redireccionado a la página de error")
	private void paginaDeError() {
		assertEquals(driver.findElement(By.xpath("//h2[@id='oops']")).getText(), textoError);
	}
}