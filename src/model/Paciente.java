package model;

public class Paciente {
	
	private Integer id;
	private String nombre;
	private String apellido;
	
	
	public Paciente() {
		
	}
	
	public Paciente(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String toString() {
		return "Nombre: " + nombre + " Apellido: " + apellido;
	}

}
