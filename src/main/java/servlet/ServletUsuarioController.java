package servlet;

import java.io.IOException;
import java.util.List;
import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig
@WebServlet(urlPatterns = { "/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

    public ServletUsuarioController() {
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {	
			
			 String acao  = request.getParameter("acao");
			 if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				 
				 String idUser = request.getParameter("id");
				 daoUsuarioRepository.deletarUsuario(idUser);
				 request.setAttribute("msg", "Excluido com sucesso!");
				 List<ModelLogin> modelLogins = daoUsuarioRepository.listarUsuario(super.getUserLogado(request));
				 request.setAttribute("modelLogins", modelLogins);
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 }
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					 
					 String idUser = request.getParameter("id");
					 daoUsuarioRepository.deletarUsuario(idUser);
					 response.getWriter().write("Excluido com sucesso!");
			 }
			 
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				 
				 String nomeBusca = request.getParameter("nomeBusca");
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.buscarUsuario(nomeBusca, super.getUserLogado(request));
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				response.getWriter().write(json);
				//System.out.println(dadosJsonUser);
				
			 }else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				 
				 String id = request.getParameter("id");
				 
				 ModelLogin modellogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
				 List<ModelLogin> modelLogins = daoUsuarioRepository.listarUsuario(super.getUserLogado(request));
				 request.setAttribute("modelLogins", modelLogins);
				 
				 request.setAttribute("msg", "Usuário(a) em edição.");
				 request.setAttribute("modologin", modellogin);
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
			 }else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				 
				 List<ModelLogin> modelLogins = daoUsuarioRepository.listarUsuario(super.getUserLogado(request));
				 
				 request.setAttribute("msg", "Usuários carregados");
				 request.setAttribute("modelLogins", modelLogins);
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
			 }
				 
				 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
					 
					 Integer offset = Integer.parseInt(request.getParameter("pagina"));
					 List<ModelLogin> modelLogins = daoUsuarioRepository.listarUsuarioPaginacao(this.getUserLogado(request), offset);
					 request.setAttribute("modelLogins", modelLogins);
				     request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
					 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 }
				 
			 else {
				 List<ModelLogin> modelLogins = daoUsuarioRepository.listarUsuario(super.getUserLogado(request));
				 request.setAttribute("modelLogins", modelLogins);
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 }
			
			}catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}

		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String mensagem ="Cadastro realizado com sucesso";
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String confirmaSenha = request.getParameter("confirmaSenha");
		String email = request.getParameter("email");
		String dtNascimento = request.getParameter("dtNascimento");
		String perfil = request.getParameter("perfil");
		String situacao = request.getParameter("situacao");
		String cep = request.getParameter("cep");
		String logradouro = request.getParameter("logradouro");
		String bairro = request.getParameter("bairro");
		String localidade = request.getParameter("localidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		String complemento = request.getParameter("complemento");
		
		ModelLogin modellogin = new ModelLogin();
		modellogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		//modellogin.setId(id == "" || id == null ? null : Long.parseLong(id));
		modellogin.setNome(nome);
		modellogin.setLogin(login);
		modellogin.setSenha(senha);
		modellogin.setConfirmaSenha(confirmaSenha);
		modellogin.setEmail(email);
		modellogin.setDtNascimento(dtNascimento);
		modellogin.setPerfil(perfil);
		modellogin.setSituacao(situacao);
		modellogin.setCep(cep);
		modellogin.setLogradouro(logradouro);
		modellogin.setBairro(bairro);
		modellogin.setLocalidade(localidade);
		modellogin.setUf(uf);
		modellogin.setNumero(numero);
		modellogin.setComplemento(complemento);
		
			if(ServletFileUpload.isMultipartContent(request)) {
				Part part = request.getPart("fileFoto"); //Pega foto da tela
				byte [] foto = IOUtils.toByteArray(part.getInputStream()); // Converte imagem para byte
				String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
				
				modellogin.setFotoUser(imagemBase64);
				modellogin.setExtensaoFotoUser(part.getContentType().split("\\/")[1]);
				//System.out.println(imagemBase64);
			}
			if (daoUsuarioRepository.validaLogin(modellogin.getLogin()) && modellogin.getId() == null) {
				mensagem = "Já existe um usuário(a) com este login, favor tentar o cadastro com um novo login.";
		}else {
			
			if (modellogin.isNovo()) {
				mensagem ="Cadastro realizado com sucesso.";
			}else {
				mensagem ="Atualizado com sucesso.";
			}
			modellogin = daoUsuarioRepository.gravarUsuario(modellogin, super.getUserLogado(request));
			List<ModelLogin> modelLogins = daoUsuarioRepository.listarUsuario(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
		}
		request.setAttribute("msg", mensagem);
		request.setAttribute("modologin", modellogin);
		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}	
	}
}