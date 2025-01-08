import java.util.ArrayList;
public class app3 {
    public static void main(String[] args) {
        ArrayList<Transporte> MediosTransporte= new ArrayList<>();
        MediosTransporte.add(new Bicicleta());
        MediosTransporte.add(new Coche());
        for(Transporte Medio: MediosTransporte){
            Medio.Arrancar();
            Medio.Detener();
        }
        
    }
}


