package macdao.parkinglot.adapter.controller;

import lombok.Getter;
import macdao.parkinglot.application.ParkCommand;
import macdao.parkinglot.application.ParkingApplicationService;
import macdao.parkinglot.application.PickApplicationService;
import macdao.parkinglot.application.PickCommand;
import macdao.parkinglot.domain.model.parkinglot.Car;
import macdao.parkinglot.domain.model.parkinglot.Ticket;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-lot")
public class ParkingController {
    private final ParkingApplicationService parkingApplicationService;
    private final PickApplicationService pickApplicationService;

    public ParkingController(ParkingApplicationService parkingApplicationService, PickApplicationService pickApplicationService) {
        this.parkingApplicationService = parkingApplicationService;
        this.pickApplicationService = pickApplicationService;
    }

    @PostMapping("/park")
    public TicketView park(@RequestBody ParkCommand parkCommand) {
        final Ticket ticket = parkingApplicationService.park(parkCommand);
        return new TicketView(ticket);
    }

    @PostMapping("/pick")
    public CarView pick(@RequestBody PickCommand pickCommand) {
        final Car car = pickApplicationService.pick(pickCommand);
        return new CarView(car);
    }

    @Getter
    public static class CarView {
        private final String carNumber;

        CarView(Car car) {
            this.carNumber = car.getCarNumber().getValue();
        }
    }

    @Getter
    public static class TicketView {
        private final String ticketId;
        private final String carNumber;
        private final String parkingLotId;

        TicketView(Ticket ticket) {
            this.ticketId = ticket.getId().getValue();
            this.carNumber = ticket.getCarNumber().getValue();
            this.parkingLotId = ticket.getParkingLotId().getValue();
        }
    }
}
