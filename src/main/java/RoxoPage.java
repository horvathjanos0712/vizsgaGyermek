import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RoxoPage {
    WebDriver driver;

    public RoxoPage(WebDriver driver) {
        this.driver = driver;
    }

    private final String url = "https://lennertamas.github.io/roxo/";
    private final By termsAndConditions = By.xpath("//*[@id=\"overlay\"]");
    private final By accept = By.id("terms-and-conditions-button");

    public void navigateTo () {
        driver.get(url);
    }
    public void clickAcceptButton() {
        driver.findElement(accept).click();
    }
    public Boolean isVisible() {
        WebElement popUpWindow = driver.findElement(termsAndConditions);
        Boolean overlay = popUpWindow.isDisplayed();
        return overlay;
    }
}
