public class Rectangulo extends Figura{
    private  int Base;
    private  int Altura;
    private String TIPO;

    public Rectangulo(int Base, int Altura){
        this.Base=Base;
        this.Altura=Altura;
    }
    public int getBase(){
        return Base;
    }
    public int getAltura(){
        return Altura;
    }
    @Override
    public String getTipo(){
        return "Rectangulo";
    }

    public void setBase(int Base) {
        this.Base = Base;
    }
    public void setAltura(int Altura){
        this.Altura=Altura;
    }

@Override
public double calcularArea(){
    return (Base*Altura);
}
}
