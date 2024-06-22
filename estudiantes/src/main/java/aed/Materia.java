package aed;

import java.util.ArrayList;

public class Materia {

    private DicTrie<String, Boolean> _estudiantes;
    private int[] _docentes;
    private ParPunteroAlias[] parPunteroArray;

    public ParPunteroAlias[] getParPunteroArray() {
        return parPunteroArray;
    }

    public void setParPunteroArray(ParPunteroAlias[] parPunteroArray) {
        this.parPunteroArray = parPunteroArray;
    }

    public Materia() {
        this._estudiantes = new DicTrie();
        this._docentes = new int[] { 0, 0, 0, 0 };
    }


    public int get_estudiantes() {
        return _estudiantes._tamaño;
    }

    public ArrayList<String> lista_estudiantes() {
        return new ArrayList<>(_estudiantes.imprimir());
    }

    public void agregarEstudiante(String estudiante) {
        if (!_estudiantes.esta(estudiante)) {
            _estudiantes.agregar(estudiante, true);  // Complejidad O(|estudiante|)
        }
    }

    public int[] get_docentes() {
        return _docentes;
    }

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

    public boolean excedeCupo() {
        int cantEstudiantes = _estudiantes._tamaño;
        boolean excedeProfesor = _docentes[0] * 250 < cantEstudiantes;
        boolean excedeJTP = _docentes[1] * 100 < cantEstudiantes;
        boolean excedeAY1 = _docentes[2] * 20 < cantEstudiantes;
        boolean excedeAY2 = _docentes[3] * 30 < cantEstudiantes;
        return excedeProfesor || excedeJTP || excedeAY1 || excedeAY2;
    }
}
