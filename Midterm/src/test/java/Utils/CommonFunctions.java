package Utils;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class CommonFunctions {

	static WebDriver driver;
	static ExtentTest test;
	static ExtentReports report;
	static ConfigFileReader cfr = new ConfigFileReader();

	public static WebDriver launchBrowser() {
		String browserName = cfr.getSpecificUrlProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			return driver;
		} else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			return driver;
		}
		return driver;
	}

	public static ExtentTest generateExtentReport() {
		report = new ExtentReports("EdurekaExtentReport.html");
		test = report.startTest("TestScenarios_Edureka");
		return test;
	}

	public static void closeExtentReport() {
		report.endTest(test);
		report.flush();
	}
		
}
