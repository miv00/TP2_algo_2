package aed;

public class ParPunteroAlias {
    // Invariante de Representación
    // punteroMaterias cumple con el invariante de representación de la clase DicTrie.
    // aliasMateria debe ser una clave valida en el DicTrie punteroMaterias.
    
    DicTrie<String, Materia> punteroMaterias;
    String aliasMateria;

    public ParPunteroAlias(DicTrie<String, Materia> materias, String alias) {
        this.punteroMaterias = materias; // O(1)
        this.aliasMateria = alias; // O(1)
    }

    public DicTrie<String, Materia> getPuntero() {
        return this.punteroMaterias; // O(1)
    }

    public String getAlias() {
        return this.aliasMateria; // O(1)
    }
}
