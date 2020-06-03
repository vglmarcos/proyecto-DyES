import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//Update

public class Menu extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener, MouseMotionListener {

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
	private JPanel bar, panel, panelsystem, panelAbout;
	private String patchProduct = "images/product.png";
	private String patchSale = "images/sale.png";
	private String patchEmployee = "images/employee.png";
	private String patchAbout = "images/about.png";
	private String patchlogomenu = "images/San-Roman-logo.png";
	private JLabel productLabel, saleLabel, employeeLabel, aboutLabel;
	private ImageIcon productImg = new ImageIcon(patchProduct);
	private ImageIcon saleImg = new ImageIcon(patchSale);
	private ImageIcon employeeImg = new ImageIcon(patchEmployee);
	private ImageIcon aboutImg = new ImageIcon(patchAbout);
	private ImageIcon logomenuImg = new ImageIcon(patchlogomenu);
	private JLabel logo, product, sale, employee, about, logout, appname;
	private JLabel systemName, javaVersion, systemVersion, enterpriseName, enterpriseDir, enterpriseTel, exit;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Menu(String title) {
		this.setLayout(null);
		this.setSize((int) (screenSize.width * 0.8), (int) (screenSize.width * 0.5));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(panel2);
		this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());
		this.addWindowListener(this);
		this.setUndecorated(true);
		this.titlewindow = title;

		// iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConnection().createStatement();
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}

		bar = new JPanel();
		bar.setBackground(barColor);
		bar.setBounds(0, 0, this.getWidth(), 30);
		bar.setLayout(null);
		bar.setVisible(true);
		bar.addMouseMotionListener(this);
		this.add(bar);

		panel = new JPanel();
		panel.setBackground(panel1);
		panel.setBounds(20, 50, this.getWidth() - 40, this.getHeight() - 70);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		panel.setVisible(true);
		this.add(panel);

		panelsystem = new JPanel();
		panelsystem.setBackground(fieldColor);
		panelsystem.setBounds(50, 80, this.panel.getWidth() - 100, this.panel.getHeight() - (this.panel.getHeight() - 250));
		panelsystem.setLayout(null);
		panelsystem.setBorder(BorderFactory.createLineBorder(barColor, 2));
		panelsystem.setVisible(true);
		panel.add(panelsystem);

		panelAbout = new JPanel();
		panelAbout.setBackground(panel1);
		panelAbout.setBounds(20, 50, this.getWidth() - 40, this.getHeight() - 70);
		panelAbout.setLayout(null);
		panelAbout.setBorder(BorderFactory.createLineBorder(barColor, 2));
		panelAbout.setVisible(false);
		this.add(panelAbout);

		titleLabel = new JLabel(titlewindow);
		titleLabel.setBounds(20, 2, 100, 30);
		titleLabel.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		titleLabel.setForeground(fontColor2);
		bar.add(titleLabel);

		closeButton = new JLabel(new ImageIcon("images/close.png"), SwingConstants.CENTER);
		closeButton.setBounds(this.bar.getWidth() - 30, 0, 30, 30);
		closeButton.setOpaque(true);
		closeButton.setBackground(barColor);
		closeButton.addMouseListener(this);
		bar.add(closeButton);

		minButton = new JLabel(new ImageIcon("images/min.png"), SwingConstants.CENTER);
		minButton.setBounds(this.bar.getWidth() - 60, 0, 30, 30);
		minButton.setOpaque(true);
		minButton.setBackground(barColor);
		minButton.addMouseListener(this);
		bar.add(minButton);

		appname = new JLabel("Kardient", SwingConstants.CENTER);
		appname.setBounds(0, 0, this.panelsystem.getWidth(), this.panelsystem.getHeight());
		appname.setFont(new Font("Microsoft New Tai Lue", 1, 60));
		appname.setForeground(fontColor1);
		panelsystem.add(appname);

		logout = new JLabel("Cerrar sesi\u00f3n",SwingConstants.CENTER);
		logout.setBounds(this.panel.getWidth() - 300, 20, 250, 40);
		logout.setFont(new Font("Microsoft New Tai Lue", 1, 22));
		logout.setForeground(buttonTextColor);
		logout.setBorder(BorderFactory.createLineBorder(barColor, 2));
		logout.setOpaque(true);
		logout.setBackground(redColor);
		logout.addMouseListener(this);
		panel.add(logout);

		product = new JLabel("", SwingConstants.CENTER);
		product.setBounds(50, this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		product.setBackground(buttonColor);
		product.setIcon(productImg);
		product.setHorizontalTextPosition(SwingConstants.CENTER);
		product.setVerticalTextPosition(SwingConstants.CENTER);
		product.setBorder(BorderFactory.createLineBorder(barColor, 2));
		product.setOpaque(true);
		product.addKeyListener(this);
		product.addFocusListener(this);
		product.addMouseListener(this);
		panel.add(product);

		productLabel = new JLabel("Productos", SwingConstants.CENTER);
		productLabel.setBounds(50, this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		productLabel.setFont(new Font("Microsoft New Tai Lue", 1, 40));
		productLabel.setForeground(buttonTextColor);
		productLabel.setBackground(buttonColorEntered);
		productLabel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		productLabel.setVisible(false);
		productLabel.setOpaque(true);
		productLabel.addKeyListener(this);
		productLabel.addFocusListener(this);
		productLabel.addMouseListener(this);
		panel.add(productLabel);

		sale = new JLabel("", SwingConstants.CENTER);
		sale.setBounds(this.product.getX() + this.product.getWidth() + 10, 
			this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		sale.setBackground(buttonColor);
		sale.setIcon(saleImg);
		sale.setHorizontalTextPosition(SwingConstants.CENTER);
		sale.setVerticalTextPosition(SwingConstants.CENTER);
		sale.setBorder(BorderFactory.createLineBorder(barColor, 2));
		sale.setOpaque(true);
		sale.addKeyListener(this);
		sale.addFocusListener(this);
		sale.addMouseListener(this);
		panel.add(sale);

		saleLabel = new JLabel("Cotizaci\u00F3n", SwingConstants.CENTER);
		saleLabel.setBounds(this.product.getX() + this.product.getWidth() + 10, 
			this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		saleLabel.setFont(new Font("Microsoft New Tai Lue", 1, 40));
		saleLabel.setForeground(buttonTextColor);
		saleLabel.setBackground(buttonColorEntered);
		saleLabel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		saleLabel.setVisible(false);
		saleLabel.setOpaque(true);
		saleLabel.addKeyListener(this);
		saleLabel.addFocusListener(this);
		saleLabel.addMouseListener(this);
		panel.add(saleLabel);

		employee = new JLabel("", SwingConstants.CENTER);
		employee.setBounds(this.sale.getX() + this.sale.getWidth() + 10, 
			this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		employee.setBackground(buttonColor);
		employee.setIcon(employeeImg);
		employee.setHorizontalTextPosition(SwingConstants.CENTER);
		employee.setVerticalTextPosition(SwingConstants.CENTER);
		employee.setBorder(BorderFactory.createLineBorder(barColor, 2));
		employee.setOpaque(true);
		employee.addKeyListener(this);
		employee.addFocusListener(this);
		employee.addMouseListener(this);
		panel.add(employee);

		employeeLabel = new JLabel("Empleados", SwingConstants.CENTER);
		employeeLabel.setBounds(this.sale.getX() + this.sale.getWidth() + 10, 
			this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		employeeLabel.setFont(new Font("Microsoft New Tai Lue", 1, 40));
		employeeLabel.setForeground(buttonTextColor);
		employeeLabel.setBackground(buttonColorEntered);
		employeeLabel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		employeeLabel.setVisible(false);
		employeeLabel.setOpaque(true);
		employeeLabel.addKeyListener(this);
		employeeLabel.addFocusListener(this);
		employeeLabel.addMouseListener(this);
		panel.add(employeeLabel);

		about = new JLabel("", SwingConstants.CENTER);
		about.setBounds(this.employee.getX() + this.employee.getWidth() + 10, 
			this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		about.setBackground(buttonColor);
		about.setIcon(aboutImg);
		about.setHorizontalTextPosition(SwingConstants.CENTER);
		about.setVerticalTextPosition(SwingConstants.CENTER);
		about.setBorder(BorderFactory.createLineBorder(barColor, 2));
		about.setOpaque(true);
		about.addKeyListener(this);
		about.addFocusListener(this);
		about.addMouseListener(this);
		panel.add(about);

		aboutLabel = new JLabel("Acerda de", SwingConstants.CENTER);
		aboutLabel.setBounds(this.employee.getX() + this.employee.getWidth() + 10, 
			this.panelsystem.getY() + this.panelsystem.getHeight() + 20, 
			(this.panel.getWidth() - 130) / 4, (this.panel.getWidth() - 130) / 4);
		aboutLabel.setFont(new Font("Microsoft New Tai Lue", 1, 40));
		aboutLabel.setForeground(buttonTextColor);
		aboutLabel.setBackground(buttonColorEntered);
		aboutLabel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		aboutLabel.setVisible(false);
		aboutLabel.setOpaque(true);
		aboutLabel.addKeyListener(this);
		aboutLabel.addFocusListener(this);
		aboutLabel.addMouseListener(this);
		panel.add(aboutLabel);

		exit = new JLabel("Regresar", SwingConstants.CENTER);
		exit.setBounds(this.panelAbout.getWidth() - 300, 20, 250, 40);
		exit.setFont(new Font("Microsoft New Tai Lue", 1, 24));
		exit.setBackground(redColor);
		exit.setForeground(buttonTextColor);
		exit.setBorder(BorderFactory.createLineBorder(barColor, 2));
		exit.setOpaque(true);
		exit.addKeyListener(this);
		exit.addFocusListener(this);
		exit.addMouseListener(this);
		panelAbout.add(exit);

		systemName = new JLabel("Kardient", SwingConstants.CENTER);
		systemName.setBounds(0, 80, this.panelAbout.getWidth(), 70);
		systemName.setFont(new Font("Microsoft New Tai Lue", 1, 60));
		systemName.setForeground(fontColor1);
		panelAbout.add(systemName);

		systemVersion = new JLabel("Versi\u00f3n 1.0", SwingConstants.CENTER);
		systemVersion.setBounds(0, this.systemName.getY() + this.systemName.getHeight() + 20, 
			this.panelAbout.getWidth(), 40);
		systemVersion.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		systemVersion.setForeground(fontColor1);
		panelAbout.add(systemVersion);

		javaVersion = new JLabel("Sistema creado en la versi\u00f3n 1.8.0_251 de JAVA", SwingConstants.CENTER);
		javaVersion.setBounds(0, this.systemVersion.getY() + this.systemVersion.getHeight() + 20, 
			this.panelAbout.getWidth(), 40);
		javaVersion.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		javaVersion.setForeground(fontColor1);
		panelAbout.add(javaVersion);

		logo = new JLabel("", SwingConstants.CENTER);
		logo.setBounds(0, this.javaVersion.getY() + this.javaVersion.getHeight() + 20, 
			this.panelAbout.getWidth(), 140);
		logo.setIcon(logomenuImg);
		logo.setHorizontalTextPosition(SwingConstants.CENTER);
		logo.setVerticalTextPosition(SwingConstants.CENTER);
		panelAbout.add(logo);

		enterpriseName = new JLabel("Cristaler\u00eda San Rom\u00e1n", SwingConstants.CENTER);
		enterpriseName.setBounds(0, this.logo.getY() + this.logo.getHeight() + 20, 
			this.panelAbout.getWidth(), 40);
		enterpriseName.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		enterpriseName.setForeground(fontColor1);
		panelAbout.add(enterpriseName);

		enterpriseDir = new JLabel("San Feipe No. 2599 Col.San Jorge Monterrey, N.L.", SwingConstants.CENTER);
		enterpriseDir.setBounds(0, this.enterpriseName.getY() + this.enterpriseName.getHeight() + 20, 
			this.panelAbout.getWidth(), 40);
		enterpriseDir.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		enterpriseDir.setForeground(fontColor1);
		panelAbout.add(enterpriseDir);

		enterpriseTel = new JLabel("Tels: 818 708-4664, 83-11-2331", SwingConstants.CENTER);
		enterpriseTel.setBounds(0, this.enterpriseDir.getY() + this.enterpriseDir.getHeight() + 20, 
			this.panelAbout.getWidth(), 40);
		enterpriseTel.setFont(new Font("Microsoft New Tai Lue", 1, 30));
		enterpriseTel.setForeground(fontColor1);
		panelAbout.add(enterpriseTel);
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

	}

	// Focus
	@Override
	public void focusGained(FocusEvent evt) {
		if (evt.getSource() == this.product) {
			this.product.setVisible(false);
			this.productLabel.setVisible(true);
			this.sale.setVisible(true);
			this.saleLabel.setVisible(false);
			this.employee.setVisible(true);
			this.employeeLabel.setVisible(false);
			this.about.setVisible(true);
			this.aboutLabel.setVisible(false);
		} 
		if (evt.getSource() == this.sale) {
			this.sale.setVisible(false);
			this.saleLabel.setVisible(true);
			this.product.setVisible(true);
			this.productLabel.setVisible(false);
			this.employee.setVisible(true);
			this.employeeLabel.setVisible(false);
			this.about.setVisible(true);
			this.aboutLabel.setVisible(false);
		} 
		if (evt.getSource() == this.employee) {
			this.employee.setVisible(false);
			this.employeeLabel.setVisible(true);
			this.product.setVisible(true);
			this.productLabel.setVisible(false);
			this.sale.setVisible(true);
			this.saleLabel.setVisible(false);
			this.about.setVisible(true);
			this.aboutLabel.setVisible(false);
		}
		if (evt.getSource() == this.about) {
			this.about.setVisible(false);
			this.aboutLabel.setVisible(true);
			this.product.setVisible(true);
			this.productLabel.setVisible(false);
			this.sale.setVisible(true);
			this.saleLabel.setVisible(false);
			this.employee.setVisible(true);
			this.employeeLabel.setVisible(false);
		}
		if (evt.getSource() == this.logout) {
			this.logout.setBackground(redColorEntered);
		}
		if (evt.getSource() == this.exit){
			this.exit.setBackground(redColorEntered);
		}
	}

	@Override
	public void focusLost(FocusEvent evt) {
		if (evt.getSource() == this.productLabel) {
			this.product.setVisible(true);
			this.productLabel.setVisible(false);
		} 
		if (evt.getSource() == this.saleLabel) {
			this.sale.setVisible(true);
			this.saleLabel.setVisible(false);
		}
		if (evt.getSource() == this.employeeLabel) {
			this.employee.setVisible(true);
			this.employeeLabel.setVisible(false);
		}
		if (evt.getSource() == this.aboutLabel) {
			this.about.setVisible(true);
			this.aboutLabel.setVisible(false);
		}
		if (evt.getSource() == this.logout) {
			this.logout.setBackground(redColor);
		}
		if (evt.getSource() == this.exit){
			this.exit.setBackground(redColor);
		}
	}

	// mouseListener
	@Override
	public void mouseReleased(MouseEvent evt) {
		if (evt.getSource().equals(this.closeButton)) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		if (evt.getSource().equals(this.minButton)) {
			this.setExtendedState(ICONIFIED);
		}
		if (evt.getSource().equals(this.logout)) {
			closeSesion();
			Login l1 = new Login("Ingresar");
			l1.setVisible(true);
			this.setVisible(false);
		}
		if (evt.getSource().equals(this.saleLabel)){
			Cotizacion c1 = new Cotizacion("Cotizaci\u00F3n");
			c1.setVisible(true);
			this.setVisible(false);
		}
		if (evt.getSource().equals(this.productLabel)){
			Producto p1 = new Producto("Producto");
			p1.setVisible(true);
			this.setVisible(false);
		}
		if (evt.getSource().equals(this.employeeLabel)){
			Empleado e1 = new Empleado("Empleado");
			e1.setVisible(true);
			this.setVisible(false);
		}
		if (evt.getSource().equals(this.aboutLabel)){
			panelAbout.setVisible(true);
			panel.setVisible(false);
		}
		if (evt.getSource() == this.exit){
			panelAbout.setVisible(false);
			panel.setVisible(true);
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
		if (evt.getSource() == this.productLabel) {
			this.product.setVisible(true);
			this.productLabel.setVisible(false);
		} 
		if (evt.getSource() == this.saleLabel) {
			this.sale.setVisible(true);
			this.saleLabel.setVisible(false);
		} 
		if (evt.getSource() == this.employeeLabel) {
			this.employee.setVisible(true);
			this.employeeLabel.setVisible(false);
		}
		if (evt.getSource() == this.aboutLabel) {
			this.about.setVisible(true);
			this.aboutLabel.setVisible(false);
		}
		if (evt.getSource() == this.logout) {
			this.logout.setBackground(redColor);
		}
		if (evt.getSource() == this.exit){
			this.exit.setBackground(redColor);
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
		if (evt.getSource() == this.product) {
			this.product.setVisible(false);
			this.productLabel.setVisible(true);
		} 
		if (evt.getSource() == this.sale) {
			this.sale.setVisible(false);
			this.saleLabel.setVisible(true);
		} 
		if (evt.getSource() == this.employee) {
			this.employee.setVisible(false);
			this.employeeLabel.setVisible(true);
		}
		if (evt.getSource() == this.about) {
			this.about.setVisible(false);
			this.aboutLabel.setVisible(true);
		}
		if (evt.getSource() == this.logout) {
			this.logout.setBackground(redColorEntered);
		}
		if (evt.getSource() == this.exit){
			this.exit.setBackground(redColorEntered);
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

	@Override
	public void mouseDragged(MouseEvent evt) {
		// TODO Auto-generated method stub
		if (evt.getSource().equals(this.bar)){
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
