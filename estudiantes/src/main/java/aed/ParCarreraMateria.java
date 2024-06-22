package aed;

public class ParCarreraMateria {
    String carrera;
    String nombreMateria;

    public ParCarreraMateria(String carrera, String nombreMateria) {
        this.carrera = carrera; // O(1)
        this.nombreMateria = nombreMateria; // O(1)
    }

    public String getNombreMateria() {
        return this.nombreMateria; // O(1)
    }

    public String getCarrera() {
        return this.carrera; // O(1)
    }
}
