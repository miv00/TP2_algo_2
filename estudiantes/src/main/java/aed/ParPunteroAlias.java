package aed;

public class ParPunteroAlias {
    DicTrie<String, Materia> punteroMaterias;
    String aliasMateria;

    public ParPunteroAlias(DicTrie<String, Materia> materias, String alias) {
        this.punteroMaterias = materias;
        this.aliasMateria = alias;
    }

    public DicTrie<String, Materia> getPuntero() {
        return this.punteroMaterias;
    }

    public String getAlias() {
        return this.aliasMateria;
    }
}
