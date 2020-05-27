import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class Empleado extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener,
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
    private JPanel bar, mostrarResultados;
    private String patchEmpleoyee = "images/empleoyee.png";
    private ImageIcon empleoyeeImg = new ImageIcon(patchEmpleoyee);
    private JLabel empleoyee, consulta, busqueda, resultados, por, ordenado, agreEmpleado, id_em, nombre, telefono;
    private JTextField consulta_txt, id_txt, nombre_txt, telefono_txt;
    private JLabel buscar, filtros, cancelar, eliminar;
    private JComboBox<String> por_cb, ordenado_cb, asce_desce;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JScrollPane scroll;
    private Integer idDb;

    public Empleado(String title) {
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

        // iniciamos conexion a la db
        db = new Conexion();
        try {
            db.conectar();
            st = db.getConnection().createStatement();

            rs = st.executeQuery("SELECT MAX(id_usu) FROM Usuario");
            rs.next();
            idDb = rs.getInt(1) + 1;
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

        // Panel para mostrar
        mostrarResultados = new JPanel();
        mostrarResultados.setBounds(20, 50, 1460, 730);
        mostrarResultados.setBackground(panel1);
        mostrarResultados.setLayout(null);
        mostrarResultados.setVisible(true);

        // Panel para agregar

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

        // Panel Mostrar

        busqueda = new JLabel("B\u00FAsqueda de empleados");
        busqueda.setBounds(50, 40, 400, 40);
        busqueda.setForeground(fontColor1);
        busqueda.setFont(new Font("Microsoft New Tai Lue", 1, 30));
        mostrarResultados.add(busqueda);

        consulta = new JLabel("Consulta");
        consulta.setBounds(50, 120, 120, 25);
        consulta.setForeground(fontColor1);
        consulta.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        mostrarResultados.add(consulta);

        resultados = new JLabel("No se han encontrado resultados para esta b\u00FAsqueda.", SwingConstants.CENTER);
        resultados.setBounds(50, 415, 1360, 30);
        resultados.setForeground(fontColor1);
        resultados.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        resultados.setVisible(false);
        mostrarResultados.add(resultados);

        consulta_txt = new JTextField();
        consulta_txt.setBounds(200, 111, 400, 40);
        consulta_txt.setBackground(fieldColor);
        consulta_txt.setForeground(fontColor2);// color de la letra
        consulta_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));// normal, tamaño
        consulta_txt.setCaretColor(new Color(255, 255, 255));
        consulta_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
        consulta_txt.setSelectedTextColor(selectedText);
        consulta_txt.setSelectionColor(selectionColor);
        consulta_txt.addFocusListener(this);
        consulta_txt.addKeyListener(this);
        consulta_txt.addMouseListener(this);
        mostrarResultados.add(consulta_txt);

        por = new JLabel("por:");
        por.setBounds(630, 120, 50, 25);
        por.setForeground(fontColor1);
        por.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        mostrarResultados.add(por);

        por_cb = new JComboBox<>();
        por_cb.setBounds(700, 111, 400, 40);
        por_cb.setBackground(fieldColor);
        por_cb.setForeground(fontColor2);
        por_cb.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        por_cb.addItem("Nombre");
        por_cb.addItem("Correo");
        por_cb.setBorder(BorderFactory.createLineBorder(barColor, 2));
        por_cb.addKeyListener(this);
        por_cb.addMouseListener(this);
        por_cb.addFocusListener(this);
        mostrarResultados.add(por_cb);

        ordenado = new JLabel("Ordenado por:");
        ordenado.setBounds(50, 180, 200, 25);
        ordenado.setForeground(fontColor1);
        ordenado.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        mostrarResultados.add(ordenado);

        ordenado_cb = new JComboBox<>();
        ordenado_cb.setBounds(240, 171, 400, 40);
        ordenado_cb.setBackground(fieldColor);
        ordenado_cb.setForeground(fontColor2);
        ordenado_cb.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        ordenado_cb.addItem("Id");
        ordenado_cb.addItem("Nombre");
        ordenado_cb.addItem("Correo");
        ordenado_cb.setBorder(BorderFactory.createLineBorder(barColor, 2));
        ordenado_cb.addKeyListener(this);
        ordenado_cb.addMouseListener(this);
        ordenado_cb.addFocusListener(this);
        ordenado_cb.addFocusListener(this);
        mostrarResultados.add(ordenado_cb);

        asce_desce = new JComboBox<>();
        asce_desce.setBounds(700, 171, 400, 40);
        asce_desce.setBackground(fieldColor);
        asce_desce.setForeground(fontColor2);
        asce_desce.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        asce_desce.addFocusListener(this);
        asce_desce.addItem("Ascendente");
        asce_desce.addItem("Descendente");
        asce_desce.setBorder(BorderFactory.createLineBorder(barColor, 2));
        asce_desce.addKeyListener(this);
        asce_desce.addMouseListener(this);
        asce_desce.addFocusListener(this);
        asce_desce.addFocusListener(this);
        mostrarResultados.add(asce_desce);

        buscar = new JLabel("Buscar", SwingConstants.CENTER);
        buscar.setBounds(1120, 111, 292, 40);
        buscar.setBackground(buttonColor);
        buscar.setForeground(buttonTextColor);
        buscar.setFont(new Font("Microsoft New Tai Lue", 1, 24));
        buscar.setBorder(BorderFactory.createLineBorder(barColor, 2));
        buscar.setOpaque(true);
        buscar.addFocusListener(this);
        buscar.addKeyListener(this);
        buscar.addMouseListener(this);
        mostrarResultados.add(buscar);

        filtros = new JLabel("Limpiar", SwingConstants.CENTER);
        filtros.setBounds(1120, 171, 292, 40);
        filtros.setBackground(buttonColor);
        filtros.setForeground(buttonTextColor);
        filtros.setFont(new Font("Microsoft New Tai Lue", 1, 24));
        filtros.setBorder(BorderFactory.createLineBorder(barColor, 2));
        filtros.setOpaque(true);
        filtros.addFocusListener(this);
        filtros.addKeyListener(this);
        filtros.addMouseListener(this);
        mostrarResultados.add(filtros);

        cancelar = new JLabel("Cancelar", SwingConstants.CENTER);
        cancelar.setBounds(50, 650, 300, 40);
        cancelar.setBackground(buttonColor);
        cancelar.setForeground(buttonTextColor);
        cancelar.setFont(new Font("Microsoft New Tai lue", 1, 24));
        cancelar.setBorder(BorderFactory.createLineBorder(barColor, 2));
        cancelar.setOpaque(true);
        cancelar.addFocusListener(this);
        cancelar.addKeyListener(this);
        cancelar.addMouseListener(this);
        mostrarResultados.add(cancelar);

        eliminar = new JLabel("Eliminar", SwingConstants.CENTER);
        eliminar.setBounds(580, 650, 300, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
        eliminar.setBackground(buttonColor);
        eliminar.setForeground(buttonTextColor);
        eliminar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
        eliminar.setBorder(BorderFactory.createLineBorder(barColor, 2));
        eliminar.setOpaque(true);
        eliminar.addFocusListener(this);
        eliminar.addKeyListener(this);
        eliminar.addMouseListener(this);
        mostrarResultados.add(eliminar);

        String[] header = new String[] { "Id", "Nombre", "Correo" };
        modelo = new DefaultTableModel(null, header);
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(fieldColor);
        scroll.setBounds(50, 240, 1360, 380);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(25); // Id
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(300); // Precio
        tabla.setRowHeight(25);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 24));
        tabla.getTableHeader().setForeground(buttonTextColor);
        tabla.getTableHeader().setBackground(buttonColor);
        tabla.setBackground(fieldColor);
        tabla.setForeground(fontColor1);
        tabla.setFont(new Font("Microsoft New Tai Lue", 0, 24));
        mostrarResultados.add(scroll);

        // Visualizar Paneles
        this.add(mostrarResultados);
        // Panel Agregar
    }

    // ActionListener
    @Override
    public void actionPerformed(ActionEvent evt) {

    }

    // KeyListener
    @Override
    public void keyTyped(KeyEvent evt) { // No se ocupa aun

    }

    @Override
    public void keyReleased(KeyEvent evt) { // No se ocupa aun

    }

    @Override
    public void keyPressed(KeyEvent evt) {

    }

    // Focus Listener

    @Override
    public void focusGained(FocusEvent evt) {
        if (evt.getSource() == this.consulta_txt) {
            this.consulta_txt.setBackground(focusColor);
        } else if (evt.getSource() == this.por_cb) {
            this.por_cb.setBackground(focusColor);
        } else if (evt.getSource() == this.ordenado_cb) {
            this.ordenado_cb.setBackground(focusColor);
        } else if (evt.getSource() == this.asce_desce) {
            this.asce_desce.setBackground(focusColor);
        } else if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.id_txt) {
            this.id_txt.setBackground(focusColor);
        } else if (evt.getSource() == this.nombre_txt) {
            this.nombre_txt.setBackground(focusColor);
        } else if (evt.getSource() == this.telefono_txt) {
            this.telefono_txt.setBackground(focusColor);
        }
    }

    @Override
    public void focusLost(FocusEvent evt) {
        if (evt.getSource() == this.consulta_txt) {
            this.consulta_txt.setBackground(fieldColor);
        } else if (evt.getSource() == this.por_cb) {
            this.por_cb.setBackground(fieldColor);
        } else if (evt.getSource() == this.ordenado_cb) {
            this.ordenado_cb.setBackground(fieldColor);
        } else if (evt.getSource() == this.asce_desce) {
            this.asce_desce.setBackground(fieldColor);
        } else if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(buttonColor);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(buttonColor);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(buttonColor);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(buttonColor);
        } else if (evt.getSource() == this.id_txt) {
            this.id_txt.setBackground(fieldColor);
        } else if (evt.getSource() == this.nombre_txt) {
            this.nombre_txt.setBackground(fieldColor);
        } else if (evt.getSource() == this.telefono_txt) {
            this.telefono_txt.setBackground(fieldColor);
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
        if (evt.getSource() == this.cancelar) {
            Menu m = new Menu("Men\u00FA");
            m.setVisible(true);
            this.setVisible(false);
        }
        if (evt.getSource() == this.buscar) {
            String consultaEmpleado = consulta_txt.getText() + "%";
            if (consultaEmpleado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe de llenar todos los campos.");
            } else {
                int rowCount = modelo.getRowCount();
                if (rowCount != 0) {
                    // Remove rows one by one from the end of the table
                    // https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
                    for (int i = rowCount - 1; i >= 0; i--) {
                        modelo.removeRow(i);
                    }
                }
                String tipoProducto = por_cb.getSelectedItem().toString();
                String ordenProducto = ordenado_cb.getSelectedItem().toString();
                String tipoOrden = asce_desce.getSelectedItem().toString();

                String campo = "";
                switch (tipoProducto) {
                    case "Nombre":
                        campo = "nom_usu";
                        break;
                    case "Correo":
                        campo = "email_usu";
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Un error raro :O");
                        break;
                }

                String orden = "";
                switch (ordenProducto) {
                    case "Id":
                        orden = "id_usu";
                        break;
                    case "Nombre":
                        orden = "nom_usu";
                        break;
                    case "Correo":
                        orden = "email_usu";
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Un error raro :O");
                        break;
                }

                String tipo = "";
                switch (tipoOrden) {
                    case "Ascendente":
                        tipo = "ASC";
                        break;
                    case "Descendente":
                        tipo = "DESC";
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Un error raro :O");
                        break;
                }

                try {
                    rs = st.executeQuery("SELECT * FROM Usuario WHERE " + campo + " LIKE '" + consultaEmpleado
                            + "' ORDER BY " + orden + " " + tipo);
                    while (rs.next()) {
                        String[] fila = new String[] { rs.getString("id_usu"), rs.getString("nom_usu"),
                                rs.getString("emai_usu") };
                        modelo.addRow(fila);
                    }
                    int result = modelo.getRowCount();
                    if (result == 0) {
                        resultados.setVisible(true);
                    } else {
                        resultados.setVisible(false);
                    }
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(null, err.toString());
                }
            }
        }

        if (evt.getSource() == this.eliminar) {
            boolean yes = false;
            int column = 0;
            int row = tabla.getSelectedRow();
            if (row != -1) { // si no esta vacia la tabla
                try {
                    String value = tabla.getModel().getValueAt(row, column).toString(); // obtenemos id
                    rs = st.executeQuery("SELECT id_usu, nick_usu, sesion_act FROM Usuario");
                    while (rs.next()) {
                        if (rs.getString("sesion_act").equals("s")) {
                            if (rs.getString("id_usu").equals(value)) {
                                yes = true;
                            }
                        }
                    }
                    if (yes) {
                        JOptionPane.showMessageDialog(null, "No se puede eliminar un usuario conectado al sistema.");
                    } else {
                        modelo.removeRow(row);
                        st.executeUpdate("DELETE FROM Usuario WHERE id_usu = '" + value + "'");
                    }
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(null, err.toString());
                }
            }
        }

        if (evt.getSource() == this.filtros) {
            consulta_txt.setText("");
            resultados.setVisible(false);
            int rowCount = modelo.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                modelo.removeRow(i);
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
        if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(buttonColor);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(buttonColor);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(buttonColor);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(buttonColor);
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
        this.buscar.setBackground(buttonColor);
        this.filtros.setBackground(buttonColor);
        this.cancelar.setBackground(buttonColor);
        this.eliminar.setBackground(buttonColor);
        if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(buttonColorEntered);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(buttonColorEntered);
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

    }

    // WindowListener
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

    // ItemListener
    @Override
    public void itemStateChanged(ItemEvent evt) {

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
