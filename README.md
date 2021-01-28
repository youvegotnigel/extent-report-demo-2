## How to run
```bash
type "mvn test" in terminal
```
## How to use

Pre-Requisites:
* Java
* Maven

1. Add dependency into  "**pom.xml**".
```xml
    <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-java</artifactId>
                <version>3.141.59</version>
            </dependency>
    
            <dependency>
                <groupId>org.testng</groupId>
                <artifactId>testng</artifactId>
                <version>7.0.0</version>
                <scope>compile</scope>
            </dependency>
    
            <dependency>
                <groupId>io.github.bonigarcia</groupId>
                <artifactId>webdrivermanager</artifactId>
                <version>4.2.2</version>
            </dependency>
    
            <dependency>
                <groupId>com.aventstack</groupId>
                <artifactId>extentreports-testng-adapter</artifactId>
                <version>1.2.2</version>
            </dependency>
    
            <dependency>
                <groupId>io.reactivex.rxjava3</groupId>
                <artifactId>rxjava</artifactId>
                <version>3.0.9</version>
            </dependency>
```
2. Create "**extent.properties**" in "***src/test/resources***".
```xml
# spark-reporter
extent.reporter.spark.start=true
extent.reporter.spark.config=
extent.reporter.spark.out=test-output/SparkReport/Index.html
```

3. Add "**Listeners**" in "***testng.xml*** file".
```xml
  <listeners>
        <listener class-name="com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter"></listener>
    </listeners>
```

4. An example test class.
```java
package test;

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
        driver = new ChromeDriver(); //pass chrome options to the driver.
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @Test(priority=2, description = "Verify that a valid user can login to the application")
    public void testLogin(){
        driver.findElement(usernameTextBox).sendKeys("Admin");
        driver.findElement(passwordTextBox).sendKeys("admin123");
        driver.findElement(logInButton).click();
        assertEquals(driver.findElement(By.id("welcome")).getText(), "Welcome David");
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

```


5. An example "**TestNG.xml**" file.
```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Regression" parallel="none">

    <listeners>
        <listener class-name="com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter"></listener>
    </listeners>

    <test name="extent-report-demo-2">
        <classes>
            <class name="test.TestClass">
                <methods>
                    <include name="testLogin"/>
                    <include name="testInvalidLogin"/>
                    <include name="testEmptyCredential"/>
                </methods>
            </class>
        </classes>
    </test>

</suite>
```

## References
* **extentreports-testng-adapter** - [GitHub](https://github.com/extent-framework/extentreports-testng-adapter) 
* **Extent Framework** - [Documentation](http://www.extentreports.com/docs/versions/4/java/testng.html) 

## Author
* **Nigel Mulholland** - [Linkedin](https://www.linkedin.com/in/nigel-mulholland/) 