package model;

import java.util.List;

public class Contexto {
	
	private Integer id;
	private String nombre;
	private String descripcion;	
	private List<Categoria> categorias;
	
	
	public Contexto() {
		
	}
	
	public Contexto(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public Contexto(String nombre, String descripcion, List<Categoria> categorias) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categorias = categorias;
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
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public String toString() {
		return "Nombre: " + nombre + " Descripcion: " + descripcion;
	}
	
}
