package macdao.parkinglot.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.ParkingLotId;
import macdao.parkinglot.domain.model.SimpleParkingRobot;
import macdao.parkinglot.domain.model.TicketId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private ParkingRobotRepository parkingRobotRepository;
    @Autowired
    private TicketRepository ticketRepository;
    private ParkingLot parkingLot;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        final ParkingLotId parkingLotId = new ParkingLotId();
        parkingLot = new ParkingLot(parkingLotId, 10);
        parkingLotRepository.save(parkingLot);
        parkingRobotRepository.save(new SimpleParkingRobot(parkingLotId));
    }

    @Test
    public void test_park() throws Exception {
        final MvcResult result = this.mockMvc.perform(post("/parking-lot/park")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"carNumber\":\"car-number-1\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(parkingLot.getSpace()).isEqualTo(9);
        final String ticketId = (String) objectMapper.readValue(result.getResponse().getContentAsString(), Map.class).get("value");
        assertThat(ticketRepository.findById(new TicketId(ticketId))).hasValueSatisfying(
                t -> {
                    assertThat(t.getId().getValue()).isEqualTo(ticketId);
                    assertThat(t.getCarNumber().getValue()).isEqualTo("car-number-1");
                    assertThat(t.getParkingLotId()).isEqualTo(parkingLot.getId());
                }
        );
    }

    @Test
    public void test_pick() {

    }
}