package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    protected WebDriver driver;
    @LocalServerPort
    protected int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    protected HomePage signUpAndLogin() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName("Tuan");
        signupPage.setLastName("Thanh");
        signupPage.setUserName("tuanthanh");
        signupPage.setPassword("123456789");
        signupPage.signUp();
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName("tuanthanh");
        loginPage.setPassword("123456789");
        loginPage.login();

        return new HomePage(driver);
    }

    @Test
    public void testRedirection() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName("Tuan");
        signupPage.setLastName("Thanh");
        signupPage.setUserName("tuanthanh1");
        signupPage.setPassword("123456789");
        signupPage.signUp();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 6);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    @Test
    public void testLargeUpload() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName("Tuan");
        signupPage.setLastName("Thanh");
        signupPage.setUserName("tuanthanh12");
        signupPage.setPassword("123456789");
        signupPage.signUp();
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName("tuanthanh12");
        loginPage.setPassword("123456789");
        loginPage.login();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("upload-button"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    @Test
    public void testBadUrl() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName("tuanthanh");
        loginPage.setPassword("123456789");
        loginPage.login();
        driver.get("http://localhost:" + this.port + "/random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }
}
