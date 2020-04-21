package manager;

import entity.Player;
import entity.Result;
import lombok.extern.log4j.Log4j;
import util.ConnectorDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ServerManager {

    private Connection connection;

    private final static String SQL_INSERT_PLAYER = "insert into bablo.players(name) values (?)";
    private final static String SQL_INSERT_RESULT = "insert into bablo.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
    private final static String SQL_SELECT_PLAYER_NAME = "select name from bablo.players where name = (?) ";
    private final static String SQL_SELECT_RESULT = "select * from bablo.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
    private final static String SQL_SELECT_MATCH = "select * from bablo.matches where matches.player1 = (?) and matches.player2 = (?) and result = (?) and matches.date = (?)";
    private final static String SQL_SELECT_RESULT_FOR_MATCH = "select idresult from bablo.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
    private final static String SQL_SELECT_PLAYER_FOR_MATCH = "select players.idplayers from bablo.players where name = (?)";
    private final static String SQL_INSERT_MATCH = "insert into bablo.matches(player1,player2,result,date) values (?,?,?,?)";
    private final static String SQL_SEARCH_PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) order    by matches.date desc limit 10";
    private final static String SQL_SEARCH_2PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and ((p1.name=(?) and p2.name = (?)) or (p1.name=(?) and p2.name = (?))) order by matches.date desc limit 10";
    private final static String SQL_SEARCH_ALL_MATCHES = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult order by matches.date desc";


    public ServerManager() throws SQLException {
        this.connection = ConnectorDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }


    public void insertPlayer(Player player) {
        PreparedStatement psToSelect = null;
        PreparedStatement ps = null;
        try {
            psToSelect = connection.prepareStatement(SQL_SELECT_PLAYER_NAME);
            psToSelect.setString(1, player.getName());
            ResultSet rs = psToSelect.executeQuery();
            while (rs.next()) {
                return;
            }
            ps = connection.prepareStatement(SQL_INSERT_PLAYER);
            ps.setString(1, player.getName());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertResult(Result result) {
        PreparedStatement ps = null;
        PreparedStatement psForCheck = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_RESULT);
            psForCheck = connection.prepareStatement(SQL_SELECT_RESULT);
            psForCheck.setString(1, result.getScore());
            psForCheck.setString(2, result.getSet(1));
            psForCheck.setString(3, result.getSet(2));
            psForCheck.setString(4, result.getSet(3));
            psForCheck.setString(5, result.getSet(4));
            psForCheck.setString(6, result.getSet(5));
            psForCheck.setString(7, result.getSet(6));
            psForCheck.setString(8, result.getSet(7));
            ResultSet rsForCheck = psForCheck.executeQuery();

            if (rsForCheck.next()) {
                return;
            } else {
                ps.setString(1, result.getScore());
                ps.setString(2, result.getSet1());
                ps.setString(3, result.getSet2());
                ps.setString(4, result.getSet3());
                ps.setString(5, result.getSet4());
                ps.setString(6, result.getSet5());
                ps.setString(7, result.getSet6());
                ps.setString(8, result.getSet7());
                ps.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMatch(Player player1, Player player2, Result result, String date) {
        PreparedStatement psForPlayer1 = null;
        PreparedStatement psForPlayer2 = null;
        PreparedStatement psForResult = null;
        PreparedStatement psForInsertMatch = null;
        PreparedStatement psForCheck = null;
        this.insertPlayer(player1);
        this.insertPlayer(player2);
        this.insertResult(result);
        try {
            psForCheck = connection.prepareStatement(SQL_SELECT_MATCH);
            psForPlayer1 = connection.prepareStatement(SQL_SELECT_PLAYER_FOR_MATCH);
            psForPlayer2 = connection.prepareStatement(SQL_SELECT_PLAYER_FOR_MATCH);
            psForResult = connection.prepareStatement(SQL_SELECT_RESULT_FOR_MATCH);
            psForInsertMatch = connection.prepareStatement(SQL_INSERT_MATCH);
            psForPlayer1.setString(1, player1.getName());
            psForPlayer2.setString(1, player2.getName());
            psForResult.setString(1, result.getScore());
            psForResult.setString(2, result.getSet(1));
            psForResult.setString(3, result.getSet(2));
            psForResult.setString(4, result.getSet(3));
            psForResult.setString(5, result.getSet(4));
            psForResult.setString(6, result.getSet(5));
            psForResult.setString(7, result.getSet(6));
            psForResult.setString(8, result.getSet(7));
            ResultSet rsForPlayer1 = psForPlayer1.executeQuery();
            ResultSet rsForPlayer2 = psForPlayer2.executeQuery();
            ResultSet rsForResult = psForResult.executeQuery();
            rsForPlayer1.next();
            rsForPlayer2.next();
            rsForResult.next();
            psForCheck.setString(1, String.valueOf(rsForPlayer1.getInt(1)));
            psForCheck.setString(2, String.valueOf(rsForPlayer2.getInt(1)));
            psForCheck.setString(3, String.valueOf(rsForResult.getInt(1)));
            psForCheck.setString(4, date);
            ResultSet resultToCheck = psForCheck.executeQuery();
            if (resultToCheck.next()) {
                return;
            } else {
                System.out.println(rsForPlayer1.getInt(1) + " - " + rsForPlayer2.getInt(1) + " - " + rsForResult.getInt(1));
                psForInsertMatch.setString(1, String.valueOf(rsForPlayer1.getInt(1)));
                psForInsertMatch.setString(2, String.valueOf(rsForPlayer2.getInt(1)));
                psForInsertMatch.setString(3, String.valueOf(rsForResult.getInt(1)));
                psForInsertMatch.setString(4, date);
                psForInsertMatch.execute();
            }

        } catch (SQLException e) {
        }
    }

    public void searchPlayersMatch(String name) {
        PreparedStatement ps = null;
        String[] result;
        double countMatches = 0, countWin = 0;
        try {
            ps = connection.prepareStatement(SQL_SEARCH_PLAYERS_MATCH);
            ps.setString(1, name);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countMatches++;
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " ");
                result = rs.getString(4).split(":");
                if (Double.valueOf(result[0]) > Double.valueOf(result[1]) && rs.getString(2).equals(name)) {
                    countWin++;
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1]) && rs.getString(3).equals(name)) {
                    countWin++;
                }
            }
            System.out.println("matches - " + countMatches + " wins - " + countWin + "winrate - " + countWin / countMatches * 100);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchPlayersMatchWithForaAndTotals(String name) {
        PreparedStatement ps = null;
        String[] result;
        double countMatches = 0, countWin = 0;

        String[] setScores = new String[7];
        String[] resultArray = new String[11];

        try {
            ps = connection.prepareStatement(SQL_SEARCH_PLAYERS_MATCH);
            ps.setString(1, name);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);
                countMatches++;


                result = rs.getString(4).split(":");
                if (Double.valueOf(result[0]) > Double.valueOf(result[1]) && rs.getString(2).equals(name)) {
                    countWin++;
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1]) && rs.getString(3).equals(name)) {
                    countWin++;
                }


                if (rs.getString(2).equals(name)) {
                    //System.out.printf("%s | %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                    //не закочено!
                    System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[1],resultArray[2],result[0],result[1],forasAndTotals[0],forasAndTotals[2],forasAndTotals[1],forasAndTotals[3], forasAndTotals[2]+forasAndTotals[3]);
                } else {
                    setScores = reverseSets(setScores);
                    System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[2],resultArray[1],result[1],result[0],forasAndTotals[1],forasAndTotals[3],forasAndTotals[0],forasAndTotals[2], forasAndTotals[2]+forasAndTotals[3]);
                    //не закочено!
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s\n", resultArray[0], resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }

        }

        System.out.println("matches - " + countMatches + " wins - " + countWin + "winrate - " + countWin / countMatches * 100);
    } catch(
    SQLException e)

    {
        e.printStackTrace();
    }

}

    public void search2PlayersMatch(String name1, String name2) {
        PreparedStatement ps = null;
        String[] result;
        double winsOfPlayer1 = 0, winsOfPlayer2 = 0;
        try {
            ps = connection.prepareStatement(SQL_SEARCH_2PLAYERS_MATCH);
            ps.setString(1, name1);
            ps.setString(2, name2);
            ps.setString(3, name2);
            ps.setString(4, name1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " ");
                result = rs.getString(4).split(":");
                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1))
                        winsOfPlayer1++;
                    else
                        winsOfPlayer2++;
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1))
                        winsOfPlayer2++;
                    else
                        winsOfPlayer1++;
                }
            }
            System.out.println("wins of " + name1 + " - " + winsOfPlayer1 + " / wins of " + name2 + " - " + winsOfPlayer2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search2PlayersMatchWithForaAndTotals(String name1, String name2) {

        PreparedStatement ps = null;
        String[] result;
        double winsOfPlayer1 = 0, winsOfPlayer2 = 0;

        //double avgFora1= 0.0, avgFora2 = 0.0;
        //int avgTotal1 = 0, avgTotal2 = 0;
        //int counterLines = 0;

        try {
            ps = connection.prepareStatement(SQL_SEARCH_2PLAYERS_MATCH);
            ps.setString(1, name1);
            ps.setString(2, name2);
            ps.setString(3, name2);
            ps.setString(4, name1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String[] setScores = new String[7];

                String[] resultArray = new String[11];
                //counterLines++;
                result = rs.getString(4).split(":");

                //int counterSets = 0;

                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);

                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1))
                        winsOfPlayer1++;
                    else
                        winsOfPlayer2++;
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1))
                        winsOfPlayer2++;
                    else
                        winsOfPlayer1++;
                }

                if (rs.getString(2).equals(name1))
                    //not finished
                    System.out.printf("%s | %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[1],resultArray[2],result[0],result[1],forasAndTotals[0],forasAndTotals[2],forasAndTotals[1],forasAndTotals[3], forasAndTotals[2]+forasAndTotals[3]);
                    //System.out.printf("%s | %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);

                else {
                    setScores = reverseSets(setScores);
                    //not finished
                    //System.out.printf("%s | %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                    System.out.printf("%s | %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[2],resultArray[1],result[1],result[0],forasAndTotals[1],forasAndTotals[3],forasAndTotals[0],forasAndTotals[2], forasAndTotals[2]+forasAndTotals[3]);
                }

                //System.out.printf("%s | %s - %s | %s | %s %s %s %s %s %s %s | Fora1: %.2f Total1: %.2f | Fora2: %.0f Total2: %.0f\n", resultArray[0],resultArray[1],resultArray[2],resultArray[3],resultArray[4],resultArray[5],resultArray[6],resultArray[7],resultArray[8],resultArray[9],resultArray[10],forasAndTotals[0],forasAndTotals[2],forasAndTotals[1],forasAndTotals[3]);

                //System.out.println(rs.getString(1) + " " + rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " ");
            }
            System.out.println("wins of " + name1 + " - " + winsOfPlayer1 + " / wins of " + name2 + " - " + winsOfPlayer2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] reverseSets(String[] setScores) {
        String[] result = new String[setScores.length];
        for (int index = 0; index > setScores.length; index++) {
            String set = setScores[index];
            if (set.equals("null")) {
                result[index] = "null";
                continue;
            }
            String[] splitedSet = set.split(":");
            result[index] = splitedSet[1] + ":" + splitedSet[0];
        }
        return result;
    }

    public double[] getForaAndTotals(String[] setScores) {
        int Total1 = 0, Total2 = 0;
        double Fora1 = 0.0, Fora2 = 0.0;
        double[] result = new double[4];
        for (int index = 0; index < 7; index++) {
            String[] splitedSet = new String[2];
            String set = setScores[index];

            if (set.equals("null")) {
                continue;
            }

            splitedSet = set.split(":");
            Total1 += Integer.parseInt(splitedSet[0]);
            Total2 += Integer.parseInt(splitedSet[1]);
        }

        Fora1 = Total1 - Total2;
        Fora2 = Total2 - Total1;

        result[0] = Fora1;
        result[1] = Fora2;
        result[2] = Total1;
        result[3] = Total2;

        return result;
    }

    public void searchAllMatches() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SEARCH_ALL_MATCHES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            //Player player = new Player("Моляка Дмитрий");
            //Player player1 = new Player("Косых Олег");
            //Result result = new Result("4:2", "10:12", "10:12", "10:12", "12:10", "12:10", "12:10", "12:10");
            //Result result1 = new Result("4:3", "10:12", "10:12", "10:12", "12:10", "12:10", "12:10", "12:10");
            ServerManager serverManager = new ServerManager();
            //serverManager.insertPlayer(player);
            //serverManager.insertPlayer(player1);
            //serverManager.insertResult(result);
            //serverManager.insertResult(result1);
            //serverManager.insertMatch(player1,player,result,date);
            String p1 = "Илья Нашивочников";
                String p2 = "Максим Хмелевский";

            System.out.printf("\n -------------%s statistics------------- \n", p1);
            serverManager.searchPlayersMatchWithForaAndTotals(p1);
            System.out.printf("\n -------------%s statistics------------- \n", p2);
            serverManager.searchPlayersMatchWithForaAndTotals(p2);
            System.out.printf("\n -------------%s - %s------------- \n", p1, p2);
            serverManager.search2PlayersMatchWithForaAndTotals(p1, p2);
            //serverManager.searchAllMatches();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
