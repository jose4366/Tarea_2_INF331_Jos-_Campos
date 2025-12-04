package org.example;

import org.example.Model.Ticket;
import org.example.Model.TipoVehiculo;
import org.example.Service.Estacionamiento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;
        int idTickets = 0;
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Estacionamiento estacionamiento = new Estacionamiento(15000, 0.10F);

        do {
            System.out.println("   SISTEMA DE ESTACIONAMIENTO  ");
            System.out.println("==================================");
            System.out.println("1. Registrar entrada de vehículo");
            System.out.println("2. Registrar salida de vehículo (calcular cobro)");
            System.out.println("3. Listar tickets abiertos");
            System.out.println("4. Listar tickets cerrados");
            System.out.println("5. Mostrar detalle de un ticket");
            System.out.println("6. Mostrar total recaudado del día");
            System.out.println("7. Salir");
            System.out.println("==================================");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();


            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese patente: ");
                    String patente = sc.nextLine();

                    System.out.println("Seleccione tipo de vehículo:");
                    System.out.println("1. AUTO (800)");
                    System.out.println("2. MOTO (500)");
                    System.out.println("3. CAMIONETA (1000)");
                    System.out.print("Opción: ");

                    int opcionVehiculo = sc.nextInt();

                    TipoVehiculo tipoVehiculo = null;

                    switch (opcionVehiculo) {
                        case 1 -> tipoVehiculo = TipoVehiculo.AUTO;
                        case 2 -> tipoVehiculo = TipoVehiculo.MOTO;
                        case 3 -> tipoVehiculo = TipoVehiculo.CAMIONETA;
                        default -> {
                            System.out.println("Opción inválida, asignando automaticamente AUTO");
                            tipoVehiculo = TipoVehiculo.AUTO;
                        }
                    }

                    estacionamiento.RegistrarVehiculo(idTickets,patente,tipoVehiculo, LocalDateTime.now());
                    idTickets += 1;
                }

                case 2 -> {
                    System.out.print("Ingrese el id del ticket: ");
                    int ticketId = sc.nextInt();
                    try {
                        estacionamiento.RegistrarSalidaVehiculo(ticketId, LocalDateTime.now());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    List<Ticket> tickets = estacionamiento.getListaDeTickets(false);
                    for (Ticket ticket: tickets){
                        System.out.println("-----------------------------------------------------------------------");
                        System.out.println("Patente del vehiculo: " + ticket.getVehiculo().getPatente() + " \n Ticket id: " + ticket.getIdTicket() + " \n Estacionado desde: " + ticket.getFechaHoraEntrada().format(formatoFechaHora) + "\n Estado: " + ticket.getEstado());
                        System.out.println("-----------------------------------------------------------------------");
                    }
                }
                case 4 -> {
                        List<Ticket> tickets = estacionamiento.getListaDeTickets(true);
                        for (Ticket ticket: tickets){
                            System.out.println("-----------------------------------------------------------------------");
                            System.out.println("Patente del vehiculo: " + ticket.getVehiculo().getPatente() + "\n Ticket id: " + ticket.getIdTicket() + "\n Estacionado desde: " + ticket.getFechaHoraEntrada().format(formatoFechaHora) + "\n Hasta las : " + ticket.getFechaHoraSalida().format(formatoFechaHora));
                            System.out.println("-----------------------------------------------------------------------");
                        }
                    }
                case 5 -> {
                    System.out.print("Ingrese el id del ticket: ");
                    int ticketId = sc.nextInt();
                    try {
                        Ticket ticket = estacionamiento.getTicket(ticketId);
                        System.out.print("Detalles del ticket: \n"+ticket);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 6 -> System.out.println(estacionamiento.totalRecaudado(LocalDateTime.now()));
                case 7 -> System.out.println("Saliendo ");
                default -> System.out.println("Opción inválida, intente nuevamente.");
            }

            System.out.println();
        } while (opcion != 7);

        sc.close();

    }
}