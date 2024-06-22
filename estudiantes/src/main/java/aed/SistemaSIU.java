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

    // Complejidad: O(sum_{c \in C} |c| * |M_c| + sum_{m \in M} sum_{n \in N_m} |n| + E)
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        _diccionarioCarreras = new DicTrie<String, DicTrie<String, Materia>>();
        _diccionarioestudiantes = new DicTrie<String, Integer>();
        // Para cada InfoMateria en infoMaterias: O(infoMaterias.length)
        for (int i = 0; i < infoMaterias.length; i++) {

            ParCarreraMateria[] par = infoMaterias[i].getParesCarreraMateria(); // O(1)
            Materia materia = new Materia(par); // O(1)
            // Para cada ParCarreraMateria en par: O(par.length)
            for (int j = 0; j < par.length; j++) {
                String carrera = par[j].getCarrera(); // O(1)
                String nombreMateria = par[j].getNombreMateria(); // O(1)
                // Si la carrera ya está en el diccionario
                if (_diccionarioCarreras.esta(carrera)) { // O(|carrera|)
                    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera); // O(|carrera|)
                    dicCarreas.agregar(nombreMateria, materia); // O(|nombreMateria|)
                } else {
                    _diccionarioCarreras.agregar(carrera, new DicTrie<>()); // O(|carrera|)
                    DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera); // O(|carrera|)
                    dicCarreas.agregar(nombreMateria, materia); // O(|nombreMateria|)
                }
            }
        }

        // Complejidad de la sección anterior:
        // Para cada carrera c en infoMaterias, se ejecuta O(|c|) para `esta` y `_diccionarioCarreras.agregar`.
        // Para cada materia m en la carrera c, se ejecuta O(|c|) para cada `obtener`.
        // Para cada nombreMateria en cada materia m en la carrera c, se ejecuta O(|nombreMateria|) para los ` dicCarreas.agregar`.

        // Total: O(sum_{c \in C} |c| * |M_c| + sum_{m \in M} sum_{n \in N_m} |n|)

        // Para cada libreta universitaria: O(libretasUniversitarias.length)
        for (int i = 0; i < libretasUniversitarias.length; i++) {
            _diccionarioestudiantes.agregar(libretasUniversitarias[i], 0); // O(1)
        }
        // Complejidad de la sección anterior: O(E)

        // Complejidad total del constructor:
        // O(sum_{c \in C} |c| * |M_c| + sum_{m \in M} sum_{n \in N_m} |n| + E)
    }


    // Complejidad: O(|c| + |m|)
    public void inscribir(String estudiante, String carrera, String materia) {
        // O(|c|)
        DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicCarreas.obtener(materia);
        // O(|estudiante|) = O(1) porque estudiante esta acotado
        mat.agregarEstudiante(estudiante);
        //  O(|estudiante|) = O(1) porque estudiante esta acotado
        int cant = _diccionarioestudiantes.obtener(estudiante);
        //  O(|estudiante|) = O(1) porque estudiante esta acotado
        _diccionarioestudiantes.agregar(estudiante, cant + 1);
        // Complejidad total:
        //  O(|c| + |m|)
    }


    // Complejidad: O(|c| + |m|)
    public int inscriptos(String materia, String carrera) {
        // O(|c|)
        DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicCarreas.obtener(materia);
        // O(1)
        return mat.get_estudiantes();
        // Complejidad total:
        //  O(|c| + |m|)
    }


    // Complejidad: O(|c| + |m|)
    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        // O(|c|)
        DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicCarreas.obtener(materia);
        // O(1)
        mat.agregar(cargo);
        // Complejidad total:
        //  O(|c| + |m|)
    }


    // Complejidad: O(|c| + |m|)
    public int[] plantelDocente(String materia, String carrera) {
        // O(|c|)
        DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicCarreas.obtener(materia);
        // O(1)
        return mat.get_docentes();
        // Complejidad total:
        //  O(|c| + |m|)
    }

    // Complejidad: O(|c| + |m|)
    public boolean excedeCupo(String materia, String carrera) {
        // O(|c|)
        DicTrie<String, Materia> dicCarreas = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicCarreas.obtener(materia);
        // O(1)
        return mat.excedeCupo();
        // Complejidad total:
        //  O(|c| + |m|)
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


    // Complejidad: O(1)
    public int materiasInscriptas(String estudiante) {
        // O(|estudiante|) = O(1) porque estudiante esta acotado
        return _diccionarioestudiantes.obtener(estudiante);
    }


    // TODO: Difieren las complejidades - Revisar estructura.
    public void cerrarMateria(String materia, String carrera) {
        // O(|c|)
        DicTrie<String, Materia> dicMaterias = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicMaterias.obtener(materia);
        // O(1)
        ParCarreraMateria[] listaMateriasBorrar = mat.get_alias();

        // O(E_m)
        for (int i = 0; i < mat.lista_estudiantes().size(); i++) {
            // O(1)
            String estudiante = mat.lista_estudiantes().get(i);
            // O(1)
            int cant = _diccionarioestudiantes.obtener(estudiante);
            // O(1)
            _diccionarioestudiantes.agregar(estudiante, cant - 1);
        }

        // O(sum_{n \in N_m} |n| + |c_n|)
        for (int i = 0; i < listaMateriasBorrar.length; i++) {
            String carreraDeDondeBorrar = listaMateriasBorrar[i].carrera; // O(1)
            DicTrie<String, Materia> listaDeMaterias = _diccionarioCarreras.obtener(carreraDeDondeBorrar); // O(|c_n|) c_n es carrera que tiene a la materia m
            listaDeMaterias.borrar(listaMateriasBorrar[i].nombreMateria); // O(|n|) n es un nombre de la materia m
        }
        // la complejidad final de este loop es \sum_{n \in n_m}{|c_n| + |n|}, lo cual es erroneo
    }
}
