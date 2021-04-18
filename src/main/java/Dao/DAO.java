package Dao;

import Model.X;

import java.io.IOException;
import java.sql.*;

public class DAO {
	
	private int maxId;
	private Connection conexao;
	
	public int getMaxId() {
		return maxId;
	}

	public DAO() throws IOException {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "X";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public void add(X usuario) {
		try {  
			this.maxId = (usuario.getId() > this.maxId) ? usuario.getId() : this.maxId;
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO X "
					       + "VALUES ("+usuario.getId()+ ", '" + usuario.getNome() + "', '"  
					       + usuario.getEmail() + "', '" + usuario.getSenha() + "');");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public X get(int idx) {
		X x = new X();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM X WHERE idx = "+ idx);	
			x.setId(rs.getInt("id"));
			x.setNome(rs.getString("nome"));
			x.setSenha(rs.getString("senha"));
			x.setEmail(rs.getString("email"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return x;
	}

	public void update(X usuario) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE X SET Email = '" + usuario.getEmail() + "', senha = '"  
				       + usuario.getSenha() + "', Nome = '" + usuario.getNome() + "'"
					   + " WHERE codigo = " + usuario.getId();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int idx) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM X WHERE idx = " + idx);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public X[] getAll() {
		X[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM X");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new X[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new X(rs.getInt("idx"), rs.getString("nome"), 
	                		                  rs.getString("email"), rs.getString("senha"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}


}