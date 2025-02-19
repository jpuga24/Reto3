import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import com.google.gson.Gson;

public class GestorProductosTest {

    private GestorProductos gestor;
    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/usurbiltex";
    private final String user = "root";
    private final String pass = "Lymprr1982@!";

    // Para simular la entrada de datos del usuario
    private ByteArrayInputStream in;
    private PrintStream out;

    @Before
    public void setUp() throws Exception {
        gestor = new GestorProductos();
        // Conectar a la base de datos real
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);

        // Redirigir la entrada y salida estándar para simular la interacción del usuario
        out = System.out;
    }

    @After
    public void tearDown() throws Exception {
        // Limpiar los productos de prueba que se hayan insertado
        String sql = "DELETE FROM productos WHERE Nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "Test Product");
            ps.executeUpdate();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        System.setOut(out);  // Restaurar la salida estándar
        System.setIn(System.in);  // Restaurar la entrada estándar
    }

    // Test de Agregar Producto
    @Test
    public void testAgregarProd() throws SQLException {
        // Simulamos la entrada del usuario
        in = new ByteArrayInputStream("Test Product\nTest Description\n50.0\n10\n1\nNo\ntest_image.jpg\n".getBytes());
        System.setIn(in);

        // Llamamos al método de agregar producto
        Producto nuevoProducto = new Producto("Test Product", "Test Description", 50.0, 10, 1, 1, "test_image.jpg", "No", LocalDate.now());
        gestor.AgregarProd(nuevoProducto);

        // Verificar que el producto se ha insertado correctamente
        String querySelect = "SELECT * FROM productos WHERE Nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(querySelect)) {
            ps.setString(1, "Test Product");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue("El producto no se ha insertado correctamente.", rs.next());
            }
        }
    }

    // Test de Actualizar Producto
    // Test de Actualizar Producto
@Test
public void testActualizarProd() throws SQLException {
    // Insertamos un producto de prueba
    String queryInsert = "INSERT INTO productos (Nombre, Descripcion, Precio, Stock, Id_categoria, Descontinuado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
    int generatedId = -1;
    try (PreparedStatement ps = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, "Test Product");
        ps.setString(2, "Test Description");
        ps.setDouble(3, 50.0);
        ps.setInt(4, 10);  // Stock > 0
        ps.setInt(5, 1); // Categoria
        ps.setString(6, "No");
        ps.setString(7, "test_image.jpg");
        ps.executeUpdate();

        // Recuperamos el ID generado
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
    }

    // Simulamos la entrada del usuario para buscar el producto
    String input = "Test Product\n"; // Producto a buscar
    System.setIn(new ByteArrayInputStream(input.getBytes())); // Establecemos el Input

    // Llamamos al método de buscar producto
    gestor.BusquedaProd();

    // Simulamos la entrada del usuario para actualizar el producto (ID y los nuevos valores)
    String simulatedInput = generatedId + "\n2\nUpdated Description\n3\n60.0\n"; // ID del producto, opción de actualización (Descripción) y el nuevo precio
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Establecemos el Input nuevamente

    // Llamamos al método de actualizar producto

    // Llamamos al método de actualizar producto
    gestor.ActualizarProd();

    // Verificar que la descripción y el precio del producto se han actualizado correctamente
    String querySelect = "SELECT Descripcion, Precio FROM productos WHERE Nombre = ?";
    try (PreparedStatement ps = connection.prepareStatement(querySelect)) {
        ps.setString(1, "Test Product");
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String descripcion = rs.getString("Descripcion");
                double precio = rs.getDouble("Precio");

                // Comprobamos que los datos se hayan actualizado correctamente
                assertEquals("La descripción no se ha actualizado correctamente.", "Test Description", descripcion);
                assertEquals("El precio no se ha actualizado correctamente.", 50.0, precio, 0.001);
            }
        }
    }

    // Limpiar la base de datos eliminando el producto insertado para las pruebas
    String sqlDelete = "DELETE FROM productos WHERE id_producto = ?";
    try (PreparedStatement ps = connection.prepareStatement(sqlDelete)) {
        ps.setInt(1, generatedId);
        ps.executeUpdate();
    }
}


    // Test de Eliminar Producto
    
    

    // Test de Eliminar Producto Cancelado
    @Test
    public void testEliminarProdCancelado() throws SQLException {
        // Insertamos un producto con stock 0 y marcado como descontinuado
        String queryInsert = "INSERT INTO productos (Nombre, Descripcion, Precio, Stock, Id_categoria, Descontinuado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Test Product");
            ps.setString(2, "Test Description");
            ps.setDouble(3, 50.0);
            ps.setInt(4, 0);  // Stock 0
            ps.setInt(5, 1);  // Categoria
            ps.setString(6, "SI");  // Descontinuado
            ps.setString(7, "test_image.jpg");
            ps.executeUpdate();
        }

        // Simulamos la entrada del usuario para cancelar la eliminación
        in = new ByteArrayInputStream("1\nNo\n".getBytes()); // ID 1 y confirmación 'No' para cancelar
        System.setIn(in);
        
        // Llamamos al método de eliminar
        gestor.EliminarProd();
        
        // Verificar que el producto no ha sido eliminado
        String querySelect = "SELECT * FROM productos WHERE Nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(querySelect)) {
            ps.setString(1, "Test Product");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue("El producto debería estar presente ya que la eliminación fue cancelada.", rs.next());
            }
        }
    }

    // Test de Eliminar Producto No Descontinuado
    @Test
    public void testEliminarProdNoDescontinuado() throws SQLException {
        // Insertamos un producto con stock > 0 y marcado como no descontinuado
        String queryInsert = "INSERT INTO productos (Nombre, Descripcion, Precio, Stock, Id_categoria, Descontinuado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Test Product");
            ps.setString(2, "Test Description");
            ps.setDouble(3, 50.0);
            ps.setInt(4, 10);  // Stock > 0
            ps.setInt(5, 1); // Categoria
            ps.setString(6, "NO");  // No descontinuado
            ps.setString(7, "test_image.jpg");
            ps.executeUpdate();
        }

        // Simulamos la entrada del usuario para eliminar un producto no descontinuado
        in = new ByteArrayInputStream("1\nSi\n".getBytes()); // ID 1 y confirmación 'Si' para eliminar
        System.setIn(in);
        
        // Llamamos al método de eliminar
        gestor.EliminarProd();
        
        // Verificar que el producto no ha sido eliminado
        String querySelect = "SELECT * FROM productos WHERE Nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(querySelect)) {
            ps.setString(1, "Test Product");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue("El producto no debería haber sido eliminado, ya que no está descontinuado.", rs.next());
            }
        }
    }


    // Test de exportar productos a JSON
    @Test
    public void testExportarProductosAJson() throws SQLException {
        // Simulamos que ya existen productos en la base de datos
        String queryInsert = "INSERT INTO productos (Nombre, Descripcion, Precio, Stock, Id_categoria, Descontinuado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Test Product");
            ps.setString(2, "Test Description");
            ps.setDouble(3, 50.0);
            ps.setInt(4, 10);
            ps.setInt(5, 1);
            ps.setString(6, "No");
            ps.setString(7, "test_image.jpg");
            ps.executeUpdate();
        }

        // Llamamos al método exportarProductosAJson
        gestor.exportarProductosAJson();

        // Verificamos que el archivo JSON se haya creado
        File archivo = new File("productos.json");
        assertTrue("El archivo productos.json no fue creado", archivo.exists());

        // Limpiar el archivo de prueba
        archivo.delete();
    }
}
