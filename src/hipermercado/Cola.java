package hipermercado;

import java.util.LinkedList;
import java.util.List;

/*
Los objetos de la clase Cola se encargan de almacenar los objetos Cliente que
esperan ser atendidos.La salida de un cliente de la cola se produce por su principio.
Las operaciones que soportan la Cola son “añadirFinal()” y “añadirPrincipio()” que añade
un nuevo cliente y “sacar()” que devuelve (y extrae) el cliente que esté al principio de
la cola, para lo cual si la cola está abierta puede esperar un máximo de 10 segundos, al
cabo de los cuales devolvería null. La cola se puede cerrar llamando al método "cerrar()".
Una cola cerrada no acepta nuevos clientes (el método “añadirFinal()” no hace nada) y
cuando se pretende sacar un cliente, y no queda ninguno, “sacar()” devuelve inmediatamente null.
La cola, además, nos devuelve el tamaño máximo que ha alcanzado desde su creación con el método “tamañoMáximo()”.
*/
public class Cola extends Thread {

    private int maxSize;
    private boolean closed;
    private List<Cliente> clientes;

    public Cola() {
        this.clientes = new LinkedList<Cliente>();
        this.maxSize = 0;
        this.closed = false;
    }

    public void añadirFinal(Cliente cliente) {
        if(closed)
            return;
        synchronized (clientes) {
            clientes.add(cliente);
            if(maxSize < clientes.size()){
                maxSize = clientes.size();
            }
        }
    }

    public void añadirPrincipio(Cliente cliente) {
        if(closed)
            return;
        synchronized (clientes) {
            clientes.add(cliente);
            if(maxSize < clientes.size()){
                maxSize = clientes.size();
            }
        }
    }

    public Cliente sacar() throws InterruptedException {
        long antes = System.currentTimeMillis();
        if (!closed && clientes.size() > 0) {
            wait(10000);
            long despues = System.currentTimeMillis();
            if(despues - antes > 10000){
                return null;
            }
            synchronized (clientes) {
                Cliente cliente = clientes.get(0);
                clientes.remove(0);
                return cliente;
            }
        }
        return null;
    }

    public void cerrar() {
        this.closed = true;
    }

    public int tamañoMáximo() {
        return maxSize;
    }

}
