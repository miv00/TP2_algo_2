package aed;

public class SistemaSIU {
    private  DicTrie _diccionarioCarreras;
    private  DicTrie _diccionarioestudiantes;
    enum CargoDocente{

        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        _diccionarioCarreras = new DicTrie<String,DicTrie>();
        _diccionarioestudiantes = new DicTrie();
        for (int i = 0; i < infoMaterias.length ; i++) {
            ParCarreraMateria[] par = infoMaterias[i].getParesCarreraMateria();
            Materia materia = new Materia(par);
            for (int j = 0; j < par.length; j++) {

                // checkiar aliansing por que si estan adentro de la misma pos del array son la misma materia
                String carrera = par[j].getCarrera();
                String nombreMateria = par[j].getNombreMateria();

                if (_diccionarioCarreras.esta(carrera)){
                    DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
                    dicCarreas.agregar(nombreMateria,materia);
                }else {
                    _diccionarioCarreras.agregar(carrera,new DicTrie<>());
                    DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
                    dicCarreas.agregar(nombreMateria,materia);
                }
            }
        }
        for (int i = 0; i < libretasUniversitarias.length ; i++) {
            _diccionarioestudiantes.agregar(libretasUniversitarias[i],0);
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){
        DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
        Materia mat = (Materia) dicCarreas.obtener(materia);
        mat.agregarEstudiante(estudiante);
        int cant= (int) _diccionarioestudiantes.obtener(estudiante);
        _diccionarioestudiantes.agregar(estudiante,cant+1);
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
        Materia mat = (Materia) dicCarreas.obtener(materia);
        mat.agregar(cargo);
    }

    public int[] plantelDocente(String materia, String carrera){
        DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
        Materia mat = (Materia) dicCarreas.obtener(materia);
        return mat.get_docentes();
    }

    public void cerrarMateria(String materia, String carrera){
        DicTrie dicMaterias = (DicTrie) _diccionarioCarreras.obtener(carrera);
        Materia mat = (Materia) dicMaterias.obtener(materia);
        ParCarreraMateria[] listaMateriasBorrar = mat.get_alias();
        for (int i = 0; i < mat.lista_estudiantes().size() ; i++) {
            String estudiante= (String) mat.lista_estudiantes().get(i);
            int cant = (int) _diccionarioestudiantes.obtener(estudiante);
            _diccionarioestudiantes.agregar(estudiante,cant-1);
        }
        for (int i = 0; i <listaMateriasBorrar.length ; i++) {
            String carreraDeDondeBorrar = listaMateriasBorrar[i].carrera;
            DicTrie listaDeMaterias = (DicTrie) _diccionarioCarreras.obtener(carreraDeDondeBorrar);
            listaDeMaterias.borrar(listaMateriasBorrar[i].nombreMateria);
        }
    }

    public int inscriptos(String materia, String carrera){
        DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
        Materia mat = (Materia) dicCarreas.obtener(materia);
        return mat.get_estudiantes();
    }

    public boolean excedeCupo(String materia, String carrera){
        DicTrie dicCarreas = (DicTrie) _diccionarioCarreras.obtener(carrera);
        Materia mat = (Materia) dicCarreas.obtener(materia);
        return mat.excedeCupo();
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        return (int) _diccionarioestudiantes.obtener(estudiante);
    }
}
