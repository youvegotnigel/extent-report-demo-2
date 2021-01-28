package test;

import com.aventstack.extentreports.testng.listener.ExtentIReporterSuiteClassListenerAdapter;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

//@Listeners({ExtentITestListenerClassAdapter.class})
public class TestClass {

    private By usernameTextBox = By.id("txtUsername");
    private By passwordTextBox = By.id("txtPassword");
    private By logInButton = By.id("btnLogin");

    private WebDriver driver;

    @BeforeClass
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
        driver = new ChromeDriver(getChromeOptions()); //pass chrome options to the driver.

        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    private ChromeOptions getChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-infobars"); // this is now disable by chrome latest versions
        options.setHeadless(false);
        return options;
    }

    @Test(priority=2, description = "Verify that a valid user can login to the application")
    public void testLogin(){
        driver.findElement(usernameTextBox).sendKeys("Admin");
        driver.findElement(passwordTextBox).sendKeys("admin123");
        driver.findElement(logInButton).click();
        assertEquals(driver.findElement(By.id("welcome")).getText(), "Welcome Paul");
    }

    @Test(priority=1, description = "Verify that an invalid user cannot login to the application")
    public void testInvalidLogin() {
        driver.findElement(usernameTextBox).sendKeys("Admin");
        driver.findElement(passwordTextBox).sendKeys("admin");
        driver.findElement(logInButton).click();
        assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @Test(priority=1, description = "Verify that an invalid user cannot login to the application")
    public void testEmptyCredential() {
        driver.findElement(usernameTextBox).sendKeys("");
        driver.findElement(passwordTextBox).sendKeys("");
        driver.findElement(logInButton).click();
        assertEquals(driver.getTitle(), "OrangeHRM");
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }


}
