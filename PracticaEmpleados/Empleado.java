
public class Empleado{
    public String Nombre;
    public int SalarioBase;

    public Empleado(String Nombre, int SalarioBase){
        this.Nombre= Nombre;
        this.SalarioBase=SalarioBase;
    }
    public String getNombre() {
        return Nombre;
    }

    public double calcularSalario() {
        return SalarioBase;
    }
}
