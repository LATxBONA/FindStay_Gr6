package LAT_AutoTest;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SignIn {
	private WebDriver driver;
    private JavascriptExecutor js;
    private Actions actions; 


    @BeforeTest
    public void setup() throws IOException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        
    }
    
    @Test(priority = 1)
    public void loginTest() throws InterruptedException {
    	driver.get("http://localhost:8080/login");
    	Thread.sleep(2000);
    	
    	WebElement username = driver.findElement(By.id("phoneNumber"));
    	username.sendKeys("0123456789");
    	
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("0123456789");
		 
		Thread.sleep(2000);
		WebElement btn_submit = driver.findElement(By.xpath("/html/body/div[2]/div/form/button"));
		btn_submit.click();
		Thread.sleep(2000);
		
		driver.get("http://localhost:8080/login");
		Thread.sleep(5000);
		WebElement username1 = driver.findElement(By.id("phoneNumber"));
		username1.sendKeys("0123456789");
		WebElement password1 = driver.findElement(By.id("password"));
		password1.sendKeys("123456789");
		Thread.sleep(2000);
		WebElement btn_submit1 = driver.findElement(By.xpath("/html/body/div[2]/div/form/button"));
		btn_submit1.click();
		driver.navigate().back();
		btn_submit1.click();
		Thread.sleep(2000);
		
		
		try {
			WebElement check = driver.findElement(By.xpath("//*[@id=\"header-infor\"]"));
			System.out.println("login thành công");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("login không thành công");
		}
		
		
		WebElement PhongTro = driver.findElement(By.xpath("/html/body/ul/li[2]/a"));
		PhongTro.click();
		Thread.sleep(2000);
		
		WebElement Home = driver.findElement(By.xpath("/html/body/ul/li[1]/a"));
		Home.click();
		Thread.sleep(2000);
		
		WebElement item1 = driver.findElement(By.xpath("/html/body/div[6]/div/div/div[3]/div[1]/a[1]"));
		js.executeScript("arguments[0].scrollIntoView(true);", item1);
		Thread.sleep(1000);
		item1.click();
		Thread.sleep(2000);
		
    }
    
    
}
