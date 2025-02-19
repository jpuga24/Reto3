import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;


public class GestorProductos {
    Scanner sc=new Scanner(System.in);
    //Conexion a la BBDD
    private String User="root";
    private String Url="jdbc:mysql://localhost:3306/usurbiltex";
    private String Pass="Lymprr1982@!";
    private Scanner scanner;
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    private ArrayList<Producto> productos = new ArrayList<>();
    //Metodos para manipular productos
    public void AgregarProd(Producto producto) {
        // Verificar si la categoría existe antes de insertar el producto
        if (!categoriaExiste(producto.getId_categoria())) {
            System.out.println("Error: La categoría con id " + producto.getId_categoria() + " no existe.");
            return; // No insertar el producto si la categoría no existe
        }
    
        // Definimos la query para insertar un producto
        String queryAgg = "INSERT INTO productos (Nombre, Descripcion, Precio, Stock, Id_categoria, Descontinuado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(Url, User, Pass);
                         PreparedStatement ps = con.prepareStatement(queryAgg)) {
                        ps.setString(1, producto.getNombre());
                        ps.setString(2, producto.getDescripcion());
                        ps.setInt(3, (int) producto.getPrecio()); // Convertir a int
                        ps.setInt(4, producto.getStock());
                        ps.setInt(5, producto.getId_categoria()); // Asumimos que id_categoria es válido
                        ps.setString(6, producto.getDescontinuado()); // "Sí" o "No"
                        ps.setString(7, producto.getImagen());
        
                        int resultado = ps.executeUpdate();
        
                        if (resultado > 0) {
                            System.out.println("Producto añadido a la base de datos correctamente: " + producto.getNombre());
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al insertar producto: " + e.getMessage());
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al cargar el driver de la base de datos: " + e.getMessage());
                }
            }
    
    // Método para verificar si la categoría existe
    private boolean categoriaExiste(int Id_categoria) {
        String query = "SELECT COUNT(*) FROM categorias WHERE id_categoria = ?";
        try (Connection con = DriverManager.getConnection(Url, User, Pass);
             PreparedStatement ps = con.prepareStatement(query)) {
    
            ps.setInt(1, Id_categoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el resultado es mayor a 0, la categoría existe
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la categoría: " + e.getMessage());
        }
        return false;
    }
    
    public void FicheroCSV() throws IOException {
        System.out.println("Cargando fichero...");
    
        // Ruta absoluta del archivo CSV
        String rutaArchivo = "C:\\Users\\Miguel Campo\\Desktop\\Usurbiltex\\AplicacionJava\\src\\Productos.csv";
    
        // Verificar si el archivo existe
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe en la ruta especificada: " + rutaArchivo);
            return;
        }
    
        // Intentar leer el archivo
        try (BufferedReader ProdCsv = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String encabezadoCsv = ProdCsv.readLine(); // Descartar la primera línea (encabezado)
    
            // Verificar si el archivo está vacío
            if (encabezadoCsv == null) {
                System.out.println("El archivo está vacío.");
                return;
            }
    
            while ((linea = ProdCsv.readLine()) != null) {
                if (linea.isEmpty()) continue; // Saltar línea vacía
    
                // Separar los valores por comas
                String[] Csvtemp = linea.split(",");
    
                // Verificar que la línea tenga el número esperado de columnas
                if (Csvtemp.length == 9) {
                    try {
                        int id_producto = Integer.parseInt(Csvtemp[0].trim());
                        String nombre = Csvtemp[1].trim();
                        String descripcion = Csvtemp[2].trim();
                        double precio = Double.parseDouble(Csvtemp[3].trim());
                        int Stock = Integer.parseInt(Csvtemp[4].trim());
                        
                        // Convertir el nombre de la categoría a un id numérico
                        String categoriaStr = Csvtemp[5].trim();
                        int id_categoria;
                        switch (categoriaStr.toLowerCase()) {
                            case "pantalones":
                                id_categoria = 1;
                                break;
                            case "sudaderas":
                                id_categoria = 2;
                                break;
                            case "camisetas":
                                id_categoria = 3;
                                break;
                            case "camisas":
                                id_categoria = 4;
                                break;
                            case "chaquetas":
                                id_categoria = 5;
                                break;
                            default:
                                System.out.println("Categoría desconocida: " + categoriaStr + ". Línea ignorada.");
                                continue; // O asignar un valor por defecto
                        }
                        
                        LocalDate fechaCreacion = LocalDate.parse(Csvtemp[6].trim());
                        String descontinuado = Csvtemp[7].trim();
                        String imagen = Csvtemp[8].trim();
    
                        // Crear el objeto Producto y agregarlo a la lista
                        Producto producto = new Producto(nombre, descripcion, precio, Stock, id_producto, id_categoria, imagen, descontinuado, fechaCreacion);
                        productos.add(producto);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al procesar la línea: " + linea);
                        e.printStackTrace(); // Para obtener más detalles del error
                    } catch (DateTimeParseException e) {
                        System.out.println("Error al parsear la fecha en la línea: " + linea);
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Línea con formato incorrecto: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    
        System.out.println("Fichero cargado correctamente.");
    
        // Imprimir los datos desglosados de cada producto
        System.out.println("Productos cargados:");
        for (Producto p : productos) {
            System.out.println("------------------------------------------------");
            System.out.println("ID: " + p.getId_producto());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Descripción: " + p.getDescripcion());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Stock: " + p.getStock());
            System.out.println("Categoría (ID): " + p.getId_categoria());
            System.out.println("Fecha de Creación: " + p.getFechaCreacion());
            System.out.println("Descontinuado: " + p.getDescontinuado());
            System.out.println("Imagen: " + p.getImagen());
        }
        System.out.println("------------------------------------------------");
    }

    public void ActualizarProd() {
        try (Connection con = DriverManager.getConnection(Url, User, Pass)) {
            // Solicitar al usuario el nombre del producto a buscar
            System.out.println("Ingrese el nombre del producto a buscar:");
            String busqueda = sc.nextLine();
    
            // Buscar en la base de datos los productos que coincidan por nombre
            String queryActu = "SELECT * FROM productos WHERE Nombre LIKE ?";
            try (PreparedStatement ps = con.prepareStatement(queryActu)) {
                ps.setString(1, "%" + busqueda + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.isBeforeFirst()) { // No hay resultados
                        System.out.println("No se encontraron productos con ese nombre.");
                        return;
                    }
                    // Mostrar productos encontrados
                    System.out.println("Productos encontrados:");
                    while (rs.next()) {
                        System.out.printf("ID: %d | Nombre: %s | Descripción: %s | Precio: %.2f | Stock: %d | Categoría: %d | Imagen: %s%n",
                                rs.getInt("id_producto"),
                                rs.getString("Nombre"),
                                rs.getString("Descripcion"),
                                rs.getDouble("Precio"),
                                rs.getInt("Stock"),
                                rs.getInt("Id_categoria"),
                                rs.getString("Imagen"));
                    }
                }
            }
    
            // Solicitar al usuario el ID del producto a actualizar
            System.out.println("\nIntroduce el ID del producto a actualizar:");
            int id = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer
    
            // Menú para actualizar los campos
            int opcion;
            do {
                System.out.println("\nSeleccione el campo que desea actualizar:");
                System.out.println("1. Nombre del producto");
                System.out.println("2. Descripción del producto");
                System.out.println("3. Precio del producto");
                System.out.println("4. Cantidad en stock");
                System.out.println("5. Id_categoría del producto");
                System.out.println("6. URL de la imagen");
                System.out.println("0. Volver al menú principal");
                System.out.print("Elige una opción: ");
    
                try {
                    opcion = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida.");
                    sc.nextLine(); // Limpiar buffer
                    opcion = -1;
                    continue;
                }
                sc.nextLine(); // Limpiar buffer
    
                String queryUpdate;
                switch (opcion) {
                    case 0:
                        System.out.println("Volviendo al menú principal.");
                        break;
                    case 1:
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = sc.nextLine();
                        queryUpdate = "UPDATE productos SET Nombre = ? WHERE id_producto = ?";
                        try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                            ps.setString(1, nuevoNombre);
                            ps.setInt(2, id);
                            if (ps.executeUpdate() > 0) {
                                System.out.println("Nombre actualizado.");
                            } else {
                                System.out.println("No se actualizó el nombre.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al actualizar nombre: " + e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.print("Nueva descripción: ");
                        String nuevaDesc = sc.nextLine();
                        queryUpdate = "UPDATE productos SET Descripcion = ? WHERE id_producto = ?";
                        try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                            ps.setString(1, nuevaDesc);
                            ps.setInt(2, id);
                            if (ps.executeUpdate() > 0) {
                                System.out.println("Descripción actualizada.");
                            } else {
                                System.out.println("No se actualizó la descripción.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al actualizar descripción: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.print("Nuevo precio: ");
                        double nuevoPrecio = sc.nextDouble();
                        sc.nextLine();
                        queryUpdate = "UPDATE productos SET Precio = ? WHERE id_producto = ?";
                        try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                            ps.setDouble(1, nuevoPrecio);
                            ps.setInt(2, id);
                            if (ps.executeUpdate() > 0) {
                                System.out.println("Precio actualizado.");
                            } else {
                                System.out.println("No se actualizó el precio.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al actualizar precio: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("Nuevo stock: ");
                        int nuevoStock = sc.nextInt();
                        sc.nextLine();
                        queryUpdate = "UPDATE productos SET Stock = ? WHERE id_producto = ?";
                        try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                            ps.setInt(1, nuevoStock);
                            ps.setInt(2, id);
                            if (ps.executeUpdate() > 0) {
                                System.out.println("Stock actualizado.");
                            } else {
                                System.out.println("No se actualizó el stock.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al actualizar stock: " + e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.print("Nueva categoría: ");
                        int nuevaCat = sc.nextInt();
                        sc.nextLine();
                        queryUpdate = "UPDATE productos SET Id_categoria = ? WHERE id_producto = ?";
                        try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                            ps.setInt(1, nuevaCat);
                            ps.setInt(2, id);
                            if (ps.executeUpdate() > 0) {
                                System.out.println("Categoría actualizada.");
                            } else {
                                System.out.println("No se actualizó la categoría.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al actualizar categoría: " + e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.print("Nuevo link de imagen: ");
                        String nuevoImg = sc.nextLine();
                        queryUpdate = "UPDATE productos SET Imagen = ? WHERE id_producto = ?";
                        try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                            ps.setString(1, nuevoImg);
                            ps.setInt(2, id);
                            if (ps.executeUpdate() > 0) {
                                System.out.println("Imagen actualizada.");
                            } else {
                                System.out.println("No se actualizó la imagen.");
                            }
                        } catch (SQLException e) {
                                                    System.out.println("Error al actualizar imagen: " + e.getMessage());
                                                }
                                                break;
                                            default:
                                                System.out.println("Opción no válida.");
                                                break;
                                        }
                                    } while (opcion != 0);
                                } catch (SQLException e) {
                                    System.out.println("Error al acceder a la base de datos: " + e.getMessage());
                                }
                            }
                        


 public void EliminarProd() {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        // Conexión a la base de datos
        con = DriverManager.getConnection(Url, User, Pass);
        
        // Consulta para obtener productos descontinuados
        String query = "SELECT * FROM productos WHERE Descontinuado = 'SI' OR Stock = 0";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        
        // Verificar si hay productos descontinuados
        boolean hayProductosDescontinuados = false;
        List<Integer> productosDescontinuados = new ArrayList<>();
        
        // Mostrar productos descontinuados
        System.out.println("Productos descontinuados disponibles para eliminar:");
        while (rs.next()) {
            int id_producto = rs.getInt("id_producto");
            String nombre = rs.getString("Nombre");
            String descripcion = rs.getString("Descripcion");
            double precio = rs.getDouble("Precio");
            int Stock = rs.getInt("Stock");
            String imagen = rs.getString("Imagen");
            int Id_categoria = rs.getInt("Id_categoria");
            LocalDate fechaCreacion = rs.getDate("FechaCreacion").toLocalDate();
            
        System.out.println("ID: " + id_producto + "\n" + "Nombre: " + nombre + "\n" + "Descripcion: " + descripcion + "\n" + "Precio: " + precio + "\n" + "Stock: " + Stock + "\n" + "Id_categoria: " + Id_categoria + "\n" + "Imagen: " + imagen + "\n" + "Fecha Creacion: " + fechaCreacion + "\n");
            productosDescontinuados.add(id_producto);
            hayProductosDescontinuados = true;
        }
        
        if (!hayProductosDescontinuados) {
            System.out.println("No hay productos descontinuados disponibles para eliminar.");
            return;
        }
        
        // Solicitar al usuario que seleccione el producto a eliminar
        System.out.println("Seleccione el ID del producto que desea eliminar (ingrese 0 para cancelar):");
        int idProductoEliminar = sc.nextInt();
        
        if (idProductoEliminar == 0) {
            System.out.println("Operacion cancelada.");
            return;
        }
        
        // Verificar si el ID ingresado corresponde a un producto descontinuado
        if (!productosDescontinuados.contains(idProductoEliminar)) {
            System.out.println("ID inválido o el producto seleccionado no está descontinuado.");
            return;
        }
        
        // Confirmar la eliminación
        System.out.println("¿Estás seguro de que deseas eliminar el producto con ID " + idProductoEliminar + "? (Si/No)");
        sc.nextLine();  // Limpiar buffer
        String confirmacion = sc.nextLine().trim();
        
        if (confirmacion.equalsIgnoreCase("Si")) {
            // Eliminar el producto de la base de datos
            String deleteQuery = "DELETE FROM productos WHERE id_producto = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, idProductoEliminar);
            int rowsAffected = deleteStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Producto con ID " + idProductoEliminar + " eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el producto con ID " + idProductoEliminar + ".");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al acceder a la base de datos: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}


 // Método para exportar los productos a un archivo JSON
 public void exportarProductosAJson() {
    List<Producto> listaProductos = new ArrayList<>();
    String query = "SELECT * FROM productos";
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(Url, User, Pass);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("FechaCreacion");
                LocalDate fecha = ts != null ? ts.toLocalDateTime().toLocalDate():LocalDate.now();
                Producto prod = new Producto(
                    rs.getString("Nombre"),
                    rs.getString("Descripcion"),
                    rs.getDouble("Precio"),
                    rs.getInt("Stock"),
                    rs.getInt("id_producto"),       // Asegúrate de que el nombre de la columna sea correcto
                    rs.getInt("Id_categoria"),
                    rs.getString("Imagen"),
                    rs.getString("Descontinuado"),
                    fecha
                );
                listaProductos.add(prod);
            }
        }
        
        // Convertir la lista de productos a JSON utilizando Gson
        //Gson es una libreria de google para facilitar la conversion de objetos java a JSON
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
        new JsonPrimitive(src.toString()))
        .setPrettyPrinting()
        .create();
    String json = gson.toJson(listaProductos);
        // Escribir el JSON en un archivo
        try (FileWriter writer = new FileWriter("productos.json")) {
            writer.write(json);
            System.out.println("Productos exportados a productos.json");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Error en la conexión o consulta: " + e.getMessage());
    }
}
public void exportarProductosConAltaGananciaAJson() {
        String query = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.Stock, p.imagen, p.Id_categoria, p.FechaCreacion, p.descontinuado, SUM(dp.Cantidad * dp.Precio) AS ganancia " +
                       "FROM productos p " +
                       "JOIN detalle_pedido dp ON p.id_producto = dp.id_producto " +
                       "GROUP BY p.id_producto, p.nombre " +
                       "HAVING ganancia > 500 " +
                       "ORDER BY ganancia DESC";

        try (Connection con = obtenerConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Producto> listaProductos = new ArrayList<>();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("FechaCreacion");
                LocalDate fecha = ts != null ? ts.toLocalDateTime().toLocalDate():LocalDate.now();
                Producto producto = new Producto(
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("Stock"),
                    rs.getInt("id_producto"),
                    rs.getInt("Id_categoria"),
                    rs.getString("imagen"),
                    rs.getString("descontinuado"),
                    fecha
                );
                listaProductos.add(producto);
            }

            Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
            new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();
            String json = gson.toJson(listaProductos);

            try (FileWriter writer = new FileWriter("productos_ganancia.json")) {
                writer.write(json);
                System.out.println("Productos exportados a productos_ganancia.json");
            } catch (IOException e) {
                System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexión o consulta: " + e.getMessage());
        }
    }

    private Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de MySQL: " + e.getMessage());
        }
        return DriverManager.getConnection(Url, User, Pass);
    }


    // Método para obtener el total de ganancias
    public void exportarTotalGananciasAJson() {
        String query = "SELECT SUM(PrecioTotal) as TotalGanancias FROM pedidos";
        ejecutarConsultaYExportarAJson(query, "total_ganancias.json");
    }

    // Método para obtener productos con stock bajo
    public void exportarProductosBajoStockAJson() {
        String query = "SELECT Nombre as Productos_BajoStock FROM productos WHERE Stock < 5";
        ejecutarConsultaYExportarAJson(query, "productos_bajo_stock.json");
    }

    // Método para obtener clientes con más pedidos
    public void exportarClientesConMasPedidosAJson() {
        String query = "SELECT u.id_user, u.nombre, COUNT(p.id_pedido) AS total_pedidos " +
                       "FROM usuario u " +
                       "JOIN pedidos p ON u.id_user = p.id_user " +
                       "GROUP BY u.id_user, u.nombre " +
                       "ORDER BY total_pedidos DESC " +
                       "LIMIT 10";
        ejecutarConsultaYExportarAJson(query, "clientes_con_mas_pedidos.json");
    }

    // Método para obtener ganancias por mes
    public void exportarGananciasPorMesAJson() {
        String query = "SELECT MONTH(FechaPedido) as mes, SUM(PrecioTotal) as ganancias " +
                       "FROM pedidos " +
                       "GROUP BY mes " +
                       "ORDER BY mes ASC";
        ejecutarConsultaYExportarAJson(query, "ganancias_por_mes.json");
    }

    // Método para obtener productos nunca comprados
    public void exportarProductosNuncaCompradosAJson() {
        String query = "SELECT p.id_producto as PRODUCTO_ID, p.nombre as PRODUCTOS_MENOS_COMPRADOS " +
                       "FROM productos p " +
                       "LEFT JOIN detalle_pedido dp ON p.id_producto = dp.id_producto " +
                       "WHERE dp.id_producto IS NULL";
        ejecutarConsultaYExportarAJson(query, "productos_nunca_comprados.json");
    }

    // Método genérico para ejecutar una consulta y exportar el resultado a JSON
    private void ejecutarConsultaYExportarAJson(String query, String nombreArchivo) {
        List<Object> resultados = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(Url, User, Pass);
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                // Crear un mapa para almacenar los datos de cada fila
                Map<String, Object> fila = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    fila.put(metaData.getColumnLabel(i), rs.getObject(i));
                }
                resultados.add(fila);
            }

            // Convertir la lista de resultados a JSON utilizando Gson
            Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
            new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();
            String json = gson.toJson(resultados);

            // Escribir el JSON en un archivo
            try (FileWriter writer = new FileWriter(nombreArchivo)) {
                writer.write(json);
                System.out.println("Datos exportados a " + nombreArchivo);
            } catch (IOException e) {
                System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión o consulta: " + e.getMessage());
        }
    }

public void ListarProd() {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        // Conexión a la base de datos
        con = DriverManager.getConnection(Url, User, Pass);
        
        // Solicitar al usuario si quiere ver todos los productos o solo por categoría
        System.out.println("¿Deseas ver todos los productos o por categoría?");
        System.out.println("1. Todos los productos");
        System.out.println("2. Productos por categoría");
        int opcionCategoria = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer

        int categoriaSeleccionada = -1;
        String query = "";
        
        if (opcionCategoria == 2) {
            // Mostrar las categorías disponibles
            System.out.println("Seleccione la categoría del producto:");
            System.out.println("1 | Pantalones");
            System.out.println("2 | Sudaderas");
            System.out.println("3 | Camisetas");
            System.out.println("4 | Camisas");
            System.out.println("5 | Chaquetas");
            System.out.println("Selecciona un numero: ");
            
            categoriaSeleccionada = sc.nextInt();
            query = "SELECT * FROM productos WHERE id_categoria = ?";
        } else {
            // Si elige todos los productos
            query = "SELECT * FROM productos";
        }

        ps = con.prepareStatement(query);
        if (opcionCategoria == 2) {
            ps.setInt(1, categoriaSeleccionada);
        }
        
        rs = ps.executeQuery();

        // Solicitar al usuario cómo quiere ordenar los productos
        System.out.println("¿Cómo deseas ordenar los productos?");
        System.out.println("1. Ordenar por precio");
        System.out.println("2. Ordenar por stock");
        int opcionOrdenar = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer

        String orden = "";
        if (opcionOrdenar == 1) {
            orden = "ORDER BY Precio";
        } else if (opcionOrdenar == 2) {
            orden = "ORDER BY Stock DESC"; // Ordenar por disponibilidad (stock) descendente
        }

        // Modificar la consulta si se seleccionó una ordenación
        if (!orden.isEmpty()) {
            if (opcionCategoria == 2) {
                query = "SELECT * FROM productos WHERE id_categoria = ? " + orden;
                ps = con.prepareStatement(query);
                ps.setInt(1, categoriaSeleccionada);
            } else {
                query = "SELECT * FROM productos " + orden;
                ps = con.prepareStatement(query);
            }
            rs = ps.executeQuery();
        }

        // Mostrar productos
        System.out.println("Productos disponibles:");
        while (rs.next()) {
            int id_producto = rs.getInt("id_producto");
            String nombre = rs.getString("Nombre");
            String descripcion = rs.getString("Descripcion");
            double precio = rs.getDouble("Precio");
            int Stock = rs.getInt("Stock");
            int id_categoria = rs.getInt("id_categoria");
            String imagen = rs.getString("Imagen");
            LocalDate fechaCreacion = rs.getDate("FechaCreacion").toLocalDate();

            
                System.out.println("ID: " + id_producto +"\n"+ " Nombre: " +  nombre +"\n"+"Descripcion: "+ descripcion +"\n"+ " Precio:"+ precio + " Stock:"+ Stock+"\n"+"Fecha_Creacio: "+ fechaCreacion+"\n");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al acceder a la base de datos: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}

    
public void BusquedaProd() {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs=null;
    
    try {
        // Establecer la conexión a la base de datos
        con = DriverManager.getConnection(Url, User, Pass);
        
        // Solicitar al usuario que ingrese el nombre o la descripción del producto a buscar
        System.out.println("Ingrese el nombre o descripcion del producto: ");
        String Buscarprod = sc.nextLine();

        // Buscar el producto por nombre o descripción en la base de datos
        String queryBuscar = "SELECT * FROM productos WHERE Nombre LIKE ? OR Descripcion LIKE ?";
        ps = con.prepareStatement(queryBuscar);
        ps.setString(1, "%" + Buscarprod + "%");
        ps.setString(2, "%" + Buscarprod + "%");
        
        rs = ps.executeQuery();

        boolean encontrado = false;
        
        // Si encontramos algún producto que coincida con el nombre o descripción, lo mostramos
        while (rs.next()) {
            System.out.println("Encontrado: ");
            System.out.println("Nombre: " + rs.getString("Nombre"));
            System.out.println("Descripción: " + rs.getString("Descripcion"));
            System.out.println("ID: " + rs.getInt("id_producto"));
            System.out.println("Precio: " + rs.getDouble("Precio"));
            System.out.println("Stock: " + rs.getInt("Stock"));
            System.out.println("Imagen: " + rs.getString("Imagen"));
            System.out.println("id_categoría: " + rs.getInt("id_categoria"));
            System.out.println("Fecha de creación: " + rs.getDate("FechaCreacion"));
            encontrado = true; 
        }

        // Si no se encontró ningún producto
        if (!encontrado) {
            System.out.println("No se ha encontrado ningún producto con esas características.");
        }
    } catch (SQLException e) {
        System.out.println("Error al conectar con la base de datos o al buscar el producto: " + e.getMessage());
    } finally {
        // Cerrar recursos
        try {
            if(rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar los recursos: " + e.getMessage());
        }
    }
}
    public ArrayList<Producto> getProductos(){
        return productos;
    } 

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }
}