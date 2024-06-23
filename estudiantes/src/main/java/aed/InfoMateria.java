package aed;

public class InfoMateria{
    // Invariante de Representación
    // paresCarreraMateria cumple con el invariante de representación de la clase ParCarreraMateria

    private ParCarreraMateria[] paresCarreraMateria;

    public InfoMateria(ParCarreraMateria[] paresCarreraMateria){
        this.paresCarreraMateria = paresCarreraMateria; // O(1)
    }

    public ParCarreraMateria[] getParesCarreraMateria() {
        return this.paresCarreraMateria; // O(1)
    }
}
