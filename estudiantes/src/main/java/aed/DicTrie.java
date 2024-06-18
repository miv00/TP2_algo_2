package aed;

import java.util.ArrayList;
import java.util.Iterator;

public class DicTrie<T extends String,H> {
    private Nodo _raiz;
    int _tamaño;


    private class Nodo {
        ArrayList<Nodo> _siguientes;
        int cantidadDeHijos;
        H significado;
        Character caracter ;

        Nodo(H v,Character c) {
            significado = v;
            cantidadDeHijos = 0;
            caracter = c;
            _siguientes= new ArrayList<Nodo>();
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
            _raiz = new Nodo(null,null);
            _tamaño = 0;

        }

        public void agregar(T claveNueva, H elem) {
            Nodo _actual = _raiz;
            if (this.esta(claveNueva)){
                for (int i = 0; i <claveNueva.length() ; i++) {
                    int charAscci = (int) claveNueva.charAt(i);
                    _actual =_actual._siguientes.get(charAscci);
                }
                _actual.significado = elem;
            }else{
            for (int i = 0; i < claveNueva.length(); i++) {
                int charAscci = (int) claveNueva.charAt(i);
                if (_actual._siguientes.get(charAscci) != null) {
                    _actual =_actual._siguientes.get(charAscci);
                } else {
                   Nodo nuevo = new Nodo(null,(char)charAscci);
                   _actual._siguientes.set(charAscci,nuevo);
                   _actual.cantidadDeHijos++;
                   _actual=_actual._siguientes.get(charAscci);
                }
            }
            _actual.significado = elem;
            _tamaño++;
            }
        }

        public H obtener(T clave) {
            Nodo _actual = _raiz;
            for (int i = 0; i < clave.length(); i++) {
                int charAscci = (int) clave.charAt(i);
                _actual = _actual._siguientes.get(charAscci);

            }
            return _actual.significado;
        }

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
            return _actual.significado!=null;
        }

        public void borrar(T clave) {
            if (this.esta(clave)){
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
    public ArrayList<String> imprimir(){
        DicTrie_Iterador iterador = new DicTrie_Iterador();
        ArrayList<String> e =iterador.armarClaves();
        return e;

    }
// probablemente se termine armando por recursion o anotando las claves en una variable interna
    private class DicTrie_Iterador   {
        private Nodo _ultimoNodoConMasDeUnHijo;
        private ArrayList<String> claves;
        private int ultimoAscciCode;

        public  DicTrie_Iterador(){
            _ultimoNodoConMasDeUnHijo = _raiz;
            this.claves=new ArrayList<>();
            ;
        }
        public ArrayList<String> armarClaves(){
            recorrerRursivo(_raiz,"",0);
            return claves;
        }
        /*public void recorrer(Nodo nodo,StringBuilder prefijo ,int valor ){
            Nodo actual = nodo;
            for (int i = valor; i <nodo._siguientes.size() ; i++) {
                if (actual.cantidadDeHijos>1){
                    _ultimoNodoConMasDeUnHijo=actual;
                    ultimoAscciCode=i;
                } if (actual.significado!=null) {
                    claves.add(prefijo.toString());

                }if (actual._siguientes.get(i)!=null){
                    prefijo.append(i);
                    actual = actual._siguientes.get(i);
                    i = 0;
                }else {
                    claves.add(prefijo.toString());
                }
            }
            }

        public void siguiente(){
            StringBuilder pre = new StringBuilder(claves.get(claves.size()-1));
            recorrer(_ultimoNodoConMasDeUnHijo,pre,ultimoAscciCode);

        }*/

        public void recorrerRursivo(Nodo nodo,String pre ,int valor ){
            Nodo actual = nodo;
            StringBuilder prefijo = new StringBuilder();
            prefijo.append(pre);
            // cosas muy cabeza que arme por estar en el laburo
            if(nodo.caracter!=null){
                prefijo.append(nodo.caracter);
            }
            if (actual.significado!=null) {
                claves.add(prefijo.toString());
            }
            for (int i = valor; i <nodo._siguientes.size() ; i++) {
                if (actual._siguientes.get(i)!=null){
                    recorrerRursivo(actual._siguientes.get(i),prefijo.toString(),0);
                    }
                else{

                    }
        }
    }







    }



    }
