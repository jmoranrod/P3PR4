package hipermercado;

import java.util.*;

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

        List<Caja> cajas = new LinkedList<>();
        Contabilidad contabilidad = new Contabilidad();
        for(int i = 0; i< nroCajas; i++){
            cajas.add(new Caja(cola, contabilidad));
        }

        for(Caja caja : cajas){
            caja.start();
        }

        //DuendeAveria duende = new DuendeAveria(cajas);

        long tiempoInicio = System.nanoTime();
        for (int i = 1; i <= clientes; i++){
            long actual = System.nanoTime();
            if((actual - tiempoInicio)/1000000 >= 60000){
                cola.cerrar();
                break;
            }
            cadencia = r.nextInt(5);
            if(!cola.getStatus()){
                Thread.sleep(cadencia * 1000);
                Cliente cliente = new Cliente();
                cola.añadirFinal(cliente);
                precioTotal += cliente.damePrecioCarro();
            }
        }

        for(Caja caja : cajas){
            try{
                caja.join();
            }catch (Exception e){}
        }

        System.out.println("\n"+System.nanoTime()/1000000+" --> Precio final: "+contabilidad.dameSaldo()+"€.");
    }

}
