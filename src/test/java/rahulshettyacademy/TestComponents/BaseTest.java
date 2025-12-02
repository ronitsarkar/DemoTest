package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		properties.load(fileInputStream);
		String mavenProp = System.getProperty("Browser");
		String browserName = mavenProp != null ? mavenProp : properties.getProperty("Browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("guest");
			if(browserName.contains("headless")) {
				options.addArguments("headless");
			}
//			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1366, 768));

		} else if (browserName.equalsIgnoreCase("firefox")) {
			// driver setup for firefox
//			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			// driver setup for edge
//			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage lauchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String pathName = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		System.out.println(testCaseName);
		System.out.println(pathName);
		File outputFile = new File(pathName);
		FileUtils.copyFile(source, outputFile);
		return pathName;
	}
}
