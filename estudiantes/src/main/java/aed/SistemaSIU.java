package aed;

import java.util.ArrayList;

public class SistemaSIU {
  private DicTrie<String, DicTrie<String, Materia>> _diccionarioCarreras;
  private DicTrie<String, Integer> _diccionarioestudiantes;

  enum CargoDocente {
    AY2,
    AY1,
    JTP,
    PROF
  }


  // TODO: Difieren las complejidades - Revisar estructura.
  // a priori la compleidad esta mal, da como resultado \sum_{c \in C}\sum_{m \in M_c}{|c| + |m|} + E
  public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
    _diccionarioCarreras = new DicTrie<String, DicTrie<String, Materia>>();
    _diccionarioestudiantes = new DicTrie<String, Integer>();

    for (int i = 0; i < infoMaterias.length; i++) {

      ParCarreraMateria[] par = infoMaterias[i].getParesCarreraMateria();
      ParPunteroAlias[] parPunteroArray = new ParPunteroAlias[par.length];
      Materia materia = new Materia();
      materia.setParPunteroArray(parPunteroArray);
      for (int j = 0; j < par.length; j++) {
        // checkiar aliansing por que si estan adentro de la misma pos del array son la
        // misma materia

        String carrera = par[j].getCarrera();
        String nombreMateria = par[j].getNombreMateria();
        DicTrie<String, Materia> dicCarreas;
        //si no esta crearla
        if (!_diccionarioCarreras.esta(carrera)) {
          _diccionarioCarreras.agregar(carrera, new DicTrie<>());
        }

        dicCarreas = _diccionarioCarreras.obtener(carrera);
        ParPunteroAlias alias = new ParPunteroAlias(dicCarreas,nombreMateria);
        parPunteroArray[j]= alias;
        dicCarreas.agregar(nombreMateria, materia);
      }

    }

    // Complejidad: O(E) * 1
    for (int i = 0; i < libretasUniversitarias.length; i++) {
      _diccionarioestudiantes.agregar(libretasUniversitarias[i], 0); // O(1)
    }

    // 
  }

  // TODO: Difieren las complejidades - Revisar estructura.
  public void inscribir(String estudiante, String carrera, String materia) {
    // O(|c|)
    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
    // O(|c| + |m|)
    Materia mat = dicCarreas.obtener(materia);

    // Cuidado con la complejidad aca, porque contains es buscar y esto se paga en
    // O(claves de estudiantes)
    mat.agregarEstudiante(estudiante);

    // O(1) porque estudiante esta acotado
    int cant = _diccionarioestudiantes.obtener(estudiante);
    // O(1) porque estudiante esta acotado
    _diccionarioestudiantes.agregar(estudiante, cant + 1);

  }

  // Complejidad: O(|c| + |m| + 1) = O(|c| + |m|)
  public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
    // O(|c|)
    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
    // O(|m|)
    Materia mat = dicCarreas.obtener(materia);

    // O(1)
    mat.agregar(cargo);
  }

  // Complejidad: O(|c| + |m| + 1) = O(|c| + |m|)
  public int[] plantelDocente(String materia, String carrera) {
    // O(|c|)
    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
    // O(|m|)
    Materia mat = dicCarreas.obtener(materia);

    // O(1)
    return mat.get_docentes();
  }

  // TODO: Difieren las complejidades - Revisar estructura.
  public void cerrarMateria(String materia, String carrera) {
    // O(|c|)
    DicTrie<String, Materia> dicMaterias = _diccionarioCarreras.obtener(carrera);
    // O(|m|)
    Materia mat = dicMaterias.obtener(materia);

    // O(1)
    ParPunteroAlias[] listaMateriasBorrar2 = mat.getParPunteroArray();
    // O(Em)
    for (int i = 0; i < mat.lista_estudiantes().size(); i++) {
      // O(1)
      String estudiante = mat.lista_estudiantes().get(i);
      // O(1)
      int cant = _diccionarioestudiantes.obtener(estudiante);
      // O(1)
      _diccionarioestudiantes.agregar(estudiante, cant - 1);
    }


    // NO CUMPLE LA COMPLEJIDAD
    for (int i = 0; i < listaMateriasBorrar2.length ; i++) {
      ParPunteroAlias data = listaMateriasBorrar2[i];
      DicTrie<String, Materia> dicDeMaterias = data.getPuntero();
      dicDeMaterias.borrar(data.getAlias());
    }
 /*
    while (listaMateriasBorrar.longitud()!=0) {

      // EL PROBLEMA ESTA ACA AHORA
      ParPunteroAlias data = listaMateriasBorrar.desencolar();
      DicTrie<String, Materia> dicDeMaterias = data.getPuntero();
      dicDeMaterias.borrar(data.getAlias());
    }*/
    // la complejidad final de este loop es \sum_{n \in n_m}{|c| + |n|}, lo cual es erroneo
  }

  // Complejidad: O(|c| + |m| + 1) = O(|c| + |m|)
  public int inscriptos(String materia, String carrera) {
    // O(|c|)
    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
    // O(|m|)
    Materia mat = dicCarreas.obtener(materia);

    // O(1)
    return mat.get_estudiantes();
  }

  // Complejidad: O(|c| + |m| + 1) = O(|c| + |m|)
  public boolean excedeCupo(String materia, String carrera) {
    // O(|c|)
    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
    // O(|m|)
    Materia mat = dicCarreas.obtener(materia);

    // O(1)
    return mat.excedeCupo();
  }

  // TODO: imprimir usa recursion, repensar.
  public String[] carreras() {
    ArrayList<String> listado = _diccionarioCarreras.imprimir();
    String[] res = new String[listado.size()];
    for (int i = 0; i < listado.size(); i++) {
      res[i] = listado.get(i);
    }
    return res;
  }

  // TODO: imprimir usa recursion, repensar.
  public String[] materias(String carrera) {
    DicTrie<String, Materia> carreraImprimir = _diccionarioCarreras.obtener(carrera);
    ArrayList<String> listado = carreraImprimir.imprimir();

    String[] res = new String[listado.size()];

    for (int i = 0; i < listado.size(); i++) {
      res[i] = listado.get(i);
    }

    return res;
  }

  // los estudiantes estan acotados, porque el numero de libreta es finito, y la
  // mayoria de los alumnos comparten prefijo
  // Complejidad: O(1)
  public int materiasInscriptas(String estudiante) {
    // O(1)
    return _diccionarioestudiantes.obtener(estudiante);
  }
}
