package parser;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

@Data
public class Parser {

    public static void main(String[] args) {
        final String url ="http://eclipse.org";
        final String url1 ="https://prognoznado.ru/rezultaty-matchej/nastolnyj-tennis";
        //final String url = "https://prognoznado.ru/rezultaty-matchej/nastolnyj-tennis/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-final/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-1-2-finala/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad/masters-pro-tur-rossija-muzhchiny-parnyj-razrjad/masters-liga-a-muzhchiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad/masters-liga-a-zhenschiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-b-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-liga-c-muzhchiny-odinochnyj-razrjad/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad/masters-liga-c-muzhchiny-odinochnyj-razrjad-final/masters-liga-c-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-final/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-muzhchiny-odinochnyj-razrjad/masters-mikst/masters-mikst-1-2-finala/masters-mikst-final/masters-mikst-match-za-3-e-mesto.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url1)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .get();
            String title = doc.title();
            System.out.println(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
