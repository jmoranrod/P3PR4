package hipermercado;

import java.util.LinkedList;

public class Cola {

    private int maxSize;
    private boolean closed;
    private LinkedList<Cliente> clientes;

    public Cola() {
        this.clientes = new LinkedList<>();
        this.maxSize = 0;
        this.closed = false;
    }

    public synchronized void añadirFinal(Cliente cliente) {
        if(closed)
            return;
        clientes.add(cliente);
        System.out.println(System.nanoTime() / 1000000 + " --> cliente número " + clientes.size() + " nombre: " + cliente.dameNombre() + " entra en la cola. Tamaño máximo de la cola: "+tamañoMáximo());
        if(maxSize < clientes.size()){
            maxSize = clientes.size();
        }
    }

    public synchronized void añadirPrincipio(Cliente cliente) {
        clientes.add(cliente);
    }

    public synchronized Cliente sacar() {
        if (!closed && clientes.isEmpty()) {
            while(clientes.isEmpty()){
                try {
                    wait(10000);
                    if(clientes.isEmpty()){
                        return null;
                    }
                } catch (InterruptedException e) {}
            }
            Cliente cliente = clientes.get(0);
            clientes.remove(0);
            return cliente;
        }else if(!closed && clientes.size() > 0){
            Cliente cliente = clientes.get(0);
            clientes.remove(0);
            return cliente;
        }

        if(closed && clientes.size() > 0){
            Cliente cliente = clientes.get(0);
            clientes.remove(0);
            return cliente;
        }

        return null;
    }

    public void cerrar() {
        System.out.println(System.nanoTime()/1000000+" --> COLA CERRADA, ¡NO SE ADMITEN MÁS CLIENTES!");
        this.closed = true;
    }

    public int tamañoMáximo() {
        return maxSize + 1;
    }

    public boolean getStatus(){
        return closed;
    }
}
