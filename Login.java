import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame
		implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener, MouseMotionListener {

	private JLabel user, password, closeButton, minButton, titleLabel, registerLabel, registerButton, welcomeLabel,
			signIn, nextButton, finishButton;
	private JPanel bar, panel, panelRegister, finalPanel;
	private JTextField userField;
	private JPasswordField passwordField;
	private Color panel1 = new Color(42, 44, 49);
	private Color panel2 = new Color(54, 57, 63);
	private Color fontColor1 = new Color(194, 196, 197);
	private Color fontColor2 = new Color(134, 138, 143);
	private Color fieldColor = new Color(48, 51, 57);
	private Color barColor = new Color(34, 36, 40);
	private Color redColor = new Color(179, 29, 29);
	private Color focusColor = new Color(53, 55, 58);
	private Color buttonColor = new Color(101, 59, 152);
	private Color buttonColorEntered = new Color(80, 48, 119);
	private Color selectedText = new Color(255, 255, 255);
	private Color selectionColor = new Color(10, 103, 215);
	private Color textFieldColorEntered = new Color(74, 86, 129);
	private Color buttonTextColor = new Color(232, 236, 249);
	private String patchLogo = "images/Logo.png";
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private int x, y;
	private String titlewindow;
	private JLabel userRegister, passwordRegister, emailRegister, nameRegister, lastnameRegister, backButton,
			backButton2;
	private JTextField userRegisterField, emailRegisterField, nameRegisterField, lastnameRegisterField;
	private JPasswordField passwordRegisterField, passwordRegisterConfirmField;
	private JLabel tira1, tira2, seePassword, passwordRegisterConfirm, seePassword2;

	public Login(String title) {

		db = new Conexion();

		try {
			db.conectar();
			db.desconectar();
		} catch (SQLException err) {
			System.out.println(err);
		}

		this.setLayout(null);
		this.setBounds(0, 0, 500, 600);
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
		bar.setBounds(0, 0, 500, 30);
		bar.setLayout(null);
		bar.setVisible(true);
		bar.addMouseMotionListener(this);
		this.add(bar);

		panel = new JPanel();
		panel.setBackground(panel1);
		panel.setBounds(20, 50, 460, 530);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		panel.setVisible(true);
		this.add(panel);

		panelRegister = new JPanel();
		panelRegister.setBackground(panel1);
		panelRegister.setBounds(20, 90, 460, 490);
		panelRegister.setLayout(null);
		panelRegister.setBorder(BorderFactory.createLineBorder(barColor, 2));
		panelRegister.setVisible(false);
		this.add(panelRegister);

		finalPanel = new JPanel();
		finalPanel.setBackground(panel1);
		finalPanel.setBounds(20, 90, 460, 490);
		finalPanel.setLayout(null);
		finalPanel.setBorder(BorderFactory.createLineBorder(barColor, 2));
		finalPanel.setVisible(false);
		this.add(finalPanel);

		titleLabel = new JLabel(titlewindow);
		titleLabel.setBounds(20, 2, 100, 30);
		titleLabel.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		titleLabel.setForeground(fontColor2);
		bar.add(titleLabel);

		closeButton = new JLabel(new ImageIcon("images/close.png"), SwingConstants.CENTER);
		closeButton.setBounds(470, 0, 30, 30);
		closeButton.setOpaque(true);
		closeButton.setBackground(barColor);
		closeButton.addMouseListener(this);
		bar.add(closeButton);

		minButton = new JLabel(new ImageIcon("images/min.png"), SwingConstants.CENTER);
		minButton.setBounds(440, 0, 30, 30);
		minButton.setOpaque(true);
		minButton.setBackground(barColor);
		minButton.addMouseListener(this);
		bar.add(minButton);

		tira1 = new JLabel();
		tira1.setBounds(20, 55, 220, 20);
		tira1.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		tira1.setForeground(fontColor2);
		tira1.setOpaque(true);
		tira1.setBackground(barColor);
		tira1.setVisible(false);
		this.add(tira1);

		tira2 = new JLabel();
		tira2.setBounds(260, 55, 220, 20);
		tira2.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		tira2.setForeground(fontColor2);
		tira2.setOpaque(true);
		tira2.setBackground(panel1);
		tira2.setVisible(false);
		this.add(tira2);

		welcomeLabel = new JLabel("\u00A1Bienvenido!", SwingConstants.CENTER);
		welcomeLabel.setBounds(0, 50, 460, 60);
		welcomeLabel.setFont(new Font("Microsoft New Tai Lue", 1, 36));
		welcomeLabel.setForeground(fontColor2);
		panel.add(welcomeLabel);

		user = new JLabel("Usuario");
		user.setBounds(20, 150, 200, 40);
		user.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		user.setForeground(fontColor2);
		panel.add(user);

		userField = new JTextField();
		userField.setBounds(20, 195, 420, 40);
		userField.setBackground(fieldColor);
		userField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		userField.setForeground(fontColor1);
		userField.setCaretColor(new Color(255, 255, 255));
		userField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		userField.setSelectedTextColor(selectedText);
		userField.setSelectionColor(selectionColor);
		userField.addKeyListener(this);
		userField.addFocusListener(this);
		userField.addMouseListener(this);
		panel.add(userField);

		password = new JLabel("Contrase\u00F1a");
		password.setBounds(20, 245, 200, 40);
		password.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		password.setForeground(fontColor2);
		panel.add(password);

		seePassword2 = new JLabel(new ImageIcon("images/see-password.png"), SwingConstants.CENTER);
		seePassword2.setBounds(400, 245, 40, 40);
		seePassword2.setOpaque(true);
		seePassword2.setBackground(panel1);
		seePassword2.addMouseListener(this);
		panel.add(seePassword2);

		passwordField = new JPasswordField();
		passwordField.setBounds(20, 290, 420, 40);
		passwordField.setBackground(fieldColor);
		passwordField.setForeground(fontColor1);
		passwordField.setCaretColor(new Color(255, 255, 255));
		passwordField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		passwordField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		passwordField.setSelectedTextColor(selectedText);
		passwordField.setSelectionColor(selectionColor);
		passwordField.addKeyListener(this);
		passwordField.addFocusListener(this);
		panel.add(passwordField);

		signIn = new JLabel("Ingresar", SwingConstants.CENTER);
		signIn.setBounds(20, 400, 420, 40);
		signIn.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		signIn.setForeground(buttonTextColor);
		signIn.setBackground(buttonColor);
		signIn.setFocusable(true);
		signIn.setOpaque(true);
		signIn.addKeyListener(this);
		signIn.addFocusListener(this);
		signIn.addMouseListener(this);
		panel.add(signIn);

		registerLabel = new JLabel("\u00BFNo tienes una cuenta?");
		registerLabel.setBounds(50, 470, 200, 30);
		registerLabel.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		registerLabel.setForeground(fontColor2);
		panel.add(registerLabel);

		registerButton = new JLabel("Reg\u00EDstrate");
		registerButton.setBounds(245, 470, 80, 30);
		registerButton.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		registerButton.setFocusable(true);
		registerButton.setForeground(buttonColor);
		registerButton.addKeyListener(this);
		registerButton.addMouseListener(this);
		registerButton.addFocusListener(this);
		panel.add(registerButton);

		// register inputs

		backButton = new JLabel("Cancelar");
		backButton.setBounds(20, 20, 70, 30);
		backButton.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		backButton.setForeground(buttonColor);
		backButton.addMouseListener(this);
		panelRegister.add(backButton);

		nameRegister = new JLabel("Nombre");
		nameRegister.setBounds(20, 70, 100, 40);
		nameRegister.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		nameRegister.setForeground(fontColor2);
		panelRegister.add(nameRegister);

		nameRegisterField = new JTextField();
		nameRegisterField.setBounds(20, 120, 420, 40);
		nameRegisterField.setBackground(fieldColor);
		nameRegisterField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nameRegisterField.setForeground(fontColor1);
		nameRegisterField.setCaretColor(new Color(255, 255, 255));
		nameRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		nameRegisterField.setSelectedTextColor(selectedText);
		nameRegisterField.setSelectionColor(selectionColor);
		nameRegisterField.addKeyListener(this);
		nameRegisterField.addFocusListener(this);
		panelRegister.add(nameRegisterField);

		lastnameRegister = new JLabel("Apellidos");
		lastnameRegister.setBounds(20, 180, 150, 40);
		lastnameRegister.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		lastnameRegister.setForeground(fontColor2);
		panelRegister.add(lastnameRegister);

		lastnameRegisterField = new JTextField();
		lastnameRegisterField.setBounds(20, 230, 420, 40);
		lastnameRegisterField.setBackground(fieldColor);
		lastnameRegisterField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		lastnameRegisterField.setForeground(fontColor1);
		lastnameRegisterField.setCaretColor(new Color(255, 255, 255));
		lastnameRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		lastnameRegisterField.setSelectedTextColor(selectedText);
		lastnameRegisterField.setSelectionColor(selectionColor);
		lastnameRegisterField.addKeyListener(this);
		lastnameRegisterField.addFocusListener(this);
		panelRegister.add(lastnameRegisterField);

		emailRegister = new JLabel("Correo Electr\u00F3nico");
		emailRegister.setBounds(20, 290, 150, 40);
		emailRegister.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		emailRegister.setForeground(fontColor2);
		panelRegister.add(emailRegister);

		emailRegisterField = new JTextField();
		emailRegisterField.setBounds(20, 340, 420, 40);
		emailRegisterField.setBackground(fieldColor);
		emailRegisterField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		emailRegisterField.setForeground(fontColor1);
		emailRegisterField.setCaretColor(new Color(255, 255, 255));
		emailRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		emailRegisterField.setSelectedTextColor(selectedText);
		emailRegisterField.setSelectionColor(selectionColor);
		emailRegisterField.addKeyListener(this);
		emailRegisterField.addFocusListener(this);
		panelRegister.add(emailRegisterField);

		nextButton = new JLabel("Siguiente", SwingConstants.CENTER);
		nextButton.setBounds(20, 420, 420, 40);
		nextButton.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		nextButton.setForeground(buttonTextColor);
		nextButton.setBackground(buttonColor);
		nextButton.setFocusable(true);
		nextButton.setOpaque(true);
		nextButton.addKeyListener(this);
		nextButton.addFocusListener(this);
		nextButton.addMouseListener(this);
		panelRegister.add(nextButton);

		// final panel

		backButton2 = new JLabel("Regresar");
		backButton2.setBounds(20, 20, 70, 30);
		backButton2.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		backButton2.setForeground(new Color(101, 59, 152));
		backButton2.addMouseListener(this);
		finalPanel.add(backButton2);

		userRegister = new JLabel("Usuario");
		userRegister.setBounds(20, 70, 100, 40);
		userRegister.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		userRegister.setForeground(fontColor2);
		finalPanel.add(userRegister);

		userRegisterField = new JTextField();
		userRegisterField.setBounds(20, 120, 420, 40);
		userRegisterField.setBackground(fieldColor);
		userRegisterField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		userRegisterField.setForeground(fontColor1);
		userRegisterField.setCaretColor(new Color(255, 255, 255));
		userRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		userRegisterField.setSelectedTextColor(selectedText);
		userRegisterField.setSelectionColor(selectionColor);
		userRegisterField.addKeyListener(this);
		userRegisterField.addFocusListener(this);
		finalPanel.add(userRegisterField);

		passwordRegister = new JLabel("Contrase\u00F1a");
		passwordRegister.setBounds(20, 180, 150, 40);
		passwordRegister.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		passwordRegister.setForeground(fontColor2);
		finalPanel.add(passwordRegister);

		seePassword = new JLabel(new ImageIcon("images/see-password.png"), SwingConstants.CENTER);
		seePassword.setBounds(400, 180, 40, 40);
		seePassword.setOpaque(true);
		seePassword.setBackground(panel1);
		seePassword.addMouseListener(this);
		finalPanel.add(seePassword);

		passwordRegisterField = new JPasswordField();
		passwordRegisterField.setBounds(20, 230, 420, 40);
		passwordRegisterField.setBackground(fieldColor);
		passwordRegisterField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		passwordRegisterField.setForeground(fontColor1);
		passwordRegisterField.setCaretColor(new Color(255, 255, 255));
		passwordRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		passwordRegisterField.setSelectedTextColor(selectedText);
		passwordRegisterField.setSelectionColor(selectionColor);
		passwordRegisterField.addKeyListener(this);
		passwordRegisterField.addFocusListener(this);
		finalPanel.add(passwordRegisterField);

		passwordRegisterConfirm = new JLabel("Confirmar contrase\u00F1a");
		passwordRegisterConfirm.setBounds(20, 290, 200, 40);
		passwordRegisterConfirm.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		passwordRegisterConfirm.setForeground(fontColor2);
		finalPanel.add(passwordRegisterConfirm);

		passwordRegisterConfirmField = new JPasswordField();
		passwordRegisterConfirmField.setBounds(20, 340, 420, 40);
		passwordRegisterConfirmField.setBackground(fieldColor);
		passwordRegisterConfirmField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		passwordRegisterConfirmField.setForeground(fontColor1);
		passwordRegisterConfirmField.setCaretColor(new Color(255, 255, 255));
		passwordRegisterConfirmField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		passwordRegisterConfirmField.setSelectedTextColor(selectedText);
		passwordRegisterConfirmField.setSelectionColor(selectionColor);
		passwordRegisterConfirmField.addKeyListener(this);
		passwordRegisterConfirmField.addFocusListener(this);
		finalPanel.add(passwordRegisterConfirmField);

		finishButton = new JLabel("Terminar", SwingConstants.CENTER);
		finishButton.setBounds(20, 420, 420, 40);
		finishButton.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		finishButton.setForeground(buttonTextColor);
		finishButton.setBackground(buttonColor);
		finishButton.setFocusable(true);
		finishButton.setOpaque(true);
		finishButton.addKeyListener(this);
		finishButton.addFocusListener(this);
		finishButton.addMouseListener(this);
		finalPanel.add(finishButton);
	}

	public void signInAction() {
		boolean isEmptyData = this.userField.getText().trim().isEmpty()
				|| new String(this.passwordField.getPassword()).isEmpty();
		if (isEmptyData) {
			System.out.println("Debe llenar todos los campos");
		} else {
			try {
				db.conectar();
				st = db.getConnection().createStatement();
				ResultSet resultados = st.executeQuery("SELECT nick_usu, contra_usu, emai_usu FROM Usuario");
				boolean exist = false;
				String userString = new String(this.userField.getText().trim());
				String passwordString = new String(this.passwordField.getPassword());
				while (resultados.next()) {
					String userDB = resultados.getString("nick_usu");
					String passwordDB = resultados.getString("contra_usu");
					String emailDB = resultados.getString("emai_usu");
					exist = userString.equals(userDB) && passwordString.equals(passwordDB)
							|| userString.equals(emailDB) && passwordString.equals(passwordDB);
					if (exist) {
						try {
							st.executeUpdate("UPDATE Usuario SET sesion_act = 's' WHERE nick_usu = '" + userDB + "'");
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
						}
						break;
					}
				}
				if (exist) {
					System.out.println("Bienvenido");
					// instanciar menu
					Menu menu = new Menu("Sistema");
					menu.setVisible(true);
					this.setVisible(false);
				} else {
					System.out.println("Usuario o password incorrectos.");
				}
				db.desconectar();
			} catch (SQLException err) {
				JOptionPane.showMessageDialog(null, "Error" + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
			userField.setText("");
			passwordField.setText("");
		}
	}

	public void registerAction() {
		boolean isEmptyData = userRegisterField.getText().trim().isEmpty()
				|| new String(passwordRegisterField.getPassword()).isEmpty()
				|| new String(passwordRegisterConfirmField.getPassword()).isEmpty()
				|| emailRegisterField.getText().trim().isEmpty() || nameRegisterField.getText().trim().isEmpty()
				|| lastnameRegisterField.getText().trim().isEmpty();

		if (isEmptyData) {
			System.out.println("Debe llenar todos los campos");
		} else {
			if (new String(passwordRegisterConfirmField.getPassword())
					.equals(new String(passwordRegisterField.getPassword()))) {
				try {
					db.conectar();
					st = db.getConnection().createStatement();

					String campos = "'" + userRegisterField.getText().trim() + "', '"
							+ emailRegisterField.getText().trim() + "', '"
							+ new String(passwordRegisterField.getPassword()) + "', '"
							+ nameRegisterField.getText().trim() + "', '" + lastnameRegisterField.getText().trim()
							+ "', 'n', 'n'";

					int resultado = st.executeUpdate(
							"INSERT INTO Usuario (nick_usu, emai_usu, contra_usu, nom_usu, ape_usu, is_admin, sesion_act) VALUES ("
									+ campos + ")");
					if (resultado == 1) {
						System.out.println("Usuario registrado con exito");
						db.desconectar();
						panel.setVisible(true);
						panelRegister.setVisible(false);
						finalPanel.setVisible(false);
						tira1.setVisible(false);
						tira2.setVisible(false);
						tira2.setBackground(panel1);
						userRegisterField.setText("");
						passwordRegisterField.setText("");
						passwordRegisterConfirm.setText("");
						emailRegisterField.setText("");
						lastnameRegisterField.setText("");
						nameRegisterField.setText("");
						userField.requestFocus();
					} else {
						System.out.println("Error al registrar usuario");
					}
				} catch (SQLException err) {
					JOptionPane.showMessageDialog(null, "Error" + err, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.out.println("las passwords deben coincidir");
			}
		}
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
	public void keyTyped(KeyEvent evt) {
		if (evt.getSource().equals(this.userField)) {
			if (this.userField.getText().length() > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.passwordField)) {
			if (this.passwordField.getPassword().length > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.userRegisterField)) {
			if (this.passwordField.getPassword().length > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.userRegisterField)) {
			if (this.userRegisterField.getText().length() > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.nameRegisterField)) {
			if (this.nameRegisterField.getText().length() > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.lastnameRegisterField)) {
			if (this.lastnameRegisterField.getText().length() > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.emailRegisterField)) {
			if (this.emailRegisterField.getText().length() > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.passwordRegisterField)) {
			if (this.passwordRegisterField.getPassword().length > 25) {
				evt.consume();
			}
		}

		if (evt.getSource().equals(this.passwordRegisterConfirmField)) {
			if (this.passwordRegisterConfirmField.getPassword().length > 25) {
				evt.consume();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		if (evt.getKeyCode() == 10) {
			if (evt.getSource().equals(this.registerButton)) {
				panel.setVisible(false);
				panelRegister.setVisible(true);
				tira1.setVisible(true);
				tira2.setVisible(true);
				userField.setText("");
				passwordField.setText("");
				nameRegisterField.requestFocus();
			}
			if (evt.getSource().equals(this.userField)) {
				signInAction();
			}
			if (evt.getSource().equals(this.passwordField)) {
				signInAction();
			}
			if (evt.getSource().equals(this.nextButton)) {
				panel.setVisible(false);
				panelRegister.setVisible(false);
				finalPanel.setVisible(true);
				tira2.setBackground(barColor);
				userRegisterField.requestFocus();
			}
			if (evt.getSource().equals(this.signIn)) {
				signInAction();
			}
			if (evt.getSource().equals(this.finishButton)) {
				registerAction();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent evt) {

	}

	// Focus
	@Override
	public void focusGained(FocusEvent evt) {
		// fields login
		if (evt.getSource().equals(this.userField)) {
			this.userField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.passwordField)) {
			this.passwordField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.signIn)) {
			this.signIn.setBackground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.registerButton)) {
			this.registerButton.setForeground(buttonColorEntered);
		}

		// fields register
		if (evt.getSource().equals(this.nameRegisterField)) {
			this.nameRegisterField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.lastnameRegisterField)) {
			this.lastnameRegisterField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.emailRegisterField)) {
			this.emailRegisterField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.nextButton)) {
			this.nextButton.setBackground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.userRegisterField)) {
			this.userRegisterField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.passwordRegisterField)) {
			this.passwordRegisterField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.passwordRegisterConfirmField)) {
			this.passwordRegisterConfirmField.setBorder(BorderFactory.createLineBorder(textFieldColorEntered, 2));
		}
		if (evt.getSource().equals(this.finishButton)) {
			this.finishButton.setBackground(buttonColorEntered);
		}
	}

	@Override
	public void focusLost(FocusEvent evt) {
		if (evt.getSource().equals(this.userField)) {
			this.userField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.passwordField)) {
			this.passwordField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.signIn)) {
			this.signIn.setBackground(buttonColor);
		}
		if (evt.getSource().equals(this.registerButton)) {
			this.registerButton.setForeground(buttonColor);
		}

		// fields register
		if (evt.getSource().equals(this.nameRegisterField)) {
			this.nameRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.lastnameRegisterField)) {
			this.lastnameRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.emailRegisterField)) {
			this.emailRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.nextButton)) {
			this.nextButton.setBackground(buttonColor);
		}
		if (evt.getSource().equals(this.userRegisterField)) {
			this.userRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.passwordRegisterField)) {
			this.passwordRegisterField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.passwordRegisterConfirmField)) {
			this.passwordRegisterConfirmField.setBorder(BorderFactory.createLineBorder(barColor, 2));
		}
		if (evt.getSource().equals(this.finishButton)) {
			this.finishButton.setBackground(buttonColor);
		}
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
		if (evt.getSource().equals(this.registerButton)) {
			panel.setVisible(false);
			panelRegister.setVisible(true);
			tira1.setVisible(true);
			tira2.setVisible(true);
			userField.setText("");
			passwordField.setText("");
			nameRegisterField.requestFocus();
		}
		if (evt.getSource().equals(this.backButton)) {
			panel.setVisible(true);
			panelRegister.setVisible(false);
			tira1.setVisible(false);
			tira2.setVisible(false);
			userRegisterField.setText("");
			passwordRegisterField.setText("");
			emailRegisterField.setText("");
			lastnameRegisterField.setText("");
			nameRegisterField.setText("");
			userField.requestFocus();
		}
		if (evt.getSource().equals(this.backButton2)) {
			panel.setVisible(false);
			panelRegister.setVisible(true);
			finalPanel.setVisible(false);
			tira2.setBackground(panel1);
			nameRegisterField.requestFocus();
		}
		if (evt.getSource().equals(this.seePassword)) {
			passwordRegisterField.setEchoChar((char) '\u2022');
			passwordRegisterConfirmField.setEchoChar((char) '\u2022');
		}
		if (evt.getSource().equals(this.seePassword2)) {
			passwordField.setEchoChar((char) '\u2022');
		}
		if (evt.getSource().equals(this.signIn)) {
			signInAction();
		}
		if (evt.getSource().equals(this.finishButton)) {
			registerAction();
		}
		if (evt.getSource().equals(this.nextButton)) {
			panel.setVisible(false);
			panelRegister.setVisible(false);
			finalPanel.setVisible(true);
			tira2.setBackground(barColor);
			userRegisterField.requestFocus();
		}
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if (evt.getSource().equals(this.seePassword)) {
			passwordRegisterField.setEchoChar((char) 0);
			passwordRegisterConfirmField.setEchoChar((char) 0);
		}
		if (evt.getSource().equals(this.seePassword2)) {
			passwordField.setEchoChar((char) 0);
		}
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		if (evt.getSource().equals(this.closeButton)) {
			this.closeButton.setBackground(barColor);
		}
		if (evt.getSource().equals(this.minButton)) {
			this.minButton.setBackground(barColor);
		}
		if (evt.getSource().equals(this.registerButton)) {
			this.registerButton.setForeground(buttonColor);
		}
		if (evt.getSource().equals(this.seePassword)) {
			this.seePassword.setBackground(panel1);
		}
		if (evt.getSource().equals(this.seePassword2)) {
			this.seePassword2.setBackground(panel1);
		}
		if (evt.getSource().equals(this.signIn)) {
			this.signIn.setBackground(buttonColor);
		}
		if (evt.getSource().equals(this.nextButton)) {
			this.nextButton.setBackground(buttonColor);
		}
		if (evt.getSource().equals(this.finishButton)) {
			this.finishButton.setBackground(buttonColor);
		}
		if (evt.getSource().equals(this.backButton)) {
			this.backButton.setForeground(buttonColor);
		}
		if (evt.getSource().equals(this.backButton2)) {
			this.backButton2.setForeground(buttonColor);
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
		if (evt.getSource().equals(this.registerButton)) {
			this.registerButton.setForeground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.seePassword)) {
			this.seePassword.setBackground(focusColor);
		}
		if (evt.getSource().equals(this.seePassword2)) {
			this.seePassword2.setBackground(focusColor);
		}
		if (evt.getSource().equals(this.signIn)) {
			this.signIn.setBackground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.nextButton)) {
			this.nextButton.setBackground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.finishButton)) {
			this.finishButton.setBackground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.backButton)) {
			this.backButton.setForeground(buttonColorEntered);
		}
		if (evt.getSource().equals(this.backButton2)) {
			this.backButton2.setForeground(buttonColorEntered);
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