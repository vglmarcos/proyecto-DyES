import java.sql.*;

public class Conexion {

    private Connection con;
    private final String uri;
    //private final String user;
    //private final String password;
    //private final String host;
    private final String database;

    public Conexion() {
        //this.host = "";
        //this.database = "";
        //this.user = "";
        //this.password = "";
        //this.uri = "jdbc:mysql://" + host + "/" + database;

        this.database = "database/Silica.accdb";
        this.uri = "jdbc:ucanaccess://" + this.database;
    }

    public void conectar() throws SQLException {
        //con = DriverManager.getConnection(uri, user, password);
        this.con = DriverManager.getConnection(this.uri);
    }

    public Connection getConnection() {
        return this.con;
    }

    public void desconectar() throws SQLException {
        this.con.close();
    }
}