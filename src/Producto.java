//Import de tiempo
import java.time.LocalDateTime;

public class Producto {
    //Atributos del Objeto Producto
    private String nombre;
    private String descripcion;
    private int id_producto;
    private int precio;
    private int Stock;
    private String imagen;
    private int id_categoria;
    private LocalDateTime fechaCreacion;
    private boolean descontinuado;

    
    //Constructor,Setter del Producto
    public Producto(String nombre, String descripcion, int id_producto, int precio, int Stock,String imagen, int id_categoria,LocalDateTime fechaCreacion,boolean descontinuado){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.id_producto=id_producto;
        this.precio=precio;
        this.Stock=Stock;
        this.imagen=imagen;
        this.id_categoria=id_categoria;
        this.fechaCreacion=LocalDateTime.now();
        this.descontinuado=descontinuado;
        
    }
//Getters0
   public String getNombre() {
        return nombre;
    }



    public String getDescripcion() {
        return descripcion;
    }



    public int getId_producto() {
        return id_producto;
    }



    public int getPrecio() {
        return precio;
    }



    public int getStock() {
        return Stock;
    }



    public String getImagen() {
        return imagen;
    }



    public int getId_categoria() {
        return id_categoria;
    }



    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public boolean getdescontinuado(){
        if(descontinuado){
            System.out.println("El producto ha sido descontinuado...");
        }
        return descontinuado;
    }
    
    //Funcion para enseñar el Producto en formato String.
    @Override
    public String toString() {
        return nombre +"\t"+ descripcion +"\t"+id_producto+"\t"+precio+"\t"+Stock+"\t"+imagen+"\t"+id_categoria+"\t"+fechaCreacion;

    }

    public boolean isDescontinuado() {
        return descontinuado;
    }

    public void setDescontinuado(boolean descontinuado) {
        this.descontinuado = descontinuado;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
