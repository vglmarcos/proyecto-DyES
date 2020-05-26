import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//Update

public class Menu extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener {

	private Color blue = new Color(0, 153, 153);
	private Color blue2 = new Color(2, 199, 199);
	private Color blue3 = new Color(0, 220, 220);
	private Color blue4 = new Color(0, 243, 243);
	private Color bluefocus = new Color(167, 255, 255);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0, 0, 0);
	private Color gray = new Color(224, 224, 224);
	private JButton product, sale, provider, employee;
	private String patchLogo = "images/Logo.png";
	private String patchProduct = "images/product.png";
	private String patchSale = "images/sale.png";
	private String patchProvider = "images/provider.png";
	private String patchEmployee = "images/employee.png";
	private JLabel tira, tira2, tira3, tira4, rights;
	private JLabel productLabel, saleLabel, providerLabel, employeeLabel;
	private ImageIcon productImg = new ImageIcon(patchProduct);
	private ImageIcon saleImg = new ImageIcon(patchSale);
	private ImageIcon providerImg = new ImageIcon(patchProvider);
	private ImageIcon employeeImg = new ImageIcon(patchEmployee);
	private Conexion db;
	private Statement st;
	private ResultSet rs;

	public Menu(String title) {
		this.setLayout(null);
		this.setBounds(0, 0, 810, 650);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(white);
		this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());
		this.addWindowListener(this);

		// iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConnection().createStatement();
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}

		product = new JButton();
		product.setBounds(125, 103, 170, 170);
		product.setBackground(white);
		product.setIcon(productImg);
		product.setHorizontalTextPosition(SwingConstants.CENTER);
		product.setVerticalTextPosition(SwingConstants.BOTTOM);
		product.setBorder(null);
		product.addActionListener(this);
		product.addKeyListener(this);
		product.addFocusListener(this);
		product.addMouseListener(this);
		add(product);

		productLabel = new JLabel("Productos", SwingConstants.CENTER);
		productLabel.setBounds(125, 273, 170, 30);
		productLabel.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		productLabel.setForeground(blue);
		add(productLabel);

		sale = new JButton();
		sale.setBounds(505, 103, 170, 170);
		sale.setBackground(white);
		sale.setIcon(saleImg);
		sale.setHorizontalTextPosition(SwingConstants.CENTER);
		sale.setVerticalTextPosition(SwingConstants.BOTTOM);
		sale.setBorder(null);
		sale.addActionListener(this);
		sale.addKeyListener(this);
		sale.addFocusListener(this);
		sale.addMouseListener(this);
		add(sale);

		saleLabel = new JLabel("Cotizaci\u00F3n", SwingConstants.CENTER);
		saleLabel.setBounds(505, 273, 170, 30);
		saleLabel.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		saleLabel.setForeground(blue);
		add(saleLabel);

		provider = new JButton();
		provider.setBounds(125, 336, 170, 170);
		provider.setBackground(white);
		provider.setIcon(providerImg);
		provider.setHorizontalTextPosition(SwingConstants.CENTER);
		provider.setVerticalTextPosition(SwingConstants.BOTTOM);
		provider.setBorder(null);
		provider.addActionListener(this);
		provider.addKeyListener(this);
		provider.addFocusListener(this);
		provider.addMouseListener(this);
		add(provider);

		providerLabel = new JLabel("Proveedor", SwingConstants.CENTER);
		providerLabel.setBounds(125, 506, 170, 30);
		providerLabel.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		providerLabel.setForeground(blue);
		add(providerLabel);

		employee = new JButton();
		employee.setBounds(505, 336, 170, 170);
		employee.setBackground(white);
		employee.setIcon(employeeImg);
		employee.setHorizontalTextPosition(SwingConstants.CENTER);
		employee.setVerticalTextPosition(SwingConstants.BOTTOM);
		employee.setBorder(null);
		employee.addActionListener(this);
		employee.addKeyListener(this);
		employee.addFocusListener(this);
		employee.addMouseListener(this);
		add(employee);

		employeeLabel = new JLabel("Empleados", SwingConstants.CENTER);
		employeeLabel.setBounds(505, 506, 170, 30);
		employeeLabel.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		employeeLabel.setForeground(blue);
		add(employeeLabel);

		rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.",
				SwingConstants.CENTER);
		rights.setBounds(0, 591, 810, 30);
		rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
		rights.setOpaque(true);
		rights.setBackground(black);
		rights.setForeground(white);
		add(rights);

		tira = new JLabel();
		tira.setBounds(0, 0, 810, 20);
		tira.setBackground(blue);
		tira.setOpaque(true);
		add(tira);

		tira2 = new JLabel();
		tira2.setBounds(0, 20, 810, 9);
		tira2.setBackground(blue2);
		tira2.setOpaque(true);
		add(tira2);

		tira3 = new JLabel();
		tira3.setBounds(0, 29, 810, 7);
		tira3.setBackground(blue3);
		tira3.setOpaque(true);
		add(tira3);

		tira4 = new JLabel();
		tira4.setBounds(0, 36, 810, 4);
		tira4.setBackground(blue4);
		tira4.setOpaque(true);
		add(tira4);
	}

	public void closeSesion() {
		try {
			// Actualizamos el estado de sesion de usuario en la db
			db.conectar();
			st = db.getConnection().createStatement();
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

	// Botones
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == sale) {
			Cotizacion c1 = new Cotizacion("Cotizaci\u00F3n");
			c1.setVisible(true);
			this.setVisible(false);
		} else if (evt.getSource() == product) {
			Producto p1 = new Producto("Producto");
			p1.setVisible(true);
			this.setVisible(false);
		} else if (evt.getSource() == provider) {
			Proveedor prov = new Proveedor("Proveedor");
			prov.setVisible(true);
			this.setVisible(false);
		} else if (evt.getSource() == employee) {
			Empleado e1 = new Empleado("Empleado");
			e1.setVisible(true);
			this.setVisible(false);
		}
	}

	// Teclado
	@Override
	public void keyTyped(KeyEvent evt) { // No se ocupa aun

	}

	@Override
	public void keyReleased(KeyEvent evt) { // No se ocupa aun

	}

	@Override
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == 10) {
			if (evt.getSource() == sale) {
				Cotizacion c1 = new Cotizacion("Cotizaci\u00F3n");
				c1.setVisible(true);
				this.setVisible(false);
			} else if (evt.getSource() == product) {
				Producto p1 = new Producto("Producto");
				p1.setVisible(true);
				this.setVisible(false);
			} else if (evt.getSource() == provider) {
				Proveedor prov = new Proveedor("Proveedor");
				prov.setVisible(true);
				this.setVisible(false);
			} else if (evt.getSource() == employee) {
				Empleado e1 = new Empleado("Empleado");
				e1.setVisible(true);
				this.setVisible(false);
			}
		}
	}

	// Focus
	@Override
	public void focusGained(FocusEvent evt) {
		if (evt.getSource() == this.product) {
			this.product.setBackground(bluefocus);
		} else if (evt.getSource() == this.sale) {
			this.sale.setBackground(bluefocus);
		} else if (evt.getSource() == this.provider) {
			this.provider.setBackground(bluefocus);
		} else if (evt.getSource() == this.employee) {
			this.employee.setBackground(bluefocus);
		}
	}

	@Override
	public void focusLost(FocusEvent evt) {
		if (evt.getSource() == this.product) {
			this.product.setBackground(white);
		} else if (evt.getSource() == this.sale) {
			this.sale.setBackground(white);
		} else if (evt.getSource() == this.provider) {
			this.provider.setBackground(white);
		} else if (evt.getSource() == this.employee) {
			this.employee.setBackground(white);
		}
	}

	// mouseListener
	@Override
	public void mouseReleased(MouseEvent evt) {

	}

	@Override
	public void mousePressed(MouseEvent evt) {

	}

	@Override
	public void mouseExited(MouseEvent evt) {
		if (evt.getSource() == this.product) {
			this.product.setBackground(white);
		} else if (evt.getSource() == this.sale) {
			this.sale.setBackground(white);
		} else if (evt.getSource() == this.provider) {
			this.provider.setBackground(white);
		} else if (evt.getSource() == this.employee) {
			this.employee.setBackground(white);
		}
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		this.product.setBackground(white);
		this.sale.setBackground(white);
		this.provider.setBackground(white);
		this.employee.setBackground(white);
		if (evt.getSource() == this.product) {
			this.product.setBackground(bluefocus);
		} else if (evt.getSource() == this.sale) {
			this.sale.setBackground(bluefocus);
		} else if (evt.getSource() == this.provider) {
			this.provider.setBackground(bluefocus);
		} else if (evt.getSource() == this.employee) {
			this.employee.setBackground(bluefocus);
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

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
}
