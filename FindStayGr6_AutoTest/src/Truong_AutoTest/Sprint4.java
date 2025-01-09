package Truong_AutoTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Sprint4 {
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	@BeforeClass
	public void setup() {
		driver.navigate().to("http://localhost:8080");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	@AfterClass
	public void close() {
		driver.close();
	}

	public void Login() throws InterruptedException {
		driver.navigate().to("http://localhost:8080/login");
		driver.findElement(By.id("phoneNumber")).sendKeys("0902126304");
		driver.findElement(By.id("password")).sendKeys("123123123");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Login']")).click();
		driver.navigate().back();
		driver.findElement(By.xpath("//button[text()='Login']")).click();
	}

//	// TC_72
	@Test(priority = 1)
	public void TestLocTK() {
		try {
			Login();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div/nav/ul/li[1]/a")).click();
			Thread.sleep(2000);
			WebElement selectElement = driver.findElement(By.xpath("//select[@name='role']"));
			Select dropdown = new Select(selectElement);
			dropdown.selectByVisibleText("Quản trị viên");
			driver.findElement(By.xpath("//button[text()='Lọc']")).click();
			List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
			boolean allRowsAreValid = true;
			for (WebElement row : rows) {
				String role = row.findElement(By.xpath("td[5]")).getText();
				if (!role.equals("Quản trị viên")) {
					allRowsAreValid = false;
					break;
				}

			}
			Assert.assertTrue(rows.size() > 0, "Không có dữ liệu trong bảng");
			Assert.assertTrue(allRowsAreValid, "Tồn tại dòng có vai trò không phải quản trị viên");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test tìm kiếm quản trị viên: " + e.getMessage());
		}
	}
//	//TC_73
	@Test(priority = 2)
	public void hienALlDanhSach() {
		try {
			driver.findElement(By.xpath("/html/body/div/div/div/form/a[1]")).click();
			Thread.sleep(2500);
			List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
			boolean allRowsAreValid = true;
			for (WebElement row : rows) {
				String role = row.findElement(By.xpath("td[5]")).getText();
				if (!role.equals("Quản trị viên")) {
					allRowsAreValid = false;
					break;
				}

			}
			Assert.assertTrue(rows.size() > 0, "Không có dữ liệu trong bảng");
			Assert.assertTrue(!allRowsAreValid, "Tồn tại chỉ đúng quản trị viên");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test hiển thị tất cả danh sách: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void delete() {
		try {
			driver.findElement(By.xpath("/html/body/div/div/div/form/a[1]")).click();
			Thread.sleep(2500);
			List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
			boolean allRowsAreValid = true;
			for (WebElement row : rows) {
				String phone = row.findElement(By.xpath("td[6]")).getText();
				if (phone.equals("0902126302")) {
					row.findElement(By.xpath(".//td[8]/form/button")).click();
					Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	                alert.accept(); 
					break;
				}
			}
			driver.findElement(By.xpath("/html/body/div/div/div/form/a[1]")).click();
			Thread.sleep(2500);
			List<WebElement> rowsCheck = driver.findElements(By.xpath("//table/tbody/tr"));
			for (WebElement row : rowsCheck) {
				String phone = row.findElement(By.xpath("td[6]")).getText();
				if (phone.equals("0902126302")) {
					allRowsAreValid = false;
					break;
				}
			}
			Assert.assertTrue(rows.size() > 0, "Không có dữ liệu trong bảng");
			Assert.assertTrue(allRowsAreValid, "Vẫn tồn tại dữ liệu chưa được xóa");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test xóa tài khoản: " + e.getMessage());
		}
	}
//	//TC_71
	@Test(priority = 4)
	public void testVerifyAccountList() {
	    try {
	    	
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        List<WebElement> headers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	            By.xpath("//table/thead//th")));
	        List<String> expectedColumns = Arrays.asList(
	            "Tên", "Email", "Ngày tạo", "Số dư", "Vai trò", "Số điện thoại", "Mật khẩu"
	        );

	        // Lấy text của các header thực tế
	        List<String> actualColumns = new ArrayList<>();
	        for(WebElement header : headers) {
	            String columnText = header.getText().trim();
	            if(!columnText.isEmpty()) {
	                actualColumns.add(columnText);
	            }
	        }

	        // Kiểm tra số lượng cột và tên cột
	        Assert.assertEquals(actualColumns.size(), expectedColumns.size(),
	            "Số lượng cột không khớp với yêu cầu");
	        Assert.assertEquals(actualColumns, expectedColumns,
	            "Tên các cột không khớp với yêu cầu");

	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test kiểm tra header danh sách tài khoản thất bại: " + e.getMessage());
	    }
	}
	
	
	
	//Test quản lí giao dịch
	
	//TC_71
		@Test(priority = 5)
		public void testVerifyBankList() {
		    try {
		    	driver.findElement(By.xpath("//a[text()='Quản lý giao dịch']")).click();
		    	Thread.sleep(2000);
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        List<WebElement> headers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
		            By.xpath("//table/thead//th")));
		        List<String> expectedColumns = Arrays.asList(
		            "Người dùng", "Số tiền", "Ngày giao dịch", "Trạng thái", "Hành động"
		        );

		        // Lấy text của các header thực tế
		        List<String> actualColumns = new ArrayList<>();
		        for(WebElement header : headers) {
		            String columnText = header.getText().trim();
		            if(!columnText.isEmpty()) {
		                actualColumns.add(columnText);
		            }
		        }

		        // Kiểm tra số lượng cột và tên cột
		        Assert.assertEquals(actualColumns.size(), expectedColumns.size(),
		            "Số lượng cột không khớp với yêu cầu");
		        Assert.assertEquals(actualColumns, expectedColumns,
		            "Tên các cột không khớp với yêu cầu");

		    } catch (Exception e) {
		        e.printStackTrace();
		        Assert.fail("Test kiểm tra header danh sách giao dịch thất bại: " + e.getMessage());
		    }
		}
		
		
		@Test(priority = 6)
		public void TestLocThanhCong() {
			try {
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[text()='Quản lý giao dịch']")).click();
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				Thread.sleep(2000);
				WebElement selectElement = driver.findElement(By.xpath("//select[@name='status']"));
				Select dropdown = new Select(selectElement);
				dropdown.selectByVisibleText("Thành công");
				driver.findElement(By.xpath("//button[text()='Lọc']")).click();
				List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
				boolean allRowsAreValid = true;
				for (WebElement row : rows) {
					String role = row.findElement(By.xpath("td[4]")).getText();
					if (!role.equals("Thành công")) {
						allRowsAreValid = false;
						break;
					}
				}
				Assert.assertTrue(rows.size() > 0, "Không có dữ liệu trong bảng");
				Assert.assertTrue(allRowsAreValid, "Tồn tại dòng có trạng thái Thành công");
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Test tìm kiếm giao dịch Thành công thất bại: " + e.getMessage());
			}
		}
		
		
		// Test quản lí bài đăng 
		
		@Test(priority = 7)
		public void testVerifyTableHeaders() {
		    try {
		    	driver.findElement(By.xpath("//a[text()='Quản lý bài đăng']")).click();
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        List<WebElement> headers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
		            By.xpath("//table/thead//th")));

		        // Danh sách các cột cần kiểm tra theo thứ tự
		        List<String> expectedColumns = Arrays.asList(
		            "ID Bài Đăng",
		            "Tên người dùng",
		            "Tiêu đề",
		            "Giá",
		            "Mô tả",
		            "Ngày tạo",
		            "Loại phòng",
		            "Tỉnh",
		            "Thành phố, quận, huyện",
		            "Xã phường",
		            "Số nhà",
		            "Số điện thoại",
		            "Loại tin",
		            "Diện tích",
		            "Trạng thái"
		        );

		        List<String> actualColumns = new ArrayList<>();
		        for(WebElement header : headers) {
		            String columnText = header.getText().trim();
		            if(!columnText.isEmpty()) {
		                actualColumns.add(columnText);
		            }
		        }
		        Assert.assertEquals(actualColumns.size(), expectedColumns.size(),
		            "Số lượng cột không khớp với yêu cầu");

		        Assert.assertEquals(actualColumns, expectedColumns,
		            "Tên hoặc thứ tự các cột không khớp với yêu cầu");

		    } catch (Exception e) {
		        e.printStackTrace();
		        Assert.fail("Test kiểm tra headers thất bại: " + e.getMessage());
		    }
		}
		
		
		@Test(priority = 8)
		public void testChangeStatus() {
			   try {
			       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			       WebElement firstRow = wait.until(ExpectedConditions.presenceOfElementLocated(
			           By.xpath("//table/tbody/tr[1]")));

			       WebElement statusDropdown = firstRow.findElement(By.xpath(".//select[@name='newStatus']"));
			       Select select = new Select(statusDropdown);
			       String currentStatus = select.getFirstSelectedOption().getText();
			       if (currentStatus.equals("Đã duyệt")) {
			           select.selectByVisibleText("Chờ duyệt");
			           WebElement updateButton = firstRow.findElement(By.xpath(".//button[text()='Cập nhật']"));
			           updateButton.click();
			           driver.navigate().refresh();
			           WebElement firstRowAfterReload = wait.until(ExpectedConditions.presenceOfElementLocated(
			               By.xpath("//table/tbody/tr[1]")));
			           WebElement statusAfterReload = firstRowAfterReload.findElement(By.xpath(".//select[@name='newStatus']"));
			           Select selectAfterReload = new Select(statusAfterReload);
			           String newStatus = selectAfterReload.getFirstSelectedOption().getText();

			           Assert.assertEquals(newStatus, "Chờ duyệt",
			               "Trạng thái không được cập nhật thành công");
			       }

			   } catch (Exception e) {
			       e.printStackTrace();
			       Assert.fail("Test thay đổi trạng thái thất bại: " + e.getMessage());
			   }
			}
}
