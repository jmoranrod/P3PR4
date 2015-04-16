package hipermercado;

/*

Los objetos de la clase Contabilidad disponen de dos métodos: “añadeSaldo()” que añade
una cantidad al saldo, y “dameSaldo()”, que devuelve la
cantidad acumulada. Cuando se crean tienen saldo cero.

 */

public class Contabilidad {

    private double saldo;

    public Contabilidad(){
        saldo = 0d;
    }

    public Contabilidad(double saldo){
        this.saldo = saldo;
    }

    public void añadeSaldo(double saldo){
        this.saldo += saldo;
    }

    public double dameSaldo(){
        return saldo;
    }

}
