import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;

public class GestorProductos {
    Scanner sc=new Scanner(System.in);
    //Conexion a la BBDD
    private String User="root";
    private String Url="jdbc:mysql://localhost:3306/UsurbilTex";
    private String Pass="";
    private ArrayList<Producto> productos = new ArrayList<>();
    //Metodos para manipular productos

    public void AgregarProd(Producto producto){
        System.out.println("Añadiendo Producto...");
        productos.add(producto);

    }

    public void FicheroCSV(){
        System.out.println("Cargando fichero...");

    }

    public void ActualizarProd(String nombre){
        System.out.println("¿Qué quieres actualizar?");
        System.out.println("0. Volver atrás");
        System.err.println("1. Nombre del producto");
        System.out.println("2. Descripción del producto");
        System.err.println("3. Precio del producto");
        System.out.println("4. Stock del producto");
        System.out.println("5. Categoría del producto");
        System.out.println("6. Imágen del producto (link externo)");
        System.out.println("Elige una opcion: ");
        int OpcActualizar=sc.nextInt();
        sc.nextLine();
        
    
            switch(OpcActualizar){
                case 0 -> {
                    System.out.println("Has seleccionado volver atrás");
                    System.out.println("Volviendo...");
                }
                


                
                case 1 -> {
                    System.out.println("Has seleccionado actualizar el nombre del producto");
                    System.out.println("Introduce el nombre del producto a cambiar: ");
                    String CambioNombre=sc.nextLine();
                    for (Producto producto : productos) {
                        if(producto.getNombre().equalsIgnoreCase(CambioNombre)){
                            System.out.println("Introduce el nombre nuevo: ");
                            String NuevoNombre= sc.nextLine();
                            producto.setNombre(NuevoNombre);
                            System.out.println("El producto"+NuevoNombre+" ha sido añadido!");

                        }
                        else{
                            System.out.println("No se ha encontrado ningun producto");
                        }
                    }
                }

                
                
                case 2 -> {
                    System.out.println("Has seleccionado actualizar la descripción del producto");
                    System.out.println("Introduce la descripción nueva:");
                    String CambioDescripcion=sc.nextLine();
                    for (Producto producto : productos) {
                        if(producto.getDescripcion().equalsIgnoreCase(CambioDescripcion)){
                            System.out.println("Introduce el nombre nuevo: ");
                            String NuevaDescripcion= sc.nextLine();
                            producto.setDescripcion(NuevaDescripcion);
                            System.out.println("El producto"+NuevaDescripcion+" ha sido añadido!");

                        }
                        else{
                            System.out.println("No se ha encontrado ningun producto");
                        }
                    }
                }


                
                case 3 -> {
                    System.out.println("Has seleccionado actualizar el precio del producto");
                    System.out.println("Introduce el nuevo precio: ");
                    int Cambioprecio=sc.nextInt();
                    for (Producto producto : productos) {
                        if(producto.getPrecio()==Cambioprecio){
                            System.out.println("Introduce el precio nuevo: ");
                            int NuevoPrecio= sc.nextInt();
                            producto.setPrecio(NuevoPrecio);
                            System.out.println("El producto"+NuevoPrecio+" ha sido añadido!"); 

                        }
                        else{
                            System.out.println("No se ha encontrado ningun producto");
                        }
                    }
                }


                
                case 4 -> {
                    System.out.println("Has seleccionado actualizar el stock del producto: ");
                    System.out.println("Introduce el nuevo stock: ");
                    int CambioStock=sc.nextInt();
                    for (Producto producto : productos) {
                        if(producto.getStock()==CambioStock){
                            System.out.println("Introduce el nombre nuevo: ");
                            int NuevoStock= sc.nextInt();
                            producto.setStock(NuevoStock);
                            System.out.println("El Stock"+NuevoStock+" ha sido añadido!");

                        }
                        else{
                            System.out.println("No se ha encontrado ningun producto");
                        }
                    }
                }



                
                case 5 -> {
                    System.out.println("Has seleccionado actualizar la categoría del producto");
                    System.out.println("Introduce la nueva categoría: ");
                    int CambioCategoria=sc.nextInt();
                    for (Producto producto : productos) {
                        if(producto.getId_categoria()==CambioCategoria){
                            System.out.println("Introduce el nombre nuevo: ");
                            int NuevaCategoria= sc.nextInt();
                            producto.setStock(NuevaCategoria);
                            System.out.println("El producto"+NuevaCategoria+" ha sido añadido!");
                        }
                        else{
                            System.out.println("No se ha encontrado ningun producto");
                        }
                    }
                }

                

                
                case 6 -> {
                    System.out.println("Has seleccionados actualizar la imagen del producto");
                    System.out.println("Introduce el nuevo link de la imagen: ");
                    String CambioImagen=sc.nextLine();
                    for (Producto producto : productos) {
                        if(producto.getImagen().equalsIgnoreCase(CambioImagen)){
                            System.out.println("Introduce la imagen nueva: ");
                            String NuevaImagen= sc.nextLine();
                            producto.setImagen(NuevaImagen);
                            System.out.println("La imagen"+NuevaImagen+" ha sido añadida!");

                        }
                        else{
                            System.out.println("No se ha encontrado ningun producto");
                        }
                    }
                }
            }
        }



    

    public void EliminarProd (Producto producto, int Stock, String nombre, boolean descontinuado){
        if(producto.getStock()==0){
            System.out.println("El producto"+ producto.getNombre() +" esta sin stock o descontinuado");

        }
        
    }

    public void ExportarInfo(){

    }

    public void ListadoProd(){
        productos.toString();

    }
    public void BusquedaProd(String nombre,String descripcion){
        for (Producto producto : productos) {
            if(producto.getNombre().equalsIgnoreCase(nombre) || producto.getDescripcion().equalsIgnoreCase(descripcion)){
               System.out.println("Encontrado!"+"---\n"+producto.getNombre()+"---\n"+producto.getDescripcion()+"---\n"+producto.getId_producto()+"---\n"+producto.getPrecio()+"---\n"+producto.getStock()+"---\n"+producto.getImagen()+"---\n"+producto.getId_categoria()+"---\n"+producto.getFechaCreacion()); 

            } else{
                System.out.println("No se ha encontrado ningun producto");
                
                
            }

            
        }

    }
}

