package Thuan_AutoTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoTestOfThuan {
	WebDriver driver;
	BufferedWriter logWriter;
	private WebDriverWait wait;
	JavascriptExecutor js;

	@BeforeClass
	public void setUp() throws IOException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		js = (JavascriptExecutor)driver;
		
		File logFile = new File("C:\\Users\\cntt4\\Downloads\\nhatky_autotest_phongtro.txt");
		try {
			logWriter = new BufferedWriter(new FileWriter(logFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logWriter.write("Bắt đầu test");
		logWriter.newLine();
	}
	
	@Test(priority = 1)
	public void DangKyTaiKhoan() throws IOException {
		
		System.out.println("Bắt đàu test chức năng đăng ký");
		logWriter.write("Bắt đàu test chức năng đăng ký");
		logWriter.newLine();
		
		driver.get("http://localhost:8080/");

		driver.get("http://localhost:8080/register");
		
		
		WebElement fullName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
		fullName.sendKeys("THuận");
		
		WebElement sdt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
		sdt.sendKeys("0384756877");
		
		WebElement mk = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		mk.sendKeys("12121212");
		
		WebElement xn_mk = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm_password")));
		xn_mk.sendKeys("12121212");
		
		
		WebElement radio_chutro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/form/div[5]/label[2]/input")));
		radio_chutro.click();
		
		WebElement btn_dangky = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("register-btn")));
		btn_dangky.click();
		
		String thongbao;
		
		if(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Đăng kí thành công , vui lòng đăng nhập']"))).isDisplayed()) {
			thongbao = "Test PASS: Đã đăng kí thành công";
			
		} else {
			thongbao = "Test FAIL: Đăng kí không thành công";
		}
		
		System.out.println(thongbao);
		logWriter.write(thongbao);
		logWriter.newLine();
		
		
	}
	
	
	@Test(priority = 2)
	public void DangTin() throws IOException {
		
		System.out.println("Bắt đàu test chức năng đăng tin");
		logWriter.write("Bắt đàu test chức năng đăng tin");
		logWriter.newLine();

		driver.get("http://localhost:8080/login");
		
		WebElement sdt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phoneNumber")));
		sdt.sendKeys("0384756877");
		
		WebElement mk = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		mk.sendKeys("12121212");
		
		WebElement btn_login = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Login']")));
		btn_login.click();

		WebElement dangtin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/div[5]/a")));
		dangtin.click();
		
		WebElement thanhPho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"citySelect\"]")));
		thanhPho.click();
		
		WebElement thanhPho1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"citySelect\"]/option[3]")));
		thanhPho1.click();
		
		WebElement quanHuyen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"districtSelect\"]")));
		quanHuyen.click();
		
		WebElement quanHuyen1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"districtSelect\"]/option[2]")));
		quanHuyen1.click();
		
		WebElement xa = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"locationward\"]")));
		xa.click();
		
		WebElement xa1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"locationward\"]/option[10]")));
		xa1.click();
		
		WebElement address = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"address\"]")));
		address.sendKeys("61/9 Võ Mười");
		
		WebElement loaiPhong = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"roomTypeid\"]")));
		loaiPhong.click();
		
		WebElement loaiPhong1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"roomTypeid\"]/option[2]")));
		loaiPhong1.click();
		
		WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"title\"]")));
		title.sendKeys("Cho thuê phòng trọ");
		
		WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"description\"]")));
		description.sendKeys("Cho thuê phòng trọ sạch sẽ, giá rẻ phù hợp sinh viên");
		
		WebElement price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("price")));
		price.sendKeys("1500000");
		
		WebElement area = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("area")));
		area.sendKeys("25");
	
		WebElement object = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"object\"]")));
		object.click();
		
		WebElement object1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"object\"]/option[2]")));
		object1.click();
		
		WebElement noithat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"fullFurniture\"]")));
		noithat.click();
		
		WebElement loaitin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loaitin\"]")));
		loaitin.click();
		
		WebElement loaitinfree = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loaitin\"]/option[2]")));
		loaitinfree.click();
		
		WebElement goitg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"goitime\"]")));
		goitg.click();
		
		WebElement goitg1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"goitime\"]/option[2]")));
		goitg1.click();
		
		WebElement tg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"songay\"]")));
		tg.click();
		
		WebElement songay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"songay\"]/option[4]")));
		songay.click();
		
		WebElement btn_submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[2]/form/div[5]/button")));
		btn_submit.click();
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept(); 
		
		String thongbao;
		
		if(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"searchtrangthai\"]"))).isDisplayed()) {
			thongbao = "Test PASS: Đã đăng tin thành công";
			
		} else {
			thongbao = "Test FAIL: Đăng tin không thành công";
		}
		
		System.out.println(thongbao);
		logWriter.write(thongbao);
		logWriter.newLine();
	}
	
	@AfterClass
	public void End() throws IOException {
		if (logWriter != null) {
			logWriter.write("Kết thúc test");
			logWriter.newLine();
			logWriter.close();
		}
//		driver.quit();
	}
}
