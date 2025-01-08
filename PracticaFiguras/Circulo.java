public class Circulo extends Figura{
    private double Radio;
    private String TIPO;
    public Circulo(double Radio){
        this.Radio=Radio;
        this.TIPO = "Circulo";
    }
    public double  getRadio(){
        return Radio;
    }
    public String getTipo(){
        return "Circulo";
    }
public void setRadio(double Radio){
    this.Radio=Radio;

    }
@Override
public double calcularArea(){
    return  Math.PI * Math.pow(Radio, 2);
}
}
