package Phat_AutoTest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class uc5_2_04 {
	private WebDriver driver;
	Actions actions;
	private JavascriptExecutor js;
	private String baseUrl = "http://localhost:8080/info";
	private String taikhoan = "0375204558";
	private String matkhau = "123456789";
	private String numberphone = "";

	@BeforeClass
	public void init() {
		driver = new ChromeDriver();
		actions = new Actions(driver);
		js = (JavascriptExecutor) driver;
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
	public void clickZalo() throws InterruptedException {
		js.executeScript("window.scrollBy(0, 500);");

		Thread.sleep(2000);
		WebElement btn_zalo = driver.findElement(
				By.xpath("/html/body/div[6]/div/div/div[3]/div[1]/a[1]/span[2]/span[5]/span[2]/button[2]"));
		numberphone = btn_zalo.getAttribute("data-phone");
		btn_zalo.click();
	}

	@Test(dependsOnMethods = "clickZalo")
	public void checkUrlZalo() {
		if (driver.getCurrentUrl().contains("https://id.zalo.me/account")
				|| driver.getCurrentUrl().equals("https://zalo.me/" + numberphone)) {
			System.out.println("Chuyển trang zalo thành công");
		} else {
			System.out.println("Chuyển trang zalo không thành công");
		}
	}
}
