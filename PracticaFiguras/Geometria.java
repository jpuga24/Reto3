
import java.util.ArrayList;

public class Geometria {
    public static void main(String[] args) {
        ArrayList<Figura> figuras= new ArrayList<>();
        figuras.add(new Rectangulo(4,5));
        figuras.add(new Circulo(3));
        for(Figura figura: figuras){
            System.out.println("El area del " + figura.getTipo()+ " es : "+figura.calcularArea());
            
        }
    }

}
