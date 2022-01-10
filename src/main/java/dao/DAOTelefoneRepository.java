package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import conection.SingleConnectionBanco;
import model.ModelTelefone;


public class DAOTelefoneRepository {
	
	private Connection connection;
	
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();
	
	
	public DAOTelefoneRepository() {
		
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception {
		
		String salvarTelefone="INSERT INTO telefone (telefoneresidencial, telefonecelular, usuarioid, usercadid) "
											+ "value(?, ?, ?, ?)";
				
		PreparedStatement preparaSql = connection.prepareStatement(salvarTelefone);
		preparaSql.setString(1, modelTelefone.getTelefoneResidencial());
		preparaSql.setString(2, modelTelefone.getTelefoneCelular());
		preparaSql.setLong(3, modelTelefone.getUsuarioID().getId());
		preparaSql.setLong(4, modelTelefone.getUsuarioCadID().getId());
		
		preparaSql.execute();
		connection.commit();
	
	}
	
	public void deletarTelefone (Long id) throws Exception {
		
		String sqlDeleta = "DELETE FROM telefone WHERE id = ?";
		
		PreparedStatement preparaSql = connection.prepareStatement(sqlDeleta);
		
		preparaSql.setLong(1, id);
		preparaSql.executeUpdate();
		connection.commit();
	}
	
	/*Abaixo metodos para listar*/
	
	public List<ModelTelefone> listaTelefone(Long usuarioID) throws Exception{
		
		List<ModelTelefone> retornaTelefone = new ArrayList<ModelTelefone>();
		
		String consultaTelefone="SELECT * FROM telefone WHERE usuarioid = ?";
		PreparedStatement statement = connection.prepareStatement(consultaTelefone);
		
		
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			ModelTelefone telefone = new ModelTelefone();
			
			telefone.setTelefoneResidencial(rs.getString("telefoneResidencial"));
			telefone.setTelefoneCelular(rs.getString("telefoneCelular"));
			telefone.setUsuarioCadID(daoUsuarioRepository.consultaUsuarioID(rs.getLong("usuarioCadID")));
			telefone.setUsuarioID(daoUsuarioRepository.consultaUsuarioID(rs.getLong("usuarioID")));
			
			retornaTelefone.add(telefone);
		}
		
		return retornaTelefone;

	}
}