package aed;

import java.util.ArrayList;

public class Materia {
    // Invariante de Representación
    // _docentes debe ser un arreglo de 4 enteros y cada posicion debe ser un valor mayor o igual a 0.
    // El DicTrie _estudiantes debe cumplir con el invariante de representacion de  la clase DicTrie.
    // _estudiantes contiene todos los estudiantes inscriptos en la materia.
    // Para el DicTrie _estudiantes, todos los significados deben ser true.
    // parPunteroArray debe cumplir con el invariante de representacion de  la clase ParPunteroAlias en cada posicion.

    private DicTrie<String, Boolean> _estudiantes;
    private int[] _docentes;
    private ParPunteroAlias[] parPunteroArray;

    public ParPunteroAlias[] getParPunteroArray() {
        return parPunteroArray; // O(1)
    }

    public void setParPunteroArray(ParPunteroAlias[] parPunteroArray) {
        this.parPunteroArray = parPunteroArray; // O(1)
    }

    public Materia() {
        this._estudiantes = new DicTrie(); // O(1)
        this._docentes = new int[] { 0, 0, 0, 0 }; // O(1)
    }


    public int get_estudiantes() {
        return _estudiantes._tamaño; // O(1)
    }

    public ArrayList<String> lista_estudiantes() {
        return new ArrayList<>(_estudiantes.imprimir()); // O(sum_{c \in C} |c|)
    }

    public void agregarEstudiante(String estudiante) {
        if (!_estudiantes.esta(estudiante)) {
            _estudiantes.agregar(estudiante, true);  // Complejidad O(|estudiante|)
        }
    }

    public int[] get_docentes() {
        return _docentes; // O(1)
    }

    // O(1)
    public void agregar(SistemaSIU.CargoDocente cargo) {
        if (cargo.equals(SistemaSIU.CargoDocente.PROF)) {
            _docentes[0]++;
        } else if (cargo.equals(SistemaSIU.CargoDocente.JTP)) {
            _docentes[1]++;
        } else if (cargo.equals(SistemaSIU.CargoDocente.AY1)) {
            _docentes[2]++;
        } else if (cargo.equals(SistemaSIU.CargoDocente.AY2)) {
            _docentes[3]++;
        }
    }

    // O(1)
    public boolean excedeCupo() {
        int cantEstudiantes = _estudiantes._tamaño;
        boolean excedeProfesor = _docentes[0] * 250 < cantEstudiantes;
        boolean excedeJTP = _docentes[1] * 100 < cantEstudiantes;
        boolean excedeAY1 = _docentes[2] * 20 < cantEstudiantes;
        boolean excedeAY2 = _docentes[3] * 30 < cantEstudiantes;
        return excedeProfesor || excedeJTP || excedeAY1 || excedeAY2;
    }
}
