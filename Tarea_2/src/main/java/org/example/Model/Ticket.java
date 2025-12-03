package org.example.Model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {

    private final int idTicket;

    private final Vehiculo vehiculo;
    private final LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private String estado;
    private int montoCobrado;

    public Ticket(int idTicket, Vehiculo vehiculo, LocalDateTime fechaHoraEntrada) {
        this.idTicket = idTicket;
        this.vehiculo = vehiculo;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.estado = "Abierto";

    }


    public boolean ValidarFechaYMontoPlausible(int montoCobrado, LocalDateTime fechaHoraSalida){
        return montoCobrado >= 0 && fechaHoraSalida != null && fechaHoraSalida.isAfter(fechaHoraEntrada);
    }

    public void CerrrarTicket(int montoCobrado, LocalDateTime fechaHoraSalida){

        if(!ValidarFechaYMontoPlausible(montoCobrado,fechaHoraSalida))throw new IllegalArgumentException("Error al cerrar el ticket");

        this.montoCobrado = montoCobrado;
        this.estado = "Cerrado";
        this.fechaHoraSalida = fechaHoraSalida;
    }
    public int getIdTicket() {
        return idTicket;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public String getEstado() {
        return estado;
    }

    public int getMontoCobrado() {
        return montoCobrado;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return idTicket == ticket.idTicket && montoCobrado == ticket.montoCobrado && Objects.equals(vehiculo, ticket.vehiculo) && Objects.equals(fechaHoraEntrada, ticket.fechaHoraEntrada) && Objects.equals(fechaHoraSalida, ticket.fechaHoraSalida) && Objects.equals(estado, ticket.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, vehiculo, fechaHoraEntrada, fechaHoraSalida, estado, montoCobrado);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "idTicket=" + idTicket +
                ", vehiculo=" + vehiculo +
                ", fechaHoraEntrada=" + fechaHoraEntrada +
                ", fechaHoraSalida=" + fechaHoraSalida +
                ", estado='" + estado + '\'' +
                ", montoCobrado=" + montoCobrado +
                '}';
    }
}
