import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DevigetChallenge {
	
	private WebDriver driver;
	
	private WebDriver webdriverSetup() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		this.driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	
	@Test
	public void devigetChallengeTest() {
		// Se instancia el driver, se ingresa a la web, se busca el producto y va a la pagina 2
		driver = webdriverSetup();
		driver.get("https://www.mercadolibre.com.ar");		
		driver.findElement(By.className("nav-search-input")).sendKeys("iphone");
		driver.findElement(By.className("nav-search-input")).submit();
		driver.findElement(By.xpath("//*[@class='andes-pagination ']//*[text()='2']")).click();
		
		// Se ubica la segunda publicacion referida a la busqueda y si el titulo contiene "iPhone" ingresa, si no rompe el test
		WebElement publicacion = driver.findElement(By.cssSelector("[id='searchResults'] [class='results-item highlighted article stack product ']:nth-child(2) h2 a"));		
        if (publicacion.getText().toLowerCase().contains("iphone"))
        	publicacion.click();
        else
            Assert.assertTrue(false, "La segunda publicacion de la segunda pagina de resultados no es un iPhone");
        
        // Si el titulo del producto contiene "iPhone" verifica que el precio no este vacio, que este el boton de comprar y que este la galeria de imagenes
        if (driver.findElement(By.cssSelector("[class='item-title__primary ']")).getText().toLowerCase().contains("iphone")) {
        	Assert.assertTrue(!driver.findElement(By.cssSelector("[id='productInfo'] [class='price-tag-fraction']")).getText().isEmpty());
        	WebElement botonComprar = driver.findElement(By.id("BidButtonTop"));
        	Assert.assertTrue(botonComprar.getAttribute("value").equals("Comprar ahora"));
        	Assert.assertTrue(driver.findElement(By.cssSelector("[class='gallery-content item-gallery__wrapper']")).isDisplayed());	
        } else {
        	Assert.assertTrue(false, "El titulo del producto no contiene 'iPhone', verificar que se cargo en la web");
        }
	}
}
