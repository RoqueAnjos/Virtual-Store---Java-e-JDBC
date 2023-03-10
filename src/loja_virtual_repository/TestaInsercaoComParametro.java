package loja_virtual_repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.recoverConnection();
		
		connection.setAutoCommit(false);
		
		try {
			PreparedStatement stm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)", 
				Statement.RETURN_GENERATED_KEYS);
		
			adicionarVariavel("SmartTV", "45 polegadas", stm);
			adicionarVariavel("Radio", "Radio de bateria", stm);
			
			connection.commit();
			
			stm.close();
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("ROLLBACK EXECUTADO");
			connection.rollback();
		}
		
	}

	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);
		
		stm.execute();
		
		ResultSet rst = stm.getGeneratedKeys();
		
		while(rst.next()) {
			Integer id = rst.getInt(1);
			System.out.println("O id criado foi: "+id);
		}
	}
}
