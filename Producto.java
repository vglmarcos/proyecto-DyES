import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class Producto extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener,
    WindowListener, ItemListener, MouseMotionListener {

  private JLabel closeButton, minButton, titleLabel;
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
  private Color buttonTextColor = new Color(232, 236, 249);
  private String titlewindow;
  private Conexion db;
  private Statement st;
  private ResultSet rs;
  private int x, y;
  private String patchLogo = "images/Logo.png";
  private JPanel bar, mostrarResultados, agregarProducto;
  private String patchProduct = "images/product.png";
  private ImageIcon productImg = new ImageIcon(patchProduct);
  private JLabel product, consulta, busqueda, por, ordenado, agreProducto, id_prod, nombre, tipo, precio, cantidad,
      resultados;
  private JTextField consulta_txt, id_txt, tipo_txt, nombre_txt, precio_txt, cantidad_txt;
  private JLabel buscar, filtros, agregar, cancelar, eliminar, guardar, regresar;
  private JComboBox<String> por_cb, ordenado_cb, asce_desce;
  private JTable tabla;
  private DefaultTableModel modelo;
  private JScrollPane scroll;
  private int idDb;
  String consultaProducto, tipoProducto, ordenProducto, tipoOrden;
  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  public Producto(String title) {// constructor
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
      rs = st.executeQuery("SELECT MAX(id_prod) FROM Producto");
      rs.next();
      idDb = rs.getInt(1) + 1;
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

    // Panel para mostrar
    mostrarResultados = new JPanel();
    mostrarResultados.setBounds(20, 50, this.getWidth() - 40, this.getHeight() - 70);
    mostrarResultados.setBackground(panel1);
    mostrarResultados.setLayout(null);
    mostrarResultados.setVisible(true);

    // Panel para agregar
    agregarProducto = new JPanel();
    agregarProducto.setBounds(20, 50, this.getWidth() - 40, this.getHeight() - 70);
    agregarProducto.setBackground(panel1);
    agregarProducto.setLayout(null);
    agregarProducto.setVisible(false);

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

    // Panel Mostrar

    busqueda = new JLabel("B\u00FAsqueda de productos");
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
    resultados.setBounds(0, mostrarResultados.getHeight() / 2, mostrarResultados.getWidth(), 30);
    resultados.setForeground(fontColor1);
    resultados.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    resultados.setVisible(false);
    mostrarResultados.add(resultados);

    consulta_txt = new JTextField();
    consulta_txt.setBounds(consulta.getX() + consulta.getWidth(), 
      111, (this.mostrarResultados.getWidth() / 4) - 20, 40);
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
    por.setBounds(consulta_txt.getX() + consulta_txt.getWidth() + 10, 120, 50, 25);
    por.setForeground(fontColor1);
    por.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    mostrarResultados.add(por);

    por_cb = new JComboBox<>();
    por_cb.setBounds(por.getX() + por.getWidth() + 10, 
      111, (this.mostrarResultados.getWidth() / 4) - 20, 40);
    por_cb.setBackground(fieldColor);
    por_cb.setForeground(fontColor2);
    por_cb.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    por_cb.addItem("Producto");
    por_cb.addItem("Precio");
    por_cb.addItem("Tipo");
    por_cb.setBorder(BorderFactory.createLineBorder(barColor, 2));
    por_cb.addKeyListener(this);
    por_cb.addMouseListener(this);
    por_cb.addFocusListener(this);
    mostrarResultados.add(por_cb);

    ordenado = new JLabel("Ordenado por:");
    ordenado.setBounds(50, 180, 180, 25);
    ordenado.setForeground(fontColor1);
    ordenado.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    mostrarResultados.add(ordenado);

    ordenado_cb = new JComboBox<>();
    ordenado_cb.setBounds(ordenado.getX() + ordenado.getWidth(), 
      171, (this.mostrarResultados.getWidth() / 4) - 20, 40);
    ordenado_cb.setBackground(fieldColor);
    ordenado_cb.setForeground(fontColor2);
    ordenado_cb.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    ordenado_cb.addItem("Id producto");
    ordenado_cb.addItem("Producto");
    ordenado_cb.addItem("Precio");
    ordenado_cb.addItem("Tipo");
    ordenado_cb.setBorder(BorderFactory.createLineBorder(barColor, 2));
    ordenado_cb.addKeyListener(this);
    ordenado_cb.addMouseListener(this);
    ordenado_cb.addFocusListener(this);
    ordenado_cb.addFocusListener(this);
    mostrarResultados.add(ordenado_cb);

    asce_desce = new JComboBox<>();
    asce_desce.setBounds(ordenado_cb.getX() + ordenado_cb.getWidth() + 10, 171, 
      (this.mostrarResultados.getWidth() / 4) - 20, 40);
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
    buscar.setBounds(this.mostrarResultados.getWidth() - 250, 111, 200, 40);
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
    filtros.setBounds(this.mostrarResultados.getWidth() - 250, 171, 200, 40);
    filtros.setBackground(buttonColor);
    filtros.setForeground(buttonTextColor);
    filtros.setFont(new Font("Microsoft New Tai Lue", 1, 24));
    filtros.setBorder(BorderFactory.createLineBorder(barColor, 2));
    filtros.setOpaque(true);
    filtros.addFocusListener(this);
    filtros.addKeyListener(this);
    filtros.addMouseListener(this);
    mostrarResultados.add(filtros);

    String[] header = new String[] { "Id", "Nombre", "Precio", "Tipo" };
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
    scroll.setBounds(50, 240, this.mostrarResultados.getWidth() - 100, this.mostrarResultados.getHeight() - 240 - 100);
    tabla.getColumnModel().getColumn(0).setPreferredWidth((int)((this.mostrarResultados.getWidth() - 100) * 0.15)); // Id
    tabla.getColumnModel().getColumn(1).setPreferredWidth((int)((this.mostrarResultados.getWidth() - 100) * 0.4)); // Nombre
    tabla.getColumnModel().getColumn(2).setPreferredWidth((int)((this.mostrarResultados.getWidth() - 100) * 0.15)); // Precio
    tabla.getColumnModel().getColumn(3).setPreferredWidth((int)((this.mostrarResultados.getWidth() - 100) * 0.15)); // Tipo
    tabla.setRowHeight(25);
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 24));
    tabla.getTableHeader().setForeground(buttonTextColor);
    tabla.getTableHeader().setBackground(buttonColor);
    tabla.setBackground(fieldColor);
    tabla.setForeground(fontColor1);
    tabla.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    mostrarResultados.add(scroll);

    agregar = new JLabel("Agregar", SwingConstants.CENTER);
    agregar.setBounds(this.mostrarResultados.getWidth() - 250, this.scroll.getY() + this.scroll.getHeight() + 30, 200, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
    agregar.setBackground(buttonColor);
    agregar.setForeground(buttonTextColor);
    agregar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
    agregar.setBorder(BorderFactory.createLineBorder(barColor, 2));
    agregar.setOpaque(true);
    agregar.addFocusListener(this);
    agregar.addKeyListener(this);
    agregar.addMouseListener(this);
    mostrarResultados.add(agregar);

    eliminar = new JLabel("Eliminar", SwingConstants.CENTER);
    eliminar.setBounds(this.agregar.getX() - 220, this.agregar.getY(), 200, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
    eliminar.setBackground(buttonColor);
    eliminar.setForeground(buttonTextColor);
    eliminar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
    eliminar.setBorder(BorderFactory.createLineBorder(barColor, 2));
    eliminar.setOpaque(true);
    eliminar.addFocusListener(this);
    eliminar.addKeyListener(this);
    eliminar.addMouseListener(this);
    mostrarResultados.add(eliminar);

    cancelar = new JLabel("Cancelar", SwingConstants.CENTER);
    cancelar.setBounds(this.eliminar.getX() - 220, this.eliminar.getY(), 200, 40);
    cancelar.setBackground(buttonColor);
    cancelar.setForeground(buttonTextColor);
    cancelar.setFont(new Font("Microsoft New Tai lue", 1, 24));
    cancelar.setBorder(BorderFactory.createLineBorder(barColor, 2));
    cancelar.setOpaque(true);
    cancelar.addFocusListener(this);
    cancelar.addKeyListener(this);
    cancelar.addMouseListener(this);
    mostrarResultados.add(cancelar);

    // Visualizar Paneles
    this.add(mostrarResultados);
    this.add(agregarProducto);

    // Panel Agregar

    agreProducto = new JLabel("Agregar producto");
    agreProducto.setBounds(50, 60, 400, 40);
    agreProducto.setForeground(fontColor1);
    agreProducto.setFont(new Font("Microsoft New Tai Lue", 1, 30));
    agregarProducto.add(agreProducto);

    id_prod = new JLabel("Id Producto");
    id_prod.setBounds(50, 200, 150, 25);
    id_prod.setForeground(fontColor1);
    id_prod.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    agregarProducto.add(id_prod);

    id_txt = new JTextField(String.valueOf(idDb), SwingConstants.CENTER);
    id_txt.setBounds(id_prod.getX() + id_prod.getWidth() + 20, id_prod.getY() + 1, 200, 40);
    id_txt.setBackground(fieldColor);
    id_txt.setForeground(fontColor2);// color de la letra
    id_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));// normal, tamaño
    id_txt.setCaretColor(new Color(255, 255, 255));
    id_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
    id_txt.setEditable(false);
    id_txt.setSelectedTextColor(selectedText);
    id_txt.setSelectionColor(selectionColor);
    id_txt.addFocusListener(this);
    id_txt.addKeyListener(this);
    id_txt.addMouseListener(this);
    agregarProducto.add(id_txt);

    nombre = new JLabel("Nombre");
    nombre.setBounds(50, this.id_prod.getY() + id_prod.getHeight() + 40, 150, 25);
    nombre.setForeground(fontColor1);
    nombre.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    agregarProducto.add(nombre);

    nombre_txt = new JTextField();
    nombre_txt.setBounds(id_prod.getX() + id_prod.getWidth() + 20, nombre.getY() + 1, 200, 40);
    nombre_txt.setBackground(fieldColor);
    nombre_txt.setForeground(fontColor2);// color de la letra
    nombre_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));// normal, tamaño
    nombre_txt.setCaretColor(new Color(255, 255, 255));
    nombre_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
    nombre_txt.setSelectedTextColor(selectedText);
    nombre_txt.setSelectionColor(selectionColor);
    nombre_txt.addFocusListener(this);
    nombre_txt.addKeyListener(this);
    nombre_txt.addMouseListener(this);
    agregarProducto.add(nombre_txt);

    tipo = new JLabel("Tipo");
    tipo.setBounds(50, nombre.getY() + nombre.getHeight() + 40, 150, 25);
    tipo.setForeground(fontColor1);
    tipo.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    agregarProducto.add(tipo);

    tipo_txt = new JTextField();
    tipo_txt.setBounds(tipo.getX() + tipo.getWidth() + 20, tipo.getY() + 1, 200, 40);
    tipo_txt.setBackground(fieldColor);
    tipo_txt.setForeground(fontColor2);// color de la letra
    tipo_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));// normal, tamaño
    tipo_txt.setCaretColor(new Color(255, 255, 255));
    tipo_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
    tipo_txt.setSelectedTextColor(selectedText);
    tipo_txt.setSelectionColor(selectionColor);
    tipo_txt.addFocusListener(this);
    tipo_txt.addKeyListener(this);
    tipo_txt.addMouseListener(this);
    agregarProducto.add(tipo_txt);

    precio = new JLabel("Precio $");
    precio.setBounds(50, tipo.getY() + tipo.getHeight() + 40, 150, 25);
    precio.setForeground(fontColor1);
    precio.setFont(new Font("Microsoft New Tai Lue", 0, 24));
    agregarProducto.add(precio);

    precio_txt = new JTextField("", SwingConstants.RIGHT);
    precio_txt.setBounds(precio.getX() + precio.getWidth() + 20, precio.getY() + 1, 200, 40);
    precio_txt.setBackground(fieldColor);
    precio_txt.setForeground(fontColor2);// color de la letra
    precio_txt.setFont(new Font("Microsoft New Tai Lue", 0, 24));// normal, tamaño
    precio_txt.setCaretColor(new Color(255, 255, 255));
    precio_txt.setBorder(BorderFactory.createLineBorder(barColor, 2));
    precio_txt.setSelectedTextColor(selectedText);
    precio_txt.setSelectionColor(selectionColor);
    precio_txt.addFocusListener(this);
    precio_txt.addKeyListener(this);
    precio_txt.addMouseListener(this);
    agregarProducto.add(precio_txt);

    product = new JLabel("", SwingConstants.CENTER);
    product.setBounds(nombre_txt.getX() + nombre_txt.getWidth() + 100, 50
      , agregarProducto.getHeight() - 100, 
      agregarProducto.getHeight() - 100);
    product.setIcon(productImg);
    product.setHorizontalTextPosition(SwingConstants.CENTER);
    product.setVerticalTextPosition(SwingConstants.CENTER);
    product.setBorder(BorderFactory.createLineBorder(barColor, 2));
    product.setBackground(buttonColor);
    product.setOpaque(true);
    agregarProducto.add(product);

    regresar = new JLabel("Regresar", SwingConstants.CENTER);
    regresar.setBounds(50, this.agregarProducto.getHeight() - 80, 200, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
    regresar.setBackground(buttonColor);
    regresar.setForeground(buttonTextColor);
    regresar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
    regresar.setBorder(BorderFactory.createLineBorder(barColor, 2));
    regresar.setOpaque(true);
    regresar.addMouseListener(this);
    regresar.addFocusListener(this);
    regresar.addKeyListener(this);
    agregarProducto.add(regresar);

    guardar = new JLabel("Guardar", SwingConstants.CENTER);
    guardar.setBounds(regresar.getX() + regresar.getWidth() + 20, this.regresar.getY(), 200, 40); // los primeros dos son la posicion y los siguientes dos son el tamaño
    guardar.setBackground(buttonColor);
    guardar.setForeground(buttonTextColor);
    guardar.setFont(new Font("Microsoft New Tai Lue", 1, 24));// nombre de la fuente, tipo de letra, tamaño
    guardar.setBorder(BorderFactory.createLineBorder(barColor, 2));
    guardar.setOpaque(true);
    guardar.addMouseListener(this);
    guardar.addFocusListener(this);
    guardar.addKeyListener(this);
    agregarProducto.add(guardar);
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
    if (evt.getKeyCode() == 10) {
      if (evt.getSource() == this.regresar) {
        this.agregarProducto.setVisible(false);
        this.mostrarResultados.setVisible(true);
      }
      if (evt.getSource() == this.guardar) {
        try {
          String campos = "'" + id_txt.getText().trim() + "', '" + nombre_txt.getText().trim() + "', '"
              + tipo_txt.getText().trim() + "', '" + precio_txt.getText().trim() + "'";

          int resultado = st.executeUpdate(
              "INSERT INTO Producto (id_prod, nom_prod, tipo_prod, prec_prod) VALUES (" + campos + ")");

          if (resultado == 1) {
            JOptionPane.showMessageDialog(null, "Producto registrado con \u00e9xito", "Registrado",
                JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Producto registrado con exito");
          } else {
            JOptionPane.showMessageDialog(null, "Error al registrar producto", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al registrar producto");
          }
        } catch (SQLException err) {
          JOptionPane.showMessageDialog(null, "Error" + err, "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
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
    } else if (evt.getSource() == this.agregar) {
      this.agregar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.id_txt) {
      this.id_txt.setBackground(focusColor);
    } else if (evt.getSource() == this.nombre_txt) {
      this.nombre_txt.setBackground(focusColor);
    } else if (evt.getSource() == this.tipo_txt) {
      this.tipo_txt.setBackground(focusColor);
    } else if (evt.getSource() == this.precio_txt) {
      this.precio_txt.setBackground(focusColor);
    } else if (evt.getSource() == this.cantidad_txt) {
      this.cantidad_txt.setBackground(focusColor);
    } else if (evt.getSource() == this.regresar) {
      this.regresar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.guardar) {
      this.guardar.setBackground(buttonColorEntered);
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
    } else if (evt.getSource() == this.agregar) {
      this.agregar.setBackground(buttonColor);
    } else if (evt.getSource() == this.id_txt) {
      this.id_txt.setBackground(fieldColor);
    } else if (evt.getSource() == this.nombre_txt) {
      this.nombre_txt.setBackground(fieldColor);
    } else if (evt.getSource() == this.tipo_txt) {
      this.tipo_txt.setBackground(fieldColor);
    } else if (evt.getSource() == this.precio_txt) {
      this.precio_txt.setBackground(fieldColor);
    } else if (evt.getSource() == this.cantidad_txt) {
      this.cantidad_txt.setBackground(fieldColor);
    } else if (evt.getSource() == this.regresar) {
      this.regresar.setBackground(buttonColor);
    } else if (evt.getSource() == this.guardar) {
      this.guardar.setBackground(buttonColor);
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
    if (evt.getSource() == this.agregar) {
      this.mostrarResultados.setVisible(false);
      this.agregarProducto.setVisible(true);
    } else if (evt.getSource() == this.regresar) {
      this.agregarProducto.setVisible(false);
      this.mostrarResultados.setVisible(true);
    } else if (evt.getSource() == this.cancelar) {
      Menu m = new Menu("Men\u00FA");
      m.setVisible(true);
      this.setVisible(false);
    } else if (evt.getSource() == this.buscar) {
      consultaProducto = consulta_txt.getText() + "%";
      if (consultaProducto.isEmpty()) {
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
        tipoProducto = por_cb.getSelectedItem().toString();
        ordenProducto = ordenado_cb.getSelectedItem().toString();
        tipoOrden = asce_desce.getSelectedItem().toString();

        String campo = "";
        switch (tipoProducto) {
          case "Id producto":
            campo = "id_prod";
            break;
          case "Producto":
            campo = "nom_prod";
            break;
          case "Precio":
            campo = "nom_prod";
            break;
          case "Tipo":
            campo = "tipo_prod";
            break;
          default:
            JOptionPane.showMessageDialog(null, "Un error raro :O");
            break;
        }

        String orden = "";
        switch (ordenProducto) {
          case "Id producto":
            orden = "id_prod";
            break;
          case "Producto":
            orden = "nom_prod";
            break;
          case "Precio":
            orden = "nom_prod";
            break;
          case "Tipo":
            orden = "tipo_prod";
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
          rs = st.executeQuery("SELECT * FROM Producto WHERE " + campo + " LIKE '" + consultaProducto + "' ORDER BY "
              + orden + " " + tipo);
          while (rs.next()) {
            String[] fila = new String[] { rs.getString("id_prod"), rs.getString("nom_prod"),
                rs.getString("prec_prod"), rs.getString("tipo_prod") };
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
    } else if (evt.getSource() == this.filtros) {
      consulta_txt.setText("");
      resultados.setVisible(false);
      int rowCount = modelo.getRowCount();
      for (int i = rowCount - 1; i >= 0; i--) {
        modelo.removeRow(i);
      }
    } else if (evt.getSource() == this.eliminar) {
      int column = 0;
      int row = tabla.getSelectedRow();
      if (row != -1) { // si no esta vacia la tabla
        String value = tabla.getModel().getValueAt(row, column).toString(); // obtenemos id
        try {
          st.executeUpdate("DELETE FROM Producto WHERE id_prod = '" + value + "'");
          modelo.removeRow(row);
        } catch (SQLException err) {
          JOptionPane.showMessageDialog(null, err.toString());
        }

      }
    }
    if (evt.getSource() == this.guardar) {
      try {
        String campos = "'" + id_txt.getText().trim() + "', '" + nombre_txt.getText().trim() + "', '"
            + tipo_txt.getText().trim() + "', '" + precio_txt.getText().trim() + "'";

        int resultado = st.executeUpdate(
            "INSERT INTO Producto (id_prod, nom_prod, tipo_prod, prec_prod) VALUES (" + campos + ")");

        if (resultado == 1) {
          JOptionPane.showMessageDialog(null, "Producto registrado con \u00e9xito", "Registrado",
              JOptionPane.INFORMATION_MESSAGE);
          System.out.println("Producto registrado con exito");
        } else {
          JOptionPane.showMessageDialog(null, "Error al registrar producto", "Error", JOptionPane.ERROR_MESSAGE);
          System.out.println("Error al registrar producto");
        }
      } catch (SQLException err) {
        JOptionPane.showMessageDialog(null, "Error" + err, "Error", JOptionPane.ERROR_MESSAGE);
      }
      nombre_txt.setText("");
      tipo_txt.setText("");
      precio_txt.setText("");
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
    } else if (evt.getSource() == this.agregar) {
      this.agregar.setBackground(buttonColor);
    } else if (evt.getSource() == this.regresar) {
      this.regresar.setBackground(buttonColor);
    } else if (evt.getSource() == this.guardar) {
      this.guardar.setBackground(buttonColor);
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
    this.agregar.setBackground(buttonColor);
    this.regresar.setBackground(buttonColor);
    this.guardar.setBackground(buttonColor);
    if (evt.getSource() == this.buscar) {
      this.buscar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.filtros) {
      this.filtros.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.cancelar) {
      this.cancelar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.eliminar) {
      this.eliminar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.agregar) {
      this.agregar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.regresar) {
      this.regresar.setBackground(buttonColorEntered);
    } else if (evt.getSource() == this.guardar) {
      this.guardar.setBackground(buttonColorEntered);
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
      String usuario = "";
      rs = st.executeQuery("SELECT nom_usu, sesion_act FROM Usuario");
      while (rs.next()) {
        if (rs.getString("sesion_act").equals("s")) {
          usuario = rs.getString("nom_usu");
          st.executeUpdate("UPDATE Usuario SET sesion_act = 'n' WHERE nom_usu = '" + usuario + "'");
          break;
        }
      }
      db.desconectar();
      System.out.println("Se ha desconectado el usuario: " + usuario);
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
