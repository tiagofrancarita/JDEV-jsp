package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoLoginRepository {
	
	private Connection connection;
	
	public DaoLoginRepository() {
		
		connection = SingleConnectionBanco.getConnection();
	}
	
	
	public boolean validarAutenticacao(ModelLogin modellogin) throws Exception {
		
		String sql ="Select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modellogin.getLogin());
		statement.setString(2, modellogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			
			modellogin.setNome(resultSet.getString("nome"));
			return true; /*Autenticado*/
			
		}
		
			return false; /*Não autenticado*/
		
	}

}
