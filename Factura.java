import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Factura extends JFrame implements ActionListener, FocusListener, MouseListener, WindowListener {

    private Color blue = new Color(0, 153, 153);
    private Color blue2 = new Color(2, 199, 199);
    private Color blue3= new Color(0, 220, 220);
    private Color blue4 = new Color(0, 243, 243);
    private Color bluefocus = new Color(167, 255, 255);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0, 0, 0);
    private Color gray = new Color(224, 224, 224);
    private Calendar fecha;
    private String dia, mes, anio;
    private JLabel logo, nom_dueno, RFC_dueno, dir_em, tel_em, id_fac, fechaLabel, nom_cliente, dir, tel, corr, head_tabla, sbt, iva, total, sbt_txt, iva_txt, total_txt;
    private JLabel nom_cliente_txt, dir_txt, tel_txt, corr_txt;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton salir, guardar;
    private Conexion db;
  	private Statement st;
    private ResultSet rs, rs2;
    private Integer id;
    private String idRecibo, idCliente, idEmpleado, idProducto;
    private String idrec, idcl, idemp, idprod;
      

    public Factura (String title, String idRecibo, String idCliente, String idEmpleado) {
        this.setLayout(null);
        this.setResizable(false);
        this.setBounds(0, 0, 561, 726); //X27
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(white);
        this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
        this.addWindowListener(this);

        //Iniciamos la conexion a la db
    	db = new Conexion();
    	try {
    		db.conectar();
            st = db.getConnection().createStatement();
            rs = st.executeQuery("SELECT MAX(id_fac) FROM Factura");
            rs.next();
            id = rs.getInt(1) + 1; //Maxima id de Factura
    	} catch (SQLException e) {
    			JOptionPane.showMessageDialog(null, "Error" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        idrec = idRecibo.toString();
        idcl = idCliente;
        idemp = idEmpleado; 

        //obtenemos fecha actual
    	fecha = Calendar.getInstance();
    	dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
        mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
        anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

        ImageIcon logo_image = new ImageIcon("./images/logo-fac.png");
        logo = new JLabel(logo_image);
        logo.setBounds(30, 20, 150, 89);
        this.add(logo);

        nom_dueno = new JLabel("<html><body>LIC. JULIA DE LA CRUZ SAN ROM\u00C1N</body></html>");
        nom_dueno.setBounds(195, 30, 150, 30);
        nom_dueno.setFont(new Font("Microsoft New Tai Lue", 1, 11 ));
        nom_dueno.setForeground(black);
        add(nom_dueno);

        RFC_dueno = new JLabel("<html><body><b>RFC: </b>FCTAM234JU54N12</body></html>");
        RFC_dueno.setBounds(195, 60, 150, 15);
        RFC_dueno.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        RFC_dueno.setForeground(black);
        add(RFC_dueno);

        dir_em = new JLabel("<html><body>San Feipe No. 2599 Col.San Jorge Monterrey, N.L.</body></html>");
        dir_em.setBounds(195, 83, 300, 15);
        dir_em.setFont(new Font("Microsoft New Tai Lue", 1, 11 ));
        dir_em.setForeground(blue);
        add(dir_em);

        tel_em = new JLabel("<html><body><b>Tels: </b>818 708-4664, 83-11-2331</body></html>");
        tel_em.setBounds(195, 98, 180, 15);
        tel_em.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        tel_em.setForeground(black);
        add(tel_em);

        id_fac = new JLabel("Id Factura: 0");
        id_fac.setBounds(410, 30, 100, 15);
        id_fac.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        id_fac.setForeground(black);
        add(id_fac);

        fechaLabel = new JLabel("Fecha:     " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(410, 45, 152, 15);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 11));
		fechaLabel.setForeground(black);
        add(fechaLabel);

        nom_cliente = new JLabel("<html><b>Receptor: </b></html>");
        nom_cliente.setBounds(30, 130, 70, 15);
        nom_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        nom_cliente.setForeground(black);
        add(nom_cliente);

        nom_cliente_txt = new JLabel();
        nom_cliente_txt.setBounds(90, 130, 180, 15);
        nom_cliente_txt.setFont( new Font("Microsoft New Tai Lue", 0, 11));
        nom_cliente_txt.setForeground(black);
        add(nom_cliente_txt);

        tel = new JLabel("<html><b>Tel\u00E9fono: </b></html>");
        tel.setBounds(350, 130, 60, 15);
        tel.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        tel.setForeground(black);
        add(tel);

        tel_txt = new JLabel();
        tel_txt.setBounds(410, 130, 500, 15);
        tel_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        tel_txt.setForeground(black);
        add(tel_txt);

        dir = new JLabel("<html><b>Direcci\u00F3n: </b></html>");
        dir.setBounds(30, 148, 60, 15);
        dir.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        dir.setForeground(black);
        add(dir);

        dir_txt = new JLabel();
        dir_txt.setBounds(90, 148, 390, 15);
        dir_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        dir_txt.setForeground(black);
        add(dir_txt);

        corr = new JLabel("<html><b>Correo electr\u00F3nico: </b></html>");
        corr.setBounds(30, 166, 110, 15);
        corr.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        corr.setForeground(black);
        add(corr);

        corr_txt = new JLabel("Correo electr\u00F3nico: ");
        corr_txt.setBounds(140, 166, 300, 15);
        corr_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        corr_txt.setForeground(black);
        add(corr_txt);

        head_tabla = new JLabel("Id     Nombre                     Tipo                P. Unitario   Dise\u00F1o      Largo     Ancho    Cantidad   Precio total");
        head_tabla.setBounds(31, 191, 500, 15);
        head_tabla.setFont(new Font("Microsoft New Tai Lue", 1 ,10));
        head_tabla.setForeground(black);
        add(head_tabla);

        String[] campos = new String[]{"Id", "Nombre", "Tipo", "P. Unitario", "Dise\u00F1o", "Largo", "Ancho",
            "Cantidad", "Precio total"};

        modelo = new DefaultTableModel(null, campos);
        tabla = new JTable(modelo){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.getColumnModel().getColumn(0).setMinWidth(25); //Id
        tabla.getColumnModel().getColumn(0).setMaxWidth(25);
        tabla.getColumnModel().getColumn(1).setMinWidth(160); //Nombre
        tabla.getColumnModel().getColumn(1).setMaxWidth(160);
        tabla.getColumnModel().getColumn(2).setMinWidth(60); //Tipo
        tabla.getColumnModel().getColumn(2).setMaxWidth(60);
        tabla.getColumnModel().getColumn(3).setMinWidth(55); //Precio unitario
        tabla.getColumnModel().getColumn(3).setMaxWidth(55);
        tabla.getColumnModel().getColumn(4).setMinWidth(40); //Diseño
        tabla.getColumnModel().getColumn(4).setMaxWidth(40);
        tabla.getColumnModel().getColumn(5).setMinWidth(30); //Largo
        tabla.getColumnModel().getColumn(5).setMaxWidth(30);
        tabla.getColumnModel().getColumn(6).setMinWidth(35); //Ancho
        tabla.getColumnModel().getColumn(6).setMaxWidth(35);
        tabla.getColumnModel().getColumn(7).setMinWidth(35); //Cantidad
        tabla.getColumnModel().getColumn(7).setMaxWidth(35);
        tabla.getColumnModel().getColumn(8).setMinWidth(55); //Precio total
        tabla.getColumnModel().getColumn(8).setMaxWidth(55);
        tabla.setRowHeight(15);
        tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 10));
        tabla.getTableHeader().setForeground(white);
        tabla.getTableHeader().setBackground(blue);
        tabla.setBackground(white);
        tabla.setForeground(black);
        tabla.setFont(new Font("Microsoft New Tai Lue", 0, 10));

        //Conexión con Carrito
        String idProducto ="";
        String nomProducto = "";
        String tipoProducto = "";
        String diseProducto = "";
        String larProducto = "";
        String anchProducto = "";
        String cantProducto = "";
        Double preuProducto, pretProducto;

        try {
            rs = st.executeQuery("SELECT * FROM Carrito WHERE id_cot = '" + idRecibo +"'");
            while(rs.next()){

                idProducto = rs.getString("id_prod");
                diseProducto = rs.getString("dis_prod");
                larProducto = rs.getString("largo_prod");
                anchProducto = rs.getString("ancho_prod");
                cantProducto = rs.getString("cant_prod");
                pretProducto = rs.getDouble("subt_prod");

                rs2 = st.executeQuery("SELECT * FROM Producto WHERE id_prod = '" + idProducto + "'");
                rs2.next();

                idprod = idProducto;

                nomProducto = rs2.getString("nom_prod");
                tipoProducto = rs2.getString("tipo_prod");
                preuProducto = rs2.getDouble("prec_prod");

                Redondear(pretProducto, 2); 
                Redondear(preuProducto, 2);

                modelo.addRow(new String[]{idProducto, nomProducto, tipoProducto, preuProducto.toString(), diseProducto, larProducto, anchProducto, cantProducto, pretProducto.toString()});
                
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err);
        }

        int rowcount;
        rowcount = tabla.getRowCount()*15;

        tabla.setBounds(30, 206, 495, rowcount);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        add(tabla);

        //abr x2
        int posy;
        posy = rowcount + 206;

        sbt = new JLabel("Subtotal: ");
        sbt.setBounds(380, posy + 25, 60, 15);
        sbt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        sbt.setForeground(black);
        add(sbt);

        sbt_txt = new JLabel("0", SwingConstants.RIGHT);
        sbt_txt.setBounds(450, posy + 25, 50, 15);
        sbt_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        sbt_txt.setForeground(black);
        add(sbt_txt);

        iva = new JLabel("IVA 16 \u0025: ");
        iva.setBounds(380, posy + 44, 60, 15);
        iva.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        iva.setForeground(black);
        add(iva);

        iva_txt = new JLabel("0", SwingConstants.RIGHT);
        iva_txt.setBounds(450, posy + 44, 50, 15);
        iva_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        iva.setForeground(black);
        add(iva_txt);

        total = new JLabel("Total: ");
        total.setBounds(380, posy + 62, 60, 15);
        total.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        total.setForeground(black);
        add(total);

        total_txt = new JLabel("0", SwingConstants.RIGHT);
        total_txt.setBounds(450, posy + 62, 50, 15);
        total_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        total_txt.setForeground(black);
        add(total_txt);

        salir = new JButton("Salir");
        salir.setBounds(235, 630, 90, 25);
        salir.setFont(new Font("Microsoft New Tai Lue", 1, 14));
        salir.setBackground(blue);
        salir.setForeground(white);
        salir.addActionListener(this);
        salir.addFocusListener(this);
        salir.addMouseListener(this);
        add(salir);

        String nomEmpleado = "";
        String nomCliente = "";
        String telCliente = "";
		String dirCliente = "";
        String corrCliente = "";
        Double subRec, ivaRec, totRec;

        try{

            //Lamando los datos de Cotizacion
            rs = st.executeQuery("SELECT * FROM Cotizacion WHERE id_cot = '" + idRecibo + "'");
            rs.next();

            subRec = rs.getDouble("sub_cot");
            ivaRec = rs.getDouble("iva_cot");
            totRec = rs.getDouble("tot_cot");

            Redondear(subRec, 2); sbt_txt.setText(subRec.toString());
            Redondear(ivaRec, 2); iva_txt.setText(ivaRec.toString());
            Redondear(totRec, 2); total_txt.setText(totRec.toString());

            //Obtenemos los datos del cliente
            rs = st.executeQuery("SELECT * FROM Cliente WHERE id_cl = '" + idCliente + "'");
            rs.next();

            nomCliente = rs.getString("nom_cl");
            telCliente = rs.getString("tel_cl");
            dirCliente = rs.getString("dir_cl");
            corrCliente = rs.getString("corr_cl");

            nom_cliente_txt.setText(nomCliente);
            tel_txt.setText(telCliente);
            dir_txt.setText(dirCliente);
            corr_txt.setText(corrCliente);

        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.toString());
        }
    }

    public static double Redondear(double numero,int digitos) {
        int cifras=(int) Math.pow(10,digitos);
        return Math.rint(numero*cifras)/cifras;
    }


    //Botones
    @Override
    public void actionPerformed(ActionEvent evt){
        if (evt.getSource() == salir){
            try {
                String camposFactura = "'" + id + "', '" + idprod + "', '" + idcl +  "', '" + idemp + "', '" + idrec + "'";
				st.executeUpdate("INSERT INTO Factura (id_fac, id_prod, id_cl, id_usu, id_rec)" +
                " VALUES (" + camposFactura + ")");
                db.desconectar();
                Menu menu = new Menu("Men\u00FA");
                menu.setVisible(true);
                this.setVisible(false);
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error" + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {
        if(evt.getSource() == this.salir){
            this.salir.setBackground(bluefocus);
            this.salir.setForeground(black);
        } 
    }

    @Override
    public void focusLost(FocusEvent evt) {
        if(evt.getSource() == this.salir){
            this.salir.setBackground(blue);
            this.salir.setForeground(white);
        }
	}

	//mouse
	@Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if(evt.getSource() == this.salir){
            this.salir.setBackground(blue);
            this.salir.setForeground(white);
        } else if (evt.getSource() == this.guardar){
            this.guardar.setBackground(blue);
            this.guardar.setForeground(white);
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        this.salir.setBackground(blue);
        this.salir.setForeground(white);
        if(evt.getSource() == this.salir){
            this.salir.setBackground(bluefocus);
            this.salir.setForeground(black);
        } 
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

	}

  //WindowListener
  @Override
  public void windowClosing(WindowEvent evt) {
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


}
