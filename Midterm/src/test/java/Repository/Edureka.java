package Repository;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utils.CommonFunctions;

public class Edureka {
	WebDriver driver;
	CommonFunctions cf = new CommonFunctions();
	ExtentTest test;
	
	public Edureka(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	By searchBox = By.name("q");
	By edurekaPage = By.xpath("//span[contains(text(),'Edureka E-Learning Platform - Edureka Certification')]");

	@Test
	public void searchGoogle(String searchText) {

		String title = driver.getTitle();

		if (title.contains("Google")) {
			test.log(LogStatus.PASS, "Title contains", "google");
		} else {
			test.log(LogStatus.FAIL, "Title doesn't contains", "google");
		}
		
		test.log(LogStatus.INFO, "Search For Text", "Edureka");
		
		driver.findElement(searchBox).sendKeys("edureka");
		driver.findElement(searchBox).sendKeys(Keys.ENTER);
		
		driver.findElement(edurekaPage).click();

	}
	
	
	
}
