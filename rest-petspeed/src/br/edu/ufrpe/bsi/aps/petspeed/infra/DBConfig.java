package br.edu.ufrpe.bsi.aps.petspeed.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://52.4.69.207:3306/petdb", "petspeed", "petspeed2019");
    }
}
