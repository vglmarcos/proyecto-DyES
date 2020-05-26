import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Cotizacion extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener, ItemListener {

	private Color blue = new Color(0, 153, 153);
  	private Color blue2 = new Color(2, 199, 199);
  	private Color blue3= new Color(0, 220, 220);
 	private Color blue4 = new Color(0, 243, 243);
  	private Color bluefocus = new Color(167, 255, 255);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0, 0, 0);
	private Color gray = new Color(224, 224, 224);
	private JLabel tira, tira2, tira3, tira4, rights;
	private JButton cancelar, agregar, guardar, borrar, agregarcot, regresar;
	private JLabel no_cot, fechaLabel, sbt, total, iva, tot_txt, iva_txt, sbt_txt, anti, pend, id_cliente, nom_cliente, tel, dir, corr;
	private JLabel prod, pre_uni, dim, x, cant, sbtotal, mas, menos, tipo, id_prod, diseno;
	private JTextField no_cotField, pre_uni_txt, dim_largo, dim_ancho, cant_txt, sbtotal_txt, id_prod_txt, diseno_txt;
	private JTextField id_cliente_txt, nom_cliente_txt, tel_txt, dir_txt, corr_txt, anti_txt, pend_txt;
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Integer id, idCliente;
	private Calendar fecha;
	private String dia, mes, anio;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JScrollPane scroll;
	private JPanel agregarCot, mostrarCot;
	private JComboBox<String> prod_com, tipo_prod;
	private String tipoProducto, nomProducto, precioUnitario, idProducto;
	private Double totalCot = 0.00;
	private Boolean existClient = false;

	public Cotizacion(String title) {
		this.setLayout(null);;
		this.setResizable(false);
		this.setBounds(0, 0, 810, 650);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(white);
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
		this.addWindowListener(this);

		idCliente = 0;

		//Panel para agregar cotizacion
		agregarCot = new JPanel();
		agregarCot.setBackground(white);
		agregarCot.setBounds(0, 0, 810, 650);
		agregarCot.setLayout(null);
		agregarCot.setVisible(false);

		//Panel para mostrar detalles de cotizacion
		mostrarCot = new JPanel();
		mostrarCot.setBackground(white);
		mostrarCot.setBounds(0, 0, 810, 650);
		mostrarCot.setLayout(null);
		mostrarCot.setVisible(true);

		//obtenemos fecha actual
		fecha = Calendar.getInstance();
		dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
		mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
		anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

		// iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConnection().createStatement();
			rs = st.executeQuery("SELECT MAX(id_cot) FROM Cotizacion");
			rs.next();
			id = rs.getInt(1) + 1; //maxima id de cotizaciones + 1
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}

		panelMostrarCot();
		panelAgregarCot();

		this.add(mostrarCot);
		this.add(agregarCot);
	}

	public void panelMostrarCot() {
		no_cot = new JLabel("Id Cotizaci\u00F3n");
		no_cot.setBounds(30, 57, 140, 25);
		no_cot.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		no_cot.setForeground(blue);
		mostrarCot.add(no_cot);

		no_cotField = new JTextField();
		no_cotField.setBounds(159, 54, 50, 30);
		no_cotField.setBackground(white);
		no_cotField.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		no_cotField.setText(id.toString());
		no_cotField.setHorizontalAlignment(JTextField.CENTER);
		no_cotField.setEditable(false);
		no_cotField.setForeground(blue);
		mostrarCot.add(no_cotField);

		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(618, 54, 152, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		fechaLabel.setForeground(black);
		mostrarCot.add(fechaLabel);

		id_cliente = new JLabel("Id Cliente");
		id_cliente.setBounds(57, 97, 80, 25);
		id_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_cliente.setForeground(black);
		mostrarCot.add(id_cliente);

		id_cliente_txt = new JTextField();
		id_cliente_txt.setBounds(159, 95, 50, 30);
		id_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_cliente_txt.setHorizontalAlignment(JTextField.CENTER);
		id_cliente_txt.setEditable(false);
		id_cliente_txt.setBackground(gray);
		id_cliente_txt.setForeground(black);
		id_cliente_txt.addFocusListener(this);
		mostrarCot.add(id_cliente_txt);

		nom_cliente = new JLabel("Nombre");
		nom_cliente.setBounds(228, 97, 70, 25);
		nom_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nom_cliente.setForeground(black);
		mostrarCot.add(nom_cliente);

		nom_cliente_txt = new JTextField();
		nom_cliente_txt.setBounds(310, 95, 461, 30);
		nom_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nom_cliente_txt.setBackground(gray);
		nom_cliente_txt.setForeground(black);
		nom_cliente_txt.addFocusListener(this);
		mostrarCot.add(nom_cliente_txt);

		dir = new JLabel("Direcci\u00F3n");
		dir.setBounds(57, 141, 80, 25);
		dir.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dir.setForeground(black);
		mostrarCot.add(dir);

		dir_txt = new JTextField();
		dir_txt.setBounds(159, 139, 612, 30);
		dir_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dir_txt.setBackground(gray);
		dir_txt.setForeground(black);
		dir_txt.addFocusListener(this);
		mostrarCot.add(dir_txt);

		tel = new JLabel("T\u00E9lefono");
		tel.setBounds(57, 184, 75, 25);
		tel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tel.setForeground(black);
		mostrarCot.add(tel);

		tel_txt = new JTextField();
		tel_txt.setBounds(159, 182, 171, 30);
		tel_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tel_txt.setBackground(gray);
		tel_txt.setForeground(black);
		tel_txt.addFocusListener(this);
		mostrarCot.add(tel_txt);

		corr = new JLabel("Correo");
		corr.setBounds(359, 184, 58, 25);
		corr.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		corr.setForeground(black);
		mostrarCot.add(corr);

		corr_txt = new JTextField();
		corr_txt.setBounds(444, 182, 327, 30);
		corr_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		corr_txt.setBackground(gray);
		corr_txt.setForeground(black);
		corr_txt.addFocusListener(this);
		mostrarCot.add(corr_txt);

		String[] campos = new String[]{"Id", "Nombre", "Tipo", "P. Unitario", "Dise\u00F1o", "Largo", "Ancho",
									   "Cantidad", "Precio total"};

		modelo = new DefaultTableModel(null, campos);
    	tabla = new JTable(modelo) {
    		@Override
      		public boolean isCellEditable(int row, int column) {
        	//all cells false
        	return false;
     		}
    	};

    	tabla.getTableHeader().setReorderingAllowed(false);
    	tabla.getTableHeader().setResizingAllowed(false);
    	scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 225, 745, 180);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(25); //Id
		tabla.getColumnModel().getColumn(1).setPreferredWidth(140); //Nombre
		tabla.getColumnModel().getColumn(2).setPreferredWidth(70); //Tipo
		tabla.getColumnModel().getColumn(3).setPreferredWidth(95); //Precio unitario
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80); //Diseño
		tabla.getColumnModel().getColumn(5).setPreferredWidth(60); //Largo
		tabla.getColumnModel().getColumn(6).setPreferredWidth(60); //Ancho
		tabla.getColumnModel().getColumn(7).setPreferredWidth(80); //Cantidad
		tabla.getColumnModel().getColumn(8).setPreferredWidth(95); //Precio total
		tabla.setRowHeight(25);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 16));
		tabla.getTableHeader().setForeground(white);
		tabla.getTableHeader().setBackground(blue);
		tabla.setBackground(white);
		tabla.setForeground(black);
		tabla.setFont(new Font("Microsoft New Tai Lue", 0, 14));
		mostrarCot.add(scroll);

		sbt = new JLabel("Subtotal: ");
		sbt.setBounds(103, 422, 100, 25);
		sbt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbt.setForeground(black);
		mostrarCot.add(sbt);

		sbt_txt = new JLabel("0", SwingConstants.CENTER);
		sbt_txt.setBounds(200, 422, 150, 25);
		sbt_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		sbt_txt.setForeground(blue);
		mostrarCot.add(sbt_txt);

		iva = new JLabel("IVA 16 \u0025:");
		iva.setBounds(103, 457, 80, 25);
		iva.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		iva.setForeground(black);
		mostrarCot.add(iva);

		iva_txt = new JLabel("0", SwingConstants.CENTER);
		iva_txt.setBounds(200, 457, 150, 25);
		iva_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		iva_txt.setForeground(blue);
		mostrarCot.add(iva_txt);

		total = new JLabel("Total: ");
		total.setBounds(103, 492, 80, 25);
		total.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		total.setForeground(black);
		mostrarCot.add(total);

		tot_txt = new JLabel("0", SwingConstants.CENTER);
		tot_txt.setBounds(200, 492, 150, 25);
		tot_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		tot_txt.setForeground(blue);
		mostrarCot.add(tot_txt);

		anti = new JLabel("Anticipo: ");
		anti.setBounds(455, 422, 110, 25);
		anti.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		anti.setForeground(black);
		mostrarCot.add(anti);

		anti_txt = new JTextField("0");
		anti_txt.setBounds(565, 419, 150, 30);
		anti_txt.setBackground(gray);
		anti_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		anti_txt.setForeground(blue);
		anti_txt.setHorizontalAlignment(JTextField.CENTER);
		anti_txt.addFocusListener(this);
		mostrarCot.add(anti_txt);

		pend = new JLabel("Pendiente: ");
		pend.setBounds(455, 457, 110, 25);
		pend.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pend.setForeground(black);
		mostrarCot.add(pend);

		pend_txt = new JTextField("");
		pend_txt.setBounds(565, 454, 150, 30);
		pend_txt.setBackground(white);
		pend_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		pend_txt.setForeground(blue);
		pend_txt.setHorizontalAlignment(JTextField.CENTER);
		pend_txt.setEditable(false);
		mostrarCot.add(pend_txt);

		cancelar = new JButton("Cancelar");
		cancelar.setBounds(82, 540, 100, 30);
		cancelar.setBackground(blue);
		cancelar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		cancelar.setForeground(white);
		cancelar.addFocusListener(this);
		cancelar.addActionListener(this);
		cancelar.addMouseListener(this);
		mostrarCot.add(cancelar);

		borrar = new JButton("Borrar");
		borrar.setBounds(264, 540, 100, 30);
		borrar.setBackground(blue);
		borrar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		borrar.setForeground(white);
		borrar.addFocusListener(this);
		borrar.addActionListener(this);
		borrar.addMouseListener(this);
		mostrarCot.add(borrar);

		agregar = new JButton("Agregar");
		agregar.setBounds(446, 540, 100, 30);
		agregar.setBackground(blue);
		agregar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		agregar.setForeground(white);
		agregar.addFocusListener(this);
		agregar.addActionListener(this);
		agregar.addMouseListener(this);
		mostrarCot.add(agregar);

		guardar = new JButton("Guardar");
		guardar.setBounds(628, 540, 100, 30);
		guardar.setBackground(blue);
		guardar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		guardar.setForeground(white);
		guardar.addFocusListener(this);
		guardar.addActionListener(this);
		guardar.addMouseListener(this);
		mostrarCot.add(guardar);

		rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.",SwingConstants.CENTER);
		rights.setBounds(0, 591, 810, 30);
		rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
		rights.setOpaque(true);
		rights.setBackground(black);
		rights.setForeground(white);
        mostrarCot.add(rights);

        tira = new JLabel();
        tira.setBounds(0,0,810,20);
        tira.setBackground(blue);
        tira.setOpaque(true);
        add(tira);

        tira2 = new JLabel();
        tira2.setBounds(0,20,810,9);
        tira2.setBackground(blue2);
        tira2.setOpaque(true);
        add(tira2);

        tira3 = new JLabel();
        tira3.setBounds(0,29,810,7);
        tira3.setBackground(blue3);
        tira3.setOpaque(true);
        add(tira3);

        tira4 = new JLabel();
        tira4.setBounds(0,36,810,4);
        tira4.setBackground(blue4);
        tira4.setOpaque(true);
        add(tira4);
	}

	public void panelAgregarCot() {
		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(618, 91, 152, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		fechaLabel.setForeground(black);
		agregarCot.add(fechaLabel);

		id_prod = new JLabel("Id Producto");
		id_prod.setBounds(490, 219, 128, 25);
		id_prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_prod.setForeground(black);
		agregarCot.add(id_prod);

		id_prod_txt = new JTextField();
		id_prod_txt.setText("");
		id_prod_txt.setBounds(623, 216, 50, 30);
		id_prod_txt.setBackground(white);
		id_prod_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		id_prod_txt.setForeground(black);
		id_prod_txt.setHorizontalAlignment(JTextField.CENTER);
		id_prod_txt.setEditable(false);
		agregarCot.add(id_prod_txt);

		pre_uni = new JLabel("Precio unitario");
		pre_uni.setBounds(57, 288, 124, 25);
		pre_uni.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pre_uni.setForeground(black);
		agregarCot.add(pre_uni);

		pre_uni_txt = new JTextField();
		pre_uni_txt.setText("0");
		pre_uni_txt.setBounds(188, 285, 120, 30);
		pre_uni_txt.setBackground(white);
		pre_uni_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pre_uni_txt.setForeground(black);
		pre_uni_txt.setEditable(false);
		agregarCot.add(pre_uni_txt);

		tipo = new JLabel("Tipo");
		tipo.setBounds(57, 150, 45, 25);
		tipo.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		tipo.setForeground(black);
		agregarCot.add(tipo);

		tipo_prod = new JComboBox<>();
		tipo_prod.setBounds(120, 147, 223, 30);
        tipo_prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        tipo_prod.setBackground(gray);
		tipo_prod.setForeground(black);
		tipo_prod.addFocusListener(this);

		try {
			rs = st.executeQuery("SELECT DISTINCT(tipo_prod) FROM Producto");
			while(rs.next()) {
				tipo_prod.addItem(rs.getString(1));
			}
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, err);
		}

		tipo_prod.addItemListener(this);
		agregarCot.add(tipo_prod);

		prod = new JLabel("Producto");
		prod.setBounds(57, 219, 77, 25);
		prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		prod.setForeground(black);
		agregarCot.add(prod);

		prod_com = new JComboBox<>();
		prod_com.setBounds(163, 216, 300, 30);
        prod_com.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        prod_com.setBackground(gray);
		prod_com.setForeground(black);
		prod_com.addFocusListener(this);
		prod_com.addKeyListener(this);

		tipoProducto = tipo_prod.getSelectedItem().toString();

		try {
			rs = st.executeQuery("SELECT nom_prod FROM Producto WHERE tipo_prod = '" + tipoProducto + "'");
			while(rs.next()) {
				prod_com.addItem(rs.getString("nom_prod"));
			}
		} catch(SQLException err) {
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
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, err);
		}

		prod_com.addItemListener(this);
		agregarCot.add(prod_com);

		diseno = new JLabel("Dise\u00F1o");
		diseno.setBounds(345, 288, 60, 25);
		diseno.setFont(new Font ("Microsoft New Tai Lue", 0, 18));
		diseno.setForeground(black);
		agregarCot.add(diseno);

		diseno_txt = new JTextField();
		diseno_txt.setBounds(432, 285, 133, 30);
		diseno_txt.setBackground(gray);
		diseno_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		diseno_txt.setForeground(black);
		diseno_txt.addFocusListener(this);
		agregarCot.add(diseno_txt);

		dim = new JLabel("Dimensiones");
		dim.setBounds(57, 357, 113, 25); //288
		dim.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim.setForeground(black);
		agregarCot.add(dim);

		dim_largo = new JTextField("0");
		dim_largo.setBounds(182, 354, 107, 30);
		dim_largo.setBackground(gray);
		dim_largo.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim_largo.setForeground(black);
		dim_largo.setHorizontalAlignment(JTextField.CENTER);
		dim_largo.addFocusListener(this);
		agregarCot.add(dim_largo);

		x = new JLabel("X");
		x.setBounds(316, 357, 11, 25);
		x.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		x.setForeground(black);
		agregarCot.add(x);

		dim_ancho = new JTextField("0");
		dim_ancho.setBounds(354, 354, 107, 30);
		dim_ancho.setBackground(gray);
		dim_ancho.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim_ancho.setForeground(black);
		dim_ancho.setHorizontalAlignment(JTextField.CENTER);
		dim_ancho.addFocusListener(this);
		agregarCot.add(dim_ancho);

		cant = new JLabel("Cantidad");
		cant.setBounds(58, 423, 76, 25);
		cant.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cant.setForeground(black);
		agregarCot.add(cant);

		cant_txt = new JTextField();
		cant_txt.setBounds(153, 420, 90, 30);
		cant_txt.setBackground(white);
		cant_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cant_txt.setForeground(black);
		cant_txt.setText("1");
		cant_txt.setHorizontalAlignment(JTextField.CENTER);
		cant_txt.setEditable(false);
		agregarCot.add(cant_txt);

		ImageIcon mas_imagen = new ImageIcon("./images/mas.png");
		mas = new JLabel(mas_imagen);
		mas.setBounds(306, 420, 30, 30);
		mas.addMouseListener(this);
		mas.addFocusListener(this);
		agregarCot.add(mas);

		ImageIcon menos_imagen = new ImageIcon("./images/menos.png");
		menos = new JLabel(menos_imagen);
		menos.setBounds(258, 420, 30, 30);
		menos.addMouseListener(this);
		menos.addFocusListener(this);
		agregarCot.add(menos);

		sbtotal = new JLabel("Subtotal");
		sbtotal.setBounds(370, 423, 70, 25);
		sbtotal.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbtotal.setForeground(black);
		agregarCot.add(sbtotal);

		sbtotal_txt = new JTextField();
		sbtotal_txt.setBounds(462, 420, 133, 30);
		sbtotal_txt.setBackground(white);
		sbtotal_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbtotal_txt.setForeground(black);
		sbtotal_txt.setText("0");
		sbtotal_txt.setEditable(false);
		agregarCot.add(sbtotal_txt);

		regresar = new JButton("Regresar");
		regresar.setBounds(494, 503, 100, 30);
		regresar.setBackground(blue);
		regresar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		regresar.setForeground(white);
		regresar.addActionListener(this);
		regresar.addKeyListener(this);
		regresar.addFocusListener(this);
		regresar.addMouseListener(this);
		agregarCot.add(regresar);

		agregarcot = new JButton("Agregar");
		agregarcot.setBounds(644, 503, 100, 30);
		agregarcot.setBackground(blue);
		agregarcot.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		agregarcot.setForeground(white);
		agregarcot.addFocusListener(this);
		agregarcot.addKeyListener(this);
		agregarcot.addActionListener(this);
		agregarcot.addMouseListener(this);
		agregarCot.add(agregarcot);

		rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.",SwingConstants.CENTER);
		rights.setBounds(0, 591, 810, 30);
		rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
		rights.setOpaque(true);
		rights.setBackground(black);
		rights.setForeground(white);
        agregarCot.add(rights);

        tira = new JLabel();
        tira.setBounds(0,0,810,20);
        tira.setBackground(blue);
        tira.setOpaque(true);
        agregarCot.add(tira);

        tira2 = new JLabel();
        tira2.setBounds(0,20,810,9);
        tira2.setBackground(blue2);
        tira2.setOpaque(true);
        agregarCot.add(tira2);

        tira3 = new JLabel();
        tira3.setBounds(0,29,810,7);
        tira3.setBackground(blue3);
        tira3.setOpaque(true);
        agregarCot.add(tira3);

        tira4 = new JLabel();
        tira4.setBounds(0,36,810,4);
        tira4.setBackground(blue4);
        tira4.setOpaque(true);
        agregarCot.add(tira4);
	}

	//ActionListener
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == this.cancelar) { //Boton cancelar
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(evt.getSource() == this.agregar) {
			mostrarCot.setVisible(false);
			agregarCot.setVisible(true);
		} else if(evt.getSource() == this.regresar) {
			mostrarCot.setVisible(true);
			agregarCot.setVisible(false);
		} else if(evt.getSource() == this.agregarcot){
			//datos producto
			nomProducto = prod_com.getSelectedItem().toString();
			String disProducto = diseno_txt.getText();
			String largoProducto = dim_largo.getText();
			String anchoProducto = dim_ancho.getText();
			String cantidadProducto = cant_txt.getText();
			String sbtProducto = sbtotal_txt.getText();

			//Comprobamos si el usuario no dejo campos vacios
			if(disProducto.isEmpty() || largoProducto.isEmpty() || anchoProducto.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
			} else if(cantidadProducto.equals("0")) {
				JOptionPane.showMessageDialog(null, "Debe agregar al menos un producto.");
			} else {
				//validamos las dimensiones
				try {
					Double largo = 0.0, ancho = 0.0;
					largo = Double.parseDouble(dim_largo.getText());
					ancho = Double.parseDouble(dim_ancho.getText());
					try {
						rs = st.executeQuery("SELECT id_prod, prec_prod FROM Producto WHERE nom_prod = '" + nomProducto + "'");
						rs.next();

						idProducto = rs.getString("id_prod");
						id_prod_txt.setEditable(true);
						id_prod_txt.setText(idProducto);
						id_prod_txt.setEditable(false);

						precioUnitario = rs.getString("prec_prod");
						pre_uni_txt.setEditable(true);
						pre_uni_txt.setText(precioUnitario);
						pre_uni_txt.setEditable(false);

						modelo.addRow(new String[]{idProducto, nomProducto, tipoProducto, precioUnitario, disProducto,
													largoProducto, anchoProducto, cantidadProducto, sbtProducto});

						Double subtotalCot = 0.00;
						Double valor;

						for (int i = 0; i < tabla.getRowCount(); i++) {
							try {
								valor = Double.parseDouble(tabla.getValueAt(i, 8).toString());
								subtotalCot += valor;
							} catch(NumberFormatException err) {
								System.out.println(err);
							}
						}

						sbt_txt.setText(subtotalCot.toString());

						Double ivaSbt = subtotalCot * 0.16;
						totalCot = subtotalCot + ivaSbt;

						tot_txt.setText(totalCot.toString());
						iva_txt.setText(ivaSbt.toString());

					} catch(SQLException err) {
						JOptionPane.showMessageDialog(null, err);
					}
					mostrarCot.setVisible(true);
					agregarCot.setVisible(false);
				} catch(NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Solamente debe ingresar n\u00FAmeros.");
					System.out.println(err);
				}
			}
		} else if(evt.getSource() == this.guardar) {
			String nomCliente = nom_cliente_txt.getText();
			String telCliente = tel_txt.getText();
			String dirCliente = dir_txt.getText();
			String corrCliente = corr_txt.getText();
			String antiCliente = anti_txt.getText();
			String pendCliente = pend_txt.getText();
			String idEmpleado = "";
			if(nomCliente.isEmpty() || telCliente.isEmpty() || dirCliente.isEmpty() ||corrCliente.isEmpty() || antiCliente.isEmpty() || pendCliente.isEmpty()){
				JOptionPane.showMessageDialog(null, "Se deben llenar todos los campos.");
			} else {

				try {
					double anticipo = Double.parseDouble(antiCliente);
					if(!(anticipo < 0.0)) {
						try {
							if(!existClient) { //si no existe el cliente, obtenemos al empleado que hizo la cotizacion
								rs = st.executeQuery("SELECT id_usu, sesion_act FROM Usuario");
								while(rs.next()) {
									if(rs.getString("sesion_act").equals("s")) {
										idEmpleado = rs.getString("id_usu");
										break;
									}
								}
								//Se agrega el cliente a la db
								String camposCliente = "'" + idCliente.toString() +  "', '" + idEmpleado + "', '" + nomCliente +
								"', '" + telCliente + "', '" + dirCliente + "', '" + corrCliente + "'";
								st.executeUpdate("INSERT INTO Cliente (id_cl, id_usu, nom_cl, tel_cl, dir_cl, corr_cl)" +
								" VALUES (" + camposCliente + ")");
							} else {
								rs = st.executeQuery("SELECT id_usu FROM Cliente WHERE id_cl = '" + idCliente.toString() + "'");
								rs.next();
								idEmpleado = rs.getString("id_usu");
							}

							//Se agrega la cotizacion a la db
							String camposCotizacion = "'" + no_cotField.getText() + "', '" + idCliente.toString() +  "', '" + idEmpleado + "', '" +
							sbt_txt.getText() + "', '" + iva_txt.getText() + "', '" + tot_txt.getText() + "', '" + anti_txt.getText() +
							"', '" + pend_txt.getText() + "'";
							st.executeUpdate("INSERT INTO Cotizacion (id_cot, id_cl, id_usu, sub_cot, iva_cot, tot_cot, ant_cot, pend_cot)" +
							" VALUES (" + camposCotizacion + ")");

							//Se agrega el carrito a la db
							rs = st.executeQuery("SELECT MAX(id_carrito) FROM Carrito");
							rs.next();
							Integer idCarrito = rs.getInt(1) + 1;
							String idProd = "";
							String disProd = "";
							String larProd = "";
							String anchProd = "";
							String canProd = "";
							String sbtProd = "";
							for (int row = 0; row < tabla.getRowCount(); row++){ //Recorremos la tabla para obtener los datos
        				DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        				idProd = model.getValueAt(row, 0).toString();
								disProd = model.getValueAt(row, 4).toString();
								larProd = model.getValueAt(row, 5).toString();
								anchProd = model.getValueAt(row, 6).toString();
								canProd = model.getValueAt(row, 7).toString();
								sbtProd = model.getValueAt(row, 8).toString();
								String camposCarrito = "'" + idCarrito.toString() + "', '" + idProd + "', '" + no_cotField.getText() +  "', '" +
								anchProd + "', '" + larProd + "', '" + disProd + "', '" + canProd + "', '" + sbtProd + "'";
								st.executeUpdate("INSERT INTO Carrito (id_carrito, id_prod, id_cot, ancho_prod, largo_prod, dis_prod, cant_prod, subt_prod)" +
								" VALUES (" + camposCarrito + ")");
								idCarrito++;
							}

							// Pasamos a mostrar el recibo
							db.desconectar();
							this.setVisible(false);
							Recibo r1 = new Recibo("Recibo", idCliente.toString(), idEmpleado.toString());
							r1.setVisible(true);
						} catch(SQLException err) {
							JOptionPane.showMessageDialog(null, err.toString());
						}
					}
				} catch(NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "El anticipo debe tomar valores enteros.");
					System.out.println(err);
				}
			}
		}
	}

	//KeyListener
	@Override
	public void keyTyped(KeyEvent evt) {
       /* if(evt.getSource() == this.no_cotField) {
			if(this.no_cotField.getText().length() >= 4) {
				evt.consume();
			}
		} */
    }

	@Override
	public void keyReleased(KeyEvent evt) {

    }

	@Override
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == 10){
			if(evt.getSource() == this.agregar) {
				mostrarCot.setVisible(false);
				agregarCot.setVisible(true);
			} else if(evt.getSource() == this.regresar) {
				mostrarCot.setVisible(true);
				agregarCot.setVisible(false);
			}
		}
	}

	//FocusListener
	@Override
    public void focusGained(FocusEvent evt) {
		if(evt.getSource() == this.id_cliente_txt) {
			this.id_cliente_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.nom_cliente_txt) {
			this.nom_cliente_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this. tel_txt) {
			this.tel_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this. dir_txt) {
			this.dir_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.corr_txt) {
			this.corr_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.prod_com) {
			this.prod_com.setBackground(bluefocus);
		} else if (evt.getSource() == this.dim_largo) {
			this.dim_largo.setBackground(bluefocus);
			dim_largo.setText("");
		} else if (evt.getSource() == this.dim_ancho) {
			this.dim_ancho.setBackground(bluefocus);
			dim_ancho.setText("");
		} else if (evt.getSource() == this.tipo_prod) {
			this.tipo_prod.setBackground(bluefocus);
		} else if (evt.getSource() == this.diseno_txt) {
			this.diseno_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.anti_txt) {
			this.anti_txt.setBackground(bluefocus);
			anti_txt.setText("");
		} else if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(bluefocus);
			this.cancelar.setForeground(black);
		} else if (evt.getSource() == this.borrar) {
			this.borrar.setBackground(bluefocus);
			this.borrar.setForeground(black);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(bluefocus);
			this.agregar.setForeground(black);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(bluefocus);
			this.guardar.setForeground(black);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(bluefocus);
			this.regresar.setForeground(black);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(bluefocus);
			this.agregarcot.setForeground(black);
		}
    }

    @Override
    public void focusLost(FocusEvent evt) {
		if(evt.getSource() == this.id_cliente_txt) {
			this.id_cliente_txt.setBackground(gray);
		} else if (evt.getSource() == this.nom_cliente_txt) {
			String nombre = nom_cliente_txt.getText();
			if(!nombre.equals("")) {
				try {
					rs = st.executeQuery("SELECT id_cl, tel_cl, dir_cl, corr_cl FROM Cliente WHERE nom_cl = '" + nombre + "'");
					existClient = rs.next();
					if(existClient) { //si existe se le asigna la misma id
						idCliente = rs.getInt("id_cl");
						id_cliente_txt.setEditable(true);
						id_cliente_txt.setText(idCliente.toString());
						id_cliente_txt.setEditable(false);

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
					} else { //si no se crea una nueva id
						rs = st.executeQuery("SELECT MAX(id_cl) FROM Cliente");
						rs.next();
						idCliente = rs.getInt(1) + 1; //maxima id de clientes + 1
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
				} catch(SQLException err) {
					System.out.println(err.toString());
				}
			}
			this.nom_cliente_txt.setBackground(gray);
		} else if (evt.getSource() == this. tel_txt) {
			this.tel_txt.setBackground(gray);
		} else if (evt.getSource() == this. dir_txt) {
			this.dir_txt.setBackground(gray);
		} else if (evt.getSource() == this.corr_txt) {
			this.corr_txt.setBackground(gray);
		} else if (evt.getSource() == this.prod_com) {
			this.prod_com.setBackground(gray);
		} else if (evt.getSource() == this.dim_largo) {
			try {
				this.dim_largo.setBackground(gray);
				this.sbtotal_txt.setEditable(true);
				Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario) * Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
				this.sbtotal_txt.setText(String.valueOf(precio));
				this.sbtotal_txt.setEditable(false);
			} catch(NumberFormatException err) {
				System.out.println(err);
			}
		} else if (evt.getSource() == this.dim_ancho) {
			try {
				this.dim_ancho.setBackground(gray);
				this.sbtotal_txt.setEditable(true);
				Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario) * Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
				this.sbtotal_txt.setText(String.valueOf(precio));
				this.sbtotal_txt.setEditable(false);
			} catch(NumberFormatException err) {
				System.out.println(err);
			}
		} else if (evt.getSource() == this.tipo_prod) {
			this.tipo_prod.setBackground(gray);
		} else if (evt.getSource() == this.diseno_txt) {
			this.diseno_txt.setBackground(gray);
		} else if (evt.getSource() == this.anti_txt) {
			try {
				this.anti_txt.setBackground(gray);
				if (Double.parseDouble(this.anti_txt.getText()) < 0)  {
					JOptionPane.showMessageDialog(null, "No se puede dar un anticipo negativo", "Error", JOptionPane.ERROR_MESSAGE);
					anti_txt.setText("");
					pend_txt.setText("");
				} else if (Double.parseDouble(this.anti_txt.getText()) > Double.parseDouble(this.tot_txt.getText())){
					JOptionPane.showMessageDialog(null, "No se puede dar un anticipo mayor al total", "Error", JOptionPane.ERROR_MESSAGE);
					anti_txt.setText("");
					pend_txt.setText("");
				} else if (Double.parseDouble(this.anti_txt.getText()) < Double.parseDouble(this.tot_txt.getText())*0.5){
					JOptionPane.showMessageDialog(null, "No se puede dar un anticipo menor al 50 \u0025 del total", "Error", JOptionPane.ERROR_MESSAGE);
					anti_txt.setText("");
					pend_txt.setText("");
				} else {
					Double pago = Double.parseDouble(this.tot_txt.getText()) - Double.parseDouble(this.anti_txt.getText());
					Double pendiente;
					pendiente = pago;
					Redondear(pendiente, 2);
					pend_txt.setText(pendiente.toString());
					pend_txt.setEditable(false);
				}
			} catch(NumberFormatException err) {
				System.out.println(err);
			}
		} else if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(blue);
			this.cancelar.setForeground(white);
		} else if (evt.getSource() == this.borrar) {
			this.borrar.setBackground(blue);
			this.borrar.setForeground(white);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(blue);
			this.agregar.setForeground(white);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(blue);
			this.guardar.setForeground(white);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(blue);
			this.regresar.setForeground(white);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(blue);
			this.agregarcot.setForeground(white);
		}
	}
	
	public static double Redondear(double numero,int digitos) {
        int cifras=(int) Math.pow(10,digitos);
        return Math.rint(numero*cifras)/cifras;
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

	//MouseListener
	@Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(blue);
			this.cancelar.setForeground(white);
		} else if (evt.getSource() == this.borrar) {
			this.borrar.setBackground(blue);
			this.borrar.setForeground(white);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(blue);
			this.agregar.setForeground(white);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(blue);
			this.guardar.setForeground(white);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(blue);
			this.regresar.setForeground(white);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(blue);
			this.agregarcot.setForeground(white);
		}
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
		this.cancelar.setBackground(blue);
		this.cancelar.setForeground(white);
		this.borrar.setBackground(blue);
		this.borrar.setForeground(white);
		this.agregar.setBackground(blue);
		this.agregar.setForeground(white);
		this.guardar.setBackground(blue);
		this.guardar.setForeground(white);
		this.regresar.setBackground(blue);
		this.regresar.setForeground(white);
		this.agregarcot.setBackground(blue);
		this.agregarcot.setForeground(white);
		if (evt.getSource() == this.cancelar) {
			this.cancelar.setBackground(bluefocus);
			this.cancelar.setForeground(black);
		} else if (evt.getSource() == this.borrar) {
			this.borrar.setBackground(bluefocus);
			this.borrar.setForeground(black);
		} else if (evt.getSource() == this.agregar) {
			this.agregar.setBackground(bluefocus);
			this.agregar.setForeground(black);
		} else if (evt.getSource() == this.guardar) {
			this.guardar.setBackground(bluefocus);
			this.guardar.setForeground(black);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(bluefocus);
			this.regresar.setForeground(black);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(bluefocus);
			this.agregarcot.setForeground(black);
		}
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if(evt.getSource() == this.menos) {
			if(Integer.parseInt(this.cant_txt.getText()) > 0) {
				this.cant_txt.setEditable(true);
				Integer valor = Integer.parseInt(this.cant_txt.getText()) - 1;
				this.cant_txt.setText(valor.toString());
				this.cant_txt.setEditable(false);
			}
		} else if(evt.getSource() == this.mas) {
			this.cant_txt.setEditable(true);
			Integer valor = Integer.parseInt(this.cant_txt.getText()) + 1;
			this.cant_txt.setText(valor.toString());
			this.cant_txt.setEditable(false);
		}
		this.sbtotal_txt.setEditable(true);
		Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario) * Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
		this.sbtotal_txt.setText(String.valueOf(precio));
		this.sbtotal_txt.setEditable(false);
	}

	//WindowListener
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

	//ItemListener
	@Override
	public void itemStateChanged(ItemEvent evt) {
		if (evt.getSource() == this.tipo_prod) {
	   		if(evt.getStateChange() == 1) {
				tipoProducto = tipo_prod.getSelectedItem().toString();
				prod_com.removeAllItems();
					try {
						rs = st.executeQuery("SELECT nom_prod FROM Producto WHERE tipo_prod = '" + tipoProducto + "'");
						Integer x = 0;
						while(rs.next()) {
							prod_com.addItem(rs.getString("nom_prod"));
							x++;
						}
						System.out.println(x);
					} catch(SQLException err) {
						JOptionPane.showMessageDialog(null, err);
					}
       	  	}
       	} else if(evt.getSource() == this.prod_com) {
			if(evt.getStateChange() == 1) {
				try {
					try {
						rs = st.executeQuery("SELECT nom_prod FROM Producto WHERE tipo_prod = '" + tipoProducto + "'");
						Integer x = 0;
						while(rs.next()) {
							prod_com.addItem(rs.getString("nom_prod"));
							x++;
						}
						prod_com.removeItemAt(0);
						//System.out.println(x);
					} catch(SQLException err) {
						JOptionPane.showMessageDialog(null, err);
					}
					// Se consulta la id y precio del producto seleccionado
					nomProducto = prod_com.getSelectedItem().toString();
					rs = st.executeQuery("SELECT id_prod, prec_prod FROM Producto WHERE nom_prod = '" + nomProducto + "'");
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
					Double precio = Double.parseDouble(this.cant_txt.getText()) * Double.parseDouble(this.precioUnitario) * Double.parseDouble(dim_largo.getText()) * Double.parseDouble(dim_ancho.getText());
					this.sbtotal_txt.setText(String.valueOf(precio));
					this.sbtotal_txt.setEditable(false);
				} catch(SQLException err) {
					JOptionPane.showMessageDialog(null, err);
				}
			}
       	}
    }
}