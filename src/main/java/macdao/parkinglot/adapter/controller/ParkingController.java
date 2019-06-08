package macdao.parkinglot.adapter.controller;

import macdao.parkinglot.application.ParkCommand;
import macdao.parkinglot.application.ParkingApplicationService;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.CarNumber;
import macdao.parkinglot.domain.model.TicketId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-lot")
public class ParkingController {
    private final ParkingApplicationService parkingApplicationService;

    public ParkingController(ParkingApplicationService parkingApplicationService) {
        this.parkingApplicationService = parkingApplicationService;
    }

    @PostMapping("/park")
    public TicketId park(@RequestBody ParkCommand parkCommand) {
        return parkingApplicationService
                .park(new Car(new CarNumber(parkCommand.getCarNumber())))
                .getId();
    }
}
