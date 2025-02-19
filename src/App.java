import java.util.*;
//Libreria util de java que incluye Scanner
import java.time.LocalDate;
//Libreria para el manejo de la fecha (FechaCreacion)
public class App {

    // Variable para el ID del producto
    private static int id_producto = 1;

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
        boolean acceso = false; //Llave de acceso 
        while (!acceso) {//Mientras el usuario no pueda acceder se le pide que introduzca el usuario y la contraseña
            System.out.print("Introduce el usuario: ");
            String UsuarioIntroducido = sc.nextLine(); 
            System.out.print("Introduce la contraseña: ");
            String ContraseñaIntroducida = sc.nextLine();

            //Condicion si el usuario y la contraseña son correctos 
            if (UsuarioIntroducido.equals(usuario) && ContraseñaIntroducida.equals(contraseña)) {
                acceso = true; // Lalve de acceso da entrada
                System.out.println("----------------------------");
                System.out.println("BIENVENIDO A USURBILTEX"); //Mensaje de bienvenida
                System.out.println("----------------------------");
                try {
                    Thread.sleep(2000); // Pausa de 2 segundos
                } catch (InterruptedException e) { //Excepcion por si hay un error
                    e.printStackTrace(); //Por si hay un eror esto hace que el programador sepa donde esta el error
                }
            } else {
                System.out.println("Vuelve a intentarlo");
            }
        }
        return true; //Login validado
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
                    break;
                }
                case 1 -> { // Agregar producto
                    boolean entradaValida; // Variable para validar el acceso
                    System.out.println("Ha seleccionado la opción de agregar nuevos productos");
                    
                    // Pedir nombre del producto
                    System.out.println("Introduce el nombre del producto:");
                    String Nuevonombre = sc.nextLine();

                    // Pedir descripcion del producto
                    System.out.println("Descripción del producto:");
                    String Nuevodescripcion = sc.nextLine();

                    //Inicializamos variable del precio
                    double Nuevoprecio = 0;
                    do { //Hacer mientras...
                        try { //Intenta hacer esto
                            System.out.println("Precio del producto:");
                            Nuevoprecio = sc.nextDouble();
                            entradaValida = true;  // Si el precio es de un formato correcto (numerico)
                        } catch (Exception e) { // Si se ingresa un precio no numerico
                            System.err.println("Dato incorrecto, por favor ingresa un numero valido para el precio.");
                            sc.nextLine(); // Salto de linea
                            entradaValida = false; //Bucle para que se repita la pregunta
                        }
                    } while (!entradaValida); //hasta que el precio numerico sea correcto

                  
                    int NuevoStock = -1;
                    do {
                        try {
                            System.out.println("Stock del producto:");
                            NuevoStock = sc.nextInt();
                            sc.nextLine();
                            entradaValida = true;  // Si el stock es numerico
                        } catch (Exception e) {
                            System.err.println("Dato incorrecto, por favor ingresa un número válido para el stock.");
                            sc.nextLine(); // Salto de linea
                            entradaValida = false;
                        }
                    } while (!entradaValida);

                    // Pedir categoria del producto
                    int Nuevocategoria = 0;
                    boolean categoriaValida = false; 
                    do {
                        // Mostrar el menú de opciones para seleccionar la categoría
                        System.out.println("Seleccione la categoría del producto:");
                        System.out.println("1 | Pantalones");
                        System.out.println("2 | Sudaderas");
                        System.out.println("3 | Camisetas");
                        System.out.println("4 | Camisas");
                        System.out.println("5 | Chaquetas");
                        System.out.println("Selecciona un numero: ");
                        // Leer la opción seleccionada por el usuario
                        int opcion = sc.nextInt();
                        sc.nextLine();

                        // Comprobar si la opción ingresada es válida
                        switch (opcion) {
                                case 1:
                                    Nuevocategoria=1; //pantalones
                                    categoriaValida = true;
                                    break;
                                case 2:
                                    Nuevocategoria = 2; //Sudaderas
                                    categoriaValida = true;
                                    break;
                            case 3:
                                    Nuevocategoria = 3; //Camisetas
                                    categoriaValida = true;
                                    break;
                            case 4:
                                    Nuevocategoria = 4; //Camisas
                                    categoriaValida = true;
                                    break;
                            case 5:
                                    Nuevocategoria =5; //Chaquetas
                                    categoriaValida = true;
                                    break;
                                    
                            default:
                                    // Si la opción no es válida, mostrar un mensaje de error
                                    System.out.println("La categoría no es válida, por favor seleccione una opción correcta.");
                                    categoriaValida = false;
                                    break;
                                }
                            } while (!categoriaValida); //Mientras la categoria no sea valida

                            System.out.println("Ingrese la URL de la imagen del producto (solo sirven externas):");
                            String NuevaImagen = sc.nextLine();

                    // Creamos un nuevo producto con los datos ingresados
                    Producto nuevoProducto = new Producto(Nuevonombre, Nuevodescripcion, Nuevoprecio, NuevoStock, id_producto++, Nuevocategoria, NuevaImagen, "NO", LocalDate.now());
                    gestor.AgregarProd(nuevoProducto); 
                    // Agregar el producto al gestor
                    break;
                }
                case 2 -> { // Cargar fichero CSV
                    System.out.println("Ha seleccionado la opción de cargar los productos desde el fichero");
                    //Ejecutamos metodo creado en el gestor
                    gestor.FicheroCSV();
                    break;
                }
               case 3 -> { // Actualizar producto
                 System.out.println("Ha seleccionado la opción de actualizar los productos");
                 
                 gestor.ActualizarProd(); // Ejecutamos  método que gestiona la actualización
                 break;
                }
                case 4 -> { // Eliminar producto
                System.out.println("Ha seleccionado la opción de eliminar productos");
                gestor.EliminarProd(); //Ejecutamos método que gestiona la eliminación
                break;
                }
                case 5 -> { // Exportar información
                    System.out.println("Ha seleccionado la opción de exportar la información");
                    int OpcExport;
                    System.out.println("Seleccione lo que desea exportar: ");
                    System.out.println("1.Datos de Estadisticas  \n2.Datos de todos los productos  \n3.Datos de productod mas vendidos");
                    OpcExport = sc.nextInt();  //Ingresar la opción
                    sc.nextLine(); // Salto de linea
                    switch (OpcExport) {
                        case 1:
                            System.out.println("Exportando datos de estadisticas...");
                            System.out.println("Exportando productos con alta ganancia...");
                            gestor.exportarProductosConAltaGananciaAJson();
                            System.out.println("Total ganancias exportadas a JSON...");
                            System.out.println("Exportando total ganancias");
                            gestor.exportarTotalGananciasAJson();
                            System.out.println("total ganancias exportadas a JSON...");
                            System.out.println("Exportando productos con bajo stock...");
                            gestor.exportarProductosBajoStockAJson() ;
                            System.out.println("Productos con bajo stock exportados a JSON...");
                            System.out.println("Exportando clientes con mas pedidos...");
                            gestor.exportarClientesConMasPedidosAJson() ;
                            System.out.println("Clientes con mas pedidos exportados a JSON...");
                            System.out.println("Exportando ganancias por mes...");
                            gestor.exportarGananciasPorMesAJson();
                            System.out.println("Ganancias por mes exportadas a JSON...");
                            System.out.println("Exportando productos nunca comprados...");
                            gestor.exportarProductosNuncaCompradosAJson();
                            System.out.println("Productos nunca comprados exportados a JSON...");
                            System.out.println("Resumen de exportaciones completado.");
                            break;
                        case 2:
                        try {
                            System.out.println("Exportando datos de todos los productos...");
                            gestor.exportarProductosAJson();
                            System.out.println("Datos exportados correctamente.");
                        } catch (Exception e) { //Excepcion por si hay un error en la exportación
                            System.err.println("Error al exportar los datos.");
                        }
                            break;
                        case 3:
                        try{ 
                        System.out.println("Exportando datos de los productos más vendidos...");
                          gestor.exportarProductosConAltaGananciaAJson();
                            System.out.println("Datos exportados correctamente.");
                        } catch (Exception e) { //Excepcion por si hay un error en la exportación
                            System.err.println("Error al exportar los datos."); 
                        }
                            break;
                    
                        default:
                            break;
                    }
                    break;
                }
                case 6 -> { // Listar productos
                System.out.println("Ha seleccionado la opción de listar los productos");
                gestor.ListarProd();  // Ejecutar método que lista los productos
                break;
                }
                case 7 -> { // Buscar productos
                    System.out.println("Ha seleccionado la opción de buscar productos");
                    gestor.BusquedaProd(); // Ejecutar método que busca los productos
                    break;
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
