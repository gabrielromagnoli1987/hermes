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
	
	// CategoriaContextoDAO es utilizado de forma interna por lo que no se encuentra en la DAOFactory	
	
	public List<Categoria> retrieveAllCategoriesOfContext(Contexto contexto) {
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM categoria_contexto WHERE contextoID = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, contexto.getId());
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	Storable categoriaDAO = DAOFactory.getCategoriaDAO();	    	
	    	
	    	while (resultSet.next()) {
	    		Categoria categoriaTemp = new Categoria();
	    		categoriaTemp.setId(resultSet.getInt("categoriaID"));
	    		categoriaTemp = (Categoria)categoriaDAO.retrieveById(categoriaTemp);
	    		categorias.add(categoriaTemp);
	    	}
	    	
	    	resultSet.close();
	    	preparedStatement.close();
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return categorias;
	}
	
	public List<Contexto> retrieveAllContextsOfCategory(Categoria categoria) {
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM categoria_contexto WHERE categoriaID = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, categoria.getId());
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	Storable contextoDAO = DAOFactory.getContextoDAO();
	    	
	    	while (resultSet.next()) {
	    		Contexto contextoTemp = new Contexto();
	    		contextoTemp.setId(resultSet.getInt("contextoID"));
	    		contextoTemp = (Contexto)contextoDAO.retrieveById(contextoTemp);
	    		contextos.add(contextoTemp);
	    	}
	    	
	    	resultSet.close();
	    	preparedStatement.close();
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return contextos;
	}

}
