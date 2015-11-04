package test;

import model.Etiqueta;
import model.dao.DAOFactory;
import model.dao.SqliteHelper;
import model.dao.Storable;

public class CreateEtiquetaTest {

	public static void main(String[] args) {
		
		Etiqueta etiqueta = new Etiqueta("etiqueta 1", "desc 1");
		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
		etiquetaDAO.create(etiqueta);

	}

}
