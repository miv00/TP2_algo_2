package aed;

import java.util.ArrayList;

public class DicTrie<T extends String,H> {
    private Nodo _raiz;
    int _tamaño;

    private class Nodo {
        ArrayList<Nodo> _siguientes;
        int cantidadDeHijos;
        H significado;
        Character caracter;

        Nodo(H v, Character c) {
            significado = v;
            cantidadDeHijos = 0;
            caracter = c;
            // esto se podria cambiar por un Array comun inicializado en 255 posiciones
            _siguientes = new ArrayList<Nodo>();
            for (int i = 0; i < 256; i++) {
                _siguientes.add(null);
            }
        }

        public boolean esNodoInutil() {
            boolean sinSignificado = significado == null;
            boolean sinHijos = this.cantidadDeHijos == 0;
            return sinSignificado && sinHijos;
        }

        public boolean masDeUnHijoOtieneSignificado() {
            boolean conSignificado = significado != null;
            boolean hijos = this.cantidadDeHijos > 1;
            return conSignificado || hijos;
        }
    }

    public DicTrie() {
        _raiz = new Nodo(null, null);
        _tamaño = 0;

    }

    //basicamente es o entrar a la pos del array que es igual al char en ascii o crearlo, eso es O(longitud de la clave)
    //tambien chekea si es que esta y entra, se puede optimizar creo ... (probablemente chekiando al final si el elemento esta o no )
    //Optimizacion :lo que hay que hacer basicamente es no preguntar si esta y al final antes de cambiar el signficado ver si esta o no en null
    // si esta lo cambias , si no lo setias y sumas al tamaño
    public void agregar(T claveNueva, H elem) {
        Nodo _actual = _raiz;
        if (this.esta(claveNueva)) {
            for (int i = 0; i < claveNueva.length(); i++) {
                int charAscii = (int) claveNueva.charAt(i);
                _actual = _actual._siguientes.get(charAscii);
            }
            _actual.significado = elem;
        } else {
            for (int i = 0; i < claveNueva.length(); i++) {
                int charAscci = (int) claveNueva.charAt(i);
                if (_actual._siguientes.get(charAscci) != null) {
                    _actual = _actual._siguientes.get(charAscci);
                } else {
                    Nodo nuevo = new Nodo(null, (char) charAscci);
                    _actual._siguientes.set(charAscci, nuevo);
                    _actual.cantidadDeHijos++;
                    _actual = _actual._siguientes.get(charAscci);
                }
            }
            _actual.significado = elem;
            _tamaño++;
        }
    }

    // mismo que el anterior usu fuertemente que los arrays tienen en las posiciones los chars etc para que la complejidad sea el largo de la  clave
    public H obtener(T clave) {
        Nodo _actual = _raiz;
        for (int i = 0; i < clave.length(); i++) {
            int charAscci = (int) clave.charAt(i);
            _actual = _actual._siguientes.get(charAscci);

        }
        return _actual.significado;
    }

    // mismo que el anterior usu fuertemente que los arrays tienen en las posiciones los chars etc para que la complejidad sea el largo de la  clave
    public boolean esta(T clave) {
        Nodo _actual = _raiz;
        for (int i = 0; i < clave.length(); i++) {
            int charAscci = (int) clave.charAt(i);
            if (_actual._siguientes.get(charAscci) != null) {
                _actual = _actual._siguientes.get(charAscci);
            } else {
                return false;
            }
        }
        return _actual.significado != null;
    }
    // mismo que el anterior usu fuertemente que los arrays tienen en las posiciones los chars etc para que la complejidad sea el largo de la  clave
    // lo complicado de este metodo es sacar  el  ultimoNodoutil que o bien es la raiz o es alguno que tenga mas de dos hijos
    // tambien puede pasar que la clave a borrar se prefijo de otra en ese caso solo se borra el significado y queda todo igual

    public void borrar(T clave) {
        if (this.esta(clave)) {
            Nodo _actual = _raiz;
            Nodo ultimoNodoUtil = _raiz;
            int ultimaClaveAborrar = clave.charAt(0);
            for (int i = 0; i < clave.length(); i++) {
                int charAscci = (int) clave.charAt(i);
                if (_actual.masDeUnHijoOtieneSignificado()) {
                    ultimoNodoUtil = _actual;
                    ultimaClaveAborrar = charAscci;
                }
                _actual = _actual._siguientes.get(charAscci);
            }
            _actual.significado = null;
            this._tamaño--;
            if (_actual.esNodoInutil()) {
                ultimoNodoUtil._siguientes.set(ultimaClaveAborrar, null);
                ultimoNodoUtil.cantidadDeHijos--;
            }

        }
    }

    public ArrayList<String> imprimir() {
        DicTrie_Iterador iterador = new DicTrie_Iterador();
        ArrayList<String> e = iterador.anotarClaves();
        return e;

    }

    // probablemente se termine armando por recursion o anotando las claves en una variable interna
    // lo termine armando por recursion
    private class DicTrie_Iterador {
        private Nodo _actual;
        private Nodo _ultimoNodoUtilVisitado;
        private ArrayList<String> _claves;

        private int _ultimoAscciCode;

        private StringBuilder _stringActual;

        public DicTrie_Iterador() {
            _actual = _raiz;
            _ultimoNodoUtilVisitado = _raiz;
            _stringActual = new StringBuilder();

            this._claves = new ArrayList<>();
            ;
        }
        public ArrayList<String> anotarClaves(){
            while (haySiguiente()){
                siguienteClaveMasAlaIzq();
                irAlNodoConMasHijosSuperior();
            }
            return _claves;
        }

        public void irAlNodoConMasHijosSuperior() {
           while (noQuedaNadaParaAcceder() && haySiguiente()){
               _ultimoAscciCode= _stringActual.charAt((_stringActual.length()-1))+1;
               _stringActual.deleteCharAt(_stringActual.length()-1);
               _actual= irAlNodo(_stringActual.toString());
           }


        }
        public boolean noQuedaNadaParaAcceder(){
            for (int i = _ultimoAscciCode; i <256 ; i++) {
                if (_actual._siguientes.get(i)!=null){
                    return false;
                }
            }
            if (_actual.equals(_raiz)){
                return false;
            }
            return true;
        }
        public Nodo irAlNodo(String busqueda){
            Nodo a =_raiz;
            for (int i = 0; i < busqueda.length(); i++) {
               a=a._siguientes.get(busqueda.charAt(i));
            }
            return a;
        }


        public boolean haySiguiente() {
            return _claves.size() < _tamaño;
        }


        //busca la primera clave mas a la izquierda
        public void siguienteClaveMasAlaIzq(){
            for (int i = _ultimoAscciCode; i <256 ; i++) {

                if (_actual._siguientes.get(i)!=null){
                    _stringActual.append((char) i);
                    _actual=_actual._siguientes.get(i);
                    if (_actual.significado!=null){
                        _claves.add(_stringActual.toString());
                    }
                    i=0;
                }
            }
        }
    }
}











