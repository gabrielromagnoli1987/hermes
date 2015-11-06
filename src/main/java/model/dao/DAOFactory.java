package model.dao;

public class DAOFactory {
	
	// Al devolver un tipo interface, se podria cambiar la implementacion de los metodos
	// y seria transparente para quien utiliza el factory
	
	public static Storable<?> getCategoriaDAO() {
		
		return new CategoriaDAO();
		
	}
	
	public static Storable<?> getContextoDAO() {
		
		return new ContextoDAO();
		
	}
	
	public static Storable<?> getEtiquetaDAO() {
		
		return new EtiquetaDAO();
		
	}
	
	public static Storable<?> getNotificacionDAO() {
		
		return new NotificacionDAO();
		
	}
	
	public static Storable<?> getPacienteDAO() {
		
		return new PacienteDAO();
		
	}
	

}
