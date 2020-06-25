package manager;

import entity.Player;
import entity.Result;
import entity.SMResult;
import entity.WinL;
import org.jetbrains.annotations.NotNull;
import util.ConnectorDB;

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
    static String tempUpdateDate;
    static String updateDate;
    //ArrayList<String> serverLeagues;

//    private final static String SQL_INSERT_PLAYER = "insert into bazabaka.players(name) values (?)";
//    private final static String SQL_INSERT_RESULT = "insert into bazabaka.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
//    private final static String SQL_SELECT_PLAYER_NAME = "select name from bazabaka.players where name = (?) ";
//    private final static String SQL_SELECT_RESULT = "select * from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
//    private final static String SQL_SELECT_MATCH = "select * from bazabaka.matches where matches.player1 = (?) and matches.player2 = (?) and result = (?) and matches.date = (?)";
//    private final static String SQL_SELECT_RESULT_FOR_MATCH = "select idresult from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
//    private final static String SQL_SELECT_PLAYER_FOR_MATCH = "select players.idplayers from bazabaka.players where name = (?)";
//    private final static String SQL_INSERT_MATCH = "insert into bazabaka.matches(player1,player2,result,date) values (?,?,?,?)";
//    private final static String SQL_SEARCH_PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) order by matches.date desc limit 10";
//    private final static String SQL_SEARCH_2PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and ((p1.name=(?) and p2.name = (?)) or (p1.name=(?) and p2.name = (?))) order by matches.date desc limit 10";
//    private final static String SQL_SEARCH_ALL_MATCHES = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and matches.date > '2020-04-22' order by matches.date desc";
//    private final static String SQL_SEARCH_PLAYERS_MATCH_FOR_DATE = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) and matches.date like(?) order by matches.date desc";

    private final static String SQL_INSERT_PLAYER = "insert into bazabaka.players(name) values (?)";
    private final static String SQL_INSERT_RESULT = "insert into bazabaka.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
    private final static String SQL_SELECT_PLAYER_NAME = "select name from bazabaka.players where name = (?) ";
    private final static String SQL_SELECT_RESULT = "select * from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
    private final static String SQL_SELECT_MATCH = "select * from bazabaka.matches where matches.player1 = (?) and matches.player2 = (?) and result = (?) and matches.date = (?)";
    private final static String SQL_SELECT_RESULT_FOR_MATCH = "select idresult from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)";
    private final static String SQL_SELECT_PLAYER_FOR_MATCH = "select players.idplayers from bazabaka.players where name = (?)";
    private final static String SQL_INSERT_MATCH = "insert into bazabaka.matches(player1,player2,result,date) values (?,?,?,?)";
    private final static String SQL_SEARCH_PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) order by matches.date desc limit 10";
    private final static String SQL_SEARCH_2PLAYERS_MATCH = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and ((p1.name=(?) and p2.name = (?)) or (p1.name=(?) and p2.name = (?))) order by matches.date desc limit 10";
    private final static String SQL_SEARCH_ALL_MATCHES = "select matchesL.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matchesL, players p1, players p2, result r where matchesL.player1 = p1.idplayers and matchesL.player2 = p2.idplayers and matchesL.result=r.idresult and matchesL.date > '2020-05-18' order by matchesL.date desc";
    private final static String SQL_SEARCH_PLAYERS_MATCH_FOR_DATE = "select matches.date, p1.name, p2.name, r.score,r.set1,r.set2,r.set3,r.set4,r.set5,r.set6,r.set7 from matches, players p1, players p2, result r where matches.player1 = p1.idplayers and matches.player2 = p2.idplayers and matches.result=r.idresult and (p1.name=(?) or p2.name = (?)) and matches.date like(?) order by matches.date desc";
    private final static String SQL_GET_MAC = "select * from bazabaka.Appusers where Appusers.mac = (?)";
    private final static String SQL_GET_PLAYERS = "select name from bazabaka.players";
    private final static String SQL_SELECT_LEAGUES = "select name from bazabaka.leagues order by idleague asc";
    private final static String SQL_SELECT_2PLAYERS_MATCH_WinL = "select m.date, p1.name, p2.name, r.score, r.set1, r.set2, r.set3, r.set4, r.set5, r.set6, r.set7 from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where ((p1.name= (?) and p2.name= (?)) or (p2.name= (?) and p1.name= (?))) and l.name like(?) and m.date <= (?) order by m.date desc limit ?";
    private final static String SQL_SELECT_2PLAYERS_MATCH_L = "select m.date, p1.name, p2.name, r.score, r.set1, r.set2, r.set3, r.set4, r.set5, r.set6, r.set7 from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where ((p1.name= (?) and p2.name= (?)) or (p2.name= (?) and p1.name= (?))) and l.name like(?) order by m.date desc limit ?";
    private final static String SQL_SELECT_PLAYER_MATCH_L = "select m.date, p1.name, p2.name, r.score, r.set1, r.set2, r.set3, r.set4, r.set5, r.set6, r.set7 from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where (p1.name= (?) or p2.name= (?)) and l.name like(?) order by m.date desc limit ?";
    private final static String SQL_GET_UPDATE_DATE = "select date from bazabaka.matchesUpdates order by date desc limit 1";
    private final static String SQL_INSERT_UPDATE_DATE = "insert into bazabaka.matchesUpdates(date) values (?)";
    private final static String SQL_INSERT_MATCHL = "insert into bazabaka.matches(player1,player2,result,date,league) values (?,?,?,?,?)";
    //private final static String SQL_INSERT_MATCH_L = "insert into bazabaka.matchesL(player1,player2,result,date,league) values ((select idplayers from bazabaka.players where name = (?)),(select idplayers from bazabaka.players where name = (?)),(select idresult from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)),?,(select idleague from bazabaka.leagues where name = ?))";
    private final static String SQL_INSERT_MATCH_L = "insert into bazabaka.matchesL(player1,player2,result,date,league) values ((select idplayers from bazabaka.players where name = (?)),(select idplayers from bazabaka.players where name = (?)),(select idresult from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)),?,(select idleague from bazabaka.leagues where name = ?))";
    private final static String SQL_SELECT_PLAYER_MATCH_L_FOR_DATE = "select m.date, p1.name, p2.name, r.score, r.set1, r.set2, r.set3, r.set4, r.set5, r.set6, r.set7 from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where (p1.name= (?) or p2.name= (?)) and l.name like(?) and m.date like (?) order by m.date desc";

    private final static String SQL_SELECT_MATCH_L = "select * from bazabaka.matchesL where matchesL.player1 = (select idplayers from bazabaka.players where name = (?)) and matchesL.player2 = (select idplayers from bazabaka.players where name = (?)) and result = (select idresult from bazabaka.result where result.score = (?) and result.set1 = (?) and result.set2 = (?) and result.set3 = (?) and result.set4 = (?) and result.set5 = (?) and result.set6 = (?) and result.set7 = (?)) and matchesL.date = (?)";
    private final static String SQL_INSERT_LEAGUE = "insert into bazabaka.leagues(name) values (?)";
    private final static String SQL_SELECT_MATCHES_FOR_WINL = "select m.date, p1.name, p2.name, r.score from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where (l.name like(?)) and (m.date like ?) order by m.date";
    private final static String SQL_INSERT_WINL = "insert into bazabaka.winLs(date,leagueid,winL) values (?,select idleague from bazabaka.leagues where name like ?,?)";
    private final static String SQL_SELECT_WINL = "select * from bazabaka.winLs where (date like ?) and (leagueid = (select idleague from bazabaka.leagues where name like ?)";
    private final static String SQL_UPDATE_WINL = "update bazabaka.winLs set winL = ? where ((date like ?) and (leagueid = ?))";

    public ServerManager() throws SQLException {
        this.connection = ConnectorDB.getConnection();

    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public ArrayList<String> getLeagues() {
        PreparedStatement ps = null;
        ArrayList<String> leaguesList = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_SELECT_LEAGUES);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                leaguesList.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaguesList;
    }

    public ArrayList<String[]> getMatchesForDay(String league, String date) {
        final int DATE_RESULT_ID = 3,P1_RESULT_ID = 0, P2_RESULT_ID = 1, SET_RESULT_ID = 2;
        PreparedStatement ps = null;
        date += "%";
        ArrayList<String[]> resultArrayList = new ArrayList<>();

        try {
            ps = connection.prepareStatement(SQL_SELECT_MATCHES_FOR_WINL);
            ps.setString(1, league);
            ps.setString(2,date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] results = new String[4];
                results[P1_RESULT_ID] = rs.getString(2);
                results[P2_RESULT_ID] = rs.getString(3);
                results[SET_RESULT_ID] = rs.getString(4);
                results[DATE_RESULT_ID] = rs.getString(1);
                resultArrayList.add(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultArrayList;
    }

    public double[] getWinLs(String league) {
        int offset = 0;
        int fineMatchesQuantity = 0;
        int p1TopTenWins = 0, p2TopTenWins = 0;
        double matchesQuantity = 0, asPredictedQuantity = 0;
        double winL = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String[]> resultArrayList = getMatchesForDay(league, df.format(new Date()));
        for (String[] line : resultArrayList) {
            boolean p1winBool = false;

            String result[] = line[2].split(":");
            int p1Score = Integer.parseInt(result[0]), p2Score = Integer.parseInt(result[1]);

            if (p1Score > p2Score) {
                p1winBool = true;
            }

            SMResult smResult = search2PlayersMatchForWinL(line[0], line[1], league, line[3], 10);
            p1TopTenWins = smResult.getP1PeriodWins();
            p2TopTenWins = smResult.getP2PeriodWins();
            if ((p1TopTenWins - p2TopTenWins >= 5 && p1winBool) || (p2TopTenWins - p1TopTenWins >= 5 && !p1winBool)) {
                asPredictedQuantity++;
                matchesQuantity++;
            } else if (p1TopTenWins - p2TopTenWins >= 5 || p2TopTenWins - p1TopTenWins >= 5) {
                matchesQuantity++;
            }
        }
        winL = asPredictedQuantity / matchesQuantity;
        return new double[]{winL, matchesQuantity};
    }

    public void checkWinLsForLeague(String league) {
        double[] results = getWinLs(league);

        System.out.printf("WinL for %s league: %.0f%% | %.0f", league, results[0], results[1]);
    }

    public WinL selectWinLsForDate(String date, String league) throws SQLException {
        date += "%";
        PreparedStatement ps = connection.prepareStatement(SQL_SELECT_WINL);
        ps.setString(1, date);
        ps.setString(2, league);
        ResultSet results = ps.executeQuery();
        WinL winL = new WinL();

        while (results.next()) {
            winL.setId(results.getInt(1));
            winL.setDate(date);
            winL.setLeagueid(results.getInt(3));
            winL.setWinL(results.getDouble(4));
            ps.close();
            return winL;
        }
        ps.close();
        return null;
    }

    public boolean setWinLsForDate(String date, String league, double winL) throws SQLException {
        WinL winLForDate = selectWinLsForDate(date,league);
        if(winLForDate == null) {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_WINL);
            ps.setString(1, date);
            ps.setString(2, league);
            ps.setDouble(3, winL);

            ps.execute();
            ps.close();
            return true;
        }

        else {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_WINL);
            ps.setDouble(1,winL);
            ps.setString(2,date);
            ps.setInt(3,winLForDate.getId());
            ps.execute();
            ps.close();
            return true;
        }
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
            ps.setString(1, mac);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
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
                System.out.printf("Инсерт матча: %d %d %d %s\n", rsForPlayer1.getInt(1), rsForPlayer2.getInt(1), rsForResult.getInt(1), date);
                psForInsertMatch.execute();
            }

        } catch (SQLException e) {
            System.out.println("ЭКСЭПШОН" + e.toString());
        }
    }

    public void prepareUpdateDates() {
        updateDate = getUpdateDate();
        tempUpdateDate = new String(updateDate);
    }

    public void insertMatchL(Player player1, Player player2, Result result, String date, String league) {

        PreparedStatement ps = null;
        this.insertPlayer(player1);
        this.insertPlayer(player2);
        this.insertResult(result);

        //if (updateDate.compareTo(date) >= 0) {
        //System.out.println("Дата более старая, пропускаем");
        //return;
        //}
        if (date.compareTo(tempUpdateDate) > 0) {
            System.out.printf("TempDate %s replased with %s\n", tempUpdateDate, date);
            tempUpdateDate = date;
        }
        try {
            ps = getPreparedStatementForMatch(player1, player2, result, date, SQL_SELECT_MATCH_L);
            ResultSet resultToCheck = ps.executeQuery();
            System.out.println("Проверим " + resultToCheck);

            if (resultToCheck.next()) {
                return;
            } else {
                System.out.println("Свежий матч");
                ps = getPreparedStatementForMatch(player1, player2, result, date, SQL_INSERT_MATCH_L);
                //int indexL = serverLeagues.indexOf(league)+1;
                //ps.setInt(12,indexL);
                //System.out.println("ЛИГА" + serverLeagues.get(indexL-1));
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

    public void insertLeague(String league) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_LEAGUE);
            ps.setString(1, league);
            ps.execute();
            System.out.printf("\nLeague %s was added", league);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public double searchPlayersMatchL(String name, String league, String date) {
        PreparedStatement ps = null;
        String[] result;
        System.out.println(date);
        date = date.substring(0, 10);
        //System.out.println(date);
        double countMatches = 0, countWin = 0;
        try {
            ps = connection.prepareStatement(SQL_SELECT_PLAYER_MATCH_L_FOR_DATE);
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, league + "%");
            ps.setString(4, date + "%");
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
        return countWin / countMatches * 100;
    }

    public double searchPlayersMatch(String name, String date) {
        PreparedStatement ps = null;
        String[] result;
        date = date.substring(0, 10);
        //System.out.println(date);
        double countMatches = 0, countWin = 0;
        try {
            ps = connection.prepareStatement(SQL_SEARCH_PLAYERS_MATCH_FOR_DATE);
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, date + "%");
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
        return countWin / countMatches * 100;
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

                String date = resultArray[0].substring(0, 10);
                String matchDate = getWeekDayFromDate(date);
                String appDate = date.substring(5, 10) + " " + matchDate;


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

                    smResult.put(resultArray[2], appDate, result[0] + ":" + result[1], String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                } else {
                    setScores = reverseSets(setScores);
                    smResult.put(resultArray[1], appDate, result[1] + ":" + result[0], String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s\n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }

            }
            smResult.setWinR(String.format("%.0f", countWin / countMatches * 100) + "%");
            System.out.printf("Matches: %.0f | Wins: %.0f | WinRate: %.0f percents\n", countMatches, countWin, countWin / countMatches * 100);
        } catch (
                SQLException e) {
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

                String date = resultArray[0].substring(0, 10);
                String matchDate = getWeekDayFromDate(date);
                String appDate = date.substring(5, 10) + " " + matchDate;


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

                    smResult.put(resultArray[2], appDate, result[0] + ":" + result[1], String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                    //не закочено!
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[1],resultArray[2],result[0],result[1],forasAndTotals[0],forasAndTotals[2],forasAndTotals[1],forasAndTotals[3], forasAndTotals[2]+forasAndTotals[3]);
                } else {
                    setScores = reverseSets(setScores);
                    //System.out.printf("%s | %s - %s | %s : %s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f\n", resultArray[0],resultArray[2],resultArray[1],result[1],result[0],forasAndTotals[1],forasAndTotals[3],forasAndTotals[0],forasAndTotals[2], forasAndTotals[2]+forasAndTotals[3]);
                    //не закочено!
                    smResult.put(resultArray[1], appDate, result[1] + ":" + result[0], String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | %s %s %s %s %s %s %s\n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }

            }
            smResult.setWinR(String.format("%.0f", countWin / countMatches * 100) + "%");
            System.out.printf("Matches: %.0f | Wins: %.0f | WinRate: %.0f percents\n", countMatches, countWin, countWin / countMatches * 100);
            //System.out.println("matches - " + countMatches + " wins - " + countWin + "winrate - " + countWin / countMatches * 100);

        } catch (
                SQLException e) {
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
        double win142day = searchPlayersMatch(name1, df.format(new Date()));
        double win242day = searchPlayersMatch(name2, df.format(new Date()));
        System.out.printf("%s(today) winrates %s / %s : %.0f / %.0f\n", df.format(new Date()), name1, name2, win142day, win242day);
        try {
            ps = connection.prepareStatement(SQL_SEARCH_2PLAYERS_MATCH);
            ps.setString(1, name1);
            ps.setString(2, name2);
            ps.setString(3, name2);
            ps.setString(4, name1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                double win1 = searchPlayersMatch(rs.getString(2), rs.getString(1));
                double win2 = searchPlayersMatch(rs.getString(3), rs.getString(1));

                String[] setScores = new String[7];

                String[] resultArray = new String[11];
                result = rs.getString(4).split(":");

                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0, 10);
                String matchDate = getWeekDayFromDate(date);

                String appDate = date.substring(5, 10) + " " + matchDate;
                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);

                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer1++;
                    } else {
                        winsOfPlayer2++;
                    }
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer2++;
                    } else {
                        winsOfPlayer1++;
                    }
                }
                if (rs.getString(2).equals(name1)) {
                    smResult.put(appDate, result[0] + ":" + result[1], String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f", win1), String.format("%.0f", win2));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], win1, win2, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                } else {
                    setScores = reverseSets(setScores);
                    smResult.put(appDate, result[1] + ":" + result[0], String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f", win2), String.format("%.0f", win1));
                    System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], win2, win1, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
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

    public String getUpdateDate() {
        PreparedStatement ps = null;
        String date = "";
        try {
            ps = connection.prepareStatement(SQL_GET_UPDATE_DATE);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                date = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void setUpdateDate() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_UPDATE_DATE);
            ps.setString(1, tempUpdateDate);
            ps.execute();
            System.out.printf("Последняя дата: %s\n", tempUpdateDate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SMResult search2PlayersMatchWithLeague(String name1, String name2, String league, int limit) {

        SMResult smResult = new SMResult();

        PreparedStatement ps = null;
        String[] result;
        double winsOfPlayer1 = 0, winsOfPlayer2 = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        double win142day = searchPlayersMatchL(name1, league, df.format(new Date()));
        double win242day = searchPlayersMatchL(name2, league, df.format(new Date()));
        //System.out.printf("%s(today) winrates %s / %s : %.0f / %.0f\n", df.format(new Date()), name1, name2, win142day, win242day);
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

                double win1 = searchPlayersMatchL(rs.getString(2), league, rs.getString(1));
                double win2 = searchPlayersMatchL(rs.getString(3), league, rs.getString(1));

                String[] setScores = new String[7];

                String[] resultArray = new String[11];
                result = rs.getString(4).split(":");

                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0, 10);
                String matchDate = getWeekDayFromDate(date);

                String appDate = date.substring(5, 10) + " " + matchDate;
                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);

                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer1++;
                    } else {
                        winsOfPlayer2++;
                    }
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer2++;
                    } else {
                        winsOfPlayer1++;
                    }
                }
                if (rs.getString(2).equals(name1)) {
                    smResult.put(appDate, result[0] + ":" + result[1], String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f", win1), String.format("%.0f", win2));
                    //System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], win1, win2, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                } else {
                    setScores = reverseSets(setScores);
                    smResult.put(appDate, result[1] + ":" + result[0], String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f", win2), String.format("%.0f", win1));
                    //System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], win2, win1, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }
            }
            smResult.setP1Wins(String.format("%.0f", winsOfPlayer1));
            smResult.setP2Wins(String.format("%.0f", winsOfPlayer2));
            smResult.setPlayer1(name1);
            smResult.setPlayer2(name2);
            //System.out.printf("Wins %s / %s : %.0f / %.0f", name1, name2, winsOfPlayer1, winsOfPlayer2);
            //System.out.println("wins of " + name1 + " - " + winsOfPlayer1 + " / wins of " + name2 + " - " + winsOfPlayer2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return smResult;
    }

    public SMResult search2PlayersMatchForWinL(String name1, String name2, String league, String dateOffset, int limit) {

        SMResult smResult = new SMResult();

        PreparedStatement ps = null;
        String[] result;
        double winsOfPlayer1 = 0, winsOfPlayer2 = 0;
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        double win142day = searchPlayersMatchL(name1, league, df.format(new Date()));
//        double win242day = searchPlayersMatchL(name2, league, df.format(new Date()));
        //System.out.printf("%s(today) winrates %s / %s : %.0f / %.0f\n", df.format(new Date()), name1, name2, win142day, win242day);
        try {
            ps = connection.prepareStatement(SQL_SELECT_2PLAYERS_MATCH_WinL);
            ps.setString(1, name1);
            ps.setString(2, name2);
            ps.setString(3, name2);
            ps.setString(4, name1);
            ps.setString(5, league);
            ps.setString(6, dateOffset);
            ps.setInt(7,limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                double win1 = searchPlayersMatchL(rs.getString(2), league, rs.getString(1));
                double win2 = searchPlayersMatchL(rs.getString(3), league, rs.getString(1));

                String[] setScores = new String[7];

                String[] resultArray = new String[11];
                result = rs.getString(4).split(":");

                for (int index = 0; index < 11; index++) {
                    resultArray[index] = rs.getString(index + 1);
                }

                String date = resultArray[0].substring(0, 10);
                String matchDate = getWeekDayFromDate(date);

                String appDate = date.substring(5, 10) + " " + matchDate;
                setScores = Arrays.copyOfRange(resultArray, 4, 11);
                double[] forasAndTotals = getForaAndTotals(setScores);

                if (Double.valueOf(result[0]) > Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer1++;
                    } else {
                        winsOfPlayer2++;
                    }
                }
                if (Double.valueOf(result[0]) < Double.valueOf(result[1])) {
                    if (rs.getString(2).equals(name1)) {
                        winsOfPlayer2++;
                    } else {
                        winsOfPlayer1++;
                    }
                }
                if (rs.getString(2).equals(name1)) {
                    smResult.put(appDate, result[0] + ":" + result[1], String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f", win1), String.format("%.0f", win2));
                    //System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[1], resultArray[2], result[0], result[1], forasAndTotals[0], forasAndTotals[2], forasAndTotals[1], forasAndTotals[3], forasAndTotals[2] + forasAndTotals[3], win1, win2, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                } else {
                    setScores = reverseSets(setScores);
                    smResult.put(appDate, result[1] + ":" + result[0], String.format("%.0f", forasAndTotals[1]), String.format("%.0f", forasAndTotals[3]), String.format("%.0f", forasAndTotals[0]), String.format("%.0f", forasAndTotals[2]), String.format("%.0f", forasAndTotals[2] + forasAndTotals[3]), String.format("%.0f", win2), String.format("%.0f", win1));
                    //System.out.printf("%s %s| %s - %s | %s:%s | Fora1: %.0f Total1: %.0f | Fora2: %.0f Total2: %.0f | Total: %.0f | WinR1: %.0f : WinR2: %.0f | %s %s %s %s %s %s %s \n", resultArray[0], matchDate, resultArray[2], resultArray[1], result[1], result[0], forasAndTotals[1], forasAndTotals[3], forasAndTotals[0], forasAndTotals[2], forasAndTotals[2] + forasAndTotals[3], win2, win1, setScores[0], setScores[1], setScores[2], setScores[3], setScores[4], setScores[5], setScores[6]);
                }
            }
            smResult.setP1Wins(String.format("%.0f", winsOfPlayer1));
            smResult.setP2Wins(String.format("%.0f", winsOfPlayer2));
            smResult.setPlayer1(name1);
            smResult.setPlayer2(name2);
            //System.out.printf("Wins %s / %s : %.0f / %.0f", name1, name2, winsOfPlayer1, winsOfPlayer2);
            //System.out.println("wins of " + name1 + " - " + winsOfPlayer1 + " / wins of " + name2 + " - " + winsOfPlayer2);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("IN DATA " + date);
        try {
            dayWeek = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE").format(dayWeek);
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

//            String p1 = "Алексей Попов";
//            String p2 = "Максим Боков";
//            String league = "%";
//            int limit = 10;
//            //serverManager.getLeagues();
//            serverManager.searchAllMatches();
//            System.out.printf("\n -------------%s statistics------------- \n", p1);
//            serverManager.searchPlayersMatchWithLeague(p1, league, limit);
//            System.out.printf("\n -------------%s statistics------------- \n", p2);
//            serverManager.searchPlayersMatchWithLeague(p2, league, limit);
//            System.out.printf("\n -------------select m.date, p1.name, p2.name, r.score from matchesL m join players p1 on m.player1 = p1.idplayers join players p2 on m.player2 = p2.idplayers join result r on m.result=r.idresult join leagues l ON l.idleague = m.league where (l.name like(?)) and (m.date like ?) order by m.dates - %s------------- \n", p1, p2);
//            serverManager.search2PlayersMatchWithLeague(p1, p2, league, limit);

            serverManager.checkWinLsForLeague("Мастерс");
            serverManager.checkWinLsForLeague("Mini Table Tennis");
            serverManager.checkWinLsForLeague("BoomCup");
            serverManager.checkWinLsForLeague("Pro Spin Series");
            serverManager.checkWinLsForLeague("Мини-теннис");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
