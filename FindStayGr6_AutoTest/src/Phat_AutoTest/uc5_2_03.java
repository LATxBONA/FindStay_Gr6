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

public class uc5_2_03 {
	private WebDriver driver;
	Actions actions;
	private JavascriptExecutor js;
	private String baseUrl = "http://localhost:8080/info";
	private String taikhoan = "0375204558";
	private String matkhau = "123456789";

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
	public void checkQuantityFavorite() {
		WebElement btn_favorite = driver.findElement(By.xpath("//*[@id=\"header-heart\"]"));
		btn_favorite.click();
	}

	@Test(dependsOnMethods = "checkQuantityFavorite")
	public void clickDeleteFavorite() throws InterruptedException {
		Thread.sleep(2000);
		WebElement btn_delete_favorite = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/a/span[1]/span/img[2]"));
		btn_delete_favorite.click();
	}

	@Test(dependsOnMethods = "clickDeleteFavorite")
	public void checkFavorite() {
		List<WebElement> list = driver.findElements(By.className("wrapper_list_search"));

		if (list.size() == 0) {
			System.out.println("Xóa thành công");
		} else {
			System.out.println("Xóa không thành công");
		}
	}
}
