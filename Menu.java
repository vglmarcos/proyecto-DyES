import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//Update

public class Menu extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener, MouseMotionListener {

	private JLabel closeButton, minButton, titleLabel;
	private JPanel bar;
	private Color panel2 = new Color(54, 57, 63);
	private Color fontColor2 = new Color(134, 138, 143);
    private Color barColor = new Color(34, 36, 40);
    private Color redColor = new Color(179, 29, 29);
	private Color focusColor = new Color(53, 55, 58);
	private String patchLogo = "images/Logo.png";
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private int x, y;
	private String titlewindow;

	public Menu(String title) {

		db = new Conexion();

		try {
			db.conectar();
			db.desconectar();
		} catch (SQLException err) {
			System.out.println(err);
		}

		this.setLayout(null);
		this.setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(panel2);
		this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());
		this.addWindowListener(this);
		this.setUndecorated(true);
		this.titlewindow = title;

		bar = new JPanel();
		bar.setBackground(barColor);
		bar.setBounds(0, 0, 800, 30);
		bar.setLayout(null);
		bar.setVisible(true);
		bar.addMouseMotionListener(this);
		this.add(bar);

		titleLabel = new JLabel(titlewindow);
		titleLabel.setBounds(20, 2, 100, 30);
		titleLabel.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		titleLabel.setForeground(fontColor2);
		bar.add(titleLabel);

		closeButton = new JLabel(new ImageIcon("images/close.png"), SwingConstants.CENTER);
		closeButton.setBounds(770, 0, 30, 30);
		closeButton.setOpaque(true);
		closeButton.setBackground(barColor);
		closeButton.addMouseListener(this);
		bar.add(closeButton);

		minButton = new JLabel(new ImageIcon("images/min.png"), SwingConstants.CENTER);
		minButton.setBounds(740, 0, 30, 30);
		minButton.setOpaque(true);
		minButton.setBackground(barColor);
		minButton.addMouseListener(this);
		bar.add(minButton);
	}

	public void closeSesion() {
		try {
			//Actualizamos el estado de sesion de usuario en la db
			db.conectar();
			st = db.getConnection().createStatement();
			rs = st.executeQuery("SELECT nick_usu, sesion_act FROM Usuario");
			String usuario = "";
			while(rs.next()) {
				if(rs.getString("sesion_act").equals("s")) {
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
		if (evt.getKeyCode() == 10) {

		}
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		
	}

	// Focus
	@Override
	public void focusGained(FocusEvent evt) {
		
	}

	@Override
	public void focusLost(FocusEvent evt) {
		
	}

	// mouse
	@Override
	public void mouseReleased(MouseEvent evt) {
		if (evt.getSource().equals(this.closeButton)) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		if (evt.getSource().equals(this.minButton)) {
			this.setExtendedState(ICONIFIED);
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
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
        if (evt.getSource().equals(this.closeButton)) {
			this.closeButton.setBackground(redColor);
		}
		if (evt.getSource().equals(this.minButton)) {
			this.minButton.setBackground(focusColor);
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

	}

	// motion event
	@Override
	public void mouseDragged(MouseEvent evt) {
		if (evt.getSource().equals(this.bar)) {
			int xScreen = evt.getXOnScreen();
			int yScreen = evt.getYOnScreen();
			this.setLocation(xScreen - x, yScreen - y);
		}
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		x = evt.getX();
		y = evt.getY();
	}

	// ventana
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
