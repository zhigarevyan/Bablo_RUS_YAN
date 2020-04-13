package parser;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import entity.Player;
import entity.Result;
import lombok.SneakyThrows;
import manager.ServerManager;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SeleniumParser {

    static ArrayList<String> datas = new ArrayList<String>();
    static ArrayList<String> names = new ArrayList<String>();
    static ArrayList<String> results = new ArrayList<String>();
    static int linesQuantity = 0;
    static ServerManager serverManager = null;

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

    public static int getInfoFromWebsite() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://1xbet.com/results/");
        WebDriverWait wait1 = new WebDriverWait(driver, 10000);
        //WebElement nastolkaButton = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
        WebElement nastolkaButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
        nastolkaButton.click();
        System.out.println(nastolkaButton.getText());
        //wait1.until(ExpectedConditions.elementToBeClickable(By.className("c-games__row")));
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchGames\"]"));
        searchBox.sendKeys("Мастерс");
        List<WebElement> dates= driver.findElements(By.className("c-games__row_light"));

        for (WebElement elements : dates) {
            String getInput = elements.getAttribute("innerText");
            //System.out.println(getInput); //SOUT info from WEBElement - Lines.
            String[] input = getInput.split("\\r?\\n");

            if(input[1].contains("/")) {
                continue;
            }
            datas.add(input[0]);
            names.add(input[1]);
            results.add(input[2]);
            linesQuantity++;
        }
        return linesQuantity;

    }

    @SneakyThrows
    public static void insertIntoDB() {
        serverManager = new ServerManager();
        String date;
        Player[] twoPlayers;
        Result result;
        int index;
        for (index = 0; index < linesQuantity; index++) {
            date = datasToDB(datas.get(index));
            twoPlayers = playersToDBForm(names.get(index));
            result = scoreToDBForm(results.get(index));
            serverManager.insertPlayer(twoPlayers[0]);
            serverManager.insertPlayer(twoPlayers[1]);
            serverManager.insertResult(result);
            serverManager.insertMatch(twoPlayers[0],twoPlayers[1],result,date);
        }
        System.out.println("Вставлено " + index + " строк.");
    }

    public static String datasToDB(String string) {
        String[] result = string.split(" ");
        String[] tempString= result[0].split("\\.");
        return "2020-"+tempString[1]+'-'+tempString[0] + " " + result[1];
    }

    public static void main(String[] args) {
        //linesQuantity = getInfoFromWebsite(); //returns number of lines;
        //insertIntoDB();
        //insertIntoDB();
        getDataForMonth();


    }
//yan ne pitukh
    public static void getDataForMonth() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://1xbet.com/results/");
        WebDriverWait wait1 = new WebDriverWait(driver, 10000);
        WebElement nastolkaButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
        nastolkaButton.click();
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchGames\"]"));
        searchBox.sendKeys("Мастерс");

        int daySelected;
        Actions builder = new Actions(driver);
        while(true) {
            //List<WebElement> lines = driver.findElements(By.className("c-games__row_light"));
            //addToLists(lines);
            WebElement calendar = driver.findElement(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div[1]"));
            Actions mouseOverCalendarAndClick = builder
                    .moveToElement(calendar)
                    .click();
            mouseOverCalendarAndClick.perform();
            WebElement selectedDay = driver.findElement(By.xpath("//span[starts-with(@class, \"cell day selected\")]"));
            daySelected = Integer.valueOf(selectedDay.getText());
            System.out.println(daySelected);
            break;
        }
        // wait1.until(ExpectedConditions.elementToBeClickable(By.className("c-games__row")));

    }

    public static void addToLists(List<WebElement> list) {

        for (WebElement elements : list) {
            String getInput = elements.getAttribute("innerText");
            String[] input = getInput.split("\\r?\\n");
            if(input[1].contains("/")) {
                continue;
            }
            datas.add(input[0]);
            names.add(input[1]);
            results.add(input[2]);
            linesQuantity++;
        }

    }

}
