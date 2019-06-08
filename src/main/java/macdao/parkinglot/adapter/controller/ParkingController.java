package macdao.parkinglot.adapter.controller;

import macdao.parkinglot.application.ParkCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-lot")
public class ParkingController {

    @PostMapping("/park")
    public void park(ParkCommand parkCommand) {
    }
}
