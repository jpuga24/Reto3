
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        System.out.println("Sistema de vehiculos!!..");
        ArrayList<Vehiculo> vehiculos= new ArrayList<>();
        vehiculos.add(new Auto("Ford", "Raptor"));
        vehiculos.add(new Moto("Harley Davidson", "Pan America"));
        

        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.Arrancar();
        }
        }
        
    }


