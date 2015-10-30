package model;

import java.util.Date;
import java.util.List;

public class Notificacion {
	
	private Integer id;
	private String text;
	private Date fechaEnvio;
	private Date fechaRecepcion;
	private Paciente paciente;
	private Contexto contexto;
	private List<Etiqueta> etiquetas;
	
	
	public Notificacion() {
		
	}
	
	public Notificacion(String text, Date fechaEnvio, Date fechaRecepcion, Paciente paciente, Contexto contexto) {
		this.text = text;
		this.fechaEnvio = fechaEnvio;
		this.fechaRecepcion = fechaRecepcion;
		this.paciente = paciente;
		this.contexto = contexto;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Contexto getContexto() {
		return contexto;
	}

	public void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}

	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}
	
	public String toString() {
		return "Nombre: " + text + " Fecha de envio: " + fechaEnvio + " Fecha de recepcion: " + fechaRecepcion + 
				" Paciente: " + paciente.toString() + " Contexto: " + contexto.toString();
	}
	
}
