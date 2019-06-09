package macdao.parkinglot.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.ParkingLotId;
import macdao.parkinglot.domain.model.ParkingRobot;
import macdao.parkinglot.domain.model.SimpleParkingPolicy;
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

import java.util.Collections;
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
    private ParkingLot parkingLot;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id-1");
        parkingLot = new ParkingLot(parkingLotId, 10);
        parkingLotRepository.save(parkingLot);
        parkingRobotRepository.save(new ParkingRobot(new SimpleParkingPolicy(), parkingLotId));
    }

    @Test
    public void test_park() throws Exception {
        final String carNumber = "car-number-1";
        this.mockMvc.perform(post("/parking-lot/park")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.singletonMap("carNumber", carNumber))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(parkingLot.getSpace()).isEqualTo(9);
    }

    @Test
    public void test_pick() throws Exception {
        final String carNumber = "car-number-1";
        final MvcResult result = this.mockMvc.perform(post("/parking-lot/park")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.singletonMap("carNumber", carNumber))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        final Map resultMap = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        final String ticketId = (String) resultMap.get("ticketId");
        final String parkingLotId = (String) resultMap.get("parkingLotId");

        final MvcResult result2 = this.mockMvc.perform(post("/parking-lot/pick")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("parkingLotId", parkingLotId, "ticketId", ticketId))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(parkingLot.getSpace()).isEqualTo(10);
        final String carNumberReturned = (String) objectMapper.readValue(result2.getResponse().getContentAsString(), Map.class).get("carNumber");
        assertThat(carNumberReturned).isEqualTo(carNumber);
    }
}