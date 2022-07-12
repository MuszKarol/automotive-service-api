package pl.KarolMusz.automotiveserviceapi.controller;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.KarolMusz.automotiveserviceapi.model.CarPart;
import pl.KarolMusz.automotiveserviceapi.repository.CarPartRepository;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarPartRepository carPartRepository;

    private CarPart carPart;

    @BeforeEach
    void setUp() {
        carPart = CarPart.builder()
                .code("1")
                .name("test")
                .price("test")
                .quantity(1)
                .build();
    }

    @AfterEach
    void tearDown() {
        if (carPartRepository.getCarPartByCode("1").isPresent()) {
            carPartRepository.delete(carPart);
        }
    }

    @Test
    @Order(1)
    @WithMockUser(authorities = "ADMIN")
    void getParts() throws Exception {
        Mockito.when(carPartRepository.findAll()).thenReturn(List.of(carPart));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/order/parts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Order(2)
    @WithMockUser(authorities = "ADMIN")
    void savePart() throws Exception {
        JsonObject car = new JsonObject();

        car.addProperty("code", carPart.getCode());
        car.addProperty("name", carPart.getName());
        car.addProperty("price", carPart.getPrice());
        car.addProperty("quantity", carPart.getQuantity());

        Mockito.when(carPartRepository.save(carPart)).thenReturn(carPart);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/order/parts")
                                .content(car.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Order(3)
    @WithMockUser(authorities = "ADMIN")
    void deletePart() throws Exception {

        Mockito.when(carPartRepository.getCarPartByCode(carPart.getCode())).thenReturn(Optional.ofNullable(carPart));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/order/parts/" + carPart.getCode())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}