abstract class Secuencia<T> {
    val sec:MutableList<T> = mutableListOf();

    fun agregar(e:T) {
        sec.add(e);
    }
    abstract fun remover():T

    fun vacio():Boolean {
        return sec.size == 0
    }
}

class Pila<T>:Secuencia<T>() {
    override fun remover():T {
        if (sec.size == 0) throw RuntimeException("Pila está vacía") 
        val el = sec[sec.size - 1];
        sec.removeAt(sec.size - 1);
        return el
    }
}

class Cola<T>:Secuencia<T>() {
    override fun remover():T {
        if (sec.size == 0) throw RuntimeException("Cola está vacía") 
        val el = sec[0];
        sec.removeAt(0);
        return el
    }
}

class Grafo(n:Int) {
    val nVertices:Int = n
    val listas:MutableList<MutableList<Int>> = mutableListOf()
    val vs = (0..nVertices)

    init {
        vs.forEach{ listas.add(mutableListOf()) };
    }

    fun nuevoLado(u:Int, v:Int) {
        validarVertice(u);
        validarVertice(v);
        listas[u].add(v);
    }

    fun adyacentes(v:Int):MutableList<Int> {
        validarVertice(v);
        return listas[v];
    }

    fun validarVertice(v:Int) {
        if (nVertices == 0 || v < 0 && v > nVertices-1)
            throw RuntimeException("vertice $v no existe en el grafo");
    }
}

abstract class Busqueda(val G:Grafo) {
    abstract val Q:Secuencia<Int>
    fun buscar(D:Int, H:Int):Int {
        G.validarVertice(D);
        G.validarVertice(H);

        val colores:MutableList<Int> = mutableListOf();
        G.vs.forEach{colores.add(0)};
        colores[D] = 1;
        this.Q.agregar(D);

        var explorados = 0;
        while (!Q.vacio()) {
            val u = Q.remover()
            G.adyacentes(u).forEach{ v->
                if (colores[v] == 0) {
                    explorados++;
                    if (v == H) return explorados;
                    colores[v] = 1;
                    Q.agregar(v);
                }
            }
        }
        return -1;
    }
}

class BFS(G:Grafo):Busqueda(G) {
    override val Q:Cola<Int> = Cola<Int>()
}
class DFS(G:Grafo):Busqueda(G) {
    override val Q:Pila<Int> = Pila<Int>()
}


fun main() {

    val G = Grafo(13);
    G.nuevoLado(0, 1);
    G.nuevoLado(1, 2);
    G.nuevoLado(2, 3);
    G.nuevoLado(0, 4);
    G.nuevoLado(4, 5);
    G.nuevoLado(5, 6);
    G.nuevoLado(0, 7);
    G.nuevoLado(7, 8);
    G.nuevoLado(8, 9);
    G.nuevoLado(0, 10);
    G.nuevoLado(10, 11);
    G.nuevoLado(11, 12);

    val bfs = BFS(G);
    val dfs = DFS(G);
    println(bfs.buscar(0, 10));
    println(dfs.buscar(0, 10));
    println(bfs.buscar(0, 12));
    println(dfs.buscar(0, 10));
}

