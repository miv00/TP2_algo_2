package aed;

import java.util.ArrayList;

//estructura para guardar las cosas de las materia, no tiene nada muy raro a explicar
public class Materia {

  private ArrayList<String> _estudiantes;
  private int[] _docentes;

  public ParCarreraMateria[] get_alias() {
    return _alias;
  }

  private ParCarreraMateria[] _alias;

  public Materia(ParCarreraMateria[] _alias) {
    this._estudiantes = new ArrayList<>();
    this._docentes = new int[] { 0, 0, 0, 0 };
    this._alias = _alias;
  }

  public int get_estudiantes() {
    return _estudiantes.size();
  }

  public ArrayList<String> lista_estudiantes() {
    return _estudiantes;
  }

  public void agregarEstudiante(String estudiante) {
    if (!_estudiantes.contains(estudiante)) {
      _estudiantes.add(estudiante);
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
    int cantEstudiantes = _estudiantes.size();
    boolean excedeProfesor = _docentes[0] * 250 < cantEstudiantes;
    boolean excedeJTP = _docentes[1] * 100 < cantEstudiantes;
    boolean excedeAY1 = _docentes[2] * 20 < cantEstudiantes;
    boolean excedeAY2 = _docentes[3] * 30 < cantEstudiantes;
    return excedeProfesor || excedeJTP || excedeAY1 || excedeAY2;
  }
}
