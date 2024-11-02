package User;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserRegistrationTests {

    WebDriver driver;

    @BeforeMethod
    void Setup() {
        driver = new ChromeDriver();
    }

    @Test
    void RegisterAndVerifyRedirection() {
        driver.get("http://14.176.232.213:8084/");
        driver.findElement(By.linkText("Register")).click();

        long index = System.currentTimeMillis();

        driver.findElement(By.name("Reg-Username")).sendKeys("thuongtest" + index);
        driver.findElement(By.name("Reg-Email")).sendKeys(String.format("thuongngo09072003+%d@gmail.com", index));
        driver.findElement(By.name("Reg-Password")).sendKeys("Co190723.");
        driver.findElement(By.name("Confirm Password")).sendKeys("Co190723.");

        WebElement checkbox = driver.findElement(By.xpath("//input[@class='custom-control-input' and @required='required']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.xpath("//input[@value='Sign Up']")).click();
    }

    @Test
    void UserLogin(){
        driver.get("http://14.176.232.213:8084/");
        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.name("email")).sendKeys("thuongtest19");
        driver.findElement(By.name("password")).sendKeys(("Co190723."));

        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']/.."));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
    }


}
