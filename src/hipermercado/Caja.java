package hipermercado;

/*

Los objetos de la clase Caja se crean pasándole al constructor un objeto Cola
y uno Contabilidad. La clase Caja se encarga de pedir un Cliente a la Cola,
atenderlo y repetir el proceso. Si la cola devuelve un cliente null, la caja se
cierra. El tiempo de atención es el precio del carrito dividido por 10 en segundos.
Cada caja tendrá un identificador único autogenerado por la clase (use atributos static)
que se empleará en el mostrado de su estado. Las cajas pueden cerrarse también mediante
el "interrupt()" de Thread. En este caso, si se está procesando un cliente éste se volverá
a introducir en la cola por el principio. El método "run()" de una caja termina al ser cerrada.

Cuando una caja se cierra, se actualiza el objeto Contabilidad con
la suma de importes de las compras completadas en esa caja.

 */

public class Caja extends Thread {

    private Cola cola;
    private Contabilidad contabilidad;
    private long tiempo;
    public static long id;
//    private boolean cerrado;

    public Caja(Cola cola, Contabilidad contabilidad) {
        this.cola = cola;
        this.contabilidad = contabilidad;
        this.id = this.getId();
//        cerrado = false;
    }

    private Cliente sacarCliente(){
        Cliente cliente = cola.sacar();
        if(cliente == null)
            this.interrupt();
        return cliente;
    }

    private void atenderCliente(){
        Cliente cliente = sacarCliente();

        if(cliente != null && !this.isInterrupted()){
            System.out.println("La caja: " + this.getId() + " está atendiendo al cliente " + cliente.dameNombre() + " durante " + cliente.damePrecioCarro() / 10 + "seg");
            esperaTiempoCarrito((long) (cliente.damePrecioCarro() / 10));
            if(this.isInterrupted()){
                cola.añadirPrincipio(cliente);
                return;
            }
            System.out.println("La caja: "+this.getId()+ " ha terminado de atender al cliente "+cliente.dameNombre());
            contabilidad.añadeSaldo(cliente.damePrecioCarro());
        }
    }

    private void esperaTiempoCarrito(long tiempo) {
        try {
            Thread.sleep(tiempo * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(!this.isInterrupted()){
            atenderCliente();
        }
        //this.notifyAll();
    }

}
