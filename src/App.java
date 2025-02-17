import java.util.*;

public class App {

    // Método para mostrar el menú
    public static void MostrarMenu() {
        System.out.println("Menú Gestión Productos UsurbilTex: ");
        System.out.println("0. Salir");
        System.out.println("1. Agregar nuevos productos");
        System.out.println("2. Cargar productos desde fichero CSV");
        System.out.println("3. Actualizar productos existentes");
        System.out.println("4. Eliminar productos");
        System.out.println("5. Exportar información");
        System.out.println("6. Listar productos");
        System.out.println("7. Buscar productos");
        System.out.print("Seleccione la opción deseada: ");
    }

    // Método de validación de login
    public static boolean ValidarLogin(Scanner sc, String usuario, String contraseña) {
        boolean acceso = false;
        while (!acceso) {
            System.out.print("Introduce el usuario: ");
            String UsuarioIntroducido = sc.nextLine();
            System.out.print("Introduce la contraseña: ");
            String ContraseñaIntroducida = sc.nextLine();

            if (UsuarioIntroducido.equals(usuario) && ContraseñaIntroducida.equals(contraseña)) {
                acceso = true;
                System.out.println("----------------------------");
                System.out.println("BIENVENIDO A USURBILTEX");
                System.out.println("----------------------------");
                try {
                    Thread.sleep(2000); // Pausa de 2 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Vuelve a intentarlo");
            }
        }
        return true;
    }

    // Main
    public static void main(String[] args) throws Exception {
        // Inicialización de variables
        int opcionUsuario;
        GestorProductos gestor = new GestorProductos();
        Scanner sc = new Scanner(System.in);
        String usuario = "Admin";
        String contraseña = "1234";

        // Validación de login
        ValidarLogin(sc, usuario, contraseña);

        // Bucle principal del menú
        do {
            // Mostrar el menú después de que el login se haya validado
            MostrarMenu();

            // Variable de opción
            opcionUsuario = sc.nextInt();
            sc.nextLine();  // Limpiamos el buffer después de nextInt()

            // Dependiendo de lo que elija el usuario
            switch (opcionUsuario) {
                case 0 -> { // Salir
                    System.out.println("Saliendo de la página");
                }
                case 1 -> { // Agregar producto
                    boolean entradaValida;
                    System.out.println("Ha seleccionado la opción de agregar nuevos productos");
                    
                    // Pedir nombre del producto
                    System.out.println("Introduce el nombre del producto:");
                    String Nuevonombre = sc.nextLine();

                    // Pedir descripcion del producto
                    System.out.println("Descripción del producto:");
                    String Nuevodescripcion = sc.nextLine();

                    // Bucle para asegurar que el precio sea un número válido
                    double Nuevoprecio = 0;
                    do {
                        try {
                            System.out.println("Precio del producto:");
                            Nuevoprecio = sc.nextDouble();
                            entradaValida = true;  // Si el precio es correcto, salimos del bucle
                        } catch (Exception e) {
                            System.err.println("Dato incorrecto, por favor ingresa un numero valido para el precio.");
                            sc.nextLine();
                            entradaValida = false;
                        }
                    } while (!entradaValida);

                    // Bucle para asegurar que el stock sea un número válido
                    int Nuevostock = 0;
                    do {
                        try {
                            System.out.println("Stock del producto:");
                            Nuevostock = sc.nextInt();
                            entradaValida = true;  // Si el stock es correcto, salimos del bucle
                        } catch (Exception e) {
                            System.err.println("Dato incorrecto, por favor ingresa un número válido para el stock.");
                            sc.nextLine(); // Limpiar el buffer
                            entradaValida = false;
                        }
                    } while (!entradaValida);

                    // Pedir categoria del producto
                    sc.nextLine(); // Limpiar el buffer de la entrada anterior
                    System.out.println("Categoría del producto:");
                    String Nuevocategoria = sc.nextLine();

                    // Crear el producto y agregarlo
                    Producto nuevoProducto = new Producto(Nuevonombre, Nuevodescripcion, Nuevoprecio, Nuevostock, Nuevocategoria);
                    gestor.AgregarProd(nuevoProducto);
                    // Agregar el producto al gestor
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                case 2 -> { // Cargar fichero CSV
                    System.out.println("Ha seleccionado la opción de cargar los productos desde el fichero");
                    gestor.FicheroCSV();
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                case 3 -> { // Actualizar producto
                    System.out.println("Ha seleccionado la opción de actualizar los productos");
                    gestor.ActualizarProd(null); // Inicializado en vacío (Cambiar y añadir producto)
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                case 4 -> { // Eliminar producto
                    System.out.println("Ha seleccionado la opción de eliminar productos");
                    gestor.EliminarProd(null, 0, null, false); // Inicializado en vacío (Cambiar y añadir producto)
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                case 5 -> { // Exportar información
                    System.out.println("Ha seleccionado la opción de exportar la información");
                    gestor.ExportarInfo();
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                case 6 -> { // Listar productos
                    System.out.println("Ha seleccionado la opción de listar los productos");
                    gestor.ListadoProd();
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                case 7 -> { // Buscar productos
                    System.out.println("Ha seleccionado la opción de buscar productos");
                    // gestor.BusquedaProd(null, null, 0, 0, 0, null, 0, null); 
                    MostrarMenu(); // Mostrar el menú nuevamente
                }
                default -> System.out.println("Opción no válida. Por favor ingresa una opción válida.");
            }

            // Solo mostramos el menú si el usuario no ha elegido salir (opción 0)
            if (opcionUsuario != 0) {
                System.out.println("\n\n");  // Agregamos un espacio antes de mostrar el menú nuevamente
            }

        } while (opcionUsuario != 0); // Bucle condicional por si se ingresa un valor no disponible en las opciones

        sc.close(); // Cerramos el scanner al final del programa
    }
}
