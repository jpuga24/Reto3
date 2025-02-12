//Import Scanner y Arraylist
import java.util.*;
//Import Manipulador ficheros
import java.io.*;
//Import tiempo
import java.time.LocalDateTime;

public class App {

    public static void MostrarMenu(){
        System.out.println("Menú Gestión Productos UsurbilTex: ");
        System.out.println("0. Salir");
        System.out.println("1. Agregar nuevos productos");
        System.out.println("2. Carga de productos desde fichero csv");
        System.out.println("3. Actualizar productos existentes");
        System.out.println("4. Eliminar productos");
        System.out.println("5. Exportar información");
        System.out.println("6. Listar productos");
        System.out.println("7. Buscar productos");
        System.out.println("Seleccione la opción deseada: ");
        // Imprimir el menu con sus opciones
    }
    



    public static boolean ValidarLogin(Scanner sc, String usuario, String contraseña){
        //Si el usuario y la contraseña son correctos da acceso, si no mensaje de reintento
        try {
        boolean acceso=false;
        while (!acceso) { 
            System.out.println("Introduce el usuario: ");
            String UsuarioIntroducido=sc.nextLine();
            System.out.println("Introduce la contraseña: ");
            String ContraseñaIntroducida=sc.nextLine();
            if(UsuarioIntroducido.equals(usuario) && ContraseñaIntroducida.equals(contraseña)){
                acceso=true;
                MostrarMenu();
            }else{
                System.out.println("Vuelve a intentarlo");
            }
        }
    } catch (Exception e) {
        //Prevencion de errores
        System.err.println("Error no se ha ingresado un valor valido");    }
        return true;

    }


    //Main
    public static void main(String[] args) throws Exception {
        //Inicializacion de variables
        int opcionUsuario;
        GestorProductos gestor=new GestorProductos();
        Scanner sc=new Scanner(System.in);
        String usuario="Admin";
        String contraseña="1234";
        System.out.println("");
        ValidarLogin(sc,usuario, contraseña);
        
        do {
             //Variable de opcion
        opcionUsuario=sc.nextInt();

        //Dependiendo de lo que elija el usuario 
        switch(opcionUsuario){
            case 0:
            //Salir
                System.out.println("Saliendo de la página");
                break;
            case 1:
            ///Agregar producto
                System.out.println("Ha seleccionado la opción de agregar nuevos productos");
                gestor.AgregarProd(null); //Inicializado en vacio (Cambiar y añadir producto)
                break;
            case 2:
            ///CargarFicheroCSV
                System.out.println("Ha seleccionado la opcion de cargar los productos desde el fichero");
                
                gestor.FicheroCSV();
                break;
            case 3:
            //Actualizar producto
                System.out.println("Ha seleccionado la opcion de actualizar los productos");
                gestor.ActualizarProd(null);
                break;
            case 4:
            //Eliminar producto
                System.out.println("Ha seleccionado la opcion de eliminar productos");
                gestor.EliminarProd(null, 0, null, false); //Inicializado en vacio (Cambiar y añadir producto)
                break;
            case 5:
            //Exportar informacion
                System.out.println("Ha seleccionado la opcion de exportar la informacion");
                gestor.ExportarInfo();
                break;
            case 6:
            //Enseñar lista de productos
                System.out.println("Ha seleccionado la opcion de listar los productos");
                gestor.ListadoProd();
                break;
            case 7:
            //Buscar productos
                System.out.println("Ha seleccionado la opcion de buscar productos");
                //gestor.BusquedaProd(null,null,0,0,0,null,0,null);
                break;
        }
            
        } while (opcionUsuario>=7 || opcionUsuario<0); //Bucle condicional por si se ingresa un valor no disponible en las opciones
        
    }
}