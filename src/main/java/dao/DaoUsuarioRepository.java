package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoUsuarioRepository {
	
	private Connection connection;
	
	public DaoUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
	}
	
public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
		
	}
	
	public ModelLogin gravarUsuario(ModelLogin modellogin, Long userLogado) throws Exception {
		//try {
		//String mensagem ="Cadastro realizado com sucesso";
			if (modellogin.isNovo()) {
				String sqlSalvar ="INSERT INTO model_login(nome, login, senha, confirmasenha, email, dtNascimento, usuario_id, perfil, situacao, cep, logradouro, bairro, localidade, uf, numero, complemento) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
				preparaSql.setString(10, modellogin.getCep());
				preparaSql.setString(11, modellogin.getLogradouro());
				preparaSql.setString(12, modellogin.getBairro());
				preparaSql.setString(13, modellogin.getLocalidade());
				preparaSql.setString(14, modellogin.getUf());
				preparaSql.setString(15, modellogin.getNumero());
				preparaSql.setString(16, modellogin.getComplemento());
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
				String sqlUpdate ="UPDATE model_login SET nome=?, login=?, senha=?, confirmasenha=?, email=?, dtnascimento=?, perfil=?, situacao=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, complemento=? WHERE id = "+modellogin.getId()+" ";
				PreparedStatement preparaSql = connection.prepareStatement(sqlUpdate);
				preparaSql.setString(1, modellogin.getNome());
				preparaSql.setString(2, modellogin.getLogin());
				preparaSql.setString(3, modellogin.getSenha());
				preparaSql.setString(4, modellogin.getConfirmaSenha());
				preparaSql.setString(5, modellogin.getEmail());
				preparaSql.setString(6, modellogin.getDtNascimento());
				preparaSql.setString(7, modellogin.getPerfil());
				preparaSql.setString(8, modellogin.getSituacao());
				preparaSql.setString(9, modellogin.getCep());
				preparaSql.setString(10, modellogin.getLogradouro());
				preparaSql.setString(11, modellogin.getBairro());
				preparaSql.setString(12, modellogin.getLocalidade());
				preparaSql.setString(13, modellogin.getUf());
				preparaSql.setString(14, modellogin.getNumero());
				preparaSql.setString(15, modellogin.getComplemento());
				preparaSql.executeUpdate();
				connection.commit();
				
				if (modellogin.getFotoUser() != null && !modellogin.getFotoUser().isEmpty()) {
					sqlUpdate = "update model_login set fotouser =?, extensaofotouser=? where login =?";
					preparaSql = connection.prepareStatement(sqlUpdate);
					preparaSql.setString(1, modellogin.getFotoUser());
					preparaSql.setString(2, modellogin.getExtensaoFotoUser());
					preparaSql.setString(3, modellogin.getLogin());
					preparaSql.execute();
					connection.commit();
				}
		}
		return this.consultaUsuario(modellogin.getLogin(), userLogado);	
	}
	
	public void deletarUsuario(String idUsuario) throws Exception {
		
		String sqlDeleta = "DELETE FROM public.model_login WHERE id = ? and useradmin is false";
		PreparedStatement preparaSql = connection.prepareStatement(sqlDeleta);
		preparaSql.setLong(1, Long.parseLong(idUsuario));
		preparaSql.executeUpdate();
		connection.commit();
	}
	
public int buscarUsuarioPaginaPaginacao(String nome, Long userLogado) throws Exception{
		
		
		String sqlBuscarUsuario ="select count(1) as total from model_login where upper(nome)  like upper(?) and useradmin is false and usuario_id = ?";
		
		PreparedStatement  buscar = connection.prepareStatement(sqlBuscarUsuario);
		buscar.setString(1, "%" + nome +"%" );
		buscar.setLong(2, userLogado);
		
		ResultSet resultadoBusca = buscar.executeQuery();
		
		resultadoBusca.next();
		
		Double cadastros = resultadoBusca.getDouble("total");
		Double porPagina = 5.0;
		Double pagina  = cadastros / porPagina;
		
		Double resto = pagina % 2;
		if (resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
		}

	public List<ModelLogin> buscarUsuario(String nome, Long userLogado) throws Exception{
		
		List<ModelLogin> busca = new ArrayList<ModelLogin>();
		String sqlBuscarUsuario ="select * from model_login where upper(nome)  like upper(?) and useradmin is false and usuario_id = ? limit 5 ";
		PreparedStatement  buscar = connection.prepareStatement(sqlBuscarUsuario);
		buscar.setString(1, "%" + nome +"%" );
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
			modelologin.setCep(resultadoBusca.getString("cep"));
			modelologin.setLogradouro(resultadoBusca.getString("logradouro"));
			modelologin.setBairro(resultadoBusca.getString("bairro"));
			modelologin.setLocalidade(resultadoBusca.getString("localidade"));
			modelologin.setUf(resultadoBusca.getString("uf"));
			modelologin.setNumero(resultadoBusca.getString("numero"));
			modelologin.setComplemento(resultadoBusca.getString("complemento"));
			busca.add(modelologin);
		}
		return busca;
	}
	public int totalPagina(Long userLogado) throws SQLException {
			
			String sqlPagina="select count(1) as total from model_login  where usuario_id = " + userLogado;
			PreparedStatement statement  = connection.prepareStatement(sqlPagina);
			
			ResultSet resultado = statement.executeQuery();
			
			resultado.next();
			Double cadastros = resultado.getDouble("total");
			Double porPagina = 5.0;
			Double pagina  = cadastros / porPagina;
			Double resto = pagina % 2;
			if (resto > 0) {
				pagina ++;
			}
			return pagina.intValue();
			}
	
	public List<ModelLogin> listarUsuarioPaginacao(Long userLogado, Integer offset) throws Exception{
		
		List<ModelLogin> busca = new ArrayList<ModelLogin>();
		String sqlBuscarUsuario ="select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by id offset "+offset+" limit 5 ";
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
	
	public List<ModelLogin> listarUsuario(Long userLogado) throws Exception{
		
		List<ModelLogin> busca = new ArrayList<ModelLogin>();
		String sqlBuscarUsuario ="select * from model_login where useradmin is false and usuario_id =" + userLogado + "  limit 5 ";
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
			modelologin.setCep(resultadoBusca.getString("cep"));
			modelologin.setLogradouro(resultadoBusca.getString("logradouro"));
			modelologin.setBairro(resultadoBusca.getString("bairro"));
			modelologin.setLocalidade(resultadoBusca.getString("localidade"));
			modelologin.setUf(resultadoBusca.getString("uf"));
			modelologin.setNumero(resultadoBusca.getString("numero"));
			modelologin.setComplemento(resultadoBusca.getString("complemento"));
			busca.add(modelologin);
			
			}
		
			return busca;
		}
	
	public ModelLogin consultaUsuario (String login, Long userLogado) throws Exception {
		
		ModelLogin modelologin = new ModelLogin();
		String sqlConsultaUsuario = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id = "+ userLogado +" limit 5 ";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuario);
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();

		while(resultadoConsultaUsuario.next()) {
			
			modelologin.setId(resultadoConsultaUsuario.getLong("id"));
			modelologin.setNome(resultadoConsultaUsuario.getString("nome"));
			modelologin.setLogin(resultadoConsultaUsuario.getString("login"));
			modelologin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modelologin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modelologin.setEmail(resultadoConsultaUsuario.getString("email"));
			modelologin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modelologin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modelologin.setSituacao(resultadoConsultaUsuario.getString("situacao"));
			modelologin.setCep(resultadoConsultaUsuario.getString("cep"));
			modelologin.setLogradouro(resultadoConsultaUsuario.getString("logradouro"));
			modelologin.setBairro(resultadoConsultaUsuario.getString("bairro"));
			modelologin.setLocalidade(resultadoConsultaUsuario.getString("localidade"));
			modelologin.setUf(resultadoConsultaUsuario.getString("uf"));
			modelologin.setNumero(resultadoConsultaUsuario.getString("numero"));
			modelologin.setComplemento(resultadoConsultaUsuario.getString("complemento"));

		}
		
		return modelologin;
	}
	
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		
		ModelLogin modelologin = new ModelLogin();
		String sqlConsultaUsuario = "select * from model_login where upper(login) = upper('"+login+"') ";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuario);
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();
		
		while(resultadoConsultaUsuario.next()) {
			
			modelologin.setId(resultadoConsultaUsuario.getLong("id"));
			modelologin.setNome(resultadoConsultaUsuario.getString("nome"));
			modelologin.setLogin(resultadoConsultaUsuario.getString("login"));
			modelologin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modelologin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modelologin.setEmail(resultadoConsultaUsuario.getString("email"));
			modelologin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modelologin.setUserAdmin(resultadoConsultaUsuario.getBoolean("userAdmin"));
			modelologin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modelologin.setSituacao(resultadoConsultaUsuario.getString("situacao"));
			modelologin.setFotoUser(resultadoConsultaUsuario.getString("fotoUser"));
			modelologin.setCep(resultadoConsultaUsuario.getString("cep"));
			modelologin.setLogradouro(resultadoConsultaUsuario.getString("logradouro"));
			modelologin.setBairro(resultadoConsultaUsuario.getString("bairro"));
			modelologin.setLocalidade(resultadoConsultaUsuario.getString("localidade"));
			modelologin.setUf(resultadoConsultaUsuario.getString("uf"));
			modelologin.setNumero(resultadoConsultaUsuario.getString("numero"));
			modelologin.setComplemento(resultadoConsultaUsuario.getString("complemento"));
		}
		
		return modelologin;
	}
	
	public ModelLogin consultaUsuario (String login) throws Exception {
		
		ModelLogin modelologin = new ModelLogin();
		String sqlConsultaUsuario = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuario);
		ResultSet resultadoConsultaUsuario = preparaSql.executeQuery();
		
		while(resultadoConsultaUsuario.next()) {
			
			modelologin.setId(resultadoConsultaUsuario.getLong("id"));
			modelologin.setNome(resultadoConsultaUsuario.getString("nome"));
			modelologin.setLogin(resultadoConsultaUsuario.getString("login"));
			modelologin.setSenha(resultadoConsultaUsuario.getString("senha"));
			modelologin.setConfirmaSenha(resultadoConsultaUsuario.getString("confirmaSenha"));
			modelologin.setEmail(resultadoConsultaUsuario.getString("email"));
			modelologin.setDtNascimento(resultadoConsultaUsuario.getString("dtNascimento"));
			modelologin.setPerfil(resultadoConsultaUsuario.getString("perfil"));
			modelologin.setSituacao(resultadoConsultaUsuario.getString("situacao"));
			modelologin.setFotoUser(resultadoConsultaUsuario.getString("fotoUser"));
			modelologin.setCep(resultadoConsultaUsuario.getString("cep"));
			modelologin.setLogradouro(resultadoConsultaUsuario.getString("logradouro"));
			modelologin.setBairro(resultadoConsultaUsuario.getString("bairro"));
			modelologin.setLocalidade(resultadoConsultaUsuario.getString("localidade"));
			modelologin.setUf(resultadoConsultaUsuario.getString("uf"));
			modelologin.setNumero(resultadoConsultaUsuario.getString("numero"));
			modelologin.setComplemento(resultadoConsultaUsuario.getString("complemento"));
		}
		
		return modelologin;
		
	}
public ModelLogin consultaUsuarioID (Long id) throws Exception {
		
		ModelLogin modellogin = new ModelLogin();
		String sqlConsultaUsuarioiD = "select * from model_login where id = ? and useradmin is false";
		PreparedStatement preparaSql = connection.prepareStatement(sqlConsultaUsuarioiD);
		preparaSql.setLong(1, (id));
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
			modellogin.setExtensaoFotoUser(resultadoConsultaUsuario.getString("extensaoFotoUser"));
			modellogin.setCep(resultadoConsultaUsuario.getString("cep"));
			modellogin.setLogradouro(resultadoConsultaUsuario.getString("logradouro"));
			modellogin.setBairro(resultadoConsultaUsuario.getString("bairro"));
			modellogin.setLocalidade(resultadoConsultaUsuario.getString("localidade"));
			modellogin.setUf(resultadoConsultaUsuario.getString("uf"));
			modellogin.setNumero(resultadoConsultaUsuario.getString("numero"));
			modellogin.setComplemento(resultadoConsultaUsuario.getString("complemento"));
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
			modellogin.setExtensaoFotoUser(resultadoConsultaUsuario.getString("extensaoFotoUser"));
			modellogin.setCep(resultadoConsultaUsuario.getString("cep"));
			modellogin.setLogradouro(resultadoConsultaUsuario.getString("logradouro"));
			modellogin.setBairro(resultadoConsultaUsuario.getString("bairro"));
			modellogin.setLocalidade(resultadoConsultaUsuario.getString("localidade"));
			modellogin.setUf(resultadoConsultaUsuario.getString("uf"));
			modellogin.setNumero(resultadoConsultaUsuario.getString("numero"));
			modellogin.setComplemento(resultadoConsultaUsuario.getString("complemento"));
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
