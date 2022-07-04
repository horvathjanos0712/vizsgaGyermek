import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoxoPageTest {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
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
    public void testTermsAndConditions() throws InterruptedException {
        RoxoPage roxoPage = new RoxoPage(driver);
        roxoPage.navigateTo();
        Thread.sleep(2000);
        roxoPage.clickAcceptButton();
        assertFalse(roxoPage.isVisible());
        Allure.addAttachment("Popup visibility", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Thread.sleep(2000);
    }

    @Test
    public void testRegistration() throws InterruptedException {
        testTermsAndConditions();
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
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginPage.clickSubmitButton();
        String expectedMessage = "User registered!";
        String actual = loginPage.getAlertMessage();
        assertEquals(expectedMessage, actual);
        Allure.addAttachment("Filled registration", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Thread.sleep(2000);
    }

    @Test
    public void testLogin() throws InterruptedException {
        testRegistration();
        Thread.sleep(3000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginButton();
        BufferedReader reader;
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Allure.addAttachment("Filled login", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Thread.sleep(2000);
        loginPage.clickLogin();
        String exp = "https://lennertamas.github.io/roxo/landing.html";
        String act = driver.getCurrentUrl();
        assertEquals(exp, act);
        Allure.addAttachment("Changed page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Thread.sleep(2000);
    }

    @Test
    public void testListData() throws InterruptedException {
        testLogin();
        Thread.sleep(2000);
        About about = new About(driver);
        about.clickAbout();
        List<Map<String, String>> list = about.listData();
        assertEquals(6, list.size());
        Allure.addAttachment("Changed page", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Thread.sleep(2000);
    }

    @Test
    public void testPagination() throws InterruptedException {
        Portfolio portfolio = new Portfolio(driver);
        testLogin();
        portfolio.clickPortfolio();
        int actual = 0;
        while (true) {
            actual += portfolio.countItems();
            if (portfolio.checkIfLastPage() == true) {
                break;
            }
            portfolio.clickNext();
            Thread.sleep(2000);
        }
        assertEquals(5, actual);
    }

    @Test
    public void testNewData() throws InterruptedException {
        testLogin();
        Thread.sleep(2000);
        LandingPage landingPage = new LandingPage(driver);
        landingPage.clickGetInTouch();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "newData.txt"));
            String line = reader.readLine();
            while (line != null) {
                landingPage.setFirstName(line);
                Thread.sleep(2000);
                // read next line
                line = reader.readLine();
                landingPage.setLastName(line);
                Thread.sleep(2000);
                line = reader.readLine();
                landingPage.setEmail(line);
                Thread.sleep(2000);
                line = reader.readLine();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        landingPage.clickProjectType();
        landingPage.clickGraphicsDesign();
        landingPage.setAboutProject();
        Allure.addAttachment("New datas", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Thread.sleep(2000);
        landingPage.clickSendMessage();
        String expected = "Message sent!";
        String actual = driver.switchTo().alert().getText();
        assertEquals(expected, actual);
    }

    @Test
    public void testClearData() throws InterruptedException {
        testNewData();
        LandingPage landingPage = new LandingPage(driver);
        landingPage.clickAlert();
        landingPage.clearData();
        String expected = "";
        String actual = driver.findElement(By.xpath("//*[@id=\"aboutProject\"]")).getText();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testModifyData() throws InterruptedException {
        testClearData();
        Thread.sleep(2000);
        LandingPage landingPage = new LandingPage(driver);
        landingPage.modifyData();
        Thread.sleep(2000);
        landingPage.clickSendMessage();
        String expected = "Message sent!";
        String actual = driver.switchTo().alert().getText();
        assertEquals(expected, actual);
    }

    @Test
    public void testDataSave() throws InterruptedException, IOException {
        testLogin();
        Thread.sleep(2000);
        About about = new About(driver);
        about.clickAbout();
        String fileName = "dataSaving.txt";
        about.createFile(fileName);
        about.writeTeamMember(fileName);
    }

    @Test
    public void testLogOut() throws InterruptedException {
        testLogin();
        LoginPage loginPage = new LoginPage(driver);
        Thread.sleep(2000);
        loginPage.clickLogOut();
        Thread.sleep(2000);
        String exp = "https://lennertamas.github.io/roxo/index.html";
        String act = driver.getCurrentUrl();
        assertEquals(exp, act);
    }
}
