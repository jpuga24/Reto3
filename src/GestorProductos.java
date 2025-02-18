import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;



public class GestorProductos {
    Scanner sc=new Scanner(System.in);
    //Conexion a la BBDD
    private String User="root";
    private String Url="jdbc:mysql://localhost:3306/usurbiltex";
    private String Pass="Lymprr1982@!";
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

    try (BufferedReader ProdCsv = new BufferedReader(new FileReader("src\\Productos.csv"))) { //Ruta relativa para prevenir errores
        String linea; //Variable para linea
        String encabezadoCsv = ProdCsv.readLine(); //Descartar la primera linea
        while ((linea = ProdCsv.readLine()) != null) { //Mientras la linea no este vacia 
            if (linea.isEmpty()) continue; //si hay espacio vacio saltar a la siguiente linea

            // separa las comas
            String[] Csvtemp = linea.split(",");
            //Condicion del array temporal
            if (Csvtemp.length == 9) { 
                try {
                    //Separar los datos por Atributos
                    int id_producto = Integer.parseInt(Csvtemp[0]);
                    String nombre = Csvtemp[1];
                    String descripcion = Csvtemp[2];
                    double precio = Double.parseDouble(Csvtemp[3]);
                    int Stock = Integer.parseInt(Csvtemp[4]);
                    int Id_categoria = Integer.parseInt(Csvtemp[5]);
                    LocalDate fechaCreacion = LocalDate.parse(Csvtemp[6]); // Ajusta el formato si es necesario
                    String descontinuado = Csvtemp[7];
                    String imagen = Csvtemp[8];
                    Producto producto = new Producto(nombre, descripcion, precio, Stock, id_producto, Id_categoria, imagen, descontinuado, fechaCreacion);
                    productos.add(producto); 
                } catch (NumberFormatException e) {
                    System.out.println("Error al procesar linea: " + linea);
                }
            } else {
                System.out.println("Linea con formato incorrecto: " + linea);
            }
        }
    }
    System.out.println("Fichero cargado correctamente.");
}


public void ActualizarProd() {
    try (Connection con = DriverManager.getConnection(Url, User, Pass)) {
        //Pedir al usuario el nombre del producto a buscar
        sc.nextLine(); 
        System.out.println("Ingrese el nombre del producto a buscar:");
        String busqueda = sc.nextLine();

        //Buscar en la base de datos los productos que coincidan por nombre
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
                    System.out.printf("ID: %d | Nombre: %s | Descripción: %s | Precio: %.2f | Stock: %d | Categoría: %s | Imagen: %s%n",
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

        //Solicitar al usuario el ID del producto a actualizar
        System.out.println("\nIntroduce el ID del producto a actualizar:");
        int id = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer

        //Menú para actualizar los campos
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
                    String nuevaCat = sc.nextLine();
                    queryUpdate = "UPDATE productos SET Categoria = ? WHERE id_producto = ?";
                    try (PreparedStatement ps = con.prepareStatement(queryUpdate)) {
                        ps.setString(1, nuevaCat);
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
        System.out.println("Error en la base de datos: " + e.getMessage());
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
            
           System.out.println("ID: " + id_producto +"\n"+ ", Nombre: " +  nombre +"\n"+", Descripcion: "+ descripcion +"\n"+ " Precio:"+ precio + " Stock:"+ Stock+"\n"+"Id_categoria: "+Id_categoria+", Imagen: "+imagen+"Fecha Creacion:"+ fechaCreacion+"\n");
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
        System.out.println("¿Estas seguro de que deseas eliminar el producto con ID " + idProductoEliminar + "? (Si/No)");
        sc.nextLine();  // Limpiar buffer
        String confirmacion = sc.nextLine();
        
        if (confirmacion.equalsIgnoreCase("Si")) {
            // Eliminar el producto de la base de datos
            String deleteQuery = "DELETE FROM productos WHERE id_producto = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, idProductoEliminar);
            int rowsAffected = deleteStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el producto.");
            }
        } else {
            System.out.println("Eliminacio cancelada.");
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


    public void ExportarInfo()throws Exception{
        //Hacer la conexion con la BBDD 
            //Query de todos los productos
        String consultaPrueba = "SELECT * FROM productos";


        try {
            FileWriter file = new FileWriter("/AplicacionJava/src/productos.json");
            Connection con = null;
            con = DriverManager.getConnection(Url, User, Pass);
            PreparedStatement myStmt = con.prepareStatement(consultaPrueba);
            ResultSet rs = myStmt.executeQuery();
            while (rs.next()) {
                int id_producto = rs.getInt("id_producto"); 
                String Nombre = rs.getString("Nombre");
                String Descripcion= rs.getString("Descripcion");
                float Precio= rs.getFloat("Precio");
                int Stock= rs.getInt("Stock");
                String FechaCreacion= rs.getString("FechaCreacion");
                int Id_categoria= rs.getInt("id_categoria");
                String ImagenUrl= rs.getString("Imagen");
                
               // file.write();
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        //Transformarlo en fichero JSON
    /*

   
     
        String consultaPrueba = "SELECT * FROM productos";
       
        
        try {
            Connection con = DriverManager.getConnection(Url, User, Pass);
            PreparedStatement myStmt = con.prepareStatement(consultaPrueba);
            ResultSet rs = myStmt.executeQuery();
            
            while (rs.next()) {
                
            }
            
            // Exportar la información a un archivo JSON
            try (FileWriter file = new FileWriter("productos.json")) {
                file.write(productosArray.toString(4)); // Convertir el array JSON a string y escribirlo en el archivo
                System.out.println("La información ha sido exportada a productos.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExportarInfo();
    }
}


 */
        //Query de estadisticas
    
        //Query de productos mas vendidos
        
    


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

        String categoriaSeleccionada = "";
        String query = "";
        
        if (opcionCategoria == 2) {
            // Mostrar las categorías disponibles
            System.out.println("Introduce la categoría (Pantalones, Camisetas, Chaquetas, Camisas, Sudaderas):");
            categoriaSeleccionada = sc.nextLine();
            query = "SELECT * FROM productos WHERE id_categoria = ?";
        } else {
            // Si elige todos los productos
            query = "SELECT * FROM productos";
        }

        ps = con.prepareStatement(query);
        if (opcionCategoria == 2) {
            ps.setString(1, categoriaSeleccionada);
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
                ps.setString(1, categoriaSeleccionada);
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

    
public void BusquedaProd(String nombre, String descripcion, Scanner sc) {
    Connection con = null;
    PreparedStatement ps = null;
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
        
        ResultSet rs = ps.executeQuery();

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