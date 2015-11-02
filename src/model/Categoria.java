package model;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private List<Contexto> contextos = new ArrayList<Contexto>();
	
	
	public Categoria() {
		
	}
	
	public Categoria(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public Categoria(String nombre, String descripcion, List<Contexto> contextos) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contextos = contextos;
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

	public List<Contexto> getContextos() {
		return contextos;
	}

	public void setContextos(List<Contexto> contextos) {
		this.contextos = contextos;
	}
	
	public String toString() {
		return "Nombre: " + nombre + " Descripcion: " + descripcion;
	}

}
