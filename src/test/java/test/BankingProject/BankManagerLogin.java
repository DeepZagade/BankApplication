package test.BankingProject;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import test.Testbase.TestBase;

public class BankManagerLogin extends TestBase {

	String fname, lname, postCode, accNumber;

	@BeforeClass
	public void beforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resource\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		test = extent.createTest(getClass().getSimpleName());
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(text(),'Bank Manager Login')]")).click();
		// CommonMethods cmObject = new CommonMethods();
		// cmObject.bankMangerLogin();

	}

	@Test(priority = 0)
	public void verifyAddCustomer() throws Exception {
		// go to add customer tab and add deatils
		fname = "Deep";
		lname = "Zagade";
		postCode = "12345";
		SoftAssert sAssert = new SoftAssert();
		driver.findElement(By.xpath("//button[contains(text(),'Add Customer')]")).click();
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(fname);
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(lname);
		driver.findElement(By.xpath("//input[@placeholder='Post Code']")).sendKeys(postCode);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Add Customer')]")).click();
		// Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		// go to customer tab check if customer is added
		driver.findElement(By.xpath("//button[contains(text(),'Customers')]")).click();
		Thread.sleep(000);
		driver.findElement(By.xpath("//input[@placeholder='Search Customer']")).sendKeys(postCode);// fname + " " +
																									// lname);
		Thread.sleep(1000);
		// WebDriverWait wait= new WebDriverWait(driver, 5);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		boolean flag = false;
		try {

			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='" + postCode + "']")));
			// flag = driver.findElement(By.xpath("//td[text()='" + postCode +
			// "']")).isDisplayed();
			flag = element.isDisplayed();
			// System.out.println("Displayed");
			Thread.sleep(1000);
			sAssert.assertTrue(flag, "Customer is not added");
			sAssert.assertAll();
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	@Test(priority = 1)
	public void verifyOpenAccount() throws Exception {

		driver.findElement(By.xpath("//button[contains(text(),'Open Account')]")).click();
		Select sel = new Select(driver.findElement(By.id("userSelect")));
		sel.selectByVisibleText(fname + " " + lname);
		Thread.sleep(2000);
		Select sel2 = new Select(driver.findElement(By.id("currency")));
		sel2.selectByVisibleText("Rupee");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'Process')]")).click();
		Thread.sleep(1000);
		Alert alert = driver.switchTo().alert();
		String str = alert.getText();
		alert.accept();
		// System.out.println("AlerText="+str);
		accNumber = str.substring(str.length() - 4);
		// System.out.println("Account Number="+accNumber);

		driver.findElement(By.xpath("//button[contains(text(),'Customers')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@placeholder='Search Customer']")).sendKeys(accNumber);
		Thread.sleep(3000);
		SoftAssert sAssert = new SoftAssert();
		boolean flag = false;
		try {
			flag = driver.findElement(By.xpath("//span[contains(text(),'" + accNumber + "')]")).isDisplayed();
			sAssert.assertTrue(flag, "Account is not opened");
			sAssert.assertAll();
		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

	@Test(priority = 3)
	public void verifyDeleteCustomer() throws Exception {
		WebElement SearchBox = driver.findElement(By.xpath("//input[@placeholder='Search Customer']"));
		SearchBox.clear();
		Thread.sleep(1000);
		SearchBox.sendKeys(accNumber);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[text()='Delete']")).click();
		Thread.sleep(1000);
		SearchBox.clear();
		Thread.sleep(500);
		SearchBox.sendKeys(accNumber);
		SoftAssert sAssert = new SoftAssert();
		boolean flag = true;
		try {
			flag = driver.findElement(By.xpath("//span[contains(text(),'" + accNumber + "')]")).isDisplayed();
			sAssert.assertFalse(flag, "Customer is not deleted");
			sAssert.assertAll();
		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

}
