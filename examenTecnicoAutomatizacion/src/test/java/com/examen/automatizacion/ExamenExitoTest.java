package com.examen.automatizacion;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExamenExitoTest {

	
	WebDriver driver;
	String Tipo_Driver = "webdriver.chrome.driver";
	String Path_Driver = "./src/test/resources/chromedriver/chromedriver.exe";
	String url = "https://www.exito.com/ ";
	
	WebDriverWait wait, wait2;
	
	@Before
	public void setUp() {
		System.setProperty(Tipo_Driver, Path_Driver);
		driver = new ChromeDriver();	
		driver.manage().window().maximize();
		driver.get(url);
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
		wait2 = new WebDriverWait(driver, Duration.ofSeconds(40));
	}
	
	@Test
	public void test() throws TimeoutException {
		
		By locator1 = By.id("menu_hamburguesa"); 
		WebElement search1 = driver.findElement(locator1); 
		search1.click(); 
		
		By locator2 = By.xpath("//p[text()='Dormitorio']"); 
		WebElement search2 = driver.findElement(locator2); 
		search2.click(); 
		
		By locator3 = By.xpath("//a[@href='/hogar/muebles/cabeceros']"); 
		WebElement search3 = driver.findElement(locator3); 
		search3.click();
		
		By locator4 = By.cssSelector("li article.productCard_productCard__M0677"); 
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator4)); 
		
		List<WebElement> articles = driver.findElements(locator4);
		
		List<Integer> quantities = Arrays.asList(1, 2, 3, 5, 10);
		
		int elementsToProcess = Math.min(5, articles.size());
		
		for (int i = 0; i < elementsToProcess; i++) { 
			try { WebElement article = articles.get(i); 
			WebElement agregarButton = article.findElement(By.cssSelector("button.DefaultStyle_default__jW12W > span")); 
			
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", agregarButton); 
			wait.until(ExpectedConditions.elementToBeClickable(agregarButton)); 
			try { agregarButton.click(); 
			} catch (ElementClickInterceptedException e) { 
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", agregarButton); 
				}
			Thread.sleep(1000);
			
			articles = driver.findElements(locator4); 
			article = articles.get(i);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("QuantitySelectorDefault_quantity__input__X0wIu"))); 
			WebElement quantitySelector = article.findElement(By.className("QuantitySelectorDefault_quantity__input__X0wIu")); 
			quantitySelector.clear(); 
			quantitySelector.sendKeys(quantities.get(i).toString());
			
			WebElement imageButton = article.findElement(By.cssSelector("use[href='/icons.svg#icon-outlined-more_mas_agregar_selector']")); 
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", imageButton); 
			wait2.until(ExpectedConditions.elementToBeClickable(imageButton)); 
			
			try { 
				imageButton.click(); 
				} catch (ElementClickInterceptedException e) { 
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", imageButton); 
					}
			} catch (StaleElementReferenceException e) { 
				System.out.println("El elemento de referencia está desactualizado para uno de los artículos."); 
				e.printStackTrace();
				
			} catch (ElementClickInterceptedException e) { 
				System.out.println("No se pudo hacer clic en el botón 'Agregar' para uno de los artículos porque otro elemento bloqueaba el clic."); 
				e.printStackTrace();
				
			} catch (NoSuchElementException e) { 
				System.out.println("No se pudo encontrar el elemento para uno de los artículos."); 
				e.printStackTrace();
				 
			} catch (Exception e) { 
				System.out.println("Se produjo un error inesperado."); 
				e.printStackTrace();
			}
		}
		
		By locator5 = By.cssSelector("div[data-fs-cart-quantity='true'] span"); 
		WebElement search5 = driver.findElement(locator5); 
		search5.click(); 
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*@After
	public void tearDown() {
		driver.quit();
	}*/
	
	}
