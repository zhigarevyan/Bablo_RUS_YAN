package parser;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import entity.Player;
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

public class SeleniumParser {

    static ArrayList<String> datas = new ArrayList<String>();
    static ArrayList<String> names = new ArrayList<String>();
    static ArrayList<String> results = new ArrayList<String>();

    public static Player[] playersToDBForm(String players){

        String clearPlayers="";
        String[] listOfPlayers;
        Player[] playersArr = {new Player(), new Player()};

        listOfPlayers = players.split(" - ");

        listOfPlayers[0].trim();
        listOfPlayers[1].trim();
        int deleteAfterIndex = listOfPlayers[0].lastIndexOf(" ");
        listOfPlayers[0] = listOfPlayers[0].substring(0,deleteAfterIndex);
        deleteAfterIndex = listOfPlayers[1].lastIndexOf(" ");
        listOfPlayers[1] = listOfPlayers[1].substring(0,deleteAfterIndex);

        System.out.println(listOfPlayers[0]+listOfPlayers[1]);
        playersArr[0].setName(listOfPlayers[0]);
        playersArr[1].setName(listOfPlayers[1]);
        return playersArr;
    }

    public static Result scoreToDBForm(String score){
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

        ParsingThread thread = new ParsingThread();
        //Player[] players = playersToDBForm("Станислав Ошкин (Рос) - Михаил Леонов (Рос)");
        thread.run();
    }

    static class ParsingThread extends Thread {

        @SneakyThrows
        @Override
        public void run() {

            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

            WebDriver driver = new ChromeDriver();
            driver.get("https://1xbet.com/results/");
            WebDriverWait wait1 = new WebDriverWait(driver, 10000);
            wait1.until(ExpectedConditions.elementToBeClickable(By.className("c-games__row")));
            WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchGames\"]"));
            this.sleep(1000);
            searchBox.sendKeys("Мастерс");
            List<WebElement> dates= driver.findElements(By.className("c-games__row_light"));

            int count = 0;
            for (WebElement elements : dates) {
                String getInput = elements.getAttribute("innerText");
                //System.out.println(getInput); //SOUT info from WEBElement - Lines.
                String[] input = getInput.split("\\r?\\n");

                datas.add(input[0]);
                names.add(input[1]);
                results.add(input[2]);
                count++;
            }

            System.out.println("Строк отпарсено: "+count);
//            System.out.println(datas);
//            System.out.println(names);
//            System.out.println(results);

        }
    }
}
