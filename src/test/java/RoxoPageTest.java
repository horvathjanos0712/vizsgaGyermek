import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RoxoPageTest {
    WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TestRoxoPage() {
        RoxoPage roxoPage = new RoxoPage(driver);
        roxoPage.navigateTo();
        roxoPage.clickAcceptButton();

    }

    @Test
    public void TestRegistration() throws InterruptedException {
        TestRoxoPage();
        Thread.sleep(3000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterButton();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "user.txt"));
            String line = reader.readLine();
            while (line != null) {
                loginPage.setUsernameField(line);
                Thread.sleep(2000);
                // read next line
                line = reader.readLine();
                loginPage.setPasswordField(line);
                Thread.sleep(2000);
                line = reader.readLine();
                loginPage.setEmailField(line);
                Thread.sleep(2000);
                line = reader.readLine();
                loginPage.setDescriptionField(line);
                line = reader.readLine();
                //reader.close();
                loginPage.clickSubmitButton();
                Allure.addAttachment("Filled registration", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
                Thread.sleep(2000);
                String expectedMessage = "User registered!";
                String actual = loginPage.getAlertMessage();
                Assertions.assertEquals(expectedMessage, actual);
                loginPage.clickLoginButton();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(new FileReader(
                    "login.txt"));
            String line = reader.readLine();
            while (line != null) {
                loginPage.fillUsername(line);
                Thread.sleep(2000);
                line = reader.readLine();
                Thread.sleep(2000);
                loginPage.fillPassword(line);
                line = reader.readLine();
                reader.close();
                Allure.addAttachment("Filled login", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
                Thread.sleep(2000);
                loginPage.clickLogin();
                String exp = "https://lennertamas.github.io/roxo/landing.html";
                String act = driver.getCurrentUrl();
                Assertions.assertEquals(exp, act);
                Allure.addAttachment("Changed page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
                Thread.sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
