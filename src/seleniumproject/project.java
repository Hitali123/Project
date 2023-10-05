package seleniumproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class project {
	WebDriver driver;

	@BeforeTest(groups = { "smoke", "regression" })
	public void driverSetup() throws IOException {
	    File file = new File("Utilities//Userdata.properties");
	    FileInputStream fileInput = new FileInputStream(file);
	    Properties prop = new Properties();
	    prop.load(fileInput);

	    String browserName = prop.getProperty("Browsername");

	    WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000)); // Reduced the wait time
	    driver.manage().window().maximize();
	}

	@Test
	public void openURL() {
	    driver.get("https://www.urbanladder.com/");
	}

	@Test(dependsOnMethods = "openURL")
	public void bookshelves() {
	    driver.findElement(By.xpath("(//*[name()='svg'][@class='category-icon'])[11]")).click();
	    driver.findElement(By.cssSelector(".close-reveal-modal.hide-mobile")).click();
	    driver.findElement(By.cssSelector("li[data-group='price'] div[class='gname']")).click();
	    driver.findElement(By.cssSelector("#price_limit_4500-8999")).click();
	}

	@Test(dependsOnMethods = "bookshelves")
	public void Storage() {
	    driver.findElement(By.xpath("(//div[normalize-space()='Storage Type'])[1]")).click();
	    // Click on the "Open" option for storage type
	    driver.findElement(By.cssSelector("#filters_storage_type_Open")).click();
	    // Click on the "Exclude out of stock" option
	    driver.findElement(By.id("filters_availability_In_Stock_Only")).click();

	    // Click on the "Material" option (uncomment this section if needed)
	    // driver.findElement(By.cssSelector("li[data-group='material'] div[class='gname']")).click();
	}

@AfterTest(groups = { "smoke", "regression" })
	public void closeBrowser() {
	    driver.quit();
	}
}
		