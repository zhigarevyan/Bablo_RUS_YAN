package parser;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

@Data
public class Parser {

    public static void main(String[] args) {
        final String url ="http://eclipse.org";
        final String url1 ="https://prognoznado.ru";
        File file = new File("D:\\UNIVER\\java\\Bablo_RUS_YAN\\src\\main\\resources\\Результаты матчей.html");
        //final String url = "https://prognoznado.ru/rezultaty-matchej/nastolnyj-tennis/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-final/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-1-2-finala/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad/masters-pro-tur-rossija-muzhchiny-parnyj-razrjad/masters-liga-a-muzhchiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad/masters-liga-a-zhenschiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-b-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-liga-c-muzhchiny-odinochnyj-razrjad/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad/masters-liga-c-muzhchiny-odinochnyj-razrjad-final/masters-liga-c-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-final/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-muzhchiny-odinochnyj-razrjad/masters-mikst/masters-mikst-1-2-finala/masters-mikst-final/masters-mikst-match-za-3-e-mesto.html";
        Document doc = null;
        try {
            doc = Jsoup.parse(file,"UTF-8");
            Elements form = doc.getElementsByClass("text-success text-right");
            for(Element elem : form){
                System.out.println(elem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
