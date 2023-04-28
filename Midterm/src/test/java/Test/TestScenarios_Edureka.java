package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Repository.*;
import Utils.*;

public class TestScenarios_Edureka {

	WebDriver driver;
	CommonFunctions cf = new CommonFunctions();
	ConfigFileReader config = new ConfigFileReader();
	ExtentTest test;

	@BeforeSuite
	public void beforeclass() {
		driver = cf.launchBrowser();
		test = cf.generateExtentReport();

	}

	@BeforeTest
	public void befortest() {
		String url = config.getSpecificUrlProperty("googleurl");
		driver.get(url);
		test.log(LogStatus.INFO, "Launch Url", url);
		
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	}

	@Test
	public void searchGoogle() {
		Edureka gs = new Edureka(driver, test);
		test.log(LogStatus.INFO, "Search for Text", "Edureka");
		gs.searchGoogle("Edureka");

	}

	@Test(priority = 1)
	public void validateHompage() {
	
		String edurekaTitle = driver.getTitle();

		if (edurekaTitle.contains("Instructor-Led Online Training with 24X7 Lifetime Support | Edureka")) {
			test.log(LogStatus.PASS, "Succesfully validated", "Edureka Home Page");
		} else {
			test.log(LogStatus.FAIL, "Unsuccesfully validated", "Edureka Home Page");
		}
	}

	@Test(priority = 2)
	public void loginEdureka() throws IOException {

		// Path for the excel file
		File file = new File("D:\\Eclipse-Workspace\\Training\\Midterm\\src\\test\\java\\UsernamePassword.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");

		// Excel username and password
		String usernameExcel = sheet.getRow(1).getCell(0).getStringCellValue();
		String passwordExcel = sheet.getRow(1).getCell(1).getStringCellValue();

		WebElement clickLogin = driver.findElement(By.xpath("//span[@data-gi-action='Login']"));
		clickLogin.click();

		WebElement username = driver.findElement(By.xpath("//input[@id = 'si_popup_email']"));
		username.sendKeys(usernameExcel);

		WebElement password = driver.findElement(By.xpath("//input[@id = 'si_popup_passwd']"));
		password.sendKeys(passwordExcel);

		driver.findElement(By.xpath("//button[@class = 'clik_btn_log btn-block']")).click();
		WebElement modalTitle = driver.findElement(By.xpath("//h4[@class = 'modal-title signup-new-title']"));

		if (modalTitle.isDisplayed()) {
			test.log(LogStatus.PASS, "Login Successfully", "Edureka");
		} else {
			test.log(LogStatus.FAIL, "login success", "Edureka");
		}
	}

	@Test(priority = 2, enabled = false)
	public void clickSignUp() {

		WebElement singUp = driver.findElement(
				By.xpath("//span[@class='signin top-signup register-user giTrackElementHeader hidden-xs']"));
		singUp.click();
	}

	@Test(priority = 3, enabled = false)
	public void enterDetails() {

		WebElement email = driver.findElement(By.xpath("//input[@id='sg_popup_email']"));
		email.sendKeys("macmaczenzen1600@gmail.com");

		WebElement phoneNumber = driver.findElement(By.xpath("//input[@id='sg_popup_phone_no']"));
		phoneNumber.sendKeys("9948571234");

		WebElement signUpButtom = driver
				.findElement(By.xpath("//button[@class='clik_btn_log btn-block signup-new-submit']"));
		signUpButtom.click();

	}

	@Test(priority = 3)
	public void validateProfile() throws IOException {

		WebElement profile = driver.findElement(By.xpath(("//img[@class='img30']")));
		
		if (profile.isDisplayed()) {
			test.log(LogStatus.PASS, "profile validated", "success");
		} else {
			test.log(LogStatus.FAIL, "profile validated", "failed");
		}
		
		profile.click();

		WebElement myProfile = driver.findElement(By.xpath("//a[@data-gi-action='Profile']"));
		myProfile.click();
		
	}

	@Test(priority = 4)
	public void topicsOfInterest() throws IOException {
	
		try {
			WebElement closeAds = driver.findElement(By.xpath("//button[@class='No thanks']"));
			if (closeAds.isDisplayed()) {
				closeAds.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			WebElement topicOfInterest = driver
					.findElement(By.xpath("//a[contains(@href, '/my-profile/topics-of-interest')]"));
			topicOfInterest.click();
			
			//Uncomment below if you haven't add a topic
			WebElement addNow = driver.findElement(By.xpath("//button[@class ='btn btn-add-more']"));
			addNow.click();
			
			//Uncomment below if you have add a topic
			//WebElement edit = driver.findElement(By.xpath(("//button[@class='btn edit-btn']"));
			//edit.click();
			
			File topics = new File("D:\\Eclipse-Workspace\\Training\\Midterm\\src\\test\\java\\TopicInterest.xlsx");
			FileInputStream fis = new FileInputStream(topics);

			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet("Sheet1");

			int rows = sheet.getLastRowNum();
			int cells = sheet.getRow(0).getLastCellNum();

			String tech = sheet.getRow(0).getCell(0).getStringCellValue();
			

			for (int r = 0; r <= rows; r++) {

				XSSFRow row = sheet.getRow(r);

				for (int c = 0; c < cells; c++) {

					XSSFCell cell = row.getCell(c);
					System.out.println(cell);
				}
				
				if(tech.contains("Big Data")) {
					test.log(LogStatus.PASS, "Valid selected topic", "Topic of Interest");
				}
				else {
					test.log(LogStatus.FAIL, "Invalid selected topic", "Topic of Interest");
				}

				WebElement OperatingSystem = driver.findElement(By.xpath("//label[text()='"+tech+"']"));
				OperatingSystem.click();
				
				WebElement saveAndContinue = driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg btn-save pull-right btn_save']"));
				saveAndContinue.click();
			}
		}
	}

	@AfterTest
	public void aftertest() throws IOException {
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    test.log(LogStatus.INFO, "Close the browser", "Chrome");
		//driver.quit();
	}

	@AfterSuite
	public void afterclass() {
		CommonFunctions.closeExtentReport();
	}

}
