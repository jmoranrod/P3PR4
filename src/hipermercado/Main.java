package hipermercado;

import java.util.*;

/*
Simulación
El programa principal se ha de encargar de pedir el número
de cajas a usar y el número de clientes de la simulación.
Se crearán y arrancarán los elementos que intervienen de
forma que los clientes se irán añadiendo a la cola con una
cadencia aleatoria de entre cero y 5. La cola se cerrará al
cabo de 60 segundos. Al procesar el último cliente, o si no
quedaran cajas abiertas, se detendrá el programa.

Se simulará asimismo el que las cajas pueden, por avería, dejar
de funcionar. Para ello se suministra una clase DuendeAveria
que ejecuta, en un hilo a parte, la acción de averiar las cajas
aleatoriamente con interrupt(). Para que se produzca este proceso
de averias es suficiente con crear un objeto de la
clase DuendeAveria al que se le pase un array o Collection con las Cajas.

Para tener un percepción de lo que ocurre se deben mostrar información que incluya instante (hora)
y estado del objeto implicado en cada uno de los siguientes eventos:

Se añade un cliente a la cola. hecho

Se saca un cliente de la cola. hecho

Se inicia el procesamiento de un cliente en una caja. hecho

Se finaliza el procesamiento de un cliente en una caja. hecho

Se añaden datos a la contabilidad. hecho

*/
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de cajas: ");
        int nroCajas = scanner.nextInt();
        System.out.print("Ingrese el número de clientes: ");
        int clientes = scanner.nextInt();
        Cola cola = new Cola();
        Random r = new Random();
        int cadencia;
        double precioTotal = 0d;
        //System.out.println(nroCajas + " " + clientes + " " + cadencia);
        long tiempoInicio = System.nanoTime();
        for (int i = 1; i <= clientes; i++){
            long actual = System.nanoTime();
            //System.out.println("Tiempo actual: "+actual+" Tiempo inicio: "+tiempoInicio+" Diferencia: "+(actual-tiempoInicio));
            if((actual - tiempoInicio)/1000000 >= 60000){
                cola.cerrar();
                //System.out.println(System.currentTimeMillis() + " --> COLA CERRADA, NO SE ADMITEN MAS CLIENTES!");
                break;
            }
            cadencia = r.nextInt(5);
            //System.out.println("Cadencia => "+cadencia);
            if(!cola.getStatus()){
                Thread.sleep(cadencia * 1000);
                Cliente cliente = new Cliente();
                cola.añadirFinal(cliente);
                //System.out.println(System.currentTimeMillis() + " --> cliente número " + i + " nombre: " + cliente.dameNombre() + " entra en la cola.");
                precioTotal += cliente.damePrecioCarro();
            }
        }
        System.out.println("El precio total debería ser: "+precioTotal);
        List<Caja> cajas = new LinkedList<Caja>();
        Contabilidad contabilidad = new Contabilidad();
        for(int i = 0; i< nroCajas; i++){
            cajas.add(new Caja(cola, contabilidad));
        }

        for(Caja caja : cajas){
            caja.start();
            //caja.join();
        }
/*
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet){
            System.out.println(thread);
        }
*/
        for(Caja caja : cajas){
            try{
                caja.join();
            }catch (Exception e){}
        }

        System.out.println("\n"+System.nanoTime()/1000000+" --> Precio final: "+contabilidad.dameSaldo()+"€.");
    }

}
