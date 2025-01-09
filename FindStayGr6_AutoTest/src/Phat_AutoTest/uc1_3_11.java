package Phat_AutoTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class uc1_3_11 {
	private WebDriver driver;
	private String baseUrl = "http://localhost:8080/info";
	private String taikhoan = "0375204558";
	private String matkhau = "123456789";

	@BeforeClass
	public void init() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.navigate().to(baseUrl);
	}
	
	@Test()
	public void ranguobemail() throws InterruptedException {
		login();
		checkRequired();
	}
	
	public void login() throws InterruptedException {
		WebElement numberPhone = driver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));

		numberPhone.sendKeys(taikhoan);
		password.sendKeys(matkhau);

		Thread.sleep(2000);
		password.sendKeys(Keys.ENTER);
	}
	
	private void checkRequired() throws InterruptedException {
		driver.navigate().to(baseUrl);

		Thread.sleep(2000);

		WebElement email = driver.findElement(By.xpath("//*[@id=\"profile-form\"]/form/div[3]/input"));

		if(email.getAttribute("required") != null && email.getAttribute("required").equals("true")) {
			System.out.println("Passed test case uc1_3_11");
		}else {
			System.out.println("Failed test case uc1_3_11");
		}
	}
}
