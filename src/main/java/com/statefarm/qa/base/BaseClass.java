package com.statefarm.qa.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.statefarm.qa.utils.Configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.asm.Advice.Local;

public class BaseClass {

	public Configuration configuration = new Configuration(null);	// why? Ans: @BeforeMethod setup >> url,  pageloadwait and implicit wait
	
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		driver = localDriver("firefox");
		driver.get(configuration.getConfiguration("url"));
		driver.manage().timeouts()
				.pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(configuration.getConfiguration("pageloadWait"))));
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(configuration.getConfiguration("implicitWait"))));

	}

	private WebDriver localDriver(String browserName) {			// private for restricted from outside access
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		}
		return driver;
	}

	protected WebDriver getDriver() {   // getter method for >> private WebDriver localDriver(String browserName) 
		return driver;
	}

	@AfterMethod
	public void terminate() {
		driver.quit();
	}
}
