import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By registerButton = By.xpath("//*[@id=\"register-form-button\"]");
    private final By usernameField = By.xpath("//*[@id=\"register-username\"]");
    private final By passwordField = By.xpath("//*[@id=\"register-password\"]");
    private final By emailField = By.xpath("//*[@id=\"register-email\"]");
    private final By descriptionField = By.xpath("//*[@id=\"register-description\"]");
    private final By submitButton = By.xpath("//*[@id=\"register\"]/form/div[6]/button");
    private final By loginButton = By.xpath("(//*[@id=\"login-form-button\"])[2]");
    private final By usernameLogin = By.xpath("//*[@id=\"email\"]");
    private final By passwordLogin = By.xpath(("//*[@id=\"password\"]"));
    private final By alertMessage = By.xpath(("//*[@id=\"register-alert\"]"));
    private final By login = By.xpath("//*[@id=\"login\"]/form/div[4]/button");

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void setUsernameField(String text) {
        driver.findElement(usernameField).sendKeys(text);
        driver.findElement(usernameField).sendKeys(Keys.TAB);
    }

    public void setPasswordField(String text) {
        driver.findElement(passwordField).sendKeys(text);
        driver.findElement(passwordField).sendKeys(Keys.TAB);
    }

    public void setEmailField(String text) {
        driver.findElement(emailField).sendKeys(text);
        driver.findElement(emailField).sendKeys(Keys.TAB);
    }

    public void setDescriptionField(String text) {
        driver.findElement(descriptionField).sendKeys(text);
        driver.findElement(descriptionField).sendKeys(Keys.TAB);
    }
    public void clickSubmitButton() {
        driver.findElement(submitButton).click();
     }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
     }

    public void fillUsername(String text) {
        driver.findElement(usernameLogin).sendKeys(text);
        driver.findElement(usernameLogin).sendKeys(Keys.TAB);
    }

    public void fillPassword(String text) {
        driver.findElement(passwordLogin).sendKeys(text);
        driver.findElement(passwordLogin).sendKeys(Keys.TAB);
    }

    public String getAlertMessage() {
        String  message = driver.findElement(alertMessage).getText();
        return message;
    }

    public void clickLogin() {
        driver.findElement(login).click();
    }
}
