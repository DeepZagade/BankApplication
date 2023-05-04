package test.Testbase;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.uiFramework.KTCTC.common.CommonMethods;
//import com.uiFramework.KTCTC.helper.getScreenShot.CaptureScreen;
//import com.uiFramework.KTCTC.helper.property.PropertyFileHelper;
//import com.uiFramework.KTCTC.utils.ExtentManager;

public class TestBase {
	public static ExtentReports extent;
	public static ExtentTest test;
	public WebDriver driver;
	public File f;
	//public CommonMethods cmObj = new CommonMethods();
	//CaptureScreen screenObj = new CaptureScreen();
	//public PropertyFileHelper proObj = new PropertyFileHelper("env.properties");
	
	@BeforeSuite
	public void beforeSuite() throws Exception{
		f = new File(System.getProperty("user.dir") + "\\src\\main\\resource\\ExtentReport\\" + "extentReport.html");
		ExtentSparkReporter spark = new ExtentSparkReporter(f);
		extent = new ExtentReports();
		extent.attachReporter(spark);	}
	
//	@BeforeMethod
//	public void beforeMethod() {
//
//	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, result.getThrowable());

//			File scrnShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//			String destPathForScrnShot = System
//					.getProperty("user.dir" + "\\src\\main\\resource\\Screenshots\\BankMangerLogin.png");
//			FileUtils.copyFile(scrnShot, new File(destPathForScrnShot));

			// String imagePath = screenObj.getScreenShot(result.getName(),driver);
			// test.addScreenCaptureFromPath(imagePath);

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, result.getName() + " is passed");

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, result.getName() + " is skipped");
		}

	}

	@AfterClass
	public void afterclass() {
		 driver.quit();
	}

	@AfterSuite
	public void afterSuiteMethod() {
		extent.flush();
		try {
			Desktop.getDesktop().browse(f.toURI());
			// driver.navigate().to(f.toURI().toString()); by me
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	
}
