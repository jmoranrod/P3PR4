package hipermercado;

/*

Los objetos de la clase Contabilidad disponen de dos métodos: “añadeSaldo()” que añade
una cantidad al saldo, y “dameSaldo()”, que devuelve la
cantidad acumulada. Cuando se crean tienen saldo cero.

 */

public class Contabilidad {

    private float saldo;

    public Contabilidad(){
        saldo = 0f;
    }

    public Contabilidad(float saldo){
        this.saldo = saldo;
    }

    public void añadeSaldo(float saldo){
        this.saldo += saldo;
    }

    public float dameSaldo(){
        return saldo;
    }

}
