public class Bicicleta implements Transporte {
    @Override
    public void Arrancar(){
        System.out.println("Pedaleando...");
    }
    @Override
    public void Detener(){
        System.out.println("Frenando la bici...");
    }

}
