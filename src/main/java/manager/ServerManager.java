package manager;

import entity.Player;
import entity.Result;
import lombok.extern.log4j.Log4j;
import util.ConnectorDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

@Log4j
public class ServerManager {

    private Connection connection;

    private final static String SQL_INSERT_PLAYER="insert into bablo.players(name) values (?)";
    private final static String SQL_INSERT_RESULT="insert into bablo.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
    private final static String SQL_SELECT_PLAYER_NAME="select name from bablo.players where name = (?) ";
    private final static String SQL_SELECT_RESULT_FOR_MATCH = "select idresult from bablo.result order by idresult desc limit 1";
    private final static String SQL_SELECT_PLAYER_FOR_MATCH = "select players.idplayers from bablo.players where name = (?)";
    private final static String SQL_INSERT_MATCH = "insert into bablo.matches(player1,player2,result,date) values (?,?,?,?)";



    public ServerManager() throws SQLException {
        this.connection = ConnectorDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }


    public void insertPlayer(Player player){
        PreparedStatement psToSelect = null;
        PreparedStatement ps = null;
        try{
            psToSelect = connection.prepareStatement(SQL_SELECT_PLAYER_NAME);
            psToSelect.setString(1,player.getName());
            ResultSet rs = psToSelect.executeQuery();
            while (rs.next()){
                return;
            }
                ps = connection.prepareStatement(SQL_INSERT_PLAYER);
                ps.setString(1, player.getName());
                ps.execute();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertResult(Result result){
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(SQL_INSERT_RESULT);
            ps.setString(1,result.getScore());
            ps.setString(2,result.getSet1());
            ps.setString(3,result.getSet2());
            ps.setString(4,result.getSet3());
            ps.setString(5,result.getSet4());
            ps.setString(6,result.getSet5());
            ps.setString(7,result.getSet6());
            ps.setString(8,result.getSet7());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMatch(Player player1, Player player2, Result result, Date date){
        PreparedStatement psForPlayer1 = null;
        PreparedStatement psForPlayer2 = null;
        PreparedStatement psForResult = null;
        PreparedStatement psForInsertMatch = null;
        try{
            psForPlayer1 = connection.prepareStatement(SQL_SELECT_PLAYER_FOR_MATCH);
            psForPlayer2 = connection.prepareStatement(SQL_SELECT_PLAYER_FOR_MATCH);
            psForResult = connection.prepareStatement(SQL_SELECT_RESULT_FOR_MATCH);
            psForInsertMatch = connection.prepareStatement(SQL_INSERT_MATCH);
            psForPlayer1.setString(1,player1.getName());
            psForPlayer2.setString(1,player2.getName());
            ResultSet rsForPlayer1 = psForPlayer1.executeQuery();
            ResultSet rsForPlayer2 = psForPlayer2.executeQuery();
            ResultSet rsForResult = psForResult.executeQuery();
            rsForPlayer1.next();
            rsForPlayer2.next();
            rsForResult.next();
            int a = rsForPlayer1.getInt(1);
            System.out.println(a);
            System.out.println(rsForPlayer1.getInt(1) + " - " + rsForPlayer2.getInt(1) + " - " + rsForResult.getInt(1));
            psForInsertMatch.setString(1,String.valueOf(rsForPlayer1.getInt(1)));
            psForInsertMatch.setString(2,String.valueOf(rsForPlayer2.getInt(1)));
            psForInsertMatch.setString(3,String.valueOf(rsForResult.getInt(1)));
            psForInsertMatch.setString(4, String.valueOf(date.toString()));
            psForInsertMatch.execute();
        }
        catch (SQLException e){

        }

    }

    public static void main(String[] args) {
        try {
            Player player = new Player("Моляка Дмитрий");
            Player player1 = new Player("Косых Олег");
            Date date = Date.valueOf("2000-01-01");
            Result result = new Result("4:3", "10:12", "10:12", "10:12", "12:10", "12:10", "12:10", "12:10");
            //Result result1 = new Result("4:3", "10:12", "10:12", "10:12", "12:10", "12:10", "12:10", "12:10");
            ServerManager serverManager = new ServerManager();
            //serverManager.insertPlayer(player);
            //serverManager.insertPlayer(player1);
            //serverManager.insertResult(result);
            //serverManager.insertResult(result1);
            serverManager.insertMatch(player1,player,result,date);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
