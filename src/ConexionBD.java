import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Creamos esta clase para prevenir errores de conexión a la base de datos

public class ConexionBD {
    //Hemos creado una clase para probar la conexion con la base de datos
    //Ya que el tema de librerias y versiones puede causar errores en la conexión
    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/usurbiltex";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Lymprr1982@!";

    public static Connection conectar() { //Metodo Conectar a la BBDD
        Connection conexion = null;
        try {
            // Cargar el driver (opcional depende de la version de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estableciendo conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD); //Conexión a la BBDD
            System.out.println("Conexión exitosa a la base de datos"); //Mensaje de conexión exitosa, aplicacion ejecutable sin problemas
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC"); //Mensaje de error si no se encuentra el driver
            e.printStackTrace(); // En este caso recomendamos indicarle al IDE las librerias que usaremos 
                                // Ctrl + Shift + P , Java: Configure Classpath, Libraries, Add Library, Y añades la ruta de la libreria
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
        //Con esto ya has verificado que la conexión a la base de datos es exitosa
        //Y que la aplicación Java puede conectarse a la base de datos sin problemas
    }
}
