package hipermercado;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
de averias es suficicnete es suficiente con crear un objeto de la
clase DuendeAveria al que se le pase un array o Collection con las Cajas.
*/
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de cajas: ");
        int nroCajas = scanner.nextInt();
        System.out.print("Número de clientes: ");
        int clientes = scanner.nextInt();
        Cola cola = new Cola();
        Random r = new Random();
        int limInf = 0, limSup = 5;
        int cadencia = r.nextInt(limSup - limInf) + limInf, restantes = 0;
        double precioTotal = 0d;
        System.out.println(nroCajas + " " + clientes + " " + cadencia);
        long tiempoInicio = System.currentTimeMillis();
        for (int i = 0; i < clientes; i++){
            if(System.currentTimeMillis() + (cadencia * 1000) >= tiempoInicio){
                Cliente cliente = new Cliente();
                cola.añadirFinal(cliente);
                precioTotal += cliente.damePrecioCarro();
            }
            /*if(cadencia > clientes){
                cola.añadirFinal(new Cliente());
            }else{
                if(i == cadencia) break;
                cola.añadirFinal(new Cliente());
                restantes = i;
            }*/
        }
        System.out.println("El precio total debería ser: "+precioTotal);
        List<Caja> cajas = new LinkedList<Caja>();
        Contabilidad contabilidad = new Contabilidad();
        for(int i = 0; i< nroCajas; i++){
            cajas.add(new Caja(cola, contabilidad));
        }

        for(Caja caja : cajas){
            caja.start();
        }

        if(System.currentTimeMillis() - tiempoInicio >= 60000)
            cola.cerrar();

        for(Caja caja : cajas){
            try{
                caja.join();
            }catch (Exception e){}
        }

        System.out.println("Precio final: "+contabilidad.dameSaldo());

    }

}


