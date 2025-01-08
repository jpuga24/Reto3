public class EmpleadoXhoras extends Empleado{
    public int HorasTrabajadas;
    public int PagoXhora;
    public EmpleadoXhoras(String Nombre, int SalarioBase, int HorasTrabajadas, int PagoXhora){
        super(Nombre, SalarioBase);
        this.HorasTrabajadas=HorasTrabajadas;
        this.PagoXhora=PagoXhora;
    
    }
    @Override
    public double calcularSalario(){
        return SalarioBase + (HorasTrabajadas*PagoXhora);
    }
    
}
