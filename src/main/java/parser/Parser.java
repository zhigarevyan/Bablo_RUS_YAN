package parser;

import entity.Player;
import entity.Result;
import lombok.Data;
import manager.ServerManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Data
public class Parser {
    private static Document doc = null;
    ServerManager serverManager =null;

    public Parser(String url, ServerManager serverManager) {
        try {
            this.serverManager = serverManager;
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36")
                    .referrer("https://prognoznado.ru")
                    .ignoreHttpErrors(true)
                    .timeout(1000 * 10)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchScore(Element parent) {
        Elements bigForm = parent.getElementsByClass("visible-xs");
        for (Element form : bigForm) {
            Elements resultSet = form.getElementsByClass("text-success text-right");
            for (Element score : resultSet) {
                Result s = scoreToResult(score.text());
                System.out.println(s);
                serverManager.insertResult(s);

            }
        }
    }

    public void searchLine() {
        Elements bigForm = doc.getElementsByClass("list-group-item");
        for (Element form : bigForm) {
            Elements toListGroup = form.getElementsByClass("list-group");
            for (Element elem : toListGroup) {
                Elements toListGroupItem = elem.getElementsByClass("list-group-item");
                for(Element item : toListGroupItem) {
                    Elements toMedia = item.getElementsByClass("media");
                    for(Element element : toMedia) {
                    searchDate(element);
                    //searchPlayers(element);
                    //searchScore(element);
                    }
                }
            }

        }
    }

    public void searchDate(Element parent) {
        Elements bigForm = parent.getElementsByClass("media-left media-middle");
        for (Element form : bigForm) {
            Elements dates = form.getElementsByClass("label label-primary");
            for (Element date : dates) {
                System.out.println(date.text());
            }
        }
    }

    public void searchPlayers(Element parent) {
        //Elements bigForm = parent.getElementsByClass("media");
        Elements bigForm = parent.getElementsByClass("media-body media-middle hidden-xs");
        for (Element form : bigForm) {
            Elements players = form.getElementsByClass("small");
            int i=1;
            for (Element elem : players) {
                Player[] playersArr = playersToString(elem.text());
                for(Player player : playersArr){
                    //serverManager.insertPlayer(player);
                    System.out.println(i+ " - "+player);
                    i++;
                }
            }
        }
    }

    public Player[] playersToString(String players){
        String clearPlayers="";
        String[] listOfPlayers;
        Player[] playersArr = {new Player(), new Player()};
        for(char ch : players.toCharArray()){
            if(ch != ',')
                clearPlayers+=ch;
        }
        listOfPlayers = clearPlayers.split(" - ");
        playersArr[0].setName(listOfPlayers[0]);
        playersArr[1].setName(listOfPlayers[1]);
        return playersArr;
    }


    public Result scoreToResult(String score){
        String clearScore="";
        String[] listOfScores;
        Result results = new Result();
        for(char ch : score.toCharArray()){
            if(ch != '(' && ch!=')' && ch!=',')
            {
                clearScore+=ch;
            }
        }
        listOfScores = clearScore.split(" ");
        results.setScore(listOfScores[0]);
        for(int i = 0; i < listOfScores.length;i++){
            results.setSet(i,listOfScores[i]);
        }
        return results;
    }


    public static void main(String[] args) throws SQLException {
        ServerManager serverManager = new ServerManager();
        Parser parser = new Parser("https://prognoznado.ru/rezultaty-matchej/nastolnyj-tennis/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-final/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-1-2-finala/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad/masters-pro-tur-rossija-muzhchiny-parnyj-razrjad/masters-liga-a-muzhchiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad/masters-liga-a-zhenschiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-b-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-liga-c-muzhchiny-odinochnyj-razrjad/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad/masters-liga-c-muzhchiny-odinochnyj-razrjad-final/masters-liga-c-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-final/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-muzhchiny-odinochnyj-razrjad/masters-mikst/masters-mikst-1-2-finala/masters-mikst-final/masters-mikst-match-za-3-e-mesto.html",serverManager);
        //parser.searchScore();
        parser.searchLine();
        //parser.searchPlayers();
        //parser.searchDate();
    }

}
