package org.example.Model;

public enum TipoVehiculo {
    AUTO(800),
    MOTO(500),
    CAMIONETA(1000);

    private final int tarifa;

    TipoVehiculo(int tarifa){
        this.tarifa = tarifa;
    }

    public int getTarifa(){
        return tarifa;
    }
}
