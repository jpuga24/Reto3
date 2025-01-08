
public abstract  class Vehiculo {
public String marca;
public String modelo;


public Vehiculo(String marca, String modelo){
    this.marca=marca;
    this.modelo=modelo;
}
public String getmarca(){
    return marca;
}
public String getmodelo(){
    return modelo;
}
public void Arrancar(){
}
}
