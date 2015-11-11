package test;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Contexto;
import model.dao.DAOFactory;
import model.dao.Storable;

public class CreateContexto {

	public static void main(String[] args) {
		
		Contexto contexto = new Contexto("Contexto X", "la descripcion");
		
		Categoria categoriaX = new Categoria("CATEGORIA X", "descripcion X");
		Categoria categoriaY = new Categoria("CATEGORIA y", "descripcion Y");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoriaX);
		categorias.add(categoriaY);
		
		contexto.setCategorias(categorias);
		
		Storable contextoDAO = DAOFactory.getContextoDAO();
		contextoDAO.create(contexto);

	}

}
