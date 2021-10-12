package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import conection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/*Intercepta todas as requisições que vierem do sistema ou mapeamento. */
@WebFilter(urlPatterns = {"/principal/*"})

public class FilterAutenticacao implements Filter {
	
	private static Connection connection;

    public FilterAutenticacao() {
       
    }

	public void destroy() {
		/*Encerra processos quando o servidor é parado, Encerra a conexao com o BD*/
		try {
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*Intercepta as requisições e envia uma respostas, validação / autenticação
		  Commits ou roolbacks de transações no BD, redirecionamentos especificos*/
		try {
			
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		
		
		String urlParaAutenticar = req.getServletPath(); //Url que está sendo acessada.
		
		//Validar se estar logado senão, redireciona para login
		if(usuarioLogado == null &&
				!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {// não logado
			
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
			request.setAttribute("msg", "Realize o login");
			redireciona.forward(req, response);
			
			return; //Para a execução e redireciona para o login...
		}else {
			chain.doFilter(request, response);
		}
		
		connection.commit();
		
		}catch(Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
		}
		
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		/*Inicia processos e/ou recusos quando o servidor sobe os recursos*/
		// Inicia a conexao com o banco
		connection = SingleConnectionBanco.getConnection();
		
	}

}
