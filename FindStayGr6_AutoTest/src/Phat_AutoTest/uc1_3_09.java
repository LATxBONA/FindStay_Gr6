package Phat_AutoTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class uc1_3_09 {
	private WebDriver driver;
	private String baseUrl = "http://localhost:8080/info";
	private String taikhoan = "0375204558";
	private String matkhau = "123456789";
	private String email = "phathuynh.040803@gmail.com";

	@BeforeClass
	public void init() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.navigate().to(baseUrl);
	}

	@Test()
	public void doiemail() throws InterruptedException {
		login();
		updateEmail();
	}

	public void login() throws InterruptedException {
		WebElement numberPhone = driver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));

		numberPhone.sendKeys(taikhoan);
		password.sendKeys(matkhau);

		Thread.sleep(2000);
		password.sendKeys(Keys.ENTER);
	}

	public void updateEmail() {
		driver.navigate().to(baseUrl);

		WebElement input_email = driver.findElement(By.xpath("//*[@id=\"profile-form\"]/form/div[3]/input"));
		input_email.clear();
		input_email.sendKeys(email);

		driver.findElement(By.xpath("//*[@id=\"profile-form\"]/form/button")).click();

		checkUpdate(email);
	}

	private void checkUpdate(String nameUpdate) {
		if (driver.getTitle().equals("Thông tin tài khoản")) {
			System.out.println("Đã vào trang: " + driver.getTitle());
		}

		WebElement checkMsg = driver.findElement(By.xpath("//*[@id=\"profile-form\"]/div/p"));

		if (checkMsg.getText().equals("Cập nhật thành công")) {
			System.out.println("Cập nhật thành công email: " + email);
		} else {
			System.out.println("Cập nhật không thành công email: " + email);
		}
	}

	/*
	 * @AfterClass() public void finish() { try { Thread.sleep(2000); } catch
	 * (InterruptedException e) { e.printStackTrace(); } driver.quit(); }
	 */
}
