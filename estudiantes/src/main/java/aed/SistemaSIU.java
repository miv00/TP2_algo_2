package aed;

import java.util.ArrayList;


public class SistemaSIU {
    // Invariante de Representacion
    // _diccionarioCarreras y _diccionarioestudiantes cumplen con el invariante de representación de la clase DicTrie.
    // Tanto las claves de _diccionarioCarreras y dicCarreas deben ser strings comprendidos por caracteres ASCII
    // Las claves de _diccionarioCarreras cumplen con el invariante de representación de la clase DicTrie.
    // Las claves de las claves de _diccionarioCarreras cumple con el invariante de representación de la clase Materia.
    // Las claves del DicTrie _diccionarioestudiantes deben ser numeros enteros mayores o iguales a 0.

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
        _diccionarioCarreras = new DicTrie<String, DicTrie<String, Materia>>(); // O(1)
        _diccionarioestudiantes = new DicTrie<String, Integer>(); // O(1)
        // O(sum_{c \in C} .... )
        for (int i = 0; i < infoMaterias.length; i++) {

            ParCarreraMateria[] par = infoMaterias[i].getParesCarreraMateria(); // O(1)
            ParPunteroAlias[] parPunteroArray = new ParPunteroAlias[par.length]; //O(sum_{n \in N_c} 1 ) = O(|N_c|)
            Materia materia = new Materia(parPunteroArray); // O(1)
            // materia.setParPunteroArray(parPunteroArray); // O(1)
            // O(sum_{N \in N_c} .... )
            for (int j = 0; j < par.length; j++) {
                String carrera = par[j].getCarrera(); // O(1)
                String nombreMateria = par[j].getNombreMateria(); // O(1)
                DicTrie<String, Materia> dicCarreas; // O(1)
                //si no esta crearla
                if (!_diccionarioCarreras.esta(carrera)) { // O(|carrera|)
                    _diccionarioCarreras.agregar(carrera, new DicTrie<>()); // O(|carrera|)
                }

                dicCarreas = _diccionarioCarreras.obtener(carrera); // O(|carrera|)
                ParPunteroAlias alias = new ParPunteroAlias(dicCarreas,nombreMateria); // O(1)
                parPunteroArray[j]= alias;  // O(1)
                dicCarreas.agregar(nombreMateria, materia);  // O(|nombreMateria|)
            }

        }

        // Complejidad de la sección anterior:
        // En el primer for (linea 30) entramos |C| veces (para cada carrera c \in C) y la cpmplejidad en una iteracion es la complejidad de la linea 33 (O(sum_{n \in N_c} 1 ) = O(|N_c|)) más la complejidad del for de la linea 37.
        // En el for de la linea 37 entramos |N_c| veces y en cada iteracion la complejidad es O(|c| + |n|) donde c es una carrera (c \in C) y n es un nombre de una materia de la carrera (n \in N_c).
        // Siguiendo los comentarios anteriores, la complejidad total es: O(sum_{c \in C} sum_{n \in N_c} (|c| + |n| + 1 ) )
        // Como O(|c| + |n| + 1) = O(|c| + |n|) y usando operaciones elementales, la complejidad total se puede reescribir como O(sum_{c \in C} sum_{n \in N_c} (|c| + |n| ) ) = O(sum_{c \in C} sum_{n in N_c} |c| + sum_{c \in C} sum_{n in N_c} |n|) = O(sum_{c \in C} |c|*|N_c| + sum_{c \in C} sum_{n in N_c} |n| )
        // N_c es el conjunto de nombres de las materias de la carrera c y claramente tiene el mismo cardinal que M_c
        // y por otro lado sum_{c \in C} sum_{n \in N_c} |n| = sum_{m \in M} sum_{n \in N_m} |n| (estamos contando de 2 formas la cantidad de nombres de materias).
        // Usando lo anterior la complejidad se puede escribir como O(sum_{c \in C} |c| * |M_c| + sum_{m \in M} sum_{n \in N_m} |n|)

        // Complejidad: O(E)
        for (int i = 0; i < libretasUniversitarias.length; i++) {
            _diccionarioestudiantes.agregar(libretasUniversitarias[i], 0); // O(1)
        }
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


    // Complejidad: O(sum_{c \in C} |c|)
    public String[] carreras() {
        //  O(sum_{c \in C} |c|)
        ArrayList<String> listado = _diccionarioCarreras.imprimir();
        String[] res = new String[listado.size()];// O(1)
        //  O(sum_{c \in C} 1)
        for (int i = 0; i < listado.size(); i++) {
            res[i] = listado.get(i); // O(1)
        }
        return res;
        // Complejidad total:
        // O(sum_{c \in C} |c|)
    }


    // Complejidad: O( |c| + sum_{m_c \in M_c} |m_c|)
    public String[] materias(String carrera) {
        // O(|c|)
        DicTrie<String, Materia> carreraImprimir = _diccionarioCarreras.obtener(carrera);
        // O(sum_{m_c \in M_c} |m_c|)
        ArrayList<String> listado = carreraImprimir.imprimir();
        String[] res = new String[listado.size()]; // O(1)
        // O(sum_{m_c \in M_c} 1)
        for (int i = 0; i < listado.size(); i++) {
            res[i] = listado.get(i); // O(1)
        }
        return res;
        // Complejidad total:
        // O( |c| + sum_{m_c \in M_c} |m_c|)
    }


    // Complejidad: O(1)
    public int materiasInscriptas(String estudiante) {
        // O(|estudiante|) = O(1) porque estudiante esta acotado
        return _diccionarioestudiantes.obtener(estudiante);
    }


    // Complejidad:  O( |c| + |m| + Sum_{n \in N_m} |n| + E_m)
    public void cerrarMateria(String materia, String carrera) {
        // O(|c|)
        DicTrie<String, Materia> dicMaterias = _diccionarioCarreras.obtener(carrera);
        // O(|m|)
        Materia mat = dicMaterias.obtener(materia);

        // O(1)
        ParPunteroAlias[] listaMateriasBorrar = mat.getParPunteroArray();
        // O(E_m)
        ArrayList<String> estudiantes = mat.lista_estudiantes();
        // O(E_m)
        for (int i = 0; i < estudiantes.size(); i++) {
            // O(1)
            String estudiante = estudiantes.get(i);
            // O(1)
            int cant = _diccionarioestudiantes.obtener(estudiante);
            // O(1)
            _diccionarioestudiantes.agregar(estudiante, cant - 1);
        }


        // O(Sum_{n \in N_m} |n|)
        for (int i = 0; i < listaMateriasBorrar.length ; i++) {
            // O(1)
            ParPunteroAlias data = listaMateriasBorrar[i];
            // O(1)
            DicTrie<String, Materia> dicDeMaterias = data.getPuntero();
            // O(|n|)
            dicDeMaterias.borrar(data.getAlias());
        }
        // Complejidad total:
        // O( |c| + |m| + Sum_{n \in N_m} |n| + E_m)
    }

}
/*
SistemaSIU:
    @TODO
        - Linea 33, tiene complejidad O(|Nc|) --LISTO
        - Linea 54: falta justificar, no explican su razonamiento. Hacen afirmaciones sin explicación
        - Linea 185 y 187: lista_estudiantes() no es O(1)! Deberian llamarlo una sola vez antes del for --LISTO*/
