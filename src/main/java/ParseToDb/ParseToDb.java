package ParseToDb;

import manager.ServerManager;
import parser.Parser;

import java.sql.SQLException;

public class ParseToDb {
    public static void main(String[] args) {
        try {
            ServerManager serverManager = new ServerManager();
            Parser parser = new Parser("https://prognoznado.ru/rezultaty-matchej/nastolnyj-tennis/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-final/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad-1-2-finala/ttstar-serija-chehija-muzhchiny-odinochnyj-razrjad/masters-pro-tur-rossija-muzhchiny-parnyj-razrjad/masters-liga-a-muzhchiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad/masters-liga-a-zhenschiny-odinochnyj-razrjad/masters-liga-b-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-a-zhenschiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/masters-liga-b-muzhchiny-odinochnyj-razrjad-final/masters-liga-a-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-liga-c-muzhchiny-odinochnyj-razrjad/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad/masters-liga-c-muzhchiny-odinochnyj-razrjad-final/masters-liga-c-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-final/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-match-za-3-e-mesto/kubok-bum-rossija-muzhchiny-odinochnyj-razrjad-1-2-finala/masters-muzhchiny-odinochnyj-razrjad/masters-mikst/masters-mikst-1-2-finala/masters-mikst-final/masters-mikst-match-za-3-e-mesto.html",serverManager);
            parser.searchLine();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
