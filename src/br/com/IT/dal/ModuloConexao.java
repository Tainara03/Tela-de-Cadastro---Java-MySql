package br.com.IT.dal;

import java.sql.*;

public class ModuloConexao {
	//método responsável por estabelecer a conexão com o banco
	public static Connection conector() {
		
		Connection conexao = null;
		//chamando o driver do arquivo importado
		String driver = "com.mysql.jdbc.Driver";
		//armazenando infos do banco
		String url = "jdbc:mysql://localhost:3306/db_control";
		String user = "root";
		String password = "";
		//testando conexão com o banco
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url,user,password);
			return conexao;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
