package org.example.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;



class TicketTest {

    private Ticket ticket;
    @BeforeEach
    void setUp() {
        Vehiculo vehiculo = new Vehiculo(TipoVehiculo.CAMIONETA,"ABCD12");
        LocalDateTime fechaEntrada = LocalDateTime.of(2025,1,1,12, 0);
        ticket = new Ticket(0,vehiculo,fechaEntrada);
    }

    @Test
    //Validar la fecha correcta y monto > 0
    void validarFechaYMontoPlausible() {

        //Given
        LocalDateTime fechaSalida = LocalDateTime.of(2025,1,1,23, 0);
        int montoCobrado = 50;

        //When

        boolean resultado =  ticket.ValidarFechaYMontoPlausible(montoCobrado,fechaSalida);

        //Then

        assertTrue(resultado);

    }


    @Test
        //Validar la fecha incorrecta y monto incorrecto
    void validarFechaYMontoPlausibleIncorreto() {

        //Given
        LocalDateTime fechaSalidaCorrecta = LocalDateTime.of(2025,1,1,23, 0);
        LocalDateTime fechaSalidaIncorrecta = LocalDateTime.of(2024,1,1,23, 0);

        int montoCobradoCorrecto = 0;
        int montoCobradoIncorrecto = -1;

        //When

        boolean resultado =  ticket.ValidarFechaYMontoPlausible(montoCobradoCorrecto, fechaSalidaIncorrecta);
        boolean resultado2 =  ticket.ValidarFechaYMontoPlausible(montoCobradoIncorrecto, fechaSalidaCorrecta);
        boolean resultado3 =  ticket.ValidarFechaYMontoPlausible(montoCobradoIncorrecto, fechaSalidaIncorrecta);

        //Then

        assertFalse(resultado);
        assertFalse(resultado2);
        assertFalse(resultado3);



    }

    @Test
    void cerrrarTicketCorrectamente() {

        //Given

        LocalDateTime fechaSalida = LocalDateTime.of(2025,1,1,23, 0);
        int montoCobrado = 50;


        //WHen

        ticket.CerrrarTicket(montoCobrado,fechaSalida);
        //Then

        assertEquals(50,ticket.getMontoCobrado());
        assertEquals(LocalDateTime.of(2025,1,1,23, 0),ticket.getFechaHoraSalida());
        assertEquals("Cerrado",ticket.getEstado());
    }

    @Test
    void cerrrarTicketIncorrectamente() {

        //Given

        LocalDateTime fechaSalida = LocalDateTime.of(2025,1,1,23, 0);
        int montoCobrado = -50;


        //WHen



        //Then como aqui lanza error ya que cree así la funcion

        assertThrows(IllegalArgumentException.class, () -> {
            ticket.CerrrarTicket(montoCobrado, fechaSalida);
        });

    }


    //no se mostro el assert throws en los videos pero lo busque ya que considere oportuno tener que ver las excepciones que puse en el codigo


}