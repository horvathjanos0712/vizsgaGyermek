import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class LandingPage {
    WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By getInTouch = By.xpath("//*[@id=\"sitenavbar\"]/ul/li[5]/a/span/span");
    private final By firstName = By.xpath(("//*[@id=\"first-name\"]"));
    private final By lastName = By.xpath(("//*[@id=\"last-name\"]"));
    private final By eMail = By.xpath(("//*[@id=\"email\"]"));
    private final By projectType = By.xpath(("//*[@id=\"projectType\"]"));
    private final By graphicsDesign = By.xpath(("//*[@id=\"projectType\"]/option[2]"));
    private final By aboutProject = By.xpath(("//*[@id=\"aboutProject\"]"));
    private final By sendMessageButton = By.xpath(("//*[@id=\"contact-form-button\"]/span/span"));

    public void clickGetInTouch() {
        driver.findElement(getInTouch).click();
    }

    public void setFirstName(String text) {
        driver.findElement(firstName).sendKeys(text);
        driver.findElement(firstName).sendKeys(Keys.TAB);
    }

    public void setLastName(String text) {
        driver.findElement(lastName).sendKeys(text);
        driver.findElement(lastName).sendKeys(Keys.TAB);
    }

    public void setEmail(String text) {
        driver.findElement(eMail).sendKeys(text);
        driver.findElement(eMail).sendKeys(Keys.TAB);
    }

    public void clickProjectType() {
        driver.findElement(projectType).click();
    }

    public void clickGraphicsDesign() {
        driver.findElement(graphicsDesign).click();
    }

    public void setAboutProject() {
        driver.findElement(aboutProject).sendKeys("This is my testwizard project.");
    }

    public void clickSendMessage() {
        driver.findElement(sendMessageButton).click();
    }

    public void clickAlert() {
        driver.switchTo().alert().accept();
    }

    public void clearData() {
        driver.findElement(aboutProject).clear();
    }

    public void modifyData() {
        driver.findElement(aboutProject).sendKeys("modification");
    }

}
