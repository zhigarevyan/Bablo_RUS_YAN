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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.instrument.ClassDefinition;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static java.lang.Thread.sleep;

public class SeleniumParser {

    static ArrayList<String> datas = new ArrayList<String>();
    static ArrayList<String> names = new ArrayList<String>();
    static ArrayList<String> results = new ArrayList<String>();
    static int linesQuantity = 0;
    static ServerManager serverManager = null;
    static String league;
    static ChromeOptions options = new ChromeOptions();
    static WebDriver driver;
    static String[] leagues = {
            "Мастерс",
            "Mini Table Tennis",
            "BoomCup",
            "Pro Spin Series",
            "Лига Про"
    };

    public static Player[] playersToDBForm(String players){

        String clearPlayers="";
        String[] listOfPlayers;
        Player[] playersArr = {new Player(), new Player()};

        listOfPlayers = players.split(" - ");
        listOfPlayers[0].trim();
        listOfPlayers[1].trim();
        int deleteAfterIndex = listOfPlayers[0].lastIndexOf(" ");
        if(deleteAfterIndex!=-1)
        listOfPlayers[0] = listOfPlayers[0].substring(0,deleteAfterIndex);
        deleteAfterIndex = listOfPlayers[1].lastIndexOf(" ");
        if(deleteAfterIndex!=-1)
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

    public static void getInfoFromWebsite() {

        //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "./chromedriver");


        driver.get("https://1xstavka.ru/results/");
        WebDriverWait wait1 = new WebDriverWait(driver, 30);
        //WebElement nastolkaButton = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
        WebElement nastolkaButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
        nastolkaButton.click();
        System.out.println(nastolkaButton.getText());
        //wait1.until(ExpectedConditions.elementToBeClickable(By.className("c-games__row")));
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchGames\"]"));

//        while(true) {
//            try {
//                sleep(15000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

     //   }

        loadLeagues(searchBox);
        driver.close();
    }

    public static void loadLeagues(WebElement searchBox) {
        for(String leagueName : leagues) {
            league = leagueName;
            searchBox.sendKeys(leagueName);
            List<WebElement> dates = driver.findElements(By.className("c-games__row_light"));
            addToLists(dates);
            insertIntoDB_L();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            searchBox.sendKeys("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
        }
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
            serverManager.insertMatch(twoPlayers[0],twoPlayers[1],result,date);
        }
        System.out.println("Вставлено " + index + " строк.");
    }

    @SneakyThrows
    public static void insertIntoDB_L() {
        serverManager = new ServerManager();
        String date;
        Player[] twoPlayers;
        Result result;
        int index;
        for (index = 0; index < linesQuantity; index++) {
            date = datasToDB(datas.get(index));
            twoPlayers = playersToDBForm(names.get(index));
            result = scoreToDBForm(results.get(index));
            result.getScore();
            serverManager.insertMatchL(twoPlayers[0],twoPlayers[1],result,date,league);
        }
        System.out.println("Вставлено " + index + " строк.");
    }

    public static String datasToDB(String string) {
        String[] result = string.split(" ");
        String[] tempString= result[0].split("\\.");
        return "2020-"+tempString[1]+'-'+tempString[0] + " " + result[1];
    }


//yan ne pitukh
    public static void getDataForMonth() {

        driver.get("https://1xbet.com/results/");
        WebDriverWait wait1 = new WebDriverWait(driver, 30);
        WebElement nastolkaButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
        nastolkaButton.click();
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchGames\"]"));
        searchBox.sendKeys(league);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("c-games__row_light")));

//        try {
//            //sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        WebElement calendar = driver.findElement(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div[1]"));
        WebElement prevMonth = driver.findElement(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div[2]/header/span[1]"));
        WebElement showResults = driver.findElement(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[2]/div[2]/div/div/div[5]/div"));
        WebElement temp = driver.findElement(By.xpath("//*[@id=\"router_app\"]/div/div[1]"));
        int monthCounter = 3;
        boolean firstMonth = true;
        performClick(calendar, driver);
        performClick(prevMonth, driver);
        while(monthCounter >= 0) {
            List<WebElement> daysToGet = driver.findElements(By.xpath("//*[@id='router_app']/div/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div[2]/div/*[.!='' and not(starts-with(@class,'cell day-header')) and not(starts-with(@class,'cell day disabled'))]"));
            if(firstMonth) {
                for(int i = 0; i < 2; i++) {
                    daysToGet.remove(daysToGet.size() - 1); //убрать нажатие на сегодняшний день
                }
                firstMonth = false;
            }
            performClick(temp,driver);
            for (int index = daysToGet.size(); index > 0; index--) { //загрузить все дни выбранного месяца

                WebElement lastDay = daysToGet.remove(index - 1);
                performClick(calendar, driver);
                performClick(lastDay, driver);
                //try-catch в случае, если произойдет ошибка иксбета. Рефреш страницы-возврат к текущему дню. Дописать на другие месяцы. Сработает только на 1 ошибку подряд.
                try {
                    wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("c-games__row_light")));
                } catch (TimeoutException e) {
//                    driver.navigate().refresh();
//                    wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"router_app\"]/div/div[2]/div/div/div[1]/div/section/ul/li[7]/a")));
//                    nastolkaButton.click();
//                    searchBox.sendKeys("Мастерс");
//                    wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("c-games__row_light")));
//                    performClick(calendar, driver);
//                    performClick(lastDay, driver);
                    performClick(showResults,driver);
                    performClick(showResults,driver);

                }
                performClick(showResults, driver);
                performClick(showResults, driver);

                loadLeagues(searchBox);
                datas = new ArrayList<String>();
                names = new ArrayList<String>();
                results = new ArrayList<String>();
                linesQuantity = 0;
            }

           performClick(calendar, driver);
            performClick(prevMonth, driver);
            monthCounter--;
        }
    }

    public static void addToLists(List<WebElement> list) {

        for (WebElement elements : list) {
            String getInput = elements.getAttribute("innerText");
            String[] input = getInput.split("\\r?\\n");
            if(input[1].contains("/")) {
                continue;
            }
            if(input[1].contains("0:0")) {
                continue;
            }
            datas.add(input[0]);
            System.out.println(input[0]);
            names.add(input[1]);
            results.add(input[2]);
            linesQuantity++;

        }

    }

    public static void performClick(WebElement element, WebDriver driver) {
        Actions builder = new Actions(driver);
        Actions action = builder
                .moveToElement(element)
                .click();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        action.perform();
    }

    public static void init() {
        //System.setProperty("webdriver.chrome.driver", "./chromedriver");
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        //options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--lang=ru");
        driver =  new ChromeDriver(options);
    }

    public static void main(String[] args) {
        init();

        league = "Pro Spin Series";
        System.out.println("\n\n---БАЗАБАКА МАНАГЕР---\n\n");
        getInfoFromWebsite();
        //getDataForMonth();

    }

}
