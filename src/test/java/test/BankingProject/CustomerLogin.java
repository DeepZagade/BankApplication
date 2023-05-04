package test.BankingProject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import test.Testbase.TestBase;

public class CustomerLogin extends TestBase {

	String fname, lname, postCode, accNumber;
	int amountToDeposite, amountToWithdraw;

	@BeforeClass
	public void beforeClass() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resource\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		test = extent.createTest(getClass().getSimpleName());
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject");
		Thread.sleep(1000);
		// Login
		driver.findElement(By.xpath("//button[contains(text(),'Customer Login')]")).click();
		Select sel = new Select(driver.findElement(By.id("userSelect")));
		sel.selectByVisibleText("Ron Weasly");
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		// CommonMethods cmObject = new CommonMethods();
		// cmObject.customerLogin();

	}

	@Test(priority = 0)
	public void verifyAmountCanBeDeposited() throws Throwable {
		String str = driver.findElement(By.xpath("//strong[@class='ng-binding'][2]")).getText();
		int balance = Integer.parseInt(str);
		driver.findElement(By.xpath("//button[contains(text(),'Deposit')]")).click();
		Thread.sleep(1000);
		amountToDeposite = 1000;
		driver.findElement(By.xpath("//input[@placeholder='amount']")).sendKeys("" + amountToDeposite);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Deposit')]")).click();
		Thread.sleep(1000);

		String str2 = driver.findElement(By.xpath("//strong[@class='ng-binding'][2]")).getText();
		int totalBalance = Integer.parseInt(str2);

		boolean flag = false;
		if (totalBalance == (balance + amountToDeposite)) {
			flag = true;
		}
		SoftAssert sAssert = new SoftAssert();
		sAssert.assertTrue(flag, "AMount is not Deposited as Balance is not matching");
		sAssert.assertAll();

	}

	@Test(priority = 1)
	public void verifySuccessBannerAfterDeposite() {
		SoftAssert sAasert = new SoftAssert();
		boolean flag = false;
		try {
			flag = driver.findElement(By.xpath("//span[contains(text(),'Deposit Successful')]")).isDisplayed();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		sAasert.assertTrue(flag, "Deposite added succefully Banner is not displayed");
		sAasert.assertAll();

	}

	@Test(priority = 2)
	public void verifyTransactionOfAmountDeposited() throws Exception {
		SoftAssert sAasert = new SoftAssert();
		driver.findElement(By.xpath("//button[contains(text(),'Transactions')]")).click();
		Thread.sleep(2000);
		boolean flag = false;
		try {
			flag = driver
					.findElement(
							By.xpath("//td[text()='" + amountToDeposite + "']/following-sibling::td[text()='Credit']"))
					.isDisplayed();
		} catch (Exception e) {
		}
		driver.findElement(By.xpath("//button[contains(text(),'Back')]")).click();
		sAasert.assertTrue(flag, "Transaction of Deposited amount is not displayed");
		sAasert.assertAll();

	}

	@Test(priority = 3)
	public void verifyAmountCanBeWithdraw() throws InterruptedException {
		String str = driver.findElement(By.xpath("//strong[@class='ng-binding'][2]")).getText();
		int Oldbalance = Integer.parseInt(str);
		driver.findElement(By.xpath("//button[contains(text(),'Withdrawl')]")).click();
		Thread.sleep(1000);
		amountToWithdraw = 500;
		driver.findElement(By.xpath("//input[@placeholder='amount']")).sendKeys("" + amountToWithdraw);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Withdraw')]")).click();
		Thread.sleep(1000);

		String str2 = driver.findElement(By.xpath("//strong[@class='ng-binding'][2]")).getText();
		int totalBalance = Integer.parseInt(str2);

		boolean flag = false;
		if (totalBalance == (Oldbalance - amountToWithdraw)) {
			flag = true;
		}
		SoftAssert sAssert = new SoftAssert();
		sAssert.assertTrue(flag, "AMount is not withdraw successfully as balance is not matching");
		sAssert.assertAll();

	}

	@Test(priority = 4)
	public void verifySuccessBannerAfterWithdrawl() {
		SoftAssert sAasert = new SoftAssert();
		boolean flag = false;
		try {
			flag = driver.findElement(By.xpath("//span[contains(text(),'Transaction successful')]")).isDisplayed();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		sAasert.assertTrue(flag, "Amount withdrawn succefully Banner is not displayed");
		sAasert.assertAll();
	}

	@Test(priority = 5)
	public void verifyTransactionOfAmountWithdrawn() throws Exception {
		SoftAssert sAasert = new SoftAssert();
		driver.findElement(By.xpath("//button[contains(text(),'Transactions')]")).click();
		// Thread.sleep(2000);
		boolean flag = false;
		try {
			flag = driver
					.findElement(
							By.xpath("//td[text()='" + amountToWithdraw + "']/following-sibling::td[text()='Debit']"))
					.isDisplayed();
		} catch (Exception e) {
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(text(),'Back')]")).click();
		sAasert.assertTrue(flag, "Transaction of Withdrawn amount is not displayed");
		sAasert.assertAll();

	}

}
