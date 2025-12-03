package org.example.Service;

import org.example.Model.Ticket;
import org.example.Model.TipoVehiculo;
import org.example.Model.Vehiculo;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estacionamiento {

    private List<Ticket> listaDeTickets;
    private int tope;
    private float descuentoFinde;

    public Estacionamiento(int tope,float descuentoFinde) {
        this.listaDeTickets = new ArrayList<Ticket>();
        this.tope = tope;
        this.descuentoFinde = descuentoFinde;
    }

    public void RegistrarVehiculo(int idTicket, String patente, TipoVehiculo tipoVehiculo, LocalDateTime fechaEntrada) {
        Ticket ticket = new Ticket(idTicket, new Vehiculo(tipoVehiculo, patente), fechaEntrada);

        listaDeTickets.add(ticket);
    }

    public void RegistrarSalidaVehiculo(int idTicket, LocalDateTime horaCierreTicket) {
        for (Ticket ticket:listaDeTickets) {

            if(ticket.getIdTicket() == idTicket){
                int montoPagar = CalcularMontoTotalPagar(ticket, horaCierreTicket);
                ticket.CerrrarTicket(montoPagar,horaCierreTicket);
                return;
            }
        }
        throw new IllegalArgumentException("Ticket no encontrado");

    }
    
    public int CalcularMontoTotalPagar(Ticket ticket, LocalDateTime horaCierreTicket){
        LocalDateTime horaInicio = ticket.getFechaHoraEntrada();
        long minutos = Duration.between(horaInicio,horaCierreTicket).toMinutes();
        if(minutos <  0)throw new IllegalArgumentException("Fecha de cierre invalida");
        int tiempoEntreBloques = 30;
        int cantidadBloquesTiempo = (int) Math.ceil((double) minutos /tiempoEntreBloques);

        int montoAPagar = cantidadBloquesTiempo * ticket.getVehiculo().getTipoVehiculo().getTarifa();

        //tope diario
        int montoLuegoDeTope = Math.min(montoAPagar, tope);

        DayOfWeek diaDeLaSemana =  horaInicio.getDayOfWeek();

        // descuento finde
        if (diaDeLaSemana == DayOfWeek.SATURDAY || diaDeLaSemana == DayOfWeek.SUNDAY)return montoLuegoDeTope - (int) Math.floor(montoLuegoDeTope * descuentoFinde);
        return montoLuegoDeTope ;

        // falta agregar lo del top del diario si hay mas de un dia
    }


    public List<Ticket> getListaDeTickets(boolean isCerrado ) {

        //retorna la lista filtrada directamente
        if(isCerrado){
            return listaDeTickets.stream()
                    .filter(t -> t.getEstado().equals("Cerrado"))
                    .toList();
        }
        return listaDeTickets.stream()
                .filter(t -> t.getEstado().equals("Abierto"))
                .toList();
    }


    //Total recaudado en un dia
    public int totalRecaudado(LocalDateTime fechaFinal){
        LocalDate dateARecaudar = fechaFinal.toLocalDate();
        int recaudado = 0;
        for (Ticket ticket: listaDeTickets ){
            if (ticket.getFechaHoraSalida() != null
                    && ticket.getFechaHoraSalida().toLocalDate().isEqual(dateARecaudar)) {
                recaudado += ticket.getMontoCobrado();
            }

        }
        return recaudado;
    }

    public Ticket getTicket(int idTicket){
        for (Ticket ticket:listaDeTickets) {
            if(ticket.getIdTicket() == idTicket){
                return ticket;
            }
        }
        throw new IllegalArgumentException("Ticket no encontrado");
    }
}
