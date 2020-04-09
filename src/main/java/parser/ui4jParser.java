package parser;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import entity.Result;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ui4jParser {

    public static Result scoreToResult(String score){
        String clearScore="";
        String[] listOfScores;
        Result results = new Result();
        score = score.replaceFirst(" ",",");
        for(char ch : score.toCharArray()){
            if(ch != '(' && ch!=')')
            {
                clearScore+=ch;
            }
        }
        listOfScores = clearScore.split(",");
        results.setScore(listOfScores[0]);
        for(int i = 0; i < listOfScores.length;i++){
            results.setSet(i,listOfScores[i]);
        }
        return results;
    }

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
            WebDriverWait wait1 = new WebDriverWait(driver, 10000);
            WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchGames\"]")));
            //WebElement searchBox = driver.findElement(By.className("c-search__input"));

            element1.sendKeys("Мастерс");
            //element1.submit();

            //wait1.wait(500);



//            List<WebElement> dates= driver.findElements(By.className("c-games__opponents"));
//
//            int count = 0;
//            for (WebElement elements : dates) {
//                System.out.println(
//                        //elements.getText());
//                        elements.getAttribute("innerText"));
//
//                count++;
//            }
//            System.out.println("Строк: "+count);

            //c-games__row_light


            ArrayList<String> datas = new ArrayList<String>();
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> results = new ArrayList<String>();


            List<WebElement> dates= driver.findElements(By.className("c-games__row_light"));
            int count = 0;
            for (WebElement elements : dates) {
                String getInput = elements.getAttribute("innerText");

                System.out.println(getInput);

                //String[] input = getInput.split("↵");
                String[] input = getInput.split("\\r?\\n");

                datas.add(input[0]);
                names.add(input[1]);
                results.add(input[2]);
                count++;
            }
            System.out.println("Строк: "+count);
            System.out.println(datas);
            System.out.println(names);
            System.out.println(results);

            for (String string : results) {
                Result s = scoreToResult(string);
                System.out.println(s);
            }
            //Thread.sleep(5000);  // Let the user actually see something!
            //driver.quit();

            //WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("xpath_of_element_to_be_clicked")));
            //element1.click();
        }
    }
}
