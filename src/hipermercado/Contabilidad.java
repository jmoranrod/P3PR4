package hipermercado;

public class Contabilidad {

    private double saldo;

    public Contabilidad(){
        saldo = 0d;
    }

    public void añadeSaldo(double saldo){
        this.saldo += saldo;
    }

    public double dameSaldo(){
        return saldo;
    }

}
