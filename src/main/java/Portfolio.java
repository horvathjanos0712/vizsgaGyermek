import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class Portfolio {
    WebDriver driver;
    public Portfolio(WebDriver driver) {
        this.driver = driver;
    }

    private final By portfolio = By.xpath("//*[@id=\"sitenavbar\"]/ul/li[3]/a");
    private final By next = By.xpath("//*[@class=\"pagination\"]/li[5]");
    private final By item = By.xpath("//*[@class=\"col-lg-6 col-md-10 mx-auto\"]");

    public void clickPortfolio() {
        driver.findElement(portfolio).click();
    }

    public int countItems() {
        int sum = 0;
        List<WebElement> items = driver.findElements(item);
        sum = items.size();
        return sum;
    }

    public boolean checkIfLastPage() {
        String attribute = driver.findElement(next).getAttribute("class");
        return attribute.equals("page-item disabled");
    }

    public void clickNext() {
        if (checkIfLastPage() == false) {
            driver.findElement(next).click();
        }
    }

}
