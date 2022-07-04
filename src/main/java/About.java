import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class About {
    WebDriver driver;

    public About(WebDriver driver) {
        this.driver = driver;
    }

    private final By about = By.xpath(("//*[@id=\"sitenavbar\"]/ul/li[2]/a"));
    private final By teamMember = By.xpath("//*[@class=\"site-team-member-content\"]");
    private final By name = By.xpath("./h3");
    private final By position = By.xpath("./p");

    public void clickAbout() {
        driver.findElement(about).click();
    }

    public List<Map<String, String>> listData() {
        List<Map<String, String>> list = new ArrayList<>();
        List<WebElement> names = driver.findElements(teamMember);

        for (int i = 0; i < names.size(); i++) {
            Map<String, String> itemsList = new HashMap<>();

            String memberName = names.get(i).findElement(name).getText();
            String memberPosition = names.get(i).findElement(position).getText();

            itemsList.put("Name", memberName);
            itemsList.put("Position", memberPosition);
            list.add(itemsList);
        }
        return list;
    }

    public void createFile(String string) throws IOException {
        File file = new File(string);
        file.createNewFile();
    }

    public void writeTeamMember(String string) throws IOException {
        List<WebElement> teamMemberList = driver.findElements(teamMember);
        FileWriter writer = new FileWriter(string);
        writer.write("Number" + "\t" + "Name" + "\t" + "Position" + "\r\n");

        for (int i = 0; i < teamMemberList.size(); i++) {
            String names = teamMemberList.get(i).findElement(name).getText();
            String positions = teamMemberList.get(i).findElement(position).getText();
            String number = String.valueOf(i+1);
            writer.write(number + "\t" + names + "\t" + positions + "\r\n");
        }
        writer.close();
    }
}
