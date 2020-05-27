import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.table.*;

public class Cotizacion extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener,
		WindowListener, ItemListener, MouseMotionListener {

	private JLabel closeButton, minButton, titleLabel;
	private Color panel1 = new Color(42, 44, 49);
	private Color panel2 = new Color(54, 57, 63);
	private Color fontColor1 = new Color(194, 196, 197);
	private Color fontColor2 = new Color(134, 138, 143);
	private Color fieldColor = new Color(48, 51, 57);
	private Color barColor = new Color(34, 36, 40);
	private Color redColor = new Color(179, 29, 29);
	private Color redColorEntered = new Color(148, 27, 27);
	private Color focusColor = new Color(53, 55, 58);
	private Color buttonColor = new Color(101, 59, 152);
	private Color buttonColorEntered = new Color(80, 48, 119);
	private Color selectedText = new Color(255, 255, 255);
	private Color selectionColor = new Color(10, 103, 215);
	private Color textFieldColorEntered = new Color(74, 86, 129);
	private Color buttonTextColor = new Color(232, 236, 249);
	private String titlewindow;
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private int x, y;
	private String patchLogo = "images/Logo.png";
	private String pacthSale = "images/sale.png";
	private ImageIcon saleImg = new ImageIcon(pacthSale);
	private JLabel cancelar, agregar, guardar, eliminar, agregarcot, regresar;
	private JLabel no_cot, fechaLabel, sbt, total, iva, tot_txt, iva_txt, sbt_txt, anti, pend, id_cliente, nom_cliente,
			tel, dir, corr;
	private JLabel addproduct, sale, prod, pre_uni, dim, x1, cant, sbtotal, mas, menos, tipo, id_prod, diseno;
	private JTextField no_cotField, pre_uni_txt, dim_largo, dim_ancho, cant_txt, sbtotal_txt, id_prod_txt, diseno_txt;
	private JTextField id_cliente_txt, nom_cliente_txt, tel_txt, dir_txt, corr_txt, anti_txt, pend_txt;
	private Integer id, idCliente;
	private Calendar fecha;
	private String dia, mes, anio;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JScrollPane scroll;
	private JPanel bar, agregarCot, mostrarCot, cortesPanel;
	private JComboBox<String> prod_com, tipo_prod;
	private String tipoProducto, nomProducto, precioUnitario, idProducto;
	private Double totalCot = 0.00;
	private Boolean existClient = false;
	private JPanel laminado;
	private JLabel cortesButton, backCot;
	private JLabel[] cortes = new JLabel[50];
	private int alt = 10;
	private boolean notY = true;

	public Cotizacion(String title) {
		this.setLayout(null);
		this.setBounds(0, 0, 1500, 800);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(panel2);
		this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());
		this.addWindowListener(this);
		this.setUndecorated(true);
		this.titlewindow = title;

		idCliente = 0;

		// iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConnection().createStatement();
			rs = st.executeQuery("SELECT MAX(id_cot) FROM Cotizacion");
			rs.next();
			id = rs.getInt(1) + 1; // maxima id de cotizaciones + 1
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}

		bar = new JPanel();
		bar.setBackground(barColor);
		bar.setBounds(0, 0, 1500, 30);
		bar.setLayout(null);
		bar.setVisible(true);
		bar.addMouseMotionListener(this);
		this.add(bar);

		// Panel para laminado
		cortesPanel = new JPanel();
		cortesPanel.setBackground(panel1);
		cortesPanel.setBounds(20, 50, 1460, 730);
		cortesPanel.setLayout(null);
		cortesPanel.setVisible(false);

		laminado = new JPanel();
		laminado.setBackground(panel2);
		laminado.setBounds(40, 77, 1380, 623);
		laminado.setBorder(BorderFactory.createLineBorder(barColor, 2));
		laminado.setLayout(null);
		laminado.setVisible(true);
		cortesPanel.add(laminado);

		backCot = new JLabel("Regresar", SwingConstants.CENTER);
		backCot.setBounds(1190, 20, 250, 40);
		backCot.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		backCot.setForeground(buttonTextColor);
		backCot.setBackground(buttonColor);
		backCot.setBorder(BorderFactory.createLineBorder(barColor, 2));
		backCot.setFocusable(true);
		backCot.setOpaque(true);
		backCot.addKeyListener(this);
		backCot.addFocusListener(this);
		backCot.addMouseListener(this);
		cortesPanel.add(backCot);

		// Panel para agregar cotizacion
		agregarCot = new JPanel();
		agregarCot.setBackground(panel1);
		agregarCot.setBounds(20, 50, 1460, 730);
		agregarCot.setLayout(null);
		agregarCot.setVisible(false);

		// Panel para mostrar detalles de cotizacion
		mostrarCot = new JPanel();
		mostrarCot.setBackground(panel1);
		mostrarCot.setBounds(20, 50, 1460, 730);
		mostrarCot.setLayout(null);
		mostrarCot.setVisible(true);

		// obtenemos fecha actual
		fecha = Calendar.getInstance();
		dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
		mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
		anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

		titleLabel = new JLabel(titlewindow);
		titleLabel.setBounds(20, 2, 100, 30);
		titleLabel.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		titleLabel.setForeground(fontColor2);
		bar.add(titleLabel);

		closeButton = new JLabel(new ImageIcon("images/close.png"), SwingConstants.CENTER);
		closeButton.setBounds(1470, 0, 30, 30);
		closeButton.setOpaque(true);
		closeButton.setBackground(barColor);
		closeButton.addMouseListener(this);
		bar.add(closeButton);

		minButton = new JLabel(new ImageIcon("images/min.png"), SwingConstants.CENTER);
		minButton.setBounds(1440, 0, 30, 30);
		minButton.setOpaque(true);
		minButton.setBackground(barColor);
		minButton.addMouseListener(this);
		bar.add(minButton);

		panelMostrarCot();
		panelAgregarCot();

		this.add(mostrarCot);
		this.add(agregarCot);
		this.add(cortesPanel);
	}

	public void panelMostrarCot() {
		cortesButton = new JLabel("Laminado", SwingConstants.CENTER);
		cortesButton.setBounds(420, 38, 250, 40);
		cortesButton.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		cortesButton.setForeground(buttonTextColor);
		cortesButton.setBackground(buttonColor);
		cortesButton.setBorder(BorderFactory.createLineBorder(barColor, 2));
		cortesButton.setFocusable(true);
		cortesButton.setOpaque(true);
		cortesButton.addKeyListener(this);
		cortesButton.addFocusListener(this);
		cortesButton.addMouseListener(this);
		mostrarCot.add(cortesButton);

		no_cot = new JLabel("Id Cotizaci\u00F3n");
		no_cot.setBounds(50, 40, 200, 40);
		no_cot.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		no_cot.setForeground(fontColor1);
		mostrarCot.add(no_cot);

		no_cotField = new JTextField();
		no_cotField.setBounds(270, 38, 100, 40);
		no_cotField.setBackground(panel1);
		no_cotField.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		no_cotField.setText(id.toString());
		no_cotField.setHorizontalAlignment(JTextField.CENTER);
		no_cotField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		no_cotField.setEditable(false);
		no_cotField.setForeground(fontColor1);
		mostrarCot.add(no_cotField);

		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(1110, 54, 200, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		fechaLabel.setForeground(fontColor1);
		mostrarCot.add(fechaLabel);

		id_cliente = new JLabel("Id Cliente");
		id_cliente.setBounds(50, 120, 120, 25);
		id_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		id_cliente.setForeground(fontColor1);
		mostrarCot.add(id_cliente);

		id_cliente_txt = new JTextField();
		id_cliente_txt.setBounds(200, 111, 100, 40);
		id_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		id_cliente_txt.setHorizontalAlignment(JTextField.CENTER);
		id_cliente_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		id_cliente_txt.setEditable(false);
		id_cliente_txt.setBackground(panel1);
		id_cliente_txt.setForeground(fontColor1);
		id_cliente_txt.addFocusListener(this);
		mostrarCot.add(id_cliente_txt);

		nom_cliente = new JLabel("Nombre");
		nom_cliente.setBounds(350, 120, 100, 25);
		nom_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		nom_cliente.setForeground(fontColor1);
		mostrarCot.add(nom_cliente);

		nom_cliente_txt = new JTextField();
		nom_cliente_txt.setBounds(480, 111, 930, 40);
		nom_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		nom_cliente_txt.setBackground(fieldColor);
		nom_cliente_txt.setForeground(fontColor2);
		nom_cliente_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		nom_cliente_txt.setSelectedTextColor(selectedText);
		nom_cliente_txt.setSelectionColor(selectionColor);
		nom_cliente_txt.addFocusListener(this);
		nom_cliente_txt.addKeyListener(this);
		nom_cliente_txt.addMouseListener(this);
		mostrarCot.add(nom_cliente_txt);

		dir = new JLabel("Direcci\u00F3n");
		dir.setBounds(50, 180, 200, 25);
		dir.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		dir.setForeground(fontColor1);
		mostrarCot.add(dir);

		dir_txt = new JTextField();
		dir_txt.setBounds(200, 171, 1200, 40);
		dir_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		dir_txt.setBackground(fieldColor);
		dir_txt.setForeground(fontColor2);
		dir_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		dir_txt.setSelectedTextColor(selectedText);
		dir_txt.setSelectionColor(selectionColor);
		dir_txt.addFocusListener(this);
		dir_txt.addKeyListener(this);
		dir_txt.addMouseListener(this);
		mostrarCot.add(dir_txt);

		tel = new JLabel("T\u00E9lefono");
		tel.setBounds(50, 240, 100, 25);
		tel.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		tel.setForeground(fontColor1);
		mostrarCot.add(tel);

		tel_txt = new JTextField();
		tel_txt.setBounds(200, 231, 200, 40);
		tel_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		tel_txt.setBackground(fieldColor);
		tel_txt.setForeground(fontColor2);
		tel_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		tel_txt.setSelectedTextColor(selectedText);
		tel_txt.setSelectionColor(selectionColor);
		tel_txt.addFocusListener(this);
		tel_txt.addKeyListener(this);
		tel_txt.addMouseListener(this);
		mostrarCot.add(tel_txt);

		corr = new JLabel("Correo");
		corr.setBounds(450, 240, 80, 25);
		corr.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		corr.setForeground(fontColor1);
		mostrarCot.add(corr);

		corr_txt = new JTextField();
		corr_txt.setBounds(580, 231, 830, 40);
		corr_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		corr_txt.setBackground(fieldColor);
		corr_txt.setForeground(fontColor2);
		corr_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		corr_txt.setSelectedTextColor(selectedText);
		corr_txt.setSelectionColor(selectionColor);
		corr_txt.addFocusListener(this);
		corr_txt.addKeyListener(this);
		corr_txt.addMouseListener(this);
		mostrarCot.add(corr_txt);

		String[] campos = new String[] { "Id", "Nombre", "Tipo", "P. Unitario", "Dise\u00F1o", "Largo", "Ancho",
				"Cantidad", "Precio total" };

		modelo = new DefaultTableModel(null, campos);
		tabla = new JTable(modelo) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.getTableHeader().setResizingAllowed(false);
		scroll = new JScrollPane(tabla);
		scroll.getViewport().setBackground(fieldColor);
		scroll.setBounds(50, 300, 1360, 200);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(25); // Id
		tabla.getColumnModel().getColumn(1).setPreferredWidth(140); // Nombre
		tabla.getColumnModel().getColumn(2).setPreferredWidth(70); // Tipo
		tabla.getColumnModel().getColumn(3).setPreferredWidth(95); // Precio unitario
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80); // Diseño
		tabla.getColumnModel().getColumn(5).setPreferredWidth(60); // Largo
		tabla.getColumnModel().getColumn(6).setPreferredWidth(60); // Ancho
		tabla.getColumnModel().getColumn(7).setPreferredWidth(80); // Cantidad
		tabla.getColumnModel().getColumn(8).setPreferredWidth(95); // Precio total
		tabla.setRowHeight(25);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 24));
		tabla.getTableHeader().setForeground(buttonTextColor);
		tabla.getTableHeader().setBackground(buttonColor);
		tabla.setBackground(fieldColor);
		tabla.setForeground(fontColor1);
		tabla.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		mostrarCot.add(scroll);

		sbt = new JLabel("Subtotal: ");
		sbt.setBounds(100, 530, 150, 25);
		sbt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		sbt.setForeground(fontColor1);
		mostrarCot.add(sbt);

		sbt_txt = new JLabel("", SwingConstants.CENTER);
		sbt_txt.setBounds(240, 530, 200, 25);
		sbt_txt.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		sbt_txt.setForeground(buttonColor);
		mostrarCot.add(sbt_txt);

		iva = new JLabel("IVA 16 \u0025:");
		iva.setBounds(100, 590, 150, 25);
		iva.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		iva.setForeground(fontColor1);
		mostrarCot.add(iva);

		iva_txt = new JLabel("0", SwingConstants.CENTER);
		iva_txt.setBounds(240, 590, 200, 25);
		iva_txt.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		iva_txt.setForeground(buttonColor);
		mostrarCot.add(iva_txt);

		total = new JLabel("Total: ");
		total.setBounds(490, 530, 150, 25);
		total.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		total.setForeground(fontColor1);
		mostrarCot.add(total);

		tot_txt = new JLabel("0", SwingConstants.CENTER);
		tot_txt.setBounds(630, 530, 200, 25);
		tot_txt.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		tot_txt.setForeground(buttonColor);
		mostrarCot.add(tot_txt);

		anti = new JLabel("Anticipo: ");
		anti.setBounds(930, 530, 200, 25);
		anti.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		anti.setForeground(fontColor1);
		mostrarCot.add(anti);

		anti_txt = new JTextField("0");
		anti_txt.setBounds(1160, 521, 200, 40);
		anti_txt.setBackground(fieldColor);
		anti_txt.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		anti_txt.setForeground(fontColor2);
		anti_txt.setHorizontalAlignment(JTextField.CENTER);
		anti_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		anti_txt.setSelectedTextColor(selectedText);
		anti_txt.setSelectionColor(selectionColor);
		anti_txt.addFocusListener(this);
		anti_txt.addKeyListener(this);
		anti_txt.addMouseListener(this);
		mostrarCot.add(anti_txt);

		pend = new JLabel("Pendiente: ");
		pend.setBounds(930, 590, 200, 25);
		pend.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		pend.setForeground(fontColor1);
		mostrarCot.add(pend);

		pend_txt = new JTextField("");
		pend_txt.setBounds(1160, 581, 200, 40);
		pend_txt.setBackground(fieldColor);
		pend_txt.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		pend_txt.setForeground(fontColor1);
		pend_txt.setHorizontalAlignment(JTextField.CENTER);
		pend_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		pend_txt.setSelectedTextColor(selectedText);
		pend_txt.setSelectionColor(selectionColor);
		pend_txt.addFocusListener(this);
		pend_txt.addKeyListener(this);
		pend_txt.addMouseListener(this);
		pend_txt.setEditable(false);
		mostrarCot.add(pend_txt);

		cancelar = new JLabel("Cancelar", SwingConstants.CENTER);
		cancelar.setBounds(50, 650, 250, 40);
		cancelar.setBackground(buttonColor);
		cancelar.setForeground(buttonTextColor);
		cancelar.setFont(new Font("Microsoft New Tai lue", 1, 24));
		cancelar.setBorder(BorderFactory.createLineBorder(barColor, 2));
		cancelar.setOpaque(true);
		cancelar.addFocusListener(this);
		cancelar.addKeyListener(this);
		cancelar.addMouseListener(this);
		mostrarCot.add(cancelar);

		eliminar = new JLabel("Eliminar", SwingConstants.CENTER);
		eliminar.setBounds(420, 650, 250, 40);
		eliminar.setBackground(buttonColor);
		eliminar.setForeground(buttonTextColor);
		eliminar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
		eliminar.setBorder(BorderFactory.createLineBorder(barColor, 2));
		eliminar.setOpaque(true);
		eliminar.addFocusListener(this);
		eliminar.addKeyListener(this);
		eliminar.addMouseListener(this);
		mostrarCot.add(eliminar);

		agregar = new JLabel("Agregar", SwingConstants.CENTER);
		agregar.setBounds(790, 650, 250, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
		agregar.setBackground(buttonColor);
		agregar.setForeground(buttonTextColor);
		agregar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
		agregar.setBorder(BorderFactory.createLineBorder(barColor, 2));
		agregar.setOpaque(true);
		agregar.addFocusListener(this);
		agregar.addKeyListener(this);
		agregar.addMouseListener(this);
		mostrarCot.add(agregar);

		guardar = new JLabel("Guardar", SwingConstants.CENTER);
		guardar.setBounds(1160, 650, 250, 40);
		guardar.setBackground(buttonColor);
		guardar.setForeground(buttonTextColor);
		guardar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
		guardar.setBorder(BorderFactory.createLineBorder(barColor, 2));
		guardar.setOpaque(true);
		guardar.addFocusListener(this);
		guardar.addKeyListener(this);
		guardar.addMouseListener(this);
		mostrarCot.add(guardar);
	}

	public void panelAgregarCot() {
		addproduct = new JLabel("A\u00f1adir Producto");
		addproduct.setBounds(50, 90, 300, 40);
		addproduct.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		addproduct.setForeground(fontColor1);
		agregarCot.add(addproduct);

		tipo = new JLabel("Tipo");
		tipo.setBounds(50, 174, 100, 25);
		tipo.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		tipo.setForeground(fontColor1);
		agregarCot.add(tipo);

		tipo_prod = new JComboBox<>();
		tipo_prod.setBounds(170, 165, 300, 40);
		tipo_prod.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		tipo_prod.setBackground(fieldColor);
		tipo_prod.setForeground(fontColor2);
		tipo_prod.setBorder(BorderFactory.createLineBorder(barColor, 2));
		tipo_prod.addFocusListener(this);
		tipo_prod.addKeyListener(this);
		tipo_prod.addMouseListener(this);

		try {
			rs = st.executeQuery("SELECT DISTINCT(tipo_prod) FROM Producto");
			while (rs.next()) {
				tipo_prod.addItem(rs.getString(1));
			}
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, err);
		}

		tipo_prod.addItemListener(this);
		agregarCot.add(tipo_prod);

		id_prod = new JLabel("Id Producto");
		id_prod.setBounds(530, 258, 150, 25);
		id_prod.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		id_prod.setForeground(fontColor1);
		agregarCot.add(id_prod);

		id_prod_txt = new JTextField();
		id_prod_txt.setText("");
		id_prod_txt.setBounds(710, 249, 100, 40);
		id_prod_txt.setBackground(fieldColor);
		id_prod_txt.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		id_prod_txt.setForeground(fontColor2);
		id_prod_txt.setHorizontalAlignment(JTextField.CENTER);
		id_prod_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		id_prod_txt.setEditable(false);
		agregarCot.add(id_prod_txt);

		pre_uni = new JLabel("Precio unitario");
		pre_uni.setBounds(50, 342, 200, 25);
		pre_uni.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		pre_uni.setForeground(fontColor1);
		agregarCot.add(pre_uni);

		pre_uni_txt = new JTextField();
		pre_uni_txt.setText("0");
		pre_uni_txt.setBounds(270, 333, 200, 40);
		pre_uni_txt.setBackground(fieldColor);
		pre_uni_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		pre_uni_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		pre_uni_txt.setForeground(fontColor1);
		pre_uni_txt.setEditable(false);
		agregarCot.add(pre_uni_txt);

		prod = new JLabel("Producto");
		prod.setBounds(50, 258, 100, 25);
		prod.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		prod.setForeground(fontColor1);
		agregarCot.add(prod);

		prod_com = new JComboBox<>();
		prod_com.setBounds(180, 249, 300, 40);
		prod_com.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		prod_com.setBackground(fieldColor);
		prod_com.setForeground(fontColor1);
		prod_com.addFocusListener(this);
		prod_com.addKeyListener(this);

		tipoProducto = tipo_prod.getSelectedItem().toString();

		try {
			rs = st.executeQuery("SELECT nom_prod FROM Producto WHERE tipo_prod = '" + tipoProducto + "'");
			while (rs.next()) {
				prod_com.addItem(rs.getString("nom_prod"));
			}
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, err);
		}

		nomProducto = prod_com.getSelectedItem().toString();

		try {
			rs = st.executeQuery("SELECT prec_prod, id_prod FROM Producto WHERE nom_prod = '" + nomProducto + "'");
			rs.next();

			precioUnitario = rs.getString("prec_prod");
			pre_uni_txt.setEditable(true);
			pre_uni_txt.setText(precioUnitario);
			pre_uni_txt.setEditable(false);

			idProducto = rs.getString("id_prod");
			id_prod_txt.setEditable(true);
			id_prod_txt.setText(idProducto);
			id_prod_txt.setEditable(false);
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, err);
		}

		prod_com.addItemListener(this);
		agregarCot.add(prod_com);

		diseno = new JLabel("Dise\u00F1o");
		diseno.setBounds(510, 342, 100, 25);
		diseno.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		diseno.setForeground(fontColor1);
		agregarCot.add(diseno);

		diseno_txt = new JTextField();
		diseno_txt.setBounds(610, 333, 200, 40);
		diseno_txt.setBackground(fieldColor);
		diseno_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		diseno_txt.setForeground(fontColor1);
		diseno_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		diseno_txt.setSelectedTextColor(selectedText);
		diseno_txt.setSelectionColor(selectionColor);
		diseno_txt.addFocusListener(this);
		diseno_txt.addKeyListener(this);
		diseno_txt.addMouseListener(this);
		agregarCot.add(diseno_txt);

		dim = new JLabel("Dimensiones");
		dim.setBounds(50, 426, 150, 25); // 288
		dim.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		dim.setForeground(fontColor1);
		agregarCot.add(dim);

		dim_largo = new JTextField("0");
		dim_largo.setBounds(230, 417, 200, 40);
		dim_largo.setBackground(fieldColor);
		dim_largo.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		dim_largo.setForeground(fontColor2);
		dim_largo.setHorizontalAlignment(JTextField.CENTER);
		dim_largo.setBorder(BorderFactory.createLineBorder(barColor, 2));
		dim_largo.setSelectedTextColor(selectedText);
		dim_largo.setSelectionColor(selectionColor);
		dim_largo.addFocusListener(this);
		dim_largo.addKeyListener(this);
		dim_largo.addMouseListener(this);
		agregarCot.add(dim_largo);

		x1 = new JLabel("X");
		x1.setBounds(480, 426, 20, 25);
		x1.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		x1.setForeground(fontColor1);
		agregarCot.add(x1);

		dim_ancho = new JTextField("0");
		dim_ancho.setBounds(550, 417, 200, 40);
		dim_ancho.setBackground(fieldColor);
		dim_ancho.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		dim_ancho.setForeground(fontColor2);
		dim_ancho.setHorizontalAlignment(JTextField.CENTER);
		dim_ancho.setBorder(BorderFactory.createLineBorder(barColor, 2));
		dim_ancho.setSelectedTextColor(selectedText);
		dim_ancho.setSelectionColor(selectionColor);
		dim_ancho.addFocusListener(this);
		dim_ancho.addKeyListener(this);
		dim_ancho.addMouseListener(this);
		agregarCot.add(dim_ancho);

		cant = new JLabel("Cantidad");
		cant.setBounds(50, 510, 100, 25);
		cant.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		cant.setForeground(fontColor1);
		agregarCot.add(cant);

		cant_txt = new JTextField();
		cant_txt.setBounds(180, 501, 200, 40);
		cant_txt.setBackground(fieldColor);
		cant_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		cant_txt.setForeground(fontColor2);
		cant_txt.setText("1");
		cant_txt.setHorizontalAlignment(JTextField.CENTER);
		cant_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		cant_txt.setEditable(false);
		agregarCot.add(cant_txt);

		ImageIcon mas_imagen = new ImageIcon("./images/mas.png");
		mas = new JLabel(mas_imagen, SwingConstants.CENTER);
		mas.setBounds(470, 505, 30, 30);
		mas.addMouseListener(this);
		mas.addFocusListener(this);
		agregarCot.add(mas);

		ImageIcon menos_imagen = new ImageIcon("./images/menos.png");
		menos = new JLabel(menos_imagen, SwingConstants.CENTER);
		menos.setBounds(410, 505, 30, 30);
		menos.addMouseListener(this);
		menos.addFocusListener(this);
		agregarCot.add(menos);

		sbtotal = new JLabel("Subtotal");
		sbtotal.setBounds(530, 510, 100, 25);
		sbtotal.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		sbtotal.setForeground(fontColor1);
		agregarCot.add(sbtotal);

		sbtotal_txt = new JTextField("", SwingConstants.CENTER);
		sbtotal_txt.setBounds(660, 501, 150, 40);
		sbtotal_txt.setBackground(fieldColor);
		sbtotal_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));
		sbtotal_txt.setForeground(fontColor1);
		sbtotal_txt.setText("0");
		sbtotal_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
		sbtotal_txt.setEditable(false);
		agregarCot.add(sbtotal_txt);

		sale = new JLabel("", SwingConstants.CENTER);
		sale.setBounds(950, 90, 460, 550);
		sale.setIcon(saleImg);
		sale.setHorizontalTextPosition(SwingConstants.CENTER);
		sale.setVerticalTextPosition(SwingConstants.CENTER);
		sale.setBorder(BorderFactory.createLineBorder(barColor, 2));
		sale.setBackground(buttonColor);
		sale.setOpaque(true);
		agregarCot.add(sale);

		regresar = new JLabel("Regresar", SwingConstants.CENTER);
		regresar.setBounds(50, 600, 300, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
		regresar.setBackground(buttonColor);
		regresar.setForeground(buttonTextColor);
		regresar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
		regresar.setBorder(BorderFactory.createLineBorder(barColor, 2));
		regresar.setOpaque(true);
		regresar.addMouseListener(this);
		regresar.addFocusListener(this);
		regresar.addKeyListener(this);
		agregarCot.add(regresar);

		agregarcot = new JLabel("Agregar", SwingConstants.CENTER);
		agregarcot.setBounds(550, 600, 300, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
		agregarcot.setBackground(buttonColor);
		agregarcot.setForeground(buttonTextColor);
		agregarcot.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
		agregarcot.setBorder(BorderFactory.createLineBorder(barColor, 2));
		agregarcot.setOpaque(true);
		agregarcot.addMouseListener(this);
		agregarcot.addFocusListener(this);
		agregarcot.addKeyListener(this);
		agregarCot.add(agregarcot);
	}

	// ActionListener
	@Override
	public void actionPerformed(ActionEvent evt) {
	}

	// KeyListener
	@Override
	public void keyTyped(KeyEvent evt) {
		/*
		 * if(evt.getSource() == this.no_cotField) {
		 * if(this.no_cotField.getText().length() >= 4) { evt.consume(); } }
		 */
	}

	@Override
	public void keyReleased(KeyEvent evt) {

	}

	@Override
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == 10) {
			if (evt.getSource() == this.agregar) {
				mostrarCot.setVisible(false);
				agregarCot.setVisible(true);
			} else if (evt.getSource() == this.regresar) {
				mostrarCot.setVisible(true);
				agregarCot.setVisible(false);
			}
		}
	}

	// FocusListener
	@Override
	public void focusGained(FocusEvent evt) {
		if (evt.getSource() == this.id_cliente_txt) {
			this.id_cliente_txt.setBackground(focusColor);
		} else if (evt.getSource() == this.nom_cliente_txt) {
			this.nom_cliente_txt.setBackground(focusColor);
		} else if (evt.getSource() == this.tel_txt) {
			this.tel_txt.setBackground(focusColor);
		} else if (evt.getSource() == this.dir_txt) {
			this.dir_txt.setBackground(focusColor);
		} else if (evt.getSource() == this.corr_txt) {
			this.corr_txt.setBackground(focusColor);
		} else if (evt.getSource() == this.prod_com) {
			this.prod_com.setBackground(focusColor);
		} else if (evt.getSource() == this.dim_largo) {
			this.dim_largo.setBackground(focusColor);
			dim_largo.setText("");
		} else if (evt.getSource() == this.dim_ancho) {
			this.dim_ancho.setBackground(focusColor);
			dim_ancho.setText("");
		} else if (evt.getSource() == this.tipo_prod) {
			this.tipo_prod.setBackground(focusColor);
		} else if (evt.getSource() == this.diseno_txt) {
			this.diseno_txt.setBackground(focusColor);
		} else if (evt.getSource() == this.anti_txt) {
			this.anti_txt.setBackground(focusColor);
			anti_txt.setText("");
		} else if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.eliminar) {
			this.eliminar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.cortesButton) {
			this.cortesButton.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.backCot) {
			this.backCot.setBackground(buttonColorEntered);
		}
	}

	@Override
	public void focusLost(FocusEvent evt) {
		if (evt.getSource() == this.id_cliente_txt) {
			this.id_cliente_txt.setBackground(fieldColor);
		} else if (evt.getSource() == this.nom_cliente_txt) {
			String nombre = nom_cliente_txt.getText() + "%";
			if (!nombre.equals("")) {
				try {
					rs = st.executeQuery("SELECT * FROM Cliente WHERE nom_cl LIKE '" + nombre + "'");
					existClient = rs.next();
					if (existClient) { // si existe se le asigna la misma id
						idCliente = rs.getInt("id_cl");
						id_cliente_txt.setEditable(true);
						id_cliente_txt.setText(idCliente.toString());
						id_cliente_txt.setEditable(false);

						nom_cliente_txt.setText(rs.getString("nom_cl"));

						String telCliente = rs.getString("tel_cl");
						tel_txt.setEditable(true);
						tel_txt.setText(telCliente);
						tel_txt.setEditable(false);

						String dirCliente = rs.getString("dir_cl");
						dir_txt.setEditable(true);
						dir_txt.setText(dirCliente);
						dir_txt.setEditable(false);

						String corrCliente = rs.getString("corr_cl");
						corr_txt.setEditable(true);
						corr_txt.setText(corrCliente);
						corr_txt.setEditable(false);
					} else { // si no se crea una nueva id
						rs = st.executeQuery("SELECT MAX(id_cl) FROM Cliente");
						rs.next();
						idCliente = rs.getInt(1) + 1; // maxima id de clientes + 1
						id_cliente_txt.setEditable(true);
						id_cliente_txt.setText(idCliente.toString());
						id_cliente_txt.setEditable(false);

						tel_txt.setEditable(true);
						tel_txt.setText("");

						dir_txt.setEditable(true);
						dir_txt.setText("");

						corr_txt.setEditable(true);
						corr_txt.setText("");
					}
				} catch (SQLException err) {
					System.out.println(err.toString());
				}
			}
			this.nom_cliente_txt.setBackground(fieldColor);
		} else if (evt.getSource() == this.tel_txt) {
			this.tel_txt.setBackground(fieldColor);
		} else if (evt.getSource() == this.dir_txt) {
			this.dir_txt.setBackground(fieldColor);
		} else if (evt.getSource() == this.corr_txt) {
			this.corr_txt.setBackground(fieldColor);
		} else if (evt.getSource() == this.prod_com) {
			this.prod_com.setBackground(fieldColor);
		} else if (evt.getSource() == this.dim_largo) {
			try {
				this.dim_largo.setBackground(fieldColor);
				this.sbtotal_txt.setEditable(true);
				Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario)
						* Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
				this.sbtotal_txt.setText(String.valueOf(precio));
				this.sbtotal_txt.setEditable(false);
			} catch (NumberFormatException err) {
				System.out.println(err);
			}
		} else if (evt.getSource() == this.dim_ancho) {
			try {
				this.dim_ancho.setBackground(fieldColor);
				this.sbtotal_txt.setEditable(true);
				Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario)
						* Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
				this.sbtotal_txt.setText(String.valueOf(precio));
				this.sbtotal_txt.setEditable(false);
			} catch (NumberFormatException err) {
				System.out.println(err);
			}
		} else if (evt.getSource() == this.tipo_prod) {
			this.tipo_prod.setBackground(fieldColor);
		} else if (evt.getSource() == this.diseno_txt) {
			this.diseno_txt.setBackground(fieldColor);
		} else if (evt.getSource() == this.anti_txt) {
			try {
				this.anti_txt.setBackground(fieldColor);
				if (Double.parseDouble(this.anti_txt.getText()) < 0) {
					JOptionPane.showMessageDialog(null, "No se puede dar un anticipo negativo", "Error",
							JOptionPane.ERROR_MESSAGE);
					anti_txt.setText("");
					pend_txt.setText("");
				} else if (Double.parseDouble(this.anti_txt.getText()) > Double.parseDouble(this.tot_txt.getText())) {
					JOptionPane.showMessageDialog(null, "No se puede dar un anticipo mayor al total", "Error",
							JOptionPane.ERROR_MESSAGE);
					anti_txt.setText("");
					pend_txt.setText("");
				} else if (Double.parseDouble(this.anti_txt.getText()) < Double.parseDouble(this.tot_txt.getText())
						* 0.5) {
					JOptionPane.showMessageDialog(null, "No se puede dar un anticipo menor al 50 \u0025 del total",
							"Error", JOptionPane.ERROR_MESSAGE);
					anti_txt.setText("");
					pend_txt.setText("");
				} else {
					Double pago = Double.parseDouble(this.tot_txt.getText())
							- Double.parseDouble(this.anti_txt.getText());
					Double pendiente;
					pendiente = pago;
					Redondear(pendiente, 2);
					pend_txt.setText(pendiente.toString());
					pend_txt.setEditable(false);
				}
			} catch (NumberFormatException err) {
				System.out.println(err);
			}
		} else if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(buttonColor);
		} else if (evt.getSource() == this.eliminar) {
			this.eliminar.setBackground(buttonColor);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(buttonColor);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(buttonColor);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(buttonColor);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(buttonColor);
		} else if (evt.getSource() == this.cortesButton) {
			this.cortesButton.setBackground(buttonColor);
		} else if (evt.getSource() == this.backCot) {
			this.backCot.setBackground(buttonColor);
		}
	}

	public static double Redondear(double numero, int digitos) {
		int cifras = (int) Math.pow(10, digitos);
		return Math.rint(numero * cifras) / cifras;
	}

	public void closeSesion() {
		try {
			// Actualizamos el estado de sesion de usuario en la db
			rs = st.executeQuery("SELECT nick_usu, sesion_act FROM Usuario");
			String usuario = "";
			while (rs.next()) {
				if (rs.getString("sesion_act").equals("s")) {
					usuario = rs.getString("nick_usu");
					st.executeUpdate("UPDATE Usuario SET sesion_act = 'n' WHERE nick_usu = '" + usuario + "'");
					System.out.println("Se ha desconectado el usuario: " + usuario);
					break;
				}
			}
			db.desconectar();
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// MouseListener
	@Override
	public void mouseReleased(MouseEvent evt) {
		if (evt.getSource().equals(this.closeButton)) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		if (evt.getSource().equals(this.minButton)) {
			this.setExtendedState(ICONIFIED);
		}
		if (evt.getSource().equals(this.cortesButton)) {
			cortesPanel.setVisible(true);
			mostrarCot.setVisible(false);
		}
		if (evt.getSource().equals(this.backCot)) {
			mostrarCot.setVisible(true);
			cortesPanel.setVisible(false);
		}
		if (evt.getSource().equals(this.eliminar)) {
			if (tabla.getSelectedRow() != -1) {
				int i = tabla.getSelectedRow();
				modelo.removeRow(i);
				cortes[i].setVisible(false);
			}
			Double subtotalCot = 0.00;
			Double valor;

			for (int i = 0; i < tabla.getRowCount(); i++) {
				try {
					valor = Double.parseDouble(tabla.getValueAt(i, 8).toString());
					subtotalCot += valor;
				} catch (NumberFormatException err) {
					System.out.println(err);
				}
			}

			sbt_txt.setText(subtotalCot.toString());

			Double ivaSbt = subtotalCot * 0.16;
			totalCot = subtotalCot + ivaSbt;

			tot_txt.setText(totalCot.toString());
			iva_txt.setText(ivaSbt.toString());
		}
		if (evt.getSource() == this.cancelar) { // Boton cancelar
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
			} catch (SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (evt.getSource() == this.agregar) {
			mostrarCot.setVisible(false);
			agregarCot.setVisible(true);
		} else if (evt.getSource() == this.regresar) {
			mostrarCot.setVisible(true);
			agregarCot.setVisible(false);
		} else if (evt.getSource() == this.agregarcot) {
			// datos producto
			nomProducto = prod_com.getSelectedItem().toString();
			String disProducto = diseno_txt.getText();
			String largoProducto = dim_largo.getText();
			String anchoProducto = dim_ancho.getText();
			String cantidadProducto = cant_txt.getText();
			String sbtProducto = sbtotal_txt.getText();

			// Comprobamos si el usuario no dejo campos vacios
			if (disProducto.isEmpty() || largoProducto.isEmpty() || anchoProducto.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
			} else if (cantidadProducto.equals("0")) {
				JOptionPane.showMessageDialog(null, "Debe agregar al menos un producto.");
			} else {
				// validamos las dimensiones
				try {
					Double largo = 0.0, ancho = 0.0;
					largo = Double.parseDouble(dim_largo.getText()) * 100;
					ancho = Double.parseDouble(dim_ancho.getText()) * 100;
					try {
						rs = st.executeQuery(
								"SELECT id_prod, prec_prod FROM Producto WHERE nom_prod = '" + nomProducto + "'");
						rs.next();

						idProducto = rs.getString("id_prod");
						id_prod_txt.setEditable(true);
						id_prod_txt.setText(idProducto);
						id_prod_txt.setEditable(false);

						precioUnitario = rs.getString("prec_prod");
						pre_uni_txt.setEditable(true);
						pre_uni_txt.setText(precioUnitario);
						pre_uni_txt.setEditable(false);

						modelo.addRow(new String[] { idProducto, nomProducto, tipoProducto, precioUnitario, disProducto,
								largoProducto, anchoProducto, cantidadProducto, sbtProducto });

						Double subtotalCot = 0.00;
						Double valor;

						for (int i = 0; i < tabla.getRowCount(); i++) {
							try {
								valor = Double.parseDouble(tabla.getValueAt(i, 8).toString());
								subtotalCot += valor;
							} catch (NumberFormatException err) {
								System.out.println(err);
							}
						}

						sbt_txt.setText(subtotalCot.toString());

						Double ivaSbt = subtotalCot * 0.16;
						totalCot = subtotalCot + ivaSbt;

						tot_txt.setText(totalCot.toString());
						iva_txt.setText(ivaSbt.toString());

						mostrarCot.setVisible(true);
						agregarCot.setVisible(false);
						if (ancho.intValue() > laminado.getWidth() || largo.intValue() > laminado.getHeight()) {
							JOptionPane.showMessageDialog(null,
									"No es posible agregar al laminado, ya que supera el tamaño permitido.");
						} else {
							int n = tabla.getRowCount();
							JLabel item = new JLabel();
							if (n == 1) {
								item.setBounds(10, 10, ancho.intValue(), largo.intValue());
								item.setBackground(buttonColor);
								item.setFocusable(true);
								item.setOpaque(true);
								cortes[n - 1] = item;
								laminado.add(cortes[n - 1]);
							} else {
								if (notY) {
									if (10 + (cortes[n - 2].getX() + cortes[n - 2].getWidth())
											+ ancho.intValue() > laminado.getWidth()) {
										alt = cortes[n - 2].getY() + cortes[n - 2].getHeight() + 10;
										item.setBounds(10, alt, ancho.intValue(), largo.intValue());
										item.setBackground(buttonColor);
										item.setFocusable(true);
										item.setOpaque(true);
									} else {
										item.setBounds(10 + (cortes[n - 2].getX() + cortes[n - 2].getWidth()), alt,
												ancho.intValue(), largo.intValue());
										item.setBackground(buttonColor);
										item.setFocusable(true);
										item.setOpaque(true);
									}
									if ((item.getY() + item.getHeight() + 10) > laminado.getHeight()) {
										JOptionPane.showMessageDialog(null, "Lamina llena");
										notY = false;
									} else {
										cortes[n - 1] = item;
										laminado.add(cortes[n - 1]);
									}
								} else {
									JOptionPane.showMessageDialog(null, "Lamina llena");
								}
							}
						}

					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err);
					}
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Solamente debe ingresar n\u00FAmeros.");
					System.out.println(err);
				}
			}
			diseno_txt.setText("");
			dim_ancho.setText("0");
			dim_largo.setText("0");
			cant_txt.setText("1");
		} else if (evt.getSource() == this.guardar) {
			String nomCliente = nom_cliente_txt.getText();
			String telCliente = tel_txt.getText();
			String dirCliente = dir_txt.getText();
			String corrCliente = corr_txt.getText();
			String antiCliente = anti_txt.getText();
			String pendCliente = pend_txt.getText();
			String idEmpleado = "";
			if (nomCliente.isEmpty() || telCliente.isEmpty() || dirCliente.isEmpty() || corrCliente.isEmpty()
					|| antiCliente.isEmpty() || pendCliente.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Se deben llenar todos los campos.");
			} else {

				try {
					double anticipo = Double.parseDouble(antiCliente);
					if (!(anticipo < 0.0)) {
						try {
							if (!existClient) { // si no existe el cliente, obtenemos al empleado que hizo la cotizacion
								rs = st.executeQuery("SELECT id_usu, sesion_act FROM Usuario");
								while (rs.next()) {
									if (rs.getString("sesion_act").equals("s")) {
										idEmpleado = rs.getString("id_usu");
										break;
									}
								}
								// Se agrega el cliente a la db
								String camposCliente = "'" + idCliente.toString() + "', '" + idEmpleado + "', '"
										+ nomCliente + "', '" + telCliente + "', '" + dirCliente + "', '" + corrCliente
										+ "'";
								st.executeUpdate("INSERT INTO Cliente (id_cl, id_usu, nom_cl, tel_cl, dir_cl, corr_cl)"
										+ " VALUES (" + camposCliente + ")");
							} else {
								rs = st.executeQuery(
										"SELECT id_usu FROM Cliente WHERE id_cl = '" + idCliente.toString() + "'");
								rs.next();
								idEmpleado = rs.getString("id_usu");
							}

							// Se agrega la cotizacion a la db
							String camposCotizacion = "'" + no_cotField.getText() + "', '" + idCliente.toString()
									+ "', '" + idEmpleado + "', '" + sbt_txt.getText() + "', '" + iva_txt.getText()
									+ "', '" + tot_txt.getText() + "', '" + anti_txt.getText() + "', '"
									+ pend_txt.getText() + "'";
							st.executeUpdate(
									"INSERT INTO Cotizacion (id_cot, id_cl, id_usu, sub_cot, iva_cot, tot_cot, ant_cot, pend_cot)"
											+ " VALUES (" + camposCotizacion + ")");

							// Se agrega el carrito a la db
							rs = st.executeQuery("SELECT MAX(id_carrito) FROM Carrito");
							rs.next();
							Integer idCarrito = rs.getInt(1) + 1;
							String idProd = "";
							String disProd = "";
							String larProd = "";
							String anchProd = "";
							String canProd = "";
							String sbtProd = "";
							for (int row = 0; row < tabla.getRowCount(); row++) { // Recorremos la tabla para obtener
																					// los datos
								DefaultTableModel model = (DefaultTableModel) tabla.getModel();
								idProd = model.getValueAt(row, 0).toString();
								disProd = model.getValueAt(row, 4).toString();
								larProd = model.getValueAt(row, 5).toString();
								anchProd = model.getValueAt(row, 6).toString();
								canProd = model.getValueAt(row, 7).toString();
								sbtProd = model.getValueAt(row, 8).toString();
								String camposCarrito = "'" + idCarrito.toString() + "', '" + idProd + "', '"
										+ no_cotField.getText() + "', '" + anchProd + "', '" + larProd + "', '"
										+ disProd + "', '" + canProd + "', '" + sbtProd + "'";
								st.executeUpdate(
										"INSERT INTO Carrito (id_carrito, id_prod, id_cot, ancho_prod, largo_prod, dis_prod, cant_prod, subt_prod)"
												+ " VALUES (" + camposCarrito + ")");
								idCarrito++;
							}

							// Pasamos a mostrar el recibo
							db.desconectar();
							this.setVisible(false);
							Recibo r1 = new Recibo("Recibo", idCliente.toString(), idEmpleado.toString());
							r1.setVisible(true);
						} catch (SQLException err) {
							JOptionPane.showMessageDialog(null, err.toString());
						}
					}
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "El anticipo debe tomar valores enteros.");
					System.out.println(err);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent evt) {

	}

	@Override
	public void mouseExited(MouseEvent evt) {
		if (evt.getSource().equals(this.closeButton)) {
			this.closeButton.setBackground(barColor);
		}
		if (evt.getSource().equals(this.minButton)) {
			this.minButton.setBackground(barColor);
		}
		if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(buttonColor);
		} else if (evt.getSource() == this.eliminar) {
			this.eliminar.setBackground(buttonColor);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(buttonColor);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(buttonColor);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(buttonColor);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(buttonColor);
		} else if (evt.getSource() == this.cortesButton) {
			this.cortesButton.setBackground(buttonColor);
		} else if (evt.getSource() == this.backCot) {
			this.backCot.setBackground(buttonColor);
		}
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		if (evt.getSource().equals(this.closeButton)) {
			this.closeButton.setBackground(redColor);
		}
		if (evt.getSource().equals(this.minButton)) {
			this.minButton.setBackground(focusColor);
		}
		this.cancelar.setBackground(buttonColor);
		this.eliminar.setBackground(buttonColor);
		this.agregar.setBackground(buttonColor);
		this.guardar.setBackground(buttonColor);
		this.regresar.setBackground(buttonColor);
		this.agregarcot.setBackground(buttonColor);
		this.cortesButton.setBackground(buttonColor);
		if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.eliminar) {
			this.eliminar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.cortesButton) {
			this.cortesButton.setBackground(buttonColorEntered);
		} else if (evt.getSource() == this.backCot) {
			this.backCot.setBackground(buttonColorEntered);
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		if (evt.getSource() == this.menos) {
			if (Integer.parseInt(this.cant_txt.getText()) > 0) {
				this.cant_txt.setEditable(true);
				Integer valor = Integer.parseInt(this.cant_txt.getText()) - 1;
				this.cant_txt.setText(valor.toString());
				this.cant_txt.setEditable(false);
			}
		} else if (evt.getSource() == this.mas) {
			this.cant_txt.setEditable(true);
			Integer valor = Integer.parseInt(this.cant_txt.getText()) + 1;
			this.cant_txt.setText(valor.toString());
			this.cant_txt.setEditable(false);
		}
		this.sbtotal_txt.setEditable(true);
		Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario)
				* Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
		this.sbtotal_txt.setText(String.valueOf(precio));
		this.sbtotal_txt.setHorizontalAlignment(SwingConstants.CENTER);
		this.sbtotal_txt.setEditable(false);
	}

	// WindowListener
	@Override
	public void windowClosing(WindowEvent evt) {
		closeSesion();
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {

	}

	@Override
	public void windowActivated(WindowEvent evt) {

	}

	@Override
	public void windowDeiconified(WindowEvent evt) {

	}

	@Override
	public void windowIconified(WindowEvent evt) {

	}

	@Override
	public void windowClosed(WindowEvent evt) {

	}

	@Override
	public void windowOpened(WindowEvent evt) {

	}

	// ItemListener
	@Override
	public void itemStateChanged(ItemEvent evt) {
		if (evt.getSource() == this.tipo_prod) {
			if (evt.getStateChange() == 1) {
				tipoProducto = tipo_prod.getSelectedItem().toString();
				prod_com.removeAllItems();
				try {
					rs = st.executeQuery("SELECT nom_prod FROM Producto WHERE tipo_prod = '" + tipoProducto + "'");
					Integer x = 0;
					while (rs.next()) {
						prod_com.addItem(rs.getString("nom_prod"));
						x++;
					}
					System.out.println(x);
				} catch (SQLException err) {
					JOptionPane.showMessageDialog(null, err);
				}
			}
		} else if (evt.getSource() == this.prod_com) {
			if (evt.getStateChange() == 1) {
				try {
					try {
						rs = st.executeQuery("SELECT nom_prod FROM Producto WHERE tipo_prod = '" + tipoProducto + "'");
						Integer x = 0;
						while (rs.next()) {
							prod_com.addItem(rs.getString("nom_prod"));
							x++;
						}
						prod_com.removeItemAt(0);
						// System.out.println(x);
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err);
					}
					// Se consulta la id y precio del producto seleccionado
					nomProducto = prod_com.getSelectedItem().toString();
					rs = st.executeQuery(
							"SELECT id_prod, prec_prod FROM Producto WHERE nom_prod = '" + nomProducto + "'");
					rs.next();

					// Id del producto
					this.idProducto = rs.getString("id_prod");
					id_prod_txt.setEditable(true);
					id_prod_txt.setText(this.idProducto);
					id_prod_txt.setEditable(false);

					// Precio unitario del producto
					this.precioUnitario = rs.getString("prec_prod");
					pre_uni_txt.setEditable(true);
					pre_uni_txt.setText(this.precioUnitario);
					pre_uni_txt.setEditable(false);

					// Sacamos el subtotal del producto acorde a la cantidad solicitada
					this.sbtotal_txt.setEditable(true);
					Double precio = Double.parseDouble(this.cant_txt.getText())
							* Double.parseDouble(this.precioUnitario) * Double.parseDouble(dim_largo.getText())
							* Double.parseDouble(dim_ancho.getText());
					this.sbtotal_txt.setText(String.valueOf(precio));
					this.sbtotal_txt.setEditable(false);
				} catch (SQLException err) {
					JOptionPane.showMessageDialog(null, err);
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		// TODO Auto-generated method stub
		if (evt.getSource().equals(this.bar)) {
			int xScreen = evt.getXOnScreen();
			int yScreen = evt.getYOnScreen();
			this.setLocation(xScreen - x, yScreen - y);
		}
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		// TODO Auto-generated method stub
		x = evt.getX();
		y = evt.getY();
	}
}