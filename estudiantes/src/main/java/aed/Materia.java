package aed;

import java.util.ArrayList;

public class Materia {

    private ArrayList<String> _estudiantes;
    private int[] _docentes;

    public ParCarreraMateria[] get_alias() {
        return _alias;
    }

    private ParCarreraMateria[] _alias;


    public Materia( ParCarreraMateria[] _alias) {
        this._estudiantes= new ArrayList<>();
        this._docentes = new int[]{0, 0, 0, 0};
        this._alias = _alias;
    }

    public int get_estudiantes() {
        return _estudiantes.size();
    }

    public ArrayList lista_estudiantes() {
        return _estudiantes;
    }
    public void agregarEstudiante(String estudiante) {

        this._estudiantes.add(estudiante);
    }

    public int[] get_docentes() {
        return _docentes;
    }


    public void agregar(SistemaSIU.CargoDocente cargo) {
        if (cargo.equals(SistemaSIU.CargoDocente.PROF)){
            _docentes[0]++;
        } else if (cargo.equals(SistemaSIU.CargoDocente.JTP)) {
            _docentes[1]++;
        } else if (cargo.equals(SistemaSIU.CargoDocente.AY1)) {
            _docentes[2]++;
        } else if (cargo.equals(SistemaSIU.CargoDocente.AY2)) {
            _docentes[3]++;
        }

    }

    public boolean excedeCupo(){
        int cupo=_docentes[0]*250+_docentes[1]*100+_docentes[2]*20+_docentes[3]*30;
        return cupo>_estudiantes.size();
    }
}
