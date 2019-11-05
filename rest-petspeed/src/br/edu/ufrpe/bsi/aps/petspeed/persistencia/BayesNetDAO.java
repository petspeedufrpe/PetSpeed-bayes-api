package br.edu.ufrpe.bsi.aps.petspeed.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.edu.ufrpe.bsi.aps.petspeed.infra.DBConfig;
import br.edu.ufrpe.bsi.aps.petspeed.models.BNetworkDBModel;

public class BayesNetDAO {
	
	public BNetworkDBModel getNetworkModel() throws Exception {
        BNetworkDBModel netModel = new BNetworkDBModel();
 
        Connection connection = DBConfig.getConnection();
 
        String sql = "SELECT * FROM BNDATA_TB WHERE ID = 1";
 
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
 
        if (result.next()) {
        	netModel.setId(result.getInt("ID"));
        	netModel.setNetwork(result.getString("BN_DATA"));
        }
        return netModel;
    }
	public int storeNetwork(BNetworkDBModel netModel) throws Exception {
        int generatedID = 0;
        Connection conexao = DBConfig.getConnection();
 
        String sql = "INSERT INTO BNDATA_TB(BN_DATA) VALUES(?)";
 
        PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, netModel.getNetwork());
        statement.execute();
         
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            generatedID = result.getInt(1);
        }
         
        return generatedID;
    }
	public void updateNetwork(BNetworkDBModel netModel) throws Exception {
        Connection conexao = DBConfig.getConnection();
 
        String sql = "UPDATE TB_NOTA SET BNDATA_TB = ? WHERE ID_NOTE = ?";
 
        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, netModel.getNetwork());
        statement.setInt(2, netModel.getId());
        statement.execute();
    }
}

