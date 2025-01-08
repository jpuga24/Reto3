import java.util.ArrayList;
public class App2 {
    public static void main(String[] args){
        ArrayList<Instrumento>instrumenacion = new ArrayList<>();
        instrumenacion.add(new Piano());
        instrumenacion.add(new Guitarra());
        for (Instrumento instruments : instrumenacion) {
        instruments.Afinar();
        instruments.Tocar();
            
        }
}

}
