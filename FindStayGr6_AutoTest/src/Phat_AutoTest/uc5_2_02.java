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

public class uc5_2_02 {
	private WebDriver driver;
	Actions actions;
	private JavascriptExecutor js;
	private String baseUrl = "http://localhost:8080/info";
	private String taikhoan = "0375204558";
	private String matkhau = "123456789";
	private int quantity_first = 0;
	private int quantity_after = 0;
	private int id_item_add = -1;

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
		
		List<WebElement> list = driver.findElements(By.className("wrapper_list_search"));
		quantity_first = list.size();
	}
	
	@Test(dependsOnMethods = "checkQuantityFavorite")
	public void clickFavorite() throws InterruptedException {
		driver.findElement(By.xpath("/html/body/header/div[1]/a/img")).click();
		
		js.executeScript("window.scrollBy(0, 500);");
		
		WebElement btn_add_favorite = driver.findElement(By.xpath("/html/body/div[6]/div/div/div[3]/div[1]/a[1]/span[1]/img[2]"));
		btn_add_favorite.click();
		
		quantity_after++;
		Thread.sleep(2000);
		WebElement btn_add_favorite_after = driver.findElement(By.xpath("/html/body/div[6]/div/div/div[3]/div[1]/a[1]/span[1]/img[2]"));
		id_item_add = Integer.valueOf(btn_add_favorite_after.getAttribute("itemid"));
		
		js.executeScript("window.scrollBy(0, -500);");
	}
	
	@Test(dependsOnMethods = "clickFavorite")
	public void checkFavorite() {
		WebElement btn_favorite = driver.findElement(By.xpath("//*[@id=\"header-heart\"]"));
		btn_favorite.click();
		
		List<WebElement> list = driver.findElements(By.className("wrapper_list_search"));
		
		if((quantity_first + quantity_after) == list.size()) {
			
			WebElement id = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/a/span[1]/span/img[1]"));
			if(Integer.valueOf(id.getAttribute("itemid")) == id_item_add) {
				System.out.println("Thêm thành công");
			}else {
				System.out.println("Thêm sai bài tin");
			}
		}else {
			System.out.println("Thêm không thành công");
		}
	}
}
