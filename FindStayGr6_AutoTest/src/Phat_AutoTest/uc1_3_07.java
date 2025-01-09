package Phat_AutoTest;

import java.time.Duration;

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

public class uc1_3_07 {
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
	public void doitenlienhe() throws InterruptedException {
		login();
		updateName();
	}

	public void login() throws InterruptedException {
		WebElement numberPhone = driver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));

		numberPhone.sendKeys(taikhoan);
		password.sendKeys(matkhau);

		Thread.sleep(2000);
		password.sendKeys(Keys.ENTER);
	}

	public void updateName() {
		driver.navigate().to(baseUrl);

		String nameUpdate = "Thịnh Phát";

		WebElement name = driver.findElement(By.xpath("//*[@id=\"profile-form\"]/form/div[2]/input"));
		name.clear();
		name.sendKeys(nameUpdate);

		driver.findElement(By.xpath("//*[@id=\"profile-form\"]/form/button")).click();

		checkUpdate(nameUpdate);
	}

	private void checkUpdate(String nameUpdate) {
		if (driver.getTitle().equals("Thông tin tài khoản")) {
			System.out.println("Đã vào trang: " + driver.getTitle());
		}

		WebElement checkMsg = driver.findElement(By.xpath("//*[@id=\"profile-form\"]/div/p"));

		WebElement name = driver.findElement(By.xpath("//*[@id=\"header-infor\"]/div[2]/span[1]/span"));
		if (checkMsg.getText().equals("Cập nhật thành công") && name.getText().equals(nameUpdate)) {
			System.out.println("Cập nhật thành công tên: " + nameUpdate);
		} else {
			System.out.println("Cập nhật không thành công: " + nameUpdate);
		}
	}

	@AfterClass()
	public void finish() {
		try {
	        Thread.sleep(2000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    driver.quit();
	}
}
