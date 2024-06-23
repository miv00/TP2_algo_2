package aed;

public class InfoMateria{
    
    private ParCarreraMateria[] paresCarreraMateria;

    public InfoMateria(ParCarreraMateria[] paresCarreraMateria){
        this.paresCarreraMateria = paresCarreraMateria; // O(1)
    }

    public ParCarreraMateria[] getParesCarreraMateria() {
        return this.paresCarreraMateria; // O(1)
    }
}
