package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.Categoria;
import model.Etiqueta;
import model.Notificacion;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Controller;

public class Monitor extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7580692210516362526L;
	
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private List<Categoria> categorias = new ArrayList<Categoria>();
	private List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
	
	
	public Monitor() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(Monitor.class.getResource("/gui/images/Hermes.jpg")));
		setTitle("Hermes");
		setSize(new Dimension(1024, 768));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Controller controller = new Controller();
		
		Object[] contenidoDeNotificaciones = controller.getContenidosDeNotificaciones();
		etiquetas = controller.getAllEtiquetas();
		Object[] contextos = controller.getContextos();
		Object[] pacientes = controller.getPacientes();
		
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
		);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 12, 455, 281);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Contenido:");
		lblNewLabel.setBounds(12, 28, 78, 15);
		panel_1.add(lblNewLabel);
		
		JLabel lblContexto = new JLabel("Contexto:");
		lblContexto.setBounds(12, 55, 70, 15);
		panel_1.add(lblContexto);
		
		JLabel lblNi = new JLabel("Paciente:");
		lblNi.setBounds(12, 82, 70, 15);
		panel_1.add(lblNi);		
		
		JComboBox<Object> comboBox = new JComboBox<Object>(contenidoDeNotificaciones);
		comboBox.setBounds(100, 23, 127, 20);
		panel_1.add(comboBox);
		
		JComboBox<Object> comboBox_1 = new JComboBox<Object>(contextos);
		comboBox_1.setBounds(100, 50, 127, 20);
		
		
		JComboBox<?> comboBox_2 = new JComboBox<Object>(pacientes);
		comboBox_2.setBounds(100, 77, 127, 20);
		panel_1.add(comboBox_2);
		
		JLabel lblFecha = new JLabel("Fecha/Hora");
		lblFecha.setBounds(12, 117, 93, 20);
		panel_1.add(lblFecha);
		
		JLabel lblDesde = new JLabel("desde:");
		lblDesde.setBounds(100, 120, 70, 15);
		panel_1.add(lblDesde);
		
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(100, 150, 159, 30);
		panel_1.add(datePicker);
				
		JLabel lblNewLhastaabel = new JLabel("hasta:");
		lblNewLhastaabel.setBounds(271, 120, 46, 15);
		panel_1.add(lblNewLhastaabel);
		
		UtilDateModel model_2 = new UtilDateModel();		
		JDatePanelImpl datePanel_2 = new JDatePanelImpl(model_2, properties);
		JDatePickerImpl datePicker_2 = new JDatePickerImpl(datePanel_2, new DateLabelFormatter());
		datePicker_2.setBounds(271, 150, 159, 30);
		panel_1.add(datePicker_2);
		
		JLabel lblNewLabel_1 = new JLabel("Categoria:");
		lblNewLabel_1.setBounds(239, 55, 78, 15);
		panel_1.add(lblNewLabel_1);
		
		JComboBox<?> comboBox_3 = new JComboBox<Object>();
		comboBox_3.setBounds(319, 50, 124, 24);
		
		comboBox_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				categorias = controller.getCategoriasDelContexto(comboBox_1.getSelectedItem());
				comboBox_3.setModel(new DefaultComboBoxModel(categorias.toArray()));
			}
		});
		panel_1.add(comboBox_1);
		
		panel_1.add(comboBox_3);
		
		JLabel lblEtiqueta = new JLabel("Etiqueta:");
		lblEtiqueta.setBounds(12, 204, 70, 15);
		panel_1.add(lblEtiqueta);
		
		JComboBox<?> comboBox_4 = new JComboBox<Object>(etiquetas.toArray());
		comboBox_4.setBounds(100, 199, 127, 20);
		panel_1.add(comboBox_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Etiquetas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(479, 12, 527, 281);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblCrearEtiqueta = new JLabel("Crear Etiqueta:");
		lblCrearEtiqueta.setBounds(12, 26, 115, 15);
		panel_2.add(lblCrearEtiqueta);
		
		textField = new JTextField();
		textField.setBounds(166, 23, 161, 22);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(12, 60, 460, 8);
		panel_2.add(separator);

		JLabel lblEliminarEtiqueta = new JLabel("Eliminar Etiqueta:");
		lblEliminarEtiqueta.setBounds(12, 80, 132, 15);
		panel_2.add(lblEliminarEtiqueta);
		
		JComboBox<?> comboBox_5 = new JComboBox<Object>(etiquetas.toArray());
		comboBox_5.setBounds(166, 80, 161, 22);
		panel_2.add(comboBox_5);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(355, 80, 117, 25);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(12, 124, 460, 8);
		panel_2.add(separator_1);
		
		JLabel lblAignarEtiqueta = new JLabel("Asignar Etiqueta:");
		lblAignarEtiqueta.setBounds(12, 144, 117, 15);
		panel_2.add(lblAignarEtiqueta);
		
		JComboBox<?> comboBox_6 = new JComboBox<Object>(etiquetas.toArray());
		comboBox_6.setBounds(166, 140, 161, 22);
		panel_2.add(comboBox_6);
		
		JButton btnAsignar = new JButton("Asignar");
		btnAsignar.setBounds(355, 144, 117, 25);
		
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(12, 185, 460, 8);
		panel_2.add(separator_2);
		
		JLabel lblRenombrarEtiqueta = new JLabel("Renombrar Etiqueta:");
		lblRenombrarEtiqueta.setBounds(12, 205, 147, 15);
		panel_2.add(lblRenombrarEtiqueta);
		
		JComboBox<?> comboBox_7 = new JComboBox<Object>(etiquetas.toArray());
		comboBox_7.setBounds(166, 205, 161, 22);
		panel_2.add(comboBox_7);
		
		JLabel lblNuevoNombre = new JLabel("Nuevo Nombre:");
		lblNuevoNombre.setBounds(12, 244, 117, 15);
		panel_2.add(lblNuevoNombre);
		
		textField_1 = new JTextField();
		textField_1.setBounds(166, 237, 161, 22);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		
		// RENOMBRAR ETIQUETA
		
		JButton btnRenombrar = new JButton("Renombrar");
		btnRenombrar.setBounds(355, 234, 117, 25);
		
		btnRenombrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(! textField_1.getText().isEmpty()) {
					controller.updateEtiqueta(comboBox_7.getSelectedItem(), textField_1.getText());
					etiquetas = controller.getAllEtiquetas();
					Object[] etiquetasArray = etiquetas.toArray();
					comboBox_4.setModel(new DefaultComboBoxModel(etiquetasArray));
					comboBox_5.setModel(new DefaultComboBoxModel(etiquetasArray));
					comboBox_6.setModel(new DefaultComboBoxModel(etiquetasArray));
					comboBox_7.setModel(new DefaultComboBoxModel(etiquetasArray));
				}
				
			}
		});
		
		panel_2.add(btnRenombrar);
		
		
		// CREAR ETIQUETA
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(355, 21, 117, 25);		
		btnCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(! textField.getText().isEmpty()) {
					controller.crearEtiqueta(textField.getText());
					etiquetas = controller.getAllEtiquetas();
					Object[] etiquetasArray = etiquetas.toArray();
					comboBox_4.setModel(new DefaultComboBoxModel(etiquetasArray));
					comboBox_5.setModel(new DefaultComboBoxModel(etiquetasArray));
					comboBox_6.setModel(new DefaultComboBoxModel(etiquetasArray));
					comboBox_7.setModel(new DefaultComboBoxModel(etiquetasArray));
				}
				
			}
		});
		
		panel_2.add(btnCrear);
		
		
		// ELMINAR ETIQUETA
		
		btnEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.deleteEtiqueta(comboBox_5.getSelectedItem());
				etiquetas = controller.getAllEtiquetas();
				Object[] etiquetasArray = etiquetas.toArray();
				comboBox_4.setModel(new DefaultComboBoxModel(etiquetasArray));
				comboBox_5.setModel(new DefaultComboBoxModel(etiquetasArray));
				comboBox_6.setModel(new DefaultComboBoxModel(etiquetasArray));
				comboBox_7.setModel(new DefaultComboBoxModel(etiquetasArray));

			}
		});
		
		panel_2.add(btnEliminar);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "Notificaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(15, 312, 991, 366);
		panel.add(panel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 28, 967, 326);
		panel_3.add(scrollPane);

		table = new JTable();
		
		String[] headers = new String[] {
				"Fecha/Hora envío", "Contenido", "Contexto", "Categoría", "Paciente", "Etiquetas"
		};
		
		Object[][] data = controller.getTableData(headers, null);
		
		table.setModel(new DefaultTableModel(data, headers));

		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		
		
		// ASIGNAR ETIQUETA
		
		btnAsignar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String notificacionContenido = (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
				Notificacion notificacionTemp = new Notificacion();
				notificacionTemp.setText(notificacionContenido);
				Etiqueta etiqueta = (Etiqueta) comboBox_6.getSelectedItem();
				controller.asignarEtiqueta(etiqueta, notificacionTemp);
			}
			
		});
		
		panel_2.add(btnAsignar);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(12, 244, 215, 25);
		
		btnFiltrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Object[] filters = new Object[7];
				filters[0] = comboBox.getSelectedItem(); // contenido
				filters[1] = comboBox_1.getSelectedItem(); // contexto
				filters[2] = comboBox_3.getSelectedItem(); // categoria
				filters[3] = comboBox_2.getSelectedItem(); // paciente
				filters[4] = model.getValue(); // fechaEnvio
				filters[5] = model_2.getValue(); // fechaRecepcion
				filters[6] = comboBox_4.getSelectedItem(); // etiqueta
				
				Object[][] data = controller.getTableData(headers, filters);
				table.setModel(new DefaultTableModel(data, headers));
			}
		});
		
		panel_1.add(btnFiltrar);
		
		JButton btnMostrarTodo = new JButton("Mostrar todo");
		btnMostrarTodo.setBounds(239, 244, 204, 25);
		
		btnMostrarTodo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
								
				Object[][] data = controller.getTableData(headers, null);
				
				table.setModel(new DefaultTableModel(data, headers));

			}
		});
		
		panel_1.add(btnMostrarTodo);
		
	}
	
	
	public class DateLabelFormatter extends AbstractFormatter {

        /**
		 * 
		 */
		private static final long serialVersionUID = -8155907680201120364L;
		
		private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
