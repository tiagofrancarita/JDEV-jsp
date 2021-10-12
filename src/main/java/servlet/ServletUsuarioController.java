package servlet;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;


@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends HttpServlet {
	
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
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 }
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					 
					 String idUser = request.getParameter("id");
					 
					 daoUsuarioRepository.deletarUsuario(idUser);
					 response.getWriter().write("Excluido com sucesso!");
					 
			 }
			 
			 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				 
				 String nomeBusca = request.getParameter("nomeBusca");
				 
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.buscarUsuario(nomeBusca);
				
				ObjectMapper mapper = new ObjectMapper();
				
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
				//System.out.println(dadosJsonUser);
				
				
			 }else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				 
				 String id = request.getParameter("id");
				 
				 ModelLogin modellogin = daoUsuarioRepository.consultaUsuarioID(id);
				 
				 request.setAttribute("msg", "Usuário(a) em edição.");
				 request.setAttribute("modologin", modellogin);
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
				 
			 }
			 
			 
			 else {
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
		
		ModelLogin modellogin = new ModelLogin();
		
		//modellogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modellogin.setId(id == "" || id == null ? null : Long.parseLong(id));
		modellogin.setNome(nome);
		modellogin.setLogin(login);
		modellogin.setSenha(senha);
		modellogin.setConfirmaSenha(confirmaSenha);
		modellogin.setEmail(email);
		modellogin.setDtNascimento(dtNascimento);
		
			if (daoUsuarioRepository.validaLogin(modellogin.getLogin()) && modellogin.getId() == null) {
				mensagem = "Já existe um usuário(a) com este login, favor tentar o cadastro com um novo login.";
		}else {
			
			if (modellogin.isNovo()) {
				mensagem ="Cadastro realizado com sucesso.";
			}else {
				mensagem ="Atualizado com sucesso.";
			}
			
			modellogin = daoUsuarioRepository.gravarUsuario(modellogin);
		}
		
		request.setAttribute("msg", mensagem);
		request.setAttribute("modologin", modellogin);
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}