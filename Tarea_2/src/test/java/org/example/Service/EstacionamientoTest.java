package org.example.Service;

import org.example.Model.Ticket;
import org.example.Model.TipoVehiculo;
import org.example.Model.Vehiculo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class EstacionamientoTest {


    private Estacionamiento estacionamiento;

    @BeforeEach
    void setUp() {

        estacionamiento = new Estacionamiento(15000,0.1F);
        estacionamiento.RegistrarVehiculo(
                0, "ABCD12", TipoVehiculo.AUTO,
                LocalDateTime.of(2025, 1, 4, 10, 0)
        );

        estacionamiento.RegistrarVehiculo(
                1, "AACD12", TipoVehiculo.MOTO,
                LocalDateTime.of(2025, 1, 1, 11, 0)
        );

        estacionamiento.RegistrarVehiculo(
                2, "ABBD12", TipoVehiculo.CAMIONETA,
                LocalDateTime.of(2025, 1, 1, 12,0)
        );

        estacionamiento.RegistrarVehiculo(
                3, "AVBD12", TipoVehiculo.CAMIONETA,
                LocalDateTime.of(2025, 1, 1, 12,0)
        );

        estacionamiento.RegistrarSalidaVehiculo(3,LocalDateTime.of(2025, 1, 1, 13,0));

        estacionamiento.RegistrarVehiculo(
                4, "AHBD12", TipoVehiculo.AUTO,
                LocalDateTime.of(2025, 1, 1, 12,0)
        );

        estacionamiento.RegistrarSalidaVehiculo(4,LocalDateTime.of(2025, 1, 2, 0,0));
    }

    @Test
    void registrarVehiculo() {



        //Given
        Vehiculo vehiculo = new Vehiculo(TipoVehiculo.AUTO,"ABCD15");
        LocalDateTime fechaEntrada = LocalDateTime.of(2025,1,1,15, 0);


        //When

        estacionamiento.RegistrarVehiculo(5,"ABCD15",TipoVehiculo.AUTO,fechaEntrada);
        //Then

        assertEquals(vehiculo,estacionamiento.getTicket(5).getVehiculo());
        assertEquals(fechaEntrada, estacionamiento.getTicket(5).getFechaHoraEntrada());
        assertEquals(vehiculo,estacionamiento.getTicket(5).getVehiculo());
    }


    @Test
    void registrarSalidaVehiculoTicketExistente() {
        // Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 1, 15, 0);

        // When
        estacionamiento.RegistrarSalidaVehiculo(1, horaSalida);

        // Then
        Ticket ticket = estacionamiento.getTicket(1);
        assertEquals("Cerrado", ticket.getEstado());
        assertEquals(horaSalida, ticket.getFechaHoraSalida());
    }

    @Test
    void registrarSalidaVehiculoTicketInexistente() {
        // Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 1, 15, 0);

        // When

        // Then

        assertThrows(IllegalArgumentException.class, () -> {
            estacionamiento.RegistrarSalidaVehiculo(100, horaSalida);
        });

    }

    @Test
    void calcularMontoTotalPagarPrecioNoLimiteCorrecto() {

        //Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 1, 12, 0);

        //deberian ser 2 bloques
        int resultadoEsperado = 2*TipoVehiculo.MOTO.getTarifa();

        Ticket ticket = estacionamiento.getTicket(1);
        //When


        int resultado = estacionamiento.CalcularMontoTotalPagar(ticket,horaSalida);
        //Then

        assertEquals(resultadoEsperado,resultado);
    }

    @Test
    void calcularMontoTotalPagarPrecioConLimiteCorrecto() {

        //Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 1, 23, 0);

        //Debería superar el tope y ajustarse al tope
        int resultadoEsperado = 15000;

        Ticket ticket = estacionamiento.getTicket(2);
        //When


        int resultado = estacionamiento.CalcularMontoTotalPagar(ticket,horaSalida);
        //Then

        assertEquals(resultadoEsperado,resultado);
    }

    @Test
    void calcularMontoTotalPagarEnTiempo0() {

        //Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 1, 12, 0);

        //Debería superar el tope y ajustarse al tope
        int resultadoEsperado = 0;

        Ticket ticket = estacionamiento.getTicket(2);
        //When


        int resultado = estacionamiento.CalcularMontoTotalPagar(ticket,horaSalida);
        //Then

        assertEquals(resultadoEsperado,resultado);
    }

    @Test
    void calcularMontoTotalPagarEnFinDeSemana() {

        //Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 4, 11, 0);

        //Deberían ser 2 bloques y con descuento de finde semana
        int resultadoEsperado = 2* TipoVehiculo.AUTO.getTarifa();
        resultadoEsperado -= (int) (resultadoEsperado* 0.1F);

        Ticket ticket = estacionamiento.getTicket(0);
        //When


        int resultado = estacionamiento.CalcularMontoTotalPagar(ticket,horaSalida);
        //Then

        assertEquals(resultadoEsperado,resultado);
    }

    @Test
    void calcularMontoTotalPagarConFechaInvalida() {

        //Given
        LocalDateTime horaSalida = LocalDateTime.of(2025, 1, 1, 2, 0);

        Ticket ticket = estacionamiento.getTicket(2);
        //When

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            estacionamiento.CalcularMontoTotalPagar(ticket, horaSalida);
        });
    }
    @Test
    void getListaDeTickets() {

        //Given, hay 3 elementos los cuales son abiertos y 1 es cerrado


        int listaLargoAbiertos = 3;
        int listaLargoCerrados = 2;

        //When

        List<Ticket> ticketsAbiertos = estacionamiento.getListaDeTickets(false);
        List<Ticket> ticketsCerrados = estacionamiento.getListaDeTickets(true);
        //Then

        assertEquals(listaLargoAbiertos, ticketsAbiertos.size());
        assertEquals(listaLargoCerrados, ticketsCerrados.size());

        //para ver que hagan "match" los estados con cerrado y abierto
        assertTrue(ticketsAbiertos.stream().allMatch(t -> t.getEstado().equals("Abierto")));
        assertTrue(ticketsCerrados.stream().allMatch(t -> t.getEstado().equals("Cerrado")));
    }

    @Test
    void totalRecaudado() {

        //Given

        int totalRecaudado = 2000;
        //When

        int resultado = estacionamiento.totalRecaudado(LocalDateTime.of(2025, 1, 1, 23, 0));
        //Then

        assertEquals(totalRecaudado,resultado);
    }
}