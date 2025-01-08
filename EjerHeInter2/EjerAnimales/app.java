
import java.util.ArrayList;

public class app{
    public static void main(String[] args) {
        ArrayList<Animal> animales =new ArrayList<>();
        animales.add(new Perro());
        animales.add(new Gato());
        for (Animal animaluni : animales) {
            System.out.println(animaluni.HacerSonido());
            
        }
    }

}
