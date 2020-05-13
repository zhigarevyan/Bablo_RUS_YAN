package manager;

import entity.Player;
import entity.Result;
import entity.SMResult;
import org.jetbrains.annotations.NotNull;
import util.ConnectorDB;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ServerManager {

    private static int limits = 10;
    private static String player1 = "";
    private static String player2 = "";

    private Connection connection;

//    private final static String SQL_INSERT_PLAYER = "insert into sKtlTcXzr6.players(name) values (?)";
//    private final static String SQL_INSERT_RESULT = "insert into sKtlTcXzr6.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
//    private final static String SQL_SELECT_PLAYER_NAME = "select name from sKtlTcXzr6.players where name = (?) ";
//    private final static String SQL_SELECT_RESULT = "select * from sKtlTcXzr6.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
//    private final static String SQL_SELECT_MATCH = "select * from sKtlTcXzr6.matches where matches.player1 = (?) and matches.player2 = (?) and result = (?) and matches.date = (?)";
//    private final static String SQL_SELECT_RESULT_FOR_MATCH = "select idresult from sKtlTcXzr6.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
//    private final static String SQL_SELECT_PLAYER_FOR_MATCH = "select players.idplayers from sKtlTcXzr6.players where name = (?)";
//    private final static String SQL_INSERT_MATCH = "insert into sKtlTcXzr6.matches(player1,player2,result,date) values (?,?,?,?)";
//    private final static String SQL_SEARCH_PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) order by matches.date desc limit 10";
//    private final static String SQL_SEARCH_2PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and ((p1.name=(?) and p2.name = (?)) or (p1.name=(?) and p2.name = (?))) order by matches.date desc limit 10";
//    private final static String SQL_SEARCH_ALL_MATCHES = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and matches.date > '2020-04-22' order by matches.date desc";
//    private final static String SQL_SEARCH_PLAYERS_MATCH_FOR_DATE = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) and matches.date like(?) order by matches.date desc";

    private final static String SQL_INSERT_PLAYER = "insert into sKtlTcXzr6.players(name) values (?)";
    private final static String SQL_INSERT_RESULT = "insert into sKtlTcXzr6.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
    private final static String SQL_SELECT_PLAYER_NAME = "select name from sKtlTcXzr6.players where name = (?) ";
    private final static String SQL_SELECT_RESULT = "select * from sKtlTcXzr6.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
    private final static String SQL_SELECT_MATCH = "select * from sKtlTcXzr6.matches where matches.player1 = (?) and matches.player2 = (?) and result = (?) and matches.date = (?)";
    private final static String SQL_SELECT_RESULT_FOR_MATCH = "select idresult from sKtlTcXzr6.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
    private final static String SQL_SELECT_PLAYER_FOR_MATCH = "select players.idplayers from sKtlTcXzr6.players where name = (?)";
    private final static String SQL_INSERT_MATCH = "insert into sKtlTcXzr6.matches(player1,player2,result,date) values (?,?,?,?)";
    private final static String SQL_SEARCH_PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) order by matches.date desc limit 10";
    private final static String SQL_SEARCH_2PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and ((p1.name=(?) and p2.name = (?)) or (p1.name=(?) and p2.name = (?))) order by matches.date desc limit 10";
    private final static String SQL_SEARCH_ALL_MATCHES = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and matches.date > '2020-05-01' order by matches.date desc";
    private final static String SQL_SEARCH_PLAYERS_MATCH_FOR_DATE = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) and matches.date like(?) order by matches.date desc";
    private final static String SQL_GET_MAC = "select * from sKtlTcXzr6.Appusers where Appusers.mac = (?)";
    private final static String SQL_GET_PLAYERS = "select name from sKtlTcXzr6.players";
    private final static String SQL_SELECT_LEAGUES = "select name from sKtlTcXzr6.leagues";
    private final static String SQL_SELECT_2PLAYERS_MATCH_L = "select m.date, p1.name, p2.name, r.score, r.set1, r.set2, r.set3, r.set4, r.set5, r.set6, r.set7 from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where ((p1.name= (?) and p2.name= (?)) or (p2.name= (?) and p1.name= (?))) and l.name like(?) order by m.date desc limit ?";
    private final static String SQL_SELECT_PLAYER_MATCH_L = "select m.date, p1.name, p2.name, r.score, r.set1, r.set2, r.set3, r.set4, r.set5, r.set6, r.set7 from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where (p1.name= (?) or p2.name= (?)) and l.name like(?) order by m.date desc limit ?";
    private final static String SQL_GET_UPDATE_DATE = "select date from sKtlTcXzr6.matchesUpdates limit 1";
    private final static String SQL_INSERT_UPDATE_DATE = "insert into sKtlTcXzr6.matchesUpdates(date) values (?)";
    private final static String SQL_INSERT_MATCHL = "insert into sKtlTcXzr6.matches(player1,player2,result,date,league) values (?,?,?,?,?)";
    private final static String SQL_INSERT_MATCH_L = "insert into sKtlTcXzr6.matchesL(player1,player2,result,date,league) values ((select idplayers from sKtlTcXzr6.players where name = (?)),(select idplayers from sKtlTcXzr6.players where name = (?)),(select idresult from sKtlTcXzr6.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)),?,(select idleague from sKtlTcXzr6.leagues where leagues.name = ?))";
    private final static String SQL_SELECT_MATCH_L = "select * from sKtlTcXzr6.matchesL where matchesL.player1 = (select idplayers from sKtlTcXzr6.players where name = (?)) and matchesL.player2 = (select idplayers from sKtlTcXzr6.players where name = (?)) and result = (select idresult from sKtlTcXzr6.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)) and matchesL.date = (?)";


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

    public boolean getAccess(String mac) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_GET_MAC);
            ps.setString(1,mac);
            ResultSet result = ps.executeQuery();

            if(result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
            System.out.println("Проверим " + resultToCheck);
            if (resultToCheck.next()) {
                return;
            } else {
                System.out.println(rsForPlayer1.getInt(1) + " - " + rsForPlayer2.getInt(1) + " - " + rsForResult.getInt(1));
                psForInsertMatch.setString(1, String.valueOf(rsForPlayer1.getInt(1)));
                psForInsertMatch.setString(2, String.valueOf(rsForPlayer2.getInt(1)));
                psForInsertMatch.setString(3, String.valueOf(rsForResult.getInt(1)));
                psForInsertMatch.setString(4, date);
                System.out.printf("Инсерт матча: %d %d %d %s\n",rsForPlayer1.getInt(1),rsForPlayer2.getInt(1),rsForResult.getInt(1),date);
                psForInsertMatch.execute();
            }

        } catch (SQLException e) {
            System.out.println("ЭКСЭПШОН" + e.toString());
        }
    }

    public void insertMatchL(Player player1, Player player2, Result result, String date, String league) {
            PreparedStatement ps = null;
            this.insertPlayer(player1);
            this.insertPlayer(player2);
            this.insertResult(result);
            try {
                ps = getPreparedStatementForMatch(player1, player2, result, date, SQL_SELECT_MATCH_L);
                ResultSet resultToCheck = ps.executeQuery();
                System.out.println("Проверим " + resultToCheck);
                if (resultToCheck.next()) {
                    return;
                } else {
                    System.out.println("Свежий матч");
                    ps = getPreparedStatementForMatch(player1, player2, result, date, SQL_INSERT_MATCH_L);
                    ps.setString(12, league);
                    ps.execute();
                }

            } catch (SQLException e) {
                System.out.println("ЭКСЭПШОН" + e.toString());
            }
    }

    @NotNull
    private PreparedStatement getPreparedStatementForMatch(Player player1, Player player2, Result result, String date, String sqlInsertMatchL) throws SQLException {
        PreparedStatement ps;
        ps = connection.prepareStatement(sqlInsertMatchL);
        ps.setString(1, player1.getName());
        ps.setString(2, player2.getName());
        ps.setString(3, result.getScore());
        ps.setString(4, result.getSet(1));
        ps.setString(5, result.getSet(2));
        ps.setString(6, result.getSet(3));
        ps.setString(7, result.getSet(4));
        ps.setString(8, result.getSet(5));
        ps.setString(9, result.getSet(6));
        ps.setString(10, result.getSet(7));
        ps.setString(11, date);
        return ps;
    }


    public double searchPlayersMatch(String name, String date) {
        PreparedStatement ps = null;
        String[] result;
        date = date.substring(0,10);
        //System.out.println(date);
        double countMatches = 0, countWin = 0;
        try {
            ps = connection.prepareStatement(SQL_SEARCH_PLAYERS_MATCH_FOR_DATE);
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, date+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countMatches++;
                //System.out.println(rs.getString(1) + " " + rs.getString(2) + " - " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " ");
                result = rs.getString(4).split(":");
                if (Double.valueOf(result[0]) > Double.valueOf(result[1]) && rs.getString(2).equals(name)) {
                    countWin++;
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1]) && rs.getString(3).equals(name)) {
                    countWin++;
                }
            }

           // System.out.println("matches - " + countMatches + " wins - " + countWin + "winrate - " + countWin / countMatches * 100);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countWin/countMatches*100;
    }

    public SMResult searchPlayersMatchWithForaAndTotals(String name) {
        PreparedStatement ps = null;
        String[] result;
        double countMatches = 0, countWin = 0;

        String[] setScores = new String[7];
        String[] resultArray = new String[11];
        SMResult smResult = new SMResult();

        try {
            ps = connection.prepareStatement(SQL_SEARCH_PLAYERS_MATCH);
            ps.setString(1, name);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0,10);
                String matchDate = getWeekDayFromDate(date);
                String appDate = date.substring(5,10) + " " + matchDate;


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

                    smResult.put(resultArray[2], appDate,result[0]+":"+result[1],String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate,resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                    //не закочено!
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[1],resultArray[2],result[0],result[1],forasAndTotals[0],forasAndTotals[2],forasAndTotals[1],forasAndTotals[3], forasAndTotals[2]+forasAndTotals[3]);
                } else {
                    setScores = reverseSets(setScores);
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[2],resultArray[1],result[1],result[0],forasAndTotals[1],forasAndTotals[3],forasAndTotals[0],forasAndTotals[2], forasAndTotals[2]+forasAndTotals[3]);
                    //не закочено!
                    smResult.put(resultArray[1], appDate,result[1]+":"+result[0],String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s\n", resultArray[0], matchDate,resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }

        }
            smResult.setWinR(String.format("%.0f",countWin/countMatches *100)+"%");
            System.out.printf("Matches: %.0f | Wins: %.0f | WinRate: %.0f percents\n",countMatches,countWin, countWin/countMatches * 100);
        //System.out.println("matches - " + countMatches + " wins - " + countWin + "winrate - " + countWin / countMatches * 100);

        } catch(
    SQLException e)

    {
        e.printStackTrace();
    }
        return smResult;
}

        public SMResult searchPlayersMatchWithLeague(String name, String league, int limit) {
        PreparedStatement ps = null;
        String[] result;
        double countMatches = 0, countWin = 0;

        String[] setScores = new String[7];
        String[] resultArray = new String[11];
        SMResult smResult = new SMResult();

        try {
            ps = connection.prepareStatement(SQL_SELECT_PLAYER_MATCH_L);
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, league);
            ps.setInt(4, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0,10);
                String matchDate = getWeekDayFromDate(date);
                String appDate = date.substring(5,10) + " " + matchDate;


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

                    smResult.put(resultArray[2], appDate,result[0]+":"+result[1],String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate,resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                    //не закочено!
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[1],resultArray[2],result[0],result[1],forasAndTotals[0],forasAndTotals[2],forasAndTotals[1],forasAndTotals[3], forasAndTotals[2]+forasAndTotals[3]);
                } else {
                    setScores = reverseSets(setScores);
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[2],resultArray[1],result[1],result[0],forasAndTotals[1],forasAndTotals[3],forasAndTotals[0],forasAndTotals[2], forasAndTotals[2]+forasAndTotals[3]);
                    //не закочено!
                    smResult.put(resultArray[1], appDate,result[1]+":"+result[0],String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s\n", resultArray[0], matchDate,resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }

            }
            smResult.setWinR(String.format("%.0f",countWin/countMatches *100)+"%");
            System.out.printf("Matches: %.0f | Wins: %.0f | WinRate: %.0f percents\n",countMatches,countWin, countWin/countMatches * 100);
            //System.out.println("matches - " + countMatches + " wins - " + countWin + "winrate - " + countWin / countMatches * 100);

        } catch(
                SQLException e)

        {
            e.printStackTrace();
        }
        return smResult;
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

    public List<String> getPlayers() {
        PreparedStatement ps = null;
        List<String> playersList = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_GET_PLAYERS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                playersList.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playersList;
    }

    public SMResult search2PlayersMatchWithForaAndTotals(String name1, String name2) {

        SMResult smResult = new SMResult();

        PreparedStatement ps = null;
        String[] result;
        double winsOfPlayer1 = 0, winsOfPlayer2 = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        double win142day = searchPlayersMatch(name1,df.format(new Date()));
        double win242day = searchPlayersMatch(name2,df.format(new Date()));
        System.out.printf("%s(today) winrates %s / %s : %.0f / %.0f\n",df.format(new Date()),name1,name2,win142day,win242day);
        try {
            ps = connection.prepareStatement(SQL_SEARCH_2PLAYERS_MATCH);
            ps.setString(1, name1);
            ps.setString(2, name2);
            ps.setString(3, name2);
            ps.setString(4, name1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                double win1 = searchPlayersMatch(rs.getString(2),rs.getString(1));
                double win2 = searchPlayersMatch(rs.getString(3),rs.getString(1));

                String[] setScores = new String[7];

                String[] resultArray = new String[11];
                result = rs.getString(4).split(":");

                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0,10);
                String matchDate = getWeekDayFromDate(date);

                String appDate = date.substring(5,10) + " " + matchDate;
                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);

                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer1++;
                    }
                    else {
                        winsOfPlayer2++;
                    }
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer2++;
                    }
                    else {
                        winsOfPlayer1++;
                    }
                }
                if (rs.getString(2).equals(name1))
                {
                    smResult.put(appDate,result[0]+":"+result[1],String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f",win1), String.format("%.0f",win2));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], win1, win2 , setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }
                else {
                    setScores = reverseSets(setScores);
                    smResult.put(appDate,result[1]+":"+result[0],String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f",win2), String.format("%.0f",win1));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3],win2 ,win1, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }
            }
            smResult.setP1Wins(String.format("%.0f", winsOfPlayer1));
            smResult.setP2Wins(String.format("%.0f", winsOfPlayer2));
            smResult.setPlayer1(name1);
            smResult.setPlayer2(name2);
            System.out.printf("Wins %s / %s : %.0f / %.0f", name1, name2, winsOfPlayer1, winsOfPlayer2);
            System.out.println("wins of " + name1 + " - " + winsOfPlayer1 + " / wins of " + name2 + " - " + winsOfPlayer2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return smResult;
    }

    public SMResult search2PlayersMatchWithLeague(String name1, String name2, String league, int limit) {

        SMResult smResult = new SMResult();

        PreparedStatement ps = null;
        String[] result;
        double winsOfPlayer1 = 0, winsOfPlayer2 = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        double win142day = searchPlayersMatch(name1,df.format(new Date()));
        double win242day = searchPlayersMatch(name2,df.format(new Date()));
        System.out.printf("%s(today) winrates %s / %s : %.0f / %.0f\n",df.format(new Date()),name1,name2,win142day,win242day);
        try {
            ps = connection.prepareStatement(SQL_SELECT_2PLAYERS_MATCH_L);
            ps.setString(1, name1);
            ps.setString(2, name2);
            ps.setString(3, name2);
            ps.setString(4, name1);
            ps.setString(5, league);
            ps.setInt(6, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                double win1 = searchPlayersMatch(rs.getString(2),rs.getString(1));
                double win2 = searchPlayersMatch(rs.getString(3),rs.getString(1));

                String[] setScores = new String[7];

                String[] resultArray = new String[11];
                result = rs.getString(4).split(":");

                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0,10);
                String matchDate = getWeekDayFromDate(date);

                String appDate = date.substring(5,10) + " " + matchDate;
                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);

                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer1++;
                    }
                    else {
                        winsOfPlayer2++;
                    }
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer2++;
                    }
                    else {
                        winsOfPlayer1++;
                    }
                }
                if (rs.getString(2).equals(name1))
                {
                    smResult.put(appDate,result[0]+":"+result[1],String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f",win1), String.format("%.0f",win2));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], win1, win2 , setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }
                else {
                    setScores = reverseSets(setScores);
                    smResult.put(appDate,result[1]+":"+result[0],String.format("%.0f",forasAndTotals[1]),String.format("%.0f",forasAndTotals[3]),String.format("%.0f",forasAndTotals[0]),String.format("%.0f",forasAndTotals[2]),String.format("%.0f",forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f",win2), String.format("%.0f",win1));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3],win2 ,win1, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }
            }
            smResult.setP1Wins(String.format("%.0f", winsOfPlayer1));
            smResult.setP2Wins(String.format("%.0f", winsOfPlayer2));
            smResult.setPlayer1(name1);
            smResult.setPlayer2(name2);
            System.out.printf("Wins %s / %s : %.0f / %.0f", name1, name2, winsOfPlayer1, winsOfPlayer2);
            System.out.println("wins of " + name1 + " - " + winsOfPlayer1 + " / wins of " + name2 + " - " + winsOfPlayer2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return smResult;
    }

    public String[] reverseSets(String[] setScores) {
        String[] result = new String[setScores.length];
        for (int index = 0; index < setScores.length; index++) {
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

    public String getWeekDayFromDate(String date) {
        Date dayWeek = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            dayWeek = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("E").format(dayWeek);
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
            ServerManager serverManager = new ServerManager();

            String p1 = "Олег Косых";
            String p2 = "Анатолий Пчелинцев";
            String league = "Mini Table Tennis";
            int limit = 10;

            serverManager.searchAllMatches();
            //System.out.print("\n");
            System.out.printf("\n -------------%s statistics------------- \n", p1);
            //serverManager.searchPlayersMatchWithForaAndTotals(p1);
            serverManager.searchPlayersMatchWithLeague(p1,league,limit);
            //System.out.print("\n");
            System.out.printf("\n -------------%s statistics------------- \n", p2);
            serverManager.searchPlayersMatchWithLeague(p2,league,limit);
            //System.out.print("\n");
            System.out.printf("\n -------------%s - %s------------- \n", p1, p2);
            serverManager.search2PlayersMatchWithLeague(p1, p2,league,limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
