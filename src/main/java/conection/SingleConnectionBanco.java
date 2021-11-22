package conection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	// postgres
	// curso-jsp

	private static String banco = "jdbc:postgresql://localhost:5433/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "MNGCC-04xRcxS3EWw7dz";
	private static Connection connection = null;

	public static Connection getConnection() {
		return connection;
	}
	
	static {//Serve para caso a classe for chamada diretamente vai se conectar do mesmo jeito.
		conectar();
	}
	
	public SingleConnectionBanco() {
		//Quando instanciar vai conectar.
		conectar();
	}

	private static void conectar() {

		try {

			if (connection == null) {
				Class.forName("org.postgresql.Driver"); // Carrega os drives para conexao.
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); // Não efetuar operações no banco sem nosso comando.
			}

		} catch (Exception e) {
			e.printStackTrace();// Mostra se existe erro ao conectar ao banco.

		}
	}

}
