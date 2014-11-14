package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.mxrck.autocompleter.TextAutoCompleter;

import negocio.IProveedorManager;
import negocio.IRepuestoManager;
import negocio.ProveedorManager;
import negocio.RepuestoManager;

import persistencia.Proveedor;
import persistencia.Repuesto;
import persistencia.Servicio;

public class AgregarRepuesto extends JDialog {

	private static final long serialVersionUID = 1L;

	JLabel jLabelNombre = null;
	JLabel jLabelPrecioUnitario = null;
	JLabel jLabelCantidad = null;
	JLabel jLabelObservaciones = null;
	JLabel jLabelProveedor = null;

	JTextFieldUpperCased jTextFieldNombre = null;
	JFormattedTextField jTextFieldPrecioUnitario = null;
	JFormattedTextField jTextFieldCantidad = null;
	JComboBox<String> jComboBoxProveedor = null;
	JTextArea jTextAreaObservaciones = null;
	JScrollPane jScrollPaneObservaciones = null;

	JPanel jPanelAgregarRepuesto = null;

	DefaultTableModel dtmRepuestosCopia = null;

	// Formats to format and parse numbers
	private NumberFormat amountDisplayFormat = null;
	private NumberFormat amountEditFormat = null;
	private NumberFormat formatterDecimal = DecimalFormat.getInstance(new Locale("es", "AR"));


	JScrollBar jscrollbar = null;

	JButton jButtonAceptar = null;
	JButton jButtonCancelar = null;

	Servicio servicio = null;
	List<Proveedor> listaProveedores = null;
	IProveedorManager proveedorManager = null;

	DetallarServicio detallarServicio = null;

	ResourceLoader resourceLoader = new ResourceLoader();

	public AgregarRepuesto(JFrame padre, String titulo, String titleBorder,
			DetallarServicio detallarServicio, Servicio servicio) {
		super(padre, titulo, true);

		this.setPreferredSize(new Dimension(418, 348));
		this.getContentPane().setLayout(new BorderLayout());
		this.detallarServicio = detallarServicio;

		this.servicio = servicio;

		// agrego icono a la ventana
		ImageIcon ImageIcon = new ImageIcon(
				resourceLoader.load("/images/jframe-icon.png"));
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);

		jPanelAgregarRepuesto = new JPanel();

		TitledBorder titledBorder;
		titledBorder = BorderFactory.createTitledBorder(titleBorder);
		titledBorder.setBorder(BorderFactory.createLineBorder(new Color(100,
				150, 100)));
		titledBorder.setTitleColor(new Color(0, 0, 128));
		jPanelAgregarRepuesto.setBorder(titledBorder);

		jPanelAgregarRepuesto
				.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
		jPanelAgregarRepuesto.setPreferredSize(new Dimension(410, 319));

		inicializarVariables();
		cerrarEsc();

		JPanel jPanelNombre = new JPanel();
		jPanelNombre.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		jPanelNombre.setPreferredSize(new Dimension(400, 100));
		jPanelNombre.add(jLabelProveedor);
		jPanelNombre.add(jComboBoxProveedor);
		jPanelNombre.add(jLabelNombre);
		jPanelNombre.add(jTextFieldNombre);

		jPanelAgregarRepuesto.add(jPanelNombre);

		JPanel jPanelPrecioCantidadProveedor = new JPanel();
		jPanelPrecioCantidadProveedor.setLayout(new GridBagLayout());
		jPanelPrecioCantidadProveedor.setPreferredSize(new Dimension(400, 30));
		GridBagConstraints b = new GridBagConstraints();

		b.gridy = 0;
		b.gridx = 2;
		b.insets = new Insets(0, -122, 0, 0);
		jPanelPrecioCantidadProveedor.add(jLabelPrecioUnitario, b);
		b.gridy = 0;
		b.gridx = 3;
		b.insets = new Insets(0, -50, 0, 0);
		jPanelPrecioCantidadProveedor.add(jTextFieldPrecioUnitario, b);
		b.gridy = 0;
		b.gridx = 4;
		b.insets = new Insets(0, 20, 0, 0);
		jPanelPrecioCantidadProveedor.add(jLabelCantidad, b);
		b.gridy = 0;
		b.gridx = 5;
		b.insets = new Insets(0, -18, 0, 0);
		jPanelPrecioCantidadProveedor.add(jTextFieldCantidad, b);

		jPanelAgregarRepuesto.add(jPanelPrecioCantidadProveedor);
		jPanelAgregarRepuesto.add(jLabelObservaciones);

		JPanel jPanelObservaciones = new JPanel();
		jPanelObservaciones.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jPanelObservaciones.setPreferredSize(new Dimension(400, 80));
		jPanelObservaciones.add(jLabelObservaciones);
		jPanelObservaciones.add(jScrollPaneObservaciones);

		jPanelAgregarRepuesto.add(jPanelObservaciones);

		JPanel jPanelBotones = new JPanel();
		jPanelBotones.setLayout(new GridBagLayout());

		GridBagConstraints d = new GridBagConstraints();

		d.gridy = 0;
		d.gridx = 0;
		d.insets = new Insets(0, 0, 0, 0);
		jPanelBotones.add(jButtonAceptar, d);
		d.gridy = 0;
		d.gridx = 1;
		d.insets = new Insets(0, 20, 0, 12);
		jPanelBotones.add(jButtonCancelar, d);

		jPanelAgregarRepuesto.add(jPanelBotones);

		this.getContentPane().add(jPanelAgregarRepuesto, BorderLayout.NORTH);
		this.getRootPane().setDefaultButton(jButtonAceptar);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	OperationDelegate aceptarAgregarRepuesto = new OperationDelegate() {
		public void invoke() {
			aceptarAgregarRepuesto();
		}
	};

	OperationDelegate cancelarAgregarRepuesto = new OperationDelegate() {
		public void invoke() {
			cancelarAgregarRepuesto();
		}
	};

	private void inicializarVariables() {
		Dimension dimensionLabel = new Dimension(75, 25);
		proveedorManager = new ProveedorManager();

		// hace que se seleccione el jComboBoxProveedor al iniciar la ventana
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jComboBoxProveedor.requestFocusInWindow();
			}
		});

		jLabelNombre = new JLabel("Nombre: ");
		jLabelNombre.setPreferredSize(new Dimension(53, 25));
		jTextFieldNombre = new JTextFieldUpperCased();
		jTextFieldNombre.setPreferredSize(new Dimension(327, 25));
		jTextFieldNombre.setText(" < Óptica izquierda delantera >");
		jTextFieldNombre.setForeground(Color.GRAY);
		jTextFieldNombre.addFocusListener(new JTextFieldFocusListener(
				jTextFieldNombre));
		jTextFieldNombre.addKeyListener(new JTextFieldKeyListener(
				jTextFieldNombre));

		setUpFormats();
		jLabelPrecioUnitario = new JLabel("Precio unitario: ");
		jLabelPrecioUnitario.setPreferredSize(new Dimension(140, 25));
		jTextFieldPrecioUnitario = new JFormattedTextField(
				new DefaultFormatterFactory(new NumberFormatter(
						amountDisplayFormat), new NumberFormatter(
						amountDisplayFormat), new NumberFormatter(
						amountEditFormat)));
		jTextFieldPrecioUnitario.setPreferredSize(new Dimension(65, 25));
		jTextFieldPrecioUnitario.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextFieldPrecioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent e) {
						int key = e.getKeyCode();
						// cambia el punto (del teclado numerico) por la coma
						if (key == 110) {
							javax.swing.SwingUtilities.invokeLater(new Runnable() {
										public void run() {
											jTextFieldPrecioUnitario.setText((jTextFieldPrecioUnitario.getText()).substring(0,jTextFieldPrecioUnitario.getText().length() - 1)+ ",");
										}
							});
						}
					}
				});

		jLabelCantidad = new JLabel("Cantidad: ");
		jLabelCantidad.setPreferredSize(dimensionLabel);

		jTextFieldCantidad = new JFormattedTextField(
				(DecimalFormat) formatterDecimal);
		jTextFieldCantidad.setFont(new Font("Dialog", Font.BOLD, 11));
		jTextFieldCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				int key = e.getKeyCode();
				// cambia el punto (del teclado numerico) por la coma
				if (key == 110) {
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							jTextFieldCantidad.setText((jTextFieldCantidad
									.getText()).substring(0, jTextFieldCantidad
									.getText().length() - 1)
									+ ",");
						}
					});
				}
			}
		});
		jTextFieldCantidad.setPreferredSize(new Dimension(35, 25));
		jTextFieldCantidad.setText("1");

		jLabelObservaciones = new JLabel("Observaciones: ");
		jLabelObservaciones.setPreferredSize(new Dimension(380, 25));

		jTextAreaObservaciones = new JTextArea();
		jTextAreaObservaciones.setFont(new Font("Dialog", Font.BOLD, 11));
		((AbstractDocument) jTextAreaObservaciones.getDocument())
				.setDocumentFilter(new UppercaseDocumentFilter());
		jTextAreaObservaciones.setFocusTraversalKeysEnabled(false);
		jTextAreaObservaciones.setLineWrap(true);
		jTextAreaObservaciones.setWrapStyleWord(true);
		jTextAreaObservaciones
				.setText(" < Ingrese observaciones que considere necesarias >");
		jTextAreaObservaciones.setForeground(Color.GRAY);
		jTextAreaObservaciones.addFocusListener(new JTextAreaFocusListener(
				jTextAreaObservaciones));
		jTextAreaObservaciones.addKeyListener(new jTextAreaKeyListener(
				jTextAreaObservaciones));

		jScrollPaneObservaciones = new JScrollPane(jTextAreaObservaciones);
		jScrollPaneObservaciones.setPreferredSize(new Dimension(380, 50));

		jLabelProveedor = new JLabel("Proveedor: ");
		jLabelProveedor.setPreferredSize(new Dimension(68, 25));

		cargarProveedores();

		// creo el botón aceptar con un ícono
		ImageIcon imageIconOk = new ImageIcon(
				resourceLoader.load("/images/menu/ok-icon.png"));
		jButtonAceptar = new JButton(" Aceptar", imageIconOk);
		jButtonAceptar.setPreferredSize(new Dimension(100, 30));
		jButtonAceptar.addActionListener(new JButtonActionListener(
				aceptarAgregarRepuesto));
		jButtonAceptar.addKeyListener(new JButtonKeyListener(
				aceptarAgregarRepuesto));

		// creo el botón cancelar con un ícono
		ImageIcon imageIconCancel = new ImageIcon(
				resourceLoader.load("/images/menu/close-icon.png"));
		jButtonCancelar = new JButton(" Cancelar", imageIconCancel);
		jButtonCancelar.setPreferredSize(new Dimension(100, 30));
		jButtonCancelar.addActionListener(new JButtonActionListener(
				cancelarAgregarRepuesto));
		jButtonCancelar.addKeyListener(new JButtonKeyListener(
				cancelarAgregarRepuesto));

		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarRepuesto.this.dispose();
			}
		});
	}

	// Create and set up number formats. These objects also
	// parse numbers input by user.
	private void setUpFormats() {
		amountDisplayFormat = NumberFormat.getCurrencyInstance(new Locale("es","AR"));
		// amountDisplayFormat.setMinimumFractionDigits(2);
		amountEditFormat = NumberFormat.getNumberInstance(new Locale("es", "AR"));
	}

	private boolean characterVaryingExceeded() {
		int cont = 0;

		if (jTextFieldNombre.getText().trim().length() > 75) {
			jTextFieldNombre.setBorder(BorderFactory.createLineBorder(Color.RED));
			jTextFieldNombre.addFocusListener(new JTextFieldFocusListener(jTextFieldNombre));
			cont++;
		}

		if (jTextFieldPrecioUnitario.getText().trim().length() > 10) {
			jTextFieldPrecioUnitario.setBorder(BorderFactory.createLineBorder(Color.RED));
			jTextFieldPrecioUnitario.addFocusListener(new JTextFieldFocusListener(jTextFieldPrecioUnitario));
			cont++;
		}
		
		if (jTextFieldCantidad.getText().trim().length() > 4) {
			jTextFieldCantidad.setBorder(BorderFactory.createLineBorder(Color.RED));
			jTextFieldCantidad.addFocusListener(new JTextFieldFocusListener(jTextFieldCantidad));
			cont++;
		}

		if (cont > 0) {
			JOptionPane
					.showMessageDialog(
							this,"Uno o más campos se encuentran excediendo el tamaño máximo permitido",
							"Campo excedido", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void aceptarAgregarRepuesto() {
		int option = 0;
		int id_repuesto;

		if (validarDatos() && characterVaryingExceeded()) {
			Repuesto repuesto = new Repuesto();
			double precio_unitario; // almacena el precio unitario del repuesto
									// una vez presionado el boton Aceptar
			double precio_total; // almacena el precio total del repuesto/s una
									// vez presionado el boton Aceptar
			double cantidad = 0;
			precio_unitario = ((Number) jTextFieldPrecioUnitario.getValue()).doubleValue();
			try {
				cantidad = formatterDecimal.parse(jTextFieldCantidad.getText().trim()).doubleValue();
			} catch (ParseException e1) {
				System.out.print(e1);
			}
			precio_total = precio_unitario * cantidad;
			repuesto.setNombre(jTextFieldNombre.getText().trim());
			repuesto.setPrecioUnitario(precio_unitario);
			repuesto.setId_servicio(servicio.getId_servicio());
			repuesto.setCantidad(cantidad);
			repuesto.setObservaciones(jTextAreaObservaciones.getText());

			if (jComboBoxProveedor.getSelectedIndex() == 0)
				repuesto.setId_proveedor(0); // 0 (cero) significa que no se
												// especifica mecanico
			else {
				repuesto.setId_proveedor(listaProveedores.get(
						jComboBoxProveedor.getSelectedIndex() - 1)
						.getId_proveedor());
			}

			IRepuestoManager repuestoManager = new RepuestoManager();
			try {
				id_repuesto = repuestoManager.nextID();
				repuesto.setId_repuesto(id_repuesto);
				option = repuestoManager.agregar(repuesto);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e, "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			switch (option) {
			case 1:
				detallarServicio.listaRepuestos.add(repuesto);
				Object fila1[] = {
						Integer.toString(repuesto.getId_repuesto()),
						repuesto.getNombre(),
						jComboBoxProveedor.getSelectedIndex() == 0 ? "< sin especificar >"
								: (listaProveedores.get(jComboBoxProveedor
										.getSelectedIndex() - 1).getNombre()),
						amountDisplayFormat.format(precio_unitario),
						(jTextFieldCantidad.getText().trim()),
						(jTextAreaObservaciones.getText().trim().equals("") ? ""
								: "\u2713"),
						amountDisplayFormat.format(precio_total),
						Integer.toString(detallarServicio.dtmRepuestos
								.getRowCount()) };
				detallarServicio.dtmRepuestos.addRow(fila1);
				// scroll hasta el final de la tabla
				detallarServicio.jTableRepuestos.scrollRectToVisible(detallarServicio.jTableRepuestos.getCellRect(detallarServicio.jTableRepuestos.getRowCount()-1, 0, true));
				
				cleanAllFields();
				this.dispose();
				break;

			case 2:
				JOptionPane.showMessageDialog(this,
						"El precio debe ser mayor a cero",
						"Campo mal ingresado", JOptionPane.ERROR_MESSAGE);
				jTextFieldPrecioUnitario.setBorder(BorderFactory
						.createLineBorder(Color.RED));
				jTextFieldPrecioUnitario
						.addFocusListener(new JTextFieldFocusListener(
								jTextFieldPrecioUnitario));

				break;
			default:
				// este mensaje nunca debería aparecer
				JOptionPane.showMessageDialog(this, "Algo salió mal", "Ups!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void cancelarAgregarRepuesto() {
		cleanAllFields();

		this.dispose();
	}

	private void cerrarEsc() {
		// esta funcion hace que la ventana BuscarProveedor se cierre con la
		// tecla ESC
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0,
				false);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AgregarRepuesto.this.dispose();
			}
		};
		jPanelAgregarRepuesto.registerKeyboardAction(actionListener, keystroke,
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	String conObservaciones() {
		if (jTextAreaObservaciones.getText().equals(""))
			return "";
		else {
			int cc = 2713;
			char ccc = (char) Integer.parseInt(String.valueOf(cc), 16);
			return String.valueOf(ccc);
		}
	}

	private boolean validarDatos() {
		int cont = 0;

		List<JTextField> jTextFields = new ArrayList<JTextField>();

		if (jTextFieldNombre.getText().contains("<")) {
			jTextFieldNombre.setText("");
		}

		jTextFields.add(jTextFieldNombre);
		jTextFields.add(jTextFieldPrecioUnitario);
		jTextFields.add(jTextFieldCantidad);

		for (JTextField jTextField : jTextFields)
			cont += isEmpty(jTextField);

		if (jTextAreaObservaciones.getText().contains("<")) {
			jTextAreaObservaciones.setText("");
		}

		if (cont > 0) {
			JOptionPane.showMessageDialog(this,
					"Uno o más campos obligatorios se encuentran vacíos",
					"Campo vacío", JOptionPane.ERROR_MESSAGE);
			return false;

		}

		return true;
	}

	private int isEmpty(JTextField jTextField) {
		if (jTextField.getText().isEmpty()
				|| jTextField.getText().contains("<")) {
			jTextField.setText("");
			jTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
			jTextField
					.addFocusListener(new JTextFieldFocusListener(jTextField));

			return 1;
		}

		return 0;
	}

	private void cleanAllFields() {
		jTextFieldNombre.setText("");
		jTextFieldPrecioUnitario.setText("");
		jTextAreaObservaciones.setText("");

	}

	private void cargarProveedores() {
		jComboBoxProveedor = new JComboBox<String>();
		jComboBoxProveedor.setFont(new Font("Dialog", Font.BOLD, 11));
		jComboBoxProveedor.setPreferredSize(new Dimension(312, 25));
		jComboBoxProveedor.addItem("< sin especificar >");

		try {
			listaProveedores = proveedorManager.listaProveedores();
			Iterator<Proveedor> iterator = listaProveedores.iterator();
			Proveedor proveedor = null;
			while (iterator.hasNext()) {
				proveedor = iterator.next();
				jComboBoxProveedor.addItem(proveedor.getNombre());
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Error al cargar la lista de proveedores", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
