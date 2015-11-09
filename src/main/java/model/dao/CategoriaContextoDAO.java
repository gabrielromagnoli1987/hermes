package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Contexto;

public class CategoriaContextoDAO {
	
	public List<Categoria> retrieveAllCategoriesOfContext(Contexto contexto) {
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM categoria_contexto WHERE contextoID = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, contexto.getId());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	Storable categoriaDAO = DAOFactory.getCategoriaDAO();	    	
	    	
	    	while (resultSet.next()) {	    		 
	    		Categoria categoriaTemp = (Categoria)categoriaDAO.retrieveById(resultSet.getInt("categoriaID"));
	    		categorias.add(categoriaTemp);
	    	}
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return categorias;
	}
	
	public List<Contexto> retrieveAllContextsOfCategory(Categoria categoria) {
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM categoria_contexto WHERE categoriaID = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, categoria.getId());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	Storable contextoDAO = DAOFactory.getContextoDAO();
	    	
	    	while (resultSet.next()) {	    		 
	    		Contexto contextoTemp = (Contexto)contextoDAO.retrieveById(resultSet.getInt("contextoID"));
	    		contextos.add(contextoTemp);
	    	}
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return contextos;
	}

}
