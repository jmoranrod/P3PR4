package hipermercado;

public class Caja extends Thread {

    private Cola cola;
    private Contabilidad contabilidad;
    public static long id;
    private double dinero;

    public Caja(Cola cola, Contabilidad contabilidad) {
        this.cola = cola;
        this.contabilidad = contabilidad;
        id = this.getId();
        this.dinero = 0d;
    }

    private Cliente sacarCliente(){
        Cliente cliente = cola.sacar();
        if(cliente == null){
            System.out.println(System.nanoTime()/1000000+ " --> Caja " + this.getId() + " cerrada.");
            this.interrupt();
            return null;
        }
        System.out.println(System.nanoTime()/1000000+" --> El cliente "+cliente.dameNombre()+" sale de la cola por la caja "+this.getId());
        return cliente;
    }

    private void atenderCliente(){
        Cliente cliente = sacarCliente();

        if(cliente != null && !this.isInterrupted()){
            System.out.println(System.nanoTime()/1000000 + " --> La caja: " + this.getId() + " está atendiendo al cliente " + cliente.dameNombre() + " durante " + cliente.damePrecioCarro() / 10 + "seg");
            esperaTiempoCarrito((long) (cliente.damePrecioCarro() / 10));
            if(this.isInterrupted()){
                cola.añadirPrincipio(cliente);
                return;
            }
            System.out.println(System.nanoTime()/1000000 + " --> La caja: " + this.getId() + " ha terminado de atender al cliente " + cliente.dameNombre());
            //contabilidad.añadeSaldo(cliente.damePrecioCarro());
            dinero += cliente.damePrecioCarro();
        }
    }

    private void esperaTiempoCarrito(long tiempo) {
        try {
            Thread.sleep(tiempo * 1000);
        } catch (InterruptedException e) {}
    }

    public void run(){
        while(!this.isInterrupted()){
            atenderCliente();
        }
        contabilidad.añadeSaldo(dinero);
        System.out.println(System.nanoTime()/1000000+ " --> " + dinero + "€"+" añadidos a la contabilidad"+" por la caja "+this.getId());
    }
}
