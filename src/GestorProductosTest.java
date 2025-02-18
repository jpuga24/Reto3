import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.sql.*;

public class GestorProductosTest {

    private Connection connection;
    private GestorProductos GestorProductos;;

    @Before
    public void setUp() throws SQLException {
        // Crear una conexión a la base de datos en memoria H2

        
        // Crear las tablas necesarias para las pruebas
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE categorias (id_categoria INT PRIMARY KEY, nombre VARCHAR(255))");
        stmt.execute("CREATE TABLE productos (id_producto INT AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(255), Descripcion VARCHAR(255), Precio INT, Stock INT, Id_categoria INT, Descontinuado VARCHAR(3), Imagen VARCHAR(255), FOREIGN KEY (Id_categoria) REFERENCES categorias(id_categoria))");
        
        // Insertar una categoría de ejemplo
        stmt.execute("INSERT INTO categorias (id_categoria, nombre) VALUES (1, 'Electrónica')");
        
        // Crear una instancia del servicio
       GestorProductose = newGestorProductose(connection); // Suponiendo que tu clase ProductoService recibe la conexión
    }

    @After
    public void tearDown() throws SQLException {
        // Limpiar la base de datos después de cada prueba
        Statement stmt = connection.createStatement();
        stmt.execute("DROP ALL OBJECTS");
        connection.close();
    }

    @Test
    public void testAgregarProd_categoriaExiste() throws SQLException {
        Producto producto = new Producto("Producto1", "Descripción", 100, 50, 1, "No", "imagen.jpg");

        // Llamar al método AgregarProd
     GestorProductose.AgregarProd(producto);

        // Verificar que el producto se haya insertado
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM productos WHERE Nombre = 'Producto1'");
        assertTrue(rs.next()); // Si existe el producto, rs.next() debe retornar true
        assertEquals("Producto1", rs.getString("Nombre"));
    }

    @Test
    public void testAgregarProd_categoriaNoExiste() throws SQLException {
        Producto producto = new Producto("Producto2", "Descripción", 200, 20, 99, "No", "imagen2.jpg");

        // Llamar al método AgregarProd, la categoría con id 99 no existe
      GestorProductose.AgregarProd(producto);

        // Verificar que no se haya insertado el producto debido a la categoría inexistente
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM productos WHERE Nombre = 'Producto2'");
        assertFalse(rs.next()); // No debería existir el producto, por lo que rs.next() debe retornar false
    }
}
