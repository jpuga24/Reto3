public class Guitarra implements Instrumento {
    @Override
    public void Tocar(){
        System.out.println("Tocando la guitarra");
    }
    
    @Override
    public void Afinar(){
        System.out.println("Guitarra afinada");
    }

}
