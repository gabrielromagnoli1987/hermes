package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Contexto;

public class ContextoDAO implements Storable<Contexto> {

	@Override
	public boolean create(Contexto contexto) {
		
		boolean result = false;
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "INSERT INTO contexto (nombre, descripcion) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	preparedStatement.setString(2, contexto.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    		
	    		List<Categoria> categorias = contexto.getCategorias();
	    		
	    		if (! categorias.isEmpty()) {
	    			
	    			Contexto contextoDB = this.retrieve(contexto);
	    			
	    			CategoriaDAO categoriaDAO = new CategoriaDAO();
	    			
	    			for (Categoria categoria : categorias) {
						result = result && categoriaDAO.create(categoria); // si ya esta creada, la excepcion se captura en el categoriaDAO
						Categoria categoriaDB = categoriaDAO.retrieve(categoria);
						result = result && saveOnMapTable(categoriaDB.getId(), contextoDB.getId()); // agregar la regla UNIQUE en la base a la combinacion de los ID
					}
	    			
//	    			String queryIdCategoria = "SELECT id FROM categoria WHERE nombre = ?";
//	    			preparedStatement = connection.prepareStatement(queryIdCategoria);
//	    			
//	    			String queryManyToMany = "INSERT INTO categoria_contexto SELECT categoriaID, contextoID ";
//	    			
//	    			for (Contexto contexto : contextos) {
//	    				queryManyToMany += queryManyToMany.format("UNION ALL SELECT %d, %d", categoria.getId(), contexto.getId());
//	    			}
//	    			
//	    			preparedStatement = connection.prepareStatement(queryManyToMany);
//	    			if (preparedStatement.executeUpdate() > 0) {
//	    				result = true;
//	    			}
		    	}
	    	}
	    	
	    	connection.close();
	    	return result;
	    	
	    } catch(ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
	    }
	    
	    return result;
	}
	
	private boolean saveOnMapTable(Integer categoriaID, Integer contextoID) {
		
		boolean result = false;
		
		try {
			Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "INSERT INTO categoria_contexto (categoriaID, contextoID) VALUES (?, ?)";
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, categoriaID);
	    	preparedStatement.setInt(2, contextoID);
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	result = true;
	    	
		} catch(ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
	    }
		
		return result;
		
	}

	@Override
	public Contexto retrieve(Contexto contexto) {
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "SELECT nombre, descripcion FROM contexto WHERE nombre = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		contexto.setNombre(resultSet.getString("nombre"));
	    		contexto.setDescripcion(resultSet.getString("descripcion"));
	    	}
	    	
	    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	    	if(generatedKeys.next()) {
	    		contexto.setId(generatedKeys.getInt(1));
            }
	    	
	    	connection.close();	    	
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return contexto;
	}
	
	@Override
	public List<Contexto> retrieveAll() {
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "SELECT * FROM contexto";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Contexto contexto = new Contexto();
	    		contexto.setId(resultSet.getInt("id"));
	    		contexto.setNombre(resultSet.getString("nombre"));
	    		contexto.setDescripcion(resultSet.getString("descripcion"));
	    		contextos.add(contexto);
	    	}
	    	
	    	connection.close();	    	
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return contextos;
	}

	@Override
	public boolean update(Contexto contexto) {
		
		boolean result = false;
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "UPDATE contexto SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	preparedStatement.setString(2, contexto.getDescripcion());	    	
	    	preparedStatement.setInt(3, contexto.getId());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

	@Override
	public boolean delete(Contexto contexto) {
		
		boolean result = false;
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	// necesario para realizar el delete en cascada
	    	String enableForeingKeys = "PRAGMA foreign_keys = ON";
	    	PreparedStatement preparedStatement = connection.prepareStatement(enableForeingKeys);
	    	preparedStatement.executeUpdate();
	    	
	    	String query = "DELETE FROM contexto WHERE nombre = ?";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {	    		
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

}
