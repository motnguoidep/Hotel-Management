package Admin;

import com.sun.source.tree.AssertTree;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Login {
    WebDriver driver;

    @BeforeMethod
    void Setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new");

        driver = new ChromeDriver(chromeOptions);

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến form tạo user
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();
    }

    long index = System.currentTimeMillis();

    /*
    * 1. Open Browser
    * 2. Navigate to http://14.176.232.213:8084/admin
    * 3. Fill in username with admin
    * 4. Fill in password with 123456
    * 5. Check on checkbox
    * 6. Click on Login button
    */

    @Test
    void AdminLogin(){
        driver.get("http://14.176.232.213:8084/admin");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys(("123456"));

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
    }
    /*
     * 1. Open Browser
     * 2. Navigate to http://14.176.232.213:8084/admin/dashboard
     * 3. Click on Users
     * 4. Click on Add User
     * 5. Fill in all fields
     * 6. Click on Submit button
     *  */

    @Test
    void AddUsersSuccess(){
        driver.get("http://14.176.232.213:8084/admin/dashboard");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys(("123456"));
        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();

        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest" + index);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(String.format("thuongngo09072003+%d@gmail.com", index));
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        driver.findElement(By.xpath("//button[@id='submit']")).click();

    }

    /*
     * 1. Open browser
     * 2. Navigate to http://14.176.232.213:8084/
     * 3. Click on Login button
     * 4. Fill in Email with thuongngo09072003+6@gmail.com
     * 5. Fill in Password with Co190723.
     * 6. Check on Checkbox
     * 7. Click on Sign in button */
    @Test
    void UserLoginWithAcountAdminCreate(){
        driver.get("http://14.176.232.213:8084/");
        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.name("email")).sendKeys("thuongngo09072003+6@gmail.com");
        driver.findElement(By.name("password")).sendKeys(("Co190723."));

        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
    }

  /* Admin add user unsuccessful when input full name less than 6 characters into [Full Name] field */
    @Test
    void AddUserWithFullNameLessThan6Characters() {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thu");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        // Kiểm tra thông báo lỗi (nếu có)
        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Username không được vượt quá 50 ký tự')]"));

        // Xác nhận rằng thông báo lỗi xuất hiện
        Assert.assertTrue(errorMessage.isDisplayed(), "Full name canot be less than 4 characters");
    }

    /* Admin add user unsuccessful when input full name less than 6 characters into [Full Name] field */
    @Test
    void AddUserWithFullNameMoreThan50Characters() {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongthuongthuongthuongthuongthuongthuongthuongthuongthuong");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        // Kiểm tra thông báo lỗi (nếu có)
        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Username không được vượt quá 50 ký tự')]"));

        // Xác nhận rằng thông báo lỗi xuất hiện
        Assert.assertTrue(errorMessage.isDisplayed(), "Full name canot be less than 4 characters");
    }

    /* Admin add user unsuccessful when input full name contain space characters into [Full Name] field */
    @Test
    void AddUserWithFullNameSpaceCharacters() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("            ");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Username không được vượt quá 50 ký tự')]"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Full name canot contain only space characters");
    }

    /* Admin add user unsuccessful when input full name contain special characters into [Full Name] field */
    @Test
    void AddUserWithFullNameSpecialCharacters() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("@#$%^&&***&@^%#");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Username không được vượt quá 50 ký tự')]"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Full name can't contain special characters");
    }

    /* Admin add user unsuccessful when input full name contain special characters into [Full Name] field */
    @Test
    void AddUserWithFullNameWithNumberAndCharacters() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuong1907");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Username không được vượt quá 50 ký tự')]"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Full name can't contain numbers and characters");
    }

    /* Admin add user unsuccessful when input full name contain special characters  and characters into [Full Name] field */
    @Test
    void AddUserWithFullNameWithSpecialCharAndChar() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuong%$%#^#^");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Full name can't contain special characters and characters')]"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Full name can't contain special characters and characters");
    }


    /* Admin add user unsuccessful when input full name contain special characters  and number into [Full Name] field */
    @Test
    void AddUserWithFullNameWithSpecialCharAndNumber() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("12345%$%#^#^");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Full name can't contain special characters and numbers')]"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Full name can't contain special characters and number");
    }

    /* Admin add user unsuccessful when input full name only contain number into [Full Name] field */
    @Test
    void AddUserWithFullNameWithNumber() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("0123456789");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003+1@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Full name can't only contain numbers')]"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Full name can't contain only number");
    }

    /* Admin add user unsuccessful with email not contain userid into [Email] field */
    @Test
    void AddUserWithAddressNotUserID() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Email Address!']"));
    }

    /* Admin add user unsuccessful with email not contain @ into [Email] field */
    @Test
    void AddUserWithAddressNotAt() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuonggmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Email Address!']"));
    }

    /* Admin add user unsuccessful with email not contain domain into [Email] field */
    @Test
    void AddUserWithAddressNotDomain() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuong@.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Email Address!']"));
    }

    /* Admin add user unsuccessful with email not exist into [Email] field */
    @Test
    void AddUserWithAddressNotExist() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("bachuthegioi@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Invalid Email!']"));
    }

    /* Admin add user unsuccessful with userId contain space into [Email] field */
    @Test
    void AddUserWithAddressContainSpace() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuong ngo@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Email Address!']"));
    }

    /* Admin add user unsuccessful with domain contain space into [Email] field */
    @Test
    void AddUserWithAddressContainSpaceDomain() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuong@gmail .com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Email Address!']"));
    }

    /* Admin add user unsuccessful with email has account into [Email] field */
    @Test
    void AddUserWithAddressHasAcount() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Email had account']"));
    }

//    Admin add user unsuccessfully when phone number more than 10 numbers
    @Test
    void AddUserWithPhoneMoreThanMax() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("039832175999999999999");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Phone number have 0-10 numbers']"));
    }

    //    Admin add user unsuccessfully when phone number less than or equal 1 numbers
    @Test
    void AddUserWithPhoneLessThanMin() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("9");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement errormessage = driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Number!']"));
        Assert.assertTrue(errormessage.isDisplayed());
    }

    //    Admin add user unsuccessfully when phone number contain characters
    @Test
    void AddUserWithPhoneWithChar() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("ngothixuanthuong");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Number!']"));
    }

    //    Admin add user unsuccessfully when phone number contain space characters
    @Test
    void AddUserWithPhoneWithSpace() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("                ");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Number!']"));
    }

    //    Admin add user unsuccessfully when phone number virtual
    @Test
    void AddUserWithPhoneNumberVirtual() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0399999999");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");  // Nhập username dài
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Invalid Phone Number!']"));
    }

    //    Admin add user unsuccessfully when username contain special characters
    @Test
    void AddUserWithUserContainSpecialChar() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("@#$%^&");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid User!']"));
    }
    //    Admin add user unsuccessfully when username more than 50 characters
    @Test
    void AddUserWithUserMoreThan50Char() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuonggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid User!']"));

    }

    //    Admin add user unsuccessfully when username contain special characters
    @Test
    void AddUserWithUserOnlyNumber() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("01234567899999");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid User!']"));
    }

    //    Admin add user unsuccessfully when username contain special characters
    @Test
    void AddUserWithLeaveUserBlank() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid User!']"));
    }
    //    Admin add user unsuccessfully when username less than 2 characters
    @Test
    void AddUserWithUserLessThan2() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("t");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Co190723.");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid User!']"));
    }
    //    Admin add user unsuccessfully with password less than 6 characters
    @Test
    void AddUserWithPasswordLessThan6() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Choco");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Choco");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Password']"));
    }

    //    Admin add user unsuccessfully with password less than 6 characters
    @Test
    void AddUserWithPasswordMoreThan32() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("ChocoChocoChocoChocoChocoChocoChocoChocoChocoChoco");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("ChocoChocoChocoChocoChocoChocoChocoChocoChocoChoco");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Password']"));
    }

    //    Admin add user unsuccessfully with password equal 6 characters
    @Test
    void AddUserWithPasswordEqual6() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Choco19");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("Choco19");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

    }
    //    Admin add user unsuccessfully with password equal 32 characters
    @Test
    void AddUserWithPasswordEqual32() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("ChocoChocoChocoChocoChocoChocoCh");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("ChocoChocoChocoChocoChocoChocoCh");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

    }

    //    Admin add user unsuccessfully when all password is special characters
    @Test
    void AddUserWithPassworAlSpecialChars() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("*@^%$^##@&@");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("*@^%$^##@&@");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();
        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Password']"));
    }

    //    Admin add user unsuccessfully when all password is numberics
    @Test
    void AddUserWithPassworAllNumbers() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();
        driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Password']"));
    }

    //    Admin add user unsuccessfully when password contain space
    @Test
    void AddUserWithPassworContainSpace() throws InterruptedException {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234   5678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("1234   5678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()='Enter Valid Password']"));
        // Xác nhận rằng thông báo lỗi xuất hiện
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message 'Enter Valid Password' was not displayed.");
    }

    //    Admin add user unsuccessfully when confirm password not match with password
    @Test
    void AddUserWithConfirmPassword() {
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='remember-me']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        // Điền thông tin người dùng
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("123456"); // Không khớp với password
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");

        // Gửi form
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        // Kiểm tra thông báo lỗi
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='mdl-textfield__error' and text()=\"Passwords Don't Match\"]"));

        // Xác nhận rằng thông báo lỗi xuất hiện
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message 'Passwords Don't Match' was not displayed.");
    }

    @Test
    void VerifyPasswordEncrypted() {
        // Mở trang đăng nhập
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        // Đăng nhập
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();

        // Điều hướng đến trang quản lý người dùng
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Users')]")).click();
        driver.findElement(By.xpath("//a[@href='/admin/user/create']")).click();

        // Điền thông tin người dùng
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");  // Password
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678"); // Confirm Password

        // Tìm và kiểm tra thuộc tính 'type' của các trường password
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        WebElement confirmPasswordField = driver.findElement(By.xpath("//input[@id='txtConfirmPwd']"));

        // Lấy thuộc tính 'type'
        String passwordType = passwordField.getAttribute("type");
        String confirmPasswordType = confirmPasswordField.getAttribute("type");

        // Kiểm tra xem cả hai trường đều là password field
        Assert.assertEquals(passwordType, "password", "Password field is not encrypted correctly.");
        Assert.assertEquals(confirmPasswordType, "password", "Confirm Password field is not encrypted correctly.");
    }

    @Test
    void testAddressMoreThan50Characters() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("a".repeat(51));  // Nhập hơn 50 ký tự
        submitForm();
        assertErrorDisplayed("Address cannot exceed 50 characters.");
    }

    @Test
    void testAddressLessThan1Character() {
        // Điền thông tin người dùng
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");// Password
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("");
        submitForm();
        assertErrorDisplayed("Address must contain at least 1 character.");
    }

    @Test
    void testAddressAllSpecialCharacters() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("!@#$%^&*()");  // Nhập toàn ký tự đặc biệt
        submitForm();
        assertErrorDisplayed("Address must contain valid characters.");
    }

    @Test
    void testAddressAllSpaceCharacters() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("     ");  // Nhập toàn khoảng trắng
        submitForm();
        assertErrorDisplayed("Address cannot contain only spaces.");
    }

    @Test
    void testAddressAllNumeric() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("1234567890");  // Nhập toàn số
        submitForm();
        assertErrorDisplayed("Address must contain at least one alphabetic character.");
    }

    @Test
    void testAddressExactly50Characters() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("a".repeat(50));  // Nhập đúng 50 ký tự
        submitForm();
        assertNoErrorDisplayed();  // Không có lỗi dự kiến
    }

    @Test
    void testAddressBlankField() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).clear();  // Để trống trường
        submitForm();
        assertErrorDisplayed("Address cannot be blank.");
    }

    @Test
    void testEnterVirtualAddress() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("http://fakeaddress.com");  // Nhập địa chỉ ảo
        submitForm();
        assertErrorDisplayed("Invalid address format.");
    }

    // Hàm gửi form
    private void submitForm() {
        driver.findElement(By.xpath("//button[@id='submit']")).click();
    }

    // Hàm kiểm tra lỗi hiển thị
    private void assertErrorDisplayed(String expectedError) {
        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and text()='" + expectedError + "']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Expected error message not displayed: " + expectedError);
    }

    // Hàm kiểm tra không có lỗi
    private void assertNoErrorDisplayed() {
        Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@class, 'alert')]")).isEmpty(), "Unexpected error message displayed.");
    }

//   In process
    @Test
    void testUploadInvalidFileType()  {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");  // Để trống trường
        // Tìm và chọn file không phải ảnh (.txt)
        WebElement dr = driver.findElement(By.xpath("//button[@class='btn btn-default']"));
        dr.sendKeys("D:\\JavaBasic\\JvSelenium\\HotelManagement\\src\\test\\java\\IMG\\testpt.txt");
        submitForm();

        // Kiểm tra thông báo lỗi
//        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'Invalid file type')]"));
//        Assert.assertTrue(errorMessage.isDisplayed(), "Expected error for invalid file type not displayed.");
    }
// In process
    @Test
    void testUploadFileLargerThan10MB() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");  // Để trống trường
        // Tìm và chọn file lớn hơn 10MB
        WebElement uploadField = driver.findElement(By.xpath("//input[@type='file']"));
        uploadField.sendKeys("C:\\path\\to\\large-file.jpg");  // Thay bằng đường dẫn file lớn trên máy bạn
        submitForm();
        // Kiểm tra thông báo lỗi
        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert') and contains(text(), 'File size exceeds 10MB')]"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Expected error for large file size not displayed.");
    }

    @Test
    void AddUserSuccessAndRedirectToListView() {
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("thuongtest");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("thuongngo09072003@gmail.com");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0398321759");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("thuongthuong");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@id='txtConfirmPwd']")).sendKeys("12345678");
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("Quang Nam");  // Để trống trường
        submitForm();
        // Kiểm tra xem có chuyển đến trang bảo mật không
        String expectedUrl = "https://the-internet.herokuapp.com/secure";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }
}
