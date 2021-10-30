package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoUsuarioRepository {
	
	private Connection connection;
	
	public DaoUsuarioRepository() {
		
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelLogin gravarUsuario(ModelLogin modellogin, Long userLogado) throws Exception {
		
		//try {
		
		//String mensagem ="Cadastro realizado com sucesso";
		
		
			if (modellogin.isNovo()) {
			
				String sqlSalvar ="INSERT INTO model_login(nome, login, senha, confirmasenha, email, dtNascimento, usuario_id, perfil, situacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparaSql = connection.prepareStatement(sqlSalvar);
		
				preparaSql.setString(1, modellogin.getNome());
				preparaSql.setString(2, modellogin.getLogin());
				preparaSql.setString(3, modellogin.getSenha());
				preparaSql.setString(4, modellogin.getConfirmaSenha());
				preparaSql.setString(5, modellogin.getEmail());
				preparaSql.setString(6, modellogin.getDtNascimento());
				preparaSql.setLong(7, userLogado);
				preparaSql.setString(8, modellogin.getPerfil());
				preparaSql.setString(9, modellogin.getSituacao());
				
				
				preparaSql.execute();
				connection.commit();
				
				if (modellogin.getFotoUser() != null && !modellogin.getFotoUser().isEmpty()) {
					sqlSalvar = "update model_login set fotouser =?, extensaofotouser=? where login =?";
					
					preparaSql = connection.prepareStatement(sqlSalvar);
					
					preparaSql.setString(1, modellogin.getFotoUser());
					preparaSql.setString(2, modellogin.getExtensaoFotoUser());
					preparaSql.setString(3, modellogin.getLogin());
					
					preparaSql.execute();
					
					connection.commit();
				}
				
				
		
		}else {
			
				String sqlUpdate ="UPDATE model_login SET nome=?, login=?, senha=?, confirmasenha=?, email=?, dtnascimento=?, perfil=?, situacao=? WHERE id = "+modellogin.getId()+" ";
				PreparedStatement preparaSql = connection.prepareStatement(sqlUpdate);
				
				preparaSql.setString(1, modellogin.getNome());
				preparaSql.setString(2, modellogin.getLogin());
				preparaSql.setString(3, modellogin.getSenha());
				preparaSql.setString(4, modellogin.getConfirmaSenha());
				preparaSql.setString(5, modellogin.getEmail());
				preparaSql.setString(6, modellogin.getDtNascimento());
				preparaSql.setString(7, modellogin.getPerfil());
				preparaSql.setString(8, modellogin.getSituacao());
				preparaSql.executeUpdate();
				connection.commit();
				
				if (modellogin.getFotoUser() != null && !modellogin.getFotoUser().isEmpty()) {
					sqlUpdate = "update model_login set fotouser =?, extensaofotouser=? where id =?";
					
					preparaSql = connection.prepareStatement(sqlUpdate);
					
					preparaSql.setString(1, modellogin.getFotoUser());
					preparaSql.setString(2, modellogin.getExtensaoFotoUser());
					preparaSql.setLong(3, modellogin.getId());
					
					preparaSql.execute();
					
					connection.commit();
				}
			
		}
		
			/*}else
		{
			//mensagem="As senhas digitadas, são diferentes. Por favor verifique.";
		}*/
		
		return this.consultaUsuario(modellogin.getLogin(), userLogado);	
		
		/*}catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	public void deletarUsuario(String idUsuario) throws Exception {
		
		String sqlDeleta = "DELETE FROM public.model_login WHERE id = ? and useradmin is false";
		
		PreparedStatement preparaSql = connection.prepareStatement(sqlDeleta);
		preparaSql.setLong(1, Long.parseLong(idUsuario));
		preparaSql.executeUpdate();
		connection.commit();
	}
	
	public List<ModelLogin> buscarUsuario(String nome, Long userLogado) throws Exception{
		
		List<ModelLogin> busca = new ArrayList<ModelLogin>();
		
		String sqlBuscarUsuario ="select * from model_login where upper(nome)  like upper(?) and useradmin is false and usuario_id = ?";
		PreparedStatement  buscar = connection.prepareStatement(sqlBuscarUsuario);
		buscar.setString(1,"%" + nome +"%");
		buscar.setLong(2, userLogado);
		
		ResultSet resultadoBusca = buscar.executeQuery();
		
		while(resultadoBusca.next()) {
			
			ModelLogin modelologin = new ModelLogin();
			modelologin.setId(resultadoBusca.getLong("id"));
			modelologin.setEmail(resultadoBusca.getString("email"));
			modelologin.setLogin(resultadoBusca.getString("login"));
			modelologin.setNome(resultadoBusca.getString("nome"));
			modelologin.setPerfil(resultadoBusca.getString("perfil"));
			modelologin.setSituacao(resultadoBusca.getString("situacao"));
			
			busca.add(modelologin);
			
			
		}
		
		return busca;
		
		
	}
	
	public List<ModelLogin> listarUsuario(Long userLogado) throws Exception{
		
		List<ModelLogin> busca = new ArrayList<ModelLogin>();
		
		String sqlBuscarUsuario ="select * from model_login where useradmin is false and usuario_id ="+ userLogado +" order by id";
		PreparedStatement  buscar = connection.prepareStatement(sqlBuscarUsuario);
		
		
		ResultSet resultadoBusca = buscar.executeQuery();
		
		while(resultadoBusca.next()) {
			
			ModelLogin modelologin = new ModelLogin();
			modelologin.setId(resultadoBusca.getLong("id"));
			modelologin.setEmail(resultadoBusca.getString("email"));
			modelologin.setLogin(resultadoBusca.getString("login"));
			modelologin.setNome(resultadoBusca.getString("nome"));
			modelologin.setDtNascimento(resultadoBusca.getString("dtNascimento"));
			modelologin.setPerfil(resultadoBusca.getString("perfil"));
			modelologin.setSituacao(resultadoBusca.getString("situacao"));
			
			busca.add(modelologin);
			
			}
			return busca;
		}
	
	public ModelLogin consultaUsuario (String login, Long userLogado) throws Exception {
		
		ModelLogin modellogin = new ModelLogin();
		
		String sqlConsultaUsuario = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id = "+ userLogado +" order by id";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuario);
		
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();
		
		while(resultadoConsultaUsuario.next()) {
			
			modellogin.setId(resultadoConsultaUsuario.getLong("id"));
			modellogin.setNome(resultadoConsultaUsuario.getString("nome"));
			modellogin.setLogin(resultadoConsultaUsuario.getString("login"));
			modellogin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modellogin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modellogin.setEmail(resultadoConsultaUsuario.getString("email"));
			modellogin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modellogin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modellogin.setSituacao(resultadoConsultaUsuario.getString("situacao"));

		}
		
		return modellogin;
		
	}
	
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		
		ModelLogin modellogin = new ModelLogin();
		
		String sqlConsultaUsuario = "select * from model_login where upper(login) = upper('"+login+"') ";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuario);
		
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();
		
		while(resultadoConsultaUsuario.next()) {
			
			modellogin.setId(resultadoConsultaUsuario.getLong("id"));
			modellogin.setNome(resultadoConsultaUsuario.getString("nome"));
			modellogin.setLogin(resultadoConsultaUsuario.getString("login"));
			modellogin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modellogin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modellogin.setEmail(resultadoConsultaUsuario.getString("email"));
			modellogin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modellogin.setUserAdmin(resultadoConsultaUsuario.getBoolean("userAdmin"));
			modellogin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modellogin.setSituacao(resultadoConsultaUsuario.getString("situacao"));
			modellogin.setFotoUser(resultadoConsultaUsuario.getString("fotoUser"));

		}
		
		return modellogin;
		
	}
	
	public ModelLogin consultaUsuario (String login) throws Exception {
		
		ModelLogin modellogin = new ModelLogin();
		
		String sqlConsultaUsuario = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false order by id";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuario);
		
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();
		
		while(resultadoConsultaUsuario.next()) {
			
			modellogin.setId(resultadoConsultaUsuario.getLong("id"));
			modellogin.setNome(resultadoConsultaUsuario.getString("nome"));
			modellogin.setLogin(resultadoConsultaUsuario.getString("login"));
			modellogin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modellogin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modellogin.setEmail(resultadoConsultaUsuario.getString("email"));
			modellogin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modellogin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modellogin.setSituacao(resultadoConsultaUsuario.getString("situacao"));
			modellogin.setFotoUser(resultadoConsultaUsuario.getString("fotoUser"));
		}
		
		return modellogin;
		
	}
	
	public ModelLogin consultaUsuarioID (String id, Long userLogado) throws Exception {
		
		ModelLogin modellogin = new ModelLogin();
		
		String sqlConsultaUsuarioiD = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuarioiD);
		preparaSql.setLong(1, Long.parseLong(id));
		preparaSql.setLong(2, userLogado);
		
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();
		
		while(resultadoConsultaUsuario.next()) {
			
			modellogin.setId(resultadoConsultaUsuario.getLong("id"));
			modellogin.setNome(resultadoConsultaUsuario.getString("nome"));
			modellogin.setLogin(resultadoConsultaUsuario.getString("login"));
			modellogin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modellogin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modellogin.setEmail(resultadoConsultaUsuario.getString("email"));
			modellogin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modellogin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modellogin.setSituacao(resultadoConsultaUsuario.getString("situacao"));
			modellogin.setFotoUser(resultadoConsultaUsuario.getString("fotoUser"));
		}
		
		return modellogin;
		
	}
	
	public boolean validaLogin(String login) throws Exception {
		
		String sqlValidaLogin ="select count(1) > 0 as Existe from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement preparaSql = connection.prepareStatement(sqlValidaLogin);
		ResultSet resultadoValidacao = preparaSql.executeQuery();
		
		resultadoValidacao.next();
		return resultadoValidacao.getBoolean("Existe");
			
	}

}
