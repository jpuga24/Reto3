
public class Moto extends Vehiculo {
public Moto(String marca, String modelo){
    super(marca, modelo);
    
}
@Override
public void Arrancar(){
    System.out.println("La moto "+ marca + modelo +" ha arrancado");
}
}
