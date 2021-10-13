package servlet;

import java.io.IOException;

import dao.DaoLoginRepository;
import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/* Usar somente em versoes abaixo do tomcat 10...
 * Baixar a dependencia javax.servlet-api 4.0.1 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/

/*Servlet ou controller como são chamadas. nomes padrão ServletLoginController*/
@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) /* Mapeamento da URL que vem da tela */
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DaoLoginRepository daoLoginRepository = new DaoLoginRepository();
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();
	

	public ServletLogin() {

	}

	/* Recebe os dados pela url em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String acao = request.getParameter("acao");
		
		if(acao == "" && acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			
			doPost(request, response);
			
		}else {
			request.getSession().invalidate(); // Invalida a sessao
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}
		
		

	}

	/* Recebe os dados enviados por um formulário. */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		
		
		String url = request.getParameter("url");

		try {

			if (login == "" && senha == "") {

				RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "Favor informar os campos Username e Senha.");
				redirecionar.forward(request, response);

			} else {

				ModelLogin modellogin =  new ModelLogin();
				modellogin.setLogin(login);
				modellogin.setSenha(senha);
				
			
				

				if (daoLoginRepository.validarAutenticacao(modellogin)) {
					
					modellogin = daoUsuarioRepository.consultaUsuarioLogado(login);

					request.getSession().setAttribute("usuario", modellogin.getLogin());
					request.getSession().setAttribute("nomeUsuario", modellogin.getNome());
					request.getSession().setAttribute("perfil", modellogin.getPerfil());
					
					
					

					if (url == null || url.equals("null")) {

						url = "principal/principal.jsp";

					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {

					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Username ou senha incorretos, favor verifique!");
					redirecionar.forward(request, response);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
		}

	}

}
