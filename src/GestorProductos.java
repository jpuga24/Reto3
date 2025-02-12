import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

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

   public void FicheroCSV() throws IOException {
    System.out.println("Cargando fichero...");
    productos.clear(); // Limpiar la lista antes de cargar nuevos datos

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
                    int stock = Integer.parseInt(Csvtemp[4]);
                    String Categoria = Csvtemp[5];
                    LocalDate fechaCreacion = LocalDate.parse(Csvtemp[6]); // Ajusta el formato si es necesario
                    boolean descontinuado = Boolean.parseBoolean(Csvtemp[7]);
                    String imagen = Csvtemp[8];
                    Producto producto = new Producto(nombre, descripcion, id_producto, precio, stock, imagen, Categoria, fechaCreacion, descontinuado);
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
                            System.out.println("Introduce la descripcion nueva: ");
                            String NuevaDescripcion= sc.nextLine();
                            producto.setDescripcion(NuevaDescripcion);
                            System.out.println("La descripcion"+NuevaDescripcion+" ha sido añadida!");
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
                            System.out.println("El Precio"+NuevoPrecio+" ha sido añadido!"); 
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
                            System.out.println("Introduce el Stock nuevo: ");
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
                    String CambioCategoria=sc.nextLine();
                    for (Producto producto : productos) {
                        if(producto.getCategoria().equalsIgnoreCase(CambioCategoria)){
                            System.out.println("Introduce la categoria nueva: ");
                            int NuevaCategoria= sc.nextInt();
                            producto.setStock(NuevaCategoria);
                            System.out.println("La categoria"+NuevaCategoria+" ha sido añadida!");
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
        //Hacer la conexion con la BBDD 
        //Transformarlo en fichero JSON
        //Query de estadisticas
        //Query de todos los productos
        //Query de productos mas vendidos

    }

    public void ListadoProd(){
        productos.toString();
        //primer menu para elegir categoria
        System.out.println("Elige la categoria: ");
        System.out.println("1. Pantalones");
        System.out.println("2. Sudaderas");
        System.out.println("3. Camisetas");
        System.out.println("4. Camisas");
        System.out.println("5. Chaquetas");
        int OpcCategoria=0;
        //segundo menu para elegir prenda
        while(OpcCategoria<1 || OpcCategoria>4){
            OpcCategoria=sc.nextInt();
        }

        String CategoriaSeleccionada=switch(OpcCategoria){
            case 1 -> "Pantalones";
            case 2 -> "Sudaderas";
            case 3 -> "Camisetas";
            case 4 -> "Camisas";
            case 5 -> "Chaquetas";
            default -> "";
            
        };

        //Filtrar productos por categoria
        List<Producto> productosFiltrados=new ArrayList<>();
        for(Producto p : productos){
            if(p.getCategoria().equalsIgnoreCase(CategoriaSeleccionada)){
                productosFiltrados.add(p);
            }
        }
        
        //Si no hay productos en esa categoria
        if(productosFiltrados.isEmpty()){
            System.out.println("No existen productos en esa categoria");
        }

        //funciones para ordenar la lista por precio, o disponibilidad
        System.out.println("Elige como quieres ordenar los productos");
        System.out.println("1. Por precio (menor a mayor)");
        System.out.println("2. Por disponibilidad (mayor stock primero)");

        int OpcOrden=0;
        while(OpcOrden<1 || OpcOrden>2){
            OpcOrden=sc.nextInt();
        }

        if(OpcOrden==1){
            //ordenar de mayor a menor el precio
        }else{
            //ordenar stock de mayor a menor
        }

    }
    
    public void BusquedaProd(String nombre,String descripcion, Scanner sc){
        System.out.println("Ingrese el nombre o descripcion de la prenda");
        String Buscarprod= sc.nextLine();

        for (Producto producto : productos) {
            if(producto.getNombre().equalsIgnoreCase(Buscarprod) || producto.getDescripcion().equalsIgnoreCase(Buscarprod)){
               System.out.println("""
                Encontrado!---"""+producto.getNombre()+"---\n"+producto.getDescripcion()+"---\n"+producto.getId_producto()+"---\n"+producto.getPrecio()+"---\n"
               +producto.getStock()+"---\n"+producto.getImagen()+"---\n"+producto.getCategoria()+"---\n"+producto.getFechaCreacion()); 

            } else{
                System.out.println("No se ha encontrado ningun producto con esas caracteristicas");
            }
        }
    }

    public ArrayList<Producto> getProductos() {
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

