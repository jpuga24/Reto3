public class EmpleadoTiempoCompleto extends Empleado {
    public int BonoAdicional= 1000;
    public EmpleadoTiempoCompleto(String Nombre, int SalarioBase, int BonoAdicional){
        super(Nombre, SalarioBase);
        this.BonoAdicional=BonoAdicional;
    }

}

