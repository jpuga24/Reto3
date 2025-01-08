public class Coche implements Transporte {
    @Override
    public void Arrancar(){
        System.out.println("Motor encendido! Arrancado");
    }
    @Override
    public void Detener(){
        System.out.println("Frenando el coche");
    }




}
