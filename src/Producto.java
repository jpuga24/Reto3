//Import de tiempo
import java.sql.Timestamp;
import java.time.LocalDate;

public class Producto {
    //Atributos del Objeto Producto
    private String nombre;
    private String descripcion;
    private int id_producto;
    private double precio;
    private int Stock;
    private String imagen;
    private int Id_categoria;
    private Timestamp fechaCreacion;
    private String descontinuado;
 
    
    //Constructor,Setter del Producto
    public Producto(String nombre, String descripcion, double precio, int Stock, int id_producto, int id_categoria, String imagen, String descontinuado, LocalDate fechaCreacion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_producto = id_producto;
        this.precio = precio;
        this.Stock = Stock;
        this.imagen = imagen;
        this.Id_categoria = id_categoria; 
        this.fechaCreacion = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        this.descontinuado = descontinuado;  
    }
//Getters
   public String getNombre() {
        return nombre;
    }



    public String getDescripcion() {
        return descripcion;
    }



    public int getId_producto() {
        return id_producto;
    }



    public double getPrecio() {
        return precio;
    }



    public int getStock() {
        return Stock;
    }



    public String getImagen() {
        return imagen;
    }



    public int getId_categoria() {
        return Id_categoria;
    }



    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    public String getDescontinuado() {
        return descontinuado;
    }
    
    //Funcion para enseñar el Producto en formato String.
    @Override
    public String toString() {
        return nombre +"\t"+ descripcion +"\t"+id_producto+"\t"+precio+"\t"+Stock+"\t"+imagen+"\t"+Id_categoria+"\t"+fechaCreacion;

    }
    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setdescripcion(String descripcion) {
        this.descripcion= descripcion;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setId_categoria(int Id_categoria) {
        this.Id_categoria = Id_categoria;
    }
    
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setDescontinuado(String descontinuado) {
        this.descontinuado = descontinuado;
    }

    









}

