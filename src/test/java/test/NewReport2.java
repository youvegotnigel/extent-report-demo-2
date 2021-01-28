package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;

public class NewReport2 {

    public WebDriver driver;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    private static final String extentReportDirectory;
    private static final String timestamp;
    private static final String fileSeparator;

    private By usernameTextBox = By.id("txtUsername");
    private By passwordTextBox = By.id("txtPassword");
    private By logInButton = By.id("btnLogin");


    @BeforeTest
    public void setExtent() {

        // specify location of the report
        htmlReporter = new ExtentHtmlReporter(extentReportDirectory + fileSeparator + "Execution Results - " + timestamp + ".html");

        htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
        htmlReporter.config().setReportName("Functional Testing"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Passing General information
        extent.setSystemInfo("Application Name", "My Projects");
        extent.setSystemInfo("Build No", "101");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("OS", "Windows 10");
        extent.setSystemInfo("Test Developer", "nigel");

    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @Test(priority = 2, description = "Verify that a valid user can login to the application")
    public void testLogin() {

        test = extent.createTest("verify valid login");

        driver.findElement(usernameTextBox).sendKeys("Admin");
        driver.findElement(passwordTextBox).sendKeys("admin123");
        driver.findElement(logInButton).click();
        assertEquals(driver.findElement(By.id("welcome")).getText(), "Welcome Steven");
    }

    @Test(priority = 1, description = "Verify that an invalid user cannot login to the application")
    public void testInvalidLogin() {

        test = extent.createTest("verify invalid login");

        driver.findElement(usernameTextBox).sendKeys("Admin");
        driver.findElement(passwordTextBox).sendKeys("admin");
        driver.findElement(logInButton).click();
        assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @Test(priority = 1, description = "Verify that an invalid user cannot login to the application")
    public void testEmptyCredential() {

        test = extent.createTest("verify empty credential login");


        driver.findElement(usernameTextBox).sendKeys("");
        driver.findElement(passwordTextBox).sendKeys("");
        driver.findElement(logInButton).click();
        assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @Test
    public void fakeTest() {
        test = extent.createTest("This is a fake test");

        test.createNode("Test case failed");
        Assert.assertTrue(false);

        test.createNode("Test case passed");
        Assert.assertTrue(true);
    }

    @Test
    public void fakeTest2() {
        test = extent.createTest("This is a fake test #2");

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);
    }

    @Test
    public void fakeTest3() {
        test = extent.createTest("This is a fake test #3");

        test.createNode("Test case failed");
        Assert.assertTrue(false);

        test.createNode("Test case failed");
        Assert.assertTrue(false);
    }

    @Test
    public void fakeTest4() {
        test = extent.createTest("This is a fake test #4");

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);

        test.createNode("Test case passed");
        Assert.assertTrue(true);
    }


    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
            String screenshotPath = NewReport2.getScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenshotPath);// adding screenshot
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
        }
        driver.quit();
    }

    //Take screenshots
    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + fileSeparator + "test-output" + fileSeparator + "html-report" + fileSeparator + screenshotName + " - " + timestamp + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    static {
        fileSeparator = File.separator;
        extentReportDirectory = System.getProperty("user.dir") + fileSeparator + "test-output" + fileSeparator + "html-report";
        timestamp = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss")).format(new Date());
    }
}
