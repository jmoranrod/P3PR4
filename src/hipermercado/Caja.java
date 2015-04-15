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
    public static long id;

    public Caja(Cola cola, Contabilidad contabilidad) {
        this.cola = cola;
        this.contabilidad = contabilidad;
        this.id = Thread.currentThread().getId();
    }
}
