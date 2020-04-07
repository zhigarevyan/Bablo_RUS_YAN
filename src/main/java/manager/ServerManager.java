package manager;

import entity.Player;
import entity.Result;
import lombok.extern.log4j.Log4j;
import util.ConnectorDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Log4j
public class ServerManager {

    private Connection connection;

    private final static String SQL_INSERT_PLAYER="insert into bablo.players(name) values (?)";
    private final static String SQL_INSERT_RESULT="insert into bablo.result (score, set1,set2,set3,set4,set5,set6,set7) values (?,?,?,?,?,?,?,?)";
    //private final static String SQL_INSERT_MATCH="";


    public ServerManager() throws SQLException {
        this.connection = ConnectorDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void insertPlayer(Player player){
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(SQL_INSERT_PLAYER);
            ps.setString(1,player.getName());
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

    public static void main(String[] args) {
        try {
            Player player = new Player("Yan");
            Player player1 = new Player("Rusl");
            Result result = new Result("4:3", "10:12", "10:12", "10:12", "12:10", "12:10", "12:10", "12:10");
            Result result1 = new Result("4:3", "10:12", "10:12", "10:12", "12:10", "12:10", "12:10", "12:10");
            ServerManager serverManager = new ServerManager();
            //serverManager.insertPlayer(player);
            //serverManager.insertPlayer(player1);
            //serverManager.insertResult(result);
            //serverManager.insertResult(result1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
