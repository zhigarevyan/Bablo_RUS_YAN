package parser;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.SneakyThrows;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ui4jParser {

    public static void main(String[] args) {

        MyThread thread = new MyThread();
        thread.run();
    }

    static class MyThread extends Thread {

        @SneakyThrows
        @Override
        public void run() {

            // Optional. If not specified, WebDriver searches the PATH for chromedriver.
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

            WebDriver driver = new ChromeDriver();
            driver.get("https://1xbet.com/results/");
            this.sleep(5000);  // Let the user actually see something!
            WebDriverWait wait1 = new WebDriverWait(driver, 4000);
            WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchGames\"]")));
            //WebElement searchBox = driver.findElement(By.className("c-search__input"));
            element1.sendKeys("Мастерс");
            element1.submit();
            Thread.sleep(5000);  // Let the user actually see something!
            //driver.quit();

            //WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("xpath_of_element_to_be_clicked")));
            //element1.click();
        }
    }
}
