package Tung_AutoTest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class testsearch {
	WebDriver driver = new ChromeDriver();
	
	@BeforeClass
	public void init() {
		
		driver.manage().window().maximize();
	}
	
	@Test(priority = 1)
	public void testsearchloaiphong() throws InterruptedException {
		driver.get("http://localhost:8080/");
		Thread.sleep(2000);
		Select sortDropdown = new Select(driver.findElement(By.id("roomTypeSelect")));
		sortDropdown.selectByValue("1");
		Thread.sleep(2000);
		driver.findElement(By.id("searchButton")).click();
		List<WebElement> listelement = driver.findElements(By.className("item_room"));
		listelement.get(0).click();
		Thread.sleep(2000);
		
		String loaiphong = driver.findElement(By.xpath("//*[@id=\"wrapper_infor_detail_room\"]/div[2]/div[1]/div/div[3]/table/tbody/tr[4]/td[2]")).getText();
		
		
		System.out.println("Kết quả kiểm tra theo loại phòng true nếu pass, false nếu failures:  Kết quả test là"+ loaiphong.equals("Phòng trọ"));
		
	}
	@Test(priority = 2)
	public void testsearchkhuvuc() throws InterruptedException {
		driver.get("http://localhost:8080/");
		driver.findElement(By.id("locationInput")).click();
		Thread.sleep(2000);
		Select sortDropdown = new Select(driver.findElement(By.id("CitySelect")));
		sortDropdown.selectByValue("1");
		Thread.sleep(2000);
		
		Select sortDropdown2 = new Select(driver.findElement(By.id("DistrictSelect")));
		sortDropdown2.selectByValue("31");
		Thread.sleep(2000);
		
		
		Select sortDropdown3 = new Select(driver.findElement(By.id("WardSelect")));
		sortDropdown3.selectByValue("540");
		Thread.sleep(2000);
		driver.findElement(By.id("applyLocation")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.id("searchButton")).click();
		
		
		  
		  List<WebElement> listelement =
		  driver.findElements(By.className("item_room")); listelement.get(0).click();
		  Thread.sleep(2000);
		  
		  String khuvuc = driver.findElement(By.xpath(
		  "//*[@id=\"wrapper_infor_detail_room\"]/div[2]/div[1]/div/p/span")).getText();
		  
		  
		  System.out.println("Kết quả kiểm theo khu vực tra true nếu pass, false nếu failures: Kết quả test là"+
				  khuvuc.equals("Địa chỉ: 5, Phường Gia Thụy, Quận Long Biên, Hà Nội"));
		 
		
	}
}
