
public class Auto extends Vehiculo {
    public Auto(String marca, String modelo){
        super(marca, modelo);
    }
@Override
public void Arrancar(){
    System.out.println("El coche "+ marca + modelo +" ha arrancado");
}
}
