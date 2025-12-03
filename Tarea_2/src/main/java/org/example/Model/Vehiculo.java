package org.example.Model;


import java.util.Objects;

public class Vehiculo {

    private final TipoVehiculo tipoVehiculo;
    private final String patente;

    public Vehiculo(TipoVehiculo tipoVehiculo, String patente) {
        this.tipoVehiculo = tipoVehiculo;
        this.patente = patente;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getPatente() {
        return patente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return tipoVehiculo == vehiculo.tipoVehiculo && Objects.equals(patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoVehiculo, patente);
    }
}
