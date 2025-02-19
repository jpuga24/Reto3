import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Creamos esta clase para prevenir errores de conexión a la base de datos

public class ConexionBD {
    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/usurbiltex";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Lymprr1982@!";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver (opcional)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estableciendo conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos");
            e.printStackTrace();
        }
        return conexion;
    }

    public static void main(String[] args) {
        // Probar la conexión
        conectar();
        System.out.println("Entrado");
    }
}
