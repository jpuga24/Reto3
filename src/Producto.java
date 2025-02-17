//Import de tiempo
import java.time.LocalDate;

public class Producto {
    //Atributos del Objeto Producto
    private String nombre;
    private String descripcion;
    private int id_producto;
    private double precio;
    private int Stock;
    private String imagen;
    private String Categoria;
    private LocalDate fechaCreacion;
    private boolean descontinuado;

    
    //Constructor,Setter del Producto
    public Producto(String nombre, String descripcion, double precio, int id_producto, String imagen){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.id_producto=id_producto;
        this.precio=precio;
        this.Stock=Stock;
        this.imagen=imagen;
        this.Categoria=Categoria;
        this.fechaCreacion=LocalDate.now();
        this.descontinuado=descontinuado;
        
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



    public String getCategoria() {
        return Categoria;
    }



    public LocalDate getFechaCreacion() {
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
        return nombre +"\t"+ descripcion +"\t"+id_producto+"\t"+precio+"\t"+Stock+"\t"+imagen+"\t"+Categoria+"\t"+fechaCreacion;

    }

    public boolean isDescontinuado() {
        //Hay que leer el dato descontinuado y transferirlo a booleano
        return descontinuado;
    }

    public void setDescontinuado(boolean descontinuado) {

        this.descontinuado = descontinuado; // if descontinuado equals si, entonces descontinuado = true
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        
        this.fechaCreacion = fechaCreacion;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public void setPrecio(double precio) {
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
