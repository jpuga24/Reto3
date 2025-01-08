

import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados.add(new EmpleadoXhoras("Carl", 1500, 35, 8));
        empleados.add(new EmpleadoTiempoCompleto("Juan", 1300, 1000));
        empleados.add(new EmpleadoXhoras("Camila", 1100, 45, 7));

        for (Empleado empleado: empleados){
            System.out.println("El empleado se llama "+ empleado.getNombre()+" y gana "+empleado.calcularSalario()+" euros");

        } 
        
    }
}
    
