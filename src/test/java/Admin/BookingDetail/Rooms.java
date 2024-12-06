package Admin.BookingDetail;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class Rooms {
    WebDriver driver;
    @BeforeMethod
    void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new");

        driver = new ChromeDriver(chromeOptions);

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://14.176.232.213:8084/admin/dashboard");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
    }

    @Test
    void searchRoom() {
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Booking')]")).click();
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("5094-966800013");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[contains(text(),'5094-966800013')]")
        ));

        Assert.assertEquals(bookingRow.getText(), "5094-966800013");
        Assert.assertEquals(
                driver.findElement(By.xpath("//td[contains(text(),'tana-room-type')]")).getText(),
                "tana-room-type"
        );

        WebElement statusElement = driver.findElement(By.xpath("//span[contains(@class,'label-secondary') and text()='ONLINE_PENDING']"));
        Assert.assertEquals(statusElement.getText(), "ONLINE_PENDING");
    }

    @Test
    void confirmAction() {
        driver.findElement(By.xpath("//span[@class='title' and contains(text(),'Booking')]")).click();
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("5094-966800013");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[contains(text(),'5094-966800013')]")
        ));
        driver.findElement(By.xpath("//td[contains(text(),'5094-966800013')]/following-sibling::td//a[contains(@class, 'btn-tbl-edit')]"))
                .click();
    }

    @Test
    void checkPendingStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='title' and contains(text(),'Booking')]")));
        bookingTitle.click();

        new Select(driver.findElement(By.name("example4_length"))).selectByValue("100");

        WebElement onlinePendingBooking = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[contains(text(), 'ONLINE_PENDING')]")));
        assertTrue(onlinePendingBooking.isDisplayed());

        WebElement actionButton = onlinePendingBooking.findElement(By.xpath("//td/span[contains(text(), 'PENDING')]/ancestor::tr//a[contains(@class, 'btn-tbl-edit')]"));
        actionButton.click();

        assertTrue(driver.getCurrentUrl().contains("/admin/booking/booking-details"));
    }

    @Test
    void checkSuccessStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='title' and contains(text(),'Booking')]")));
        bookingTitle.click();

        WebElement successBooking = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[contains(text(), 'SUCCESS')]")));
        assertTrue(successBooking.isDisplayed());

        WebElement actionButton = successBooking.findElement(By.xpath("//td/span[contains(text(), 'SUCCESS')]/ancestor::tr//a[contains(@class, 'btn-tbl-edit')]"));
        actionButton.click();

        assertTrue(driver.getCurrentUrl().contains("/admin/booking/booking-details"));
    }

    @Test
    void checkCancelStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='title' and contains(text(),'Booking')]")));
        bookingTitle.click();

        WebElement cancelBooking = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[contains(text(), 'CANCEL')]")));
        assertTrue(cancelBooking.isDisplayed());

        WebElement actionButton = cancelBooking.findElement(By.xpath("//td/span[contains(text(), 'CANCEL')]/ancestor::tr//a[contains(@class, 'btn-tbl-edit')]"));
        actionButton.click();

        assertTrue(driver.getCurrentUrl().contains("/admin/booking/booking-details"));
    }

    @Test
    void checkStayingStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='title' and contains(text(),'Booking')]")));
        bookingTitle.click();

        WebElement stayingBooking = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[contains(text(), 'STAYING')]")));
        assertTrue(stayingBooking.isDisplayed());

        WebElement actionButton = stayingBooking.findElement(By.xpath("//td/span[contains(text(), 'STAYING')]/ancestor::tr//a[contains(@class, 'btn-tbl-edit')]"));
        actionButton.click();

        assertTrue(driver.getCurrentUrl().contains("/admin/booking/booking-details"));
    }

    @Test
    void makeConfirm(){
        checkPendingStatus();

        driver.findElement(By.xpath("//div/a[@class='btn btn-secondary ' and text()=' Make Confirm']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr/th/b[contains(text(), 'Booking Status')]/ancestor::tr/td/span")));
        String updatedStatus = bookingStatus.getText();

        assertEquals("STAYING", updatedStatus);
    }

    @Test
    void cancelBookingAfterConfirm() throws InterruptedException {
        checkPendingStatus();

        driver.findElement(By.xpath("//div/a[@class='btn btn-secondary ' and text()=' Make Confirm']")).click();

        driver.findElement(By.xpath("(//button)[1]")).click();
        driver.findElement(By.xpath("//a[@class='dropdown-item' and text()='CANCEL']")).click();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        Thread.sleep(3000);
        alert.dismiss();
    }

    @Test
    void acceptBookingAfterConfirm() throws InterruptedException {
        checkPendingStatus();

        driver.findElement(By.xpath("//div/a[@class='btn btn-secondary ' and text()=' Make Confirm']")).click();

        driver.findElement(By.xpath("(//button)[1]")).click();
        driver.findElement(By.xpath("//a[@class='dropdown-item' and text()='CANCEL']")).click();

        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        alert.accept();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookingStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr/th/b[contains(text(), 'Booking Status')]/ancestor::tr/td/span")));
        String updatedStatus = bookingStatus.getText();
        assertEquals("CANCEL", updatedStatus);
    }

    @Test
    void addService() throws InterruptedException {
        checkStayingStatus();
        driver.findElement(By.xpath("//a[@data-target='#add_service']")).click();
        driver.findElement(By.xpath("//select[@name='service']")).click();
        new Select(driver.findElement(By.name("service"))).selectByIndex(1);

        Thread.sleep(2000);
        driver.findElement(By.name("qty")).sendKeys("10");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
    @Test
    void resetService() throws InterruptedException {
        checkStayingStatus();
        driver.findElement(By.xpath("//a[@data-target='#add_service']")).click();
        driver.findElement(By.xpath("//select[@name='service']")).click();
        new Select(driver.findElement(By.name("service"))).selectByIndex(1);

        Thread.sleep(2000);
        driver.findElement(By.name("qty")).sendKeys("10");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@type='reset']")).click();
        Thread.sleep(1000);

        // Step 8: Assert that the "service" field is reset to "Select Service"
        String selectedService = new Select(driver.findElement(By.name("service"))).getFirstSelectedOption().getText();
        assertEquals("Select Service", selectedService, "Select Service");

        // Step 9: Assert that the "qty" field is reset to 0
        String quantityValue = driver.findElement(By.name("qty")).getAttribute("value");
        assertEquals("0", quantityValue, "");
    }

    @Test
    void checkOut(){
        checkStayingStatus();
        driver.findElement(By.xpath("//div/a[@data-target='#checkout']")).click();
        driver.findElement(By.xpath("//label[@class='custom-control-label']")).click();
        driver.findElement(By.xpath("//button[@id='btn-send']")).click();

        driver.findElement(By.xpath("//button[@onclick=\"$('#payment-details').hide();$('#card').hide();$('#cash').hide();$('#initial-content').show();$('#payment-form').trigger('reset');\"]")).click();
        driver.findElement(By.xpath("//button[@id='btn-send']")).click();
        driver.findElement(By.xpath("(//button[@type='submit' and @class='btn btn-tsk'])[3]")).click();
    }

}

