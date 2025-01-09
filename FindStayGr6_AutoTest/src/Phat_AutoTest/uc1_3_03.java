package Phat_AutoTest;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class uc1_3_03 {
	private WebDriver driver;
	Actions actions;
	private String baseUrl = "http://localhost:8080/info";
	private String taikhoan = "0375204558";
	private String matkhau = "123456789";

	@BeforeClass
	public void init() {
		driver = new ChromeDriver();
		actions = new Actions(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.navigate().to(baseUrl);
	}

	@Test()
	public void login() throws InterruptedException {
		WebElement numberPhone = driver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));

		numberPhone.sendKeys(taikhoan);
		password.sendKeys(matkhau);

		Thread.sleep(2000);
		password.sendKeys(Keys.ENTER);
	}

	@Test(dependsOnMethods = "login")
	private void checkRequired() throws InterruptedException {
		driver.navigate().to(baseUrl);
		
		WebElement btn_dmk = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div[1]/a[2]"));
		btn_dmk.click();

		Thread.sleep(2000);

		WebElement matkhaucu = driver.findElement(By.xpath("//*[@id=\"passwordForm\"]/div[1]/input"));
		WebElement matkhaumoi = driver.findElement(By.xpath("//*[@id=\"passwordForm\"]/div[2]/input"));
		WebElement xacnhanmatkhau = driver.findElement(By.xpath("//*[@id=\"passwordForm\"]/div[3]/input"));

		if(matkhaucu.getAttribute("required").equals("true") && matkhaumoi.getAttribute("required").equals("true") && xacnhanmatkhau.getAttribute("required").equals("true")) {
			System.out.println("Passed test case uc1_3_03");
		}else {
			System.out.println("Failed test case uc1_3_03");
		}
	}

	/*
	 * @AfterClass() public void finish() { try { Thread.sleep(2000); } catch
	 * (InterruptedException e) { e.printStackTrace(); } driver.quit(); }
	 */
}
