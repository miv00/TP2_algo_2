package aed;

import java.util.ArrayList;

public class DicTrie<T extends String, H> {
    // Invariante de Representación
    // El trie siempre tiene que tener un nodo presente (nodo raiz), incluso si el trie esta vacio.
    // Las claves deben ser strings comprendidos por caracteres ASCII.
    // Cada nodo puede tener hasta 256 hijos (uno por cada posible caracter ASCII).
    // Los nodos tienen un solo padre salvo la raiz (que no tiene padre) ie es un arbol.
    // Los nodos que no tienen significado tienen hijos.
    
    private Nodo _raiz;
    int _tamaño;

    private class Nodo {
        ArrayList<Nodo> _siguientes;
        int cantidadDeHijos;
        H significado;
        Character caracter;

        Nodo(H v, Character c) {
            significado = v;                      // O(1)
            cantidadDeHijos = 0;                  // O(1)
            caracter = c;                         // O(1)
            _siguientes = new ArrayList<>(256);   // O(256)
            for (int i = 0; i < 256; i++) {
                _siguientes.add(null);            // O(256)
            }
        }

        public boolean esNodoInutil() {
            boolean sinSignificado = significado == null;  // O(1)
            boolean sinHijos = this.cantidadDeHijos == 0;  // O(1)
            return sinSignificado && sinHijos;             // O(1)
        }

        public boolean masDeUnHijoOtieneSignificado() {
            boolean conSignificado = significado != null;  // O(1)
            boolean hijos = this.cantidadDeHijos > 1;      // O(1)
            return conSignificado || hijos;                // O(1)
        }
    }

    public DicTrie() {
        _raiz = new Nodo(null, null);   // O(256)
        _tamaño = 0;                   // O(1)
    }

    // Complejidad: O(|claveNueva|)
    public void agregar(T claveNueva, H elem) {
        Nodo _actual = _raiz;           // O(1)
        if (this.esta(claveNueva)) {    // O(|claveNueva|)
            for (int i = 0; i < claveNueva.length(); i++) {
                int charAscii = (int) claveNueva.charAt(i);  // O(1)
                _actual = _actual._siguientes.get(charAscii); // O(1)
            }
            _actual.significado = elem; // O(1)
        } else {
            for (int i = 0; i < claveNueva.length(); i++) {
                int charAscii = (int) claveNueva.charAt(i);  // O(1)
                if (_actual._siguientes.get(charAscii) != null) {
                    _actual = _actual._siguientes.get(charAscii); // O(1)
                } else {
                    Nodo nuevo = new Nodo(null, (char) charAscii);  // O(256)
                    _actual._siguientes.set(charAscii, nuevo);      // O(1)
                    _actual.cantidadDeHijos++;                      // O(1)
                    _actual = _actual._siguientes.get(charAscii);   // O(1)
                }
            }
            _actual.significado = elem; // O(1)
            _tamaño++;                  // O(1)
        }
    }

    // Complejidad: O(|clave|)
    public H obtener(T clave) {
        Nodo _actual = _raiz;           // O(1)
        // O(|clave|)
        for (int i = 0; i < clave.length(); i++) {
            int charAscii = (int) clave.charAt(i);  // O(1)
            _actual = _actual._siguientes.get(charAscii); // O(1)
        }
        return _actual.significado;     // O(1)
    }

    // Complejidad: O(|clave|)
    public boolean esta(T clave) {
        Nodo _actual = _raiz;           // O(1)
        // O(|clave|)
        for (int i = 0; i < clave.length(); i++) {
            int charAscii = (int) clave.charAt(i);  // O(1)
            if (_actual._siguientes.get(charAscii) != null) {
                _actual = _actual._siguientes.get(charAscii); // O(1)
            } else {
                return false;            // O(1)
            }
        }
        return _actual.significado != null; // O(1)
    }

    // Complejidad: O(|clave|)
    public void borrar(T clave) {
        if (this.esta(clave)) {          // O(|clave|)
            Nodo _actual = _raiz;        // O(1)
            Nodo ultimoNodoUtil = _raiz; // O(1)
            int ultimaClaveAborrar = clave.charAt(0);  // O(1)
            // O(|clave|)
            for (int i = 0; i < clave.length(); i++) {
                int charAscii = (int) clave.charAt(i);  // O(1)
                if (_actual.masDeUnHijoOtieneSignificado()) {
                    ultimoNodoUtil = _actual;          // O(1)
                    ultimaClaveAborrar = charAscii;    // O(1)
                }
                _actual = _actual._siguientes.get(charAscii); // O(1)
            }
            _actual.significado = null; // O(1)
            this._tamaño--;             // O(1)
            if (_actual.esNodoInutil()) {
                ultimoNodoUtil._siguientes.set(ultimaClaveAborrar, null); // O(1)
                ultimoNodoUtil.cantidadDeHijos--;                         // O(1)
            }
        }
    }

    // Voy a usar C para el conjunto de claves y |c| para la longitud de una clave c \in C

    // Complejidad: O(sum_{c \in C} |c|)
    public ArrayList<String> imprimir() {
        DicTrie_Iterador iterador = new DicTrie_Iterador();
        return iterador.armarClaves();  // O(sum_{c \in C} |c|)
    }

    private class DicTrie_Iterador {
        private ArrayList<String> claves;

        public DicTrie_Iterador() {
            this.claves = new ArrayList<>();  // O(1)
        }

        // Complejidad: O(sum_{c \in C} |c|)
        public ArrayList<String> armarClaves() {
            recorrerIterativo();             // O(sum_{c \in C} |c|)
            return claves;                   // O(1)
        }

        // Complejidad: O(sum_{c \in C} |c|)
        private void recorrerIterativo() {
            Pila<NodoConPrefijo> stack = new Pila<>();  // O(1)
            stack.push(new NodoConPrefijo(_raiz, new StringBuffer()));  // O(1)

            while (!stack.isEmpty()) {       // O(sum_{c \in C} |c|)
                NodoConPrefijo actualConPrefijo = stack.pop();  // O(1)
                Nodo actual = actualConPrefijo.nodo;            // O(1)
                StringBuffer prefijo = actualConPrefijo.prefijo; // O(1)

                if (actual.significado != null) {
                    claves.add(prefijo.toString());   // O(|prefijo|)
                }

                // Iterar sobre los hijos en orden creciente (0 a 255)
                for (int i = 255; i >= 0; i--) {      // O(1) por iteración, 256 veces
                    Nodo siguiente = actual._siguientes.get(i);  // O(1)
                    if (siguiente != null) {
                        StringBuffer nuevoPrefijo = new StringBuffer(prefijo);  // O(|prefijo|)
                        nuevoPrefijo.append((char) i);                        // O(1)
                        stack.push(new NodoConPrefijo(siguiente, nuevoPrefijo)); // O(1)
                    }
                }
            }
        }

        private class NodoConPrefijo {
            Nodo nodo;
            StringBuffer prefijo;

            NodoConPrefijo(Nodo nodo, StringBuffer prefijo) {
                this.nodo = nodo;           // O(1)
                this.prefijo = prefijo;     // O(1)
            }
        }
    }

    private class Pila<E> {
        private ArrayList<E> elementos;

        public Pila() {
            elementos = new ArrayList<>(); // O(1)
        }

        public void push(E elem) {
            elementos.add(elem);           // O(1)
        }

        public E pop() {
            if (isEmpty()) {
                throw new RuntimeException("Pila vacía");
            }
            return elementos.remove(elementos.size() - 1);  // O(1)
        }

        public boolean isEmpty() {
            return elementos.isEmpty();    // O(1)
        }
    }
}
