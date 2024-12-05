# Note by me

1. [User Registration Tests](src/test/java/User/UserRegistrationTests.java)
   - ```java
     RegisterAndVerifyRedirection() 
   ```
       /*
     * 1. Open browser
     * 2. Navigate to http://14.176.232.213:8084/
     * 3. Click on Register button
     * 4. Fill in  Username with thuongtest
     * 5. Fill in Email with thuongngo09072003@gmail.com
     * 6. Fill in Password with Co190723.
     * 7. Fill in Confirm Password with Co190723.
     * 8. Check on Checkbox
     * 9. Click on input.btn.btn-success */
   
2. [Rooms](src/test/java/Admin/BookingDetail/Rooms.java)
```Java
checkPendingStatus()

@Test
void checkPendingStatus() {
   setup();

   // Chờ đến khi trang Booking xuất hiện và click vào tiêu đề
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   WebElement bookingTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='title' and contains(text(),'Booking')]")));
   bookingTitle.click();

   // Chờ đến khi booking với trạng thái 'ONLINE_PENDING' xuất hiện
   WebElement onlinePendingBooking = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[contains(text(), 'ONLINE_PENDING')]")));

   // Kiểm tra xem booking có hiển thị trạng thái ONLINE_PENDING không
   assertTrue(onlinePendingBooking.isDisplayed());

   // Tìm nút 'Action' (biểu tượng mắt) cho booking đó
   WebElement actionButton = onlinePendingBooking.findElement(By.xpath("//td/span[contains(text(), 'PENDING')]/ancestor::tr//a[contains(@class, 'btn-tbl-edit')]"));

   // Click vào nút 'Action'
   actionButton.click();

   assertTrue(driver.getCurrentUrl().contains("/admin/booking/booking-details"));

}

```