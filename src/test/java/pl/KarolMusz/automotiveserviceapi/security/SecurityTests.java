package pl.KarolMusz.automotiveserviceapi.security;

import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void createClientTest() throws Exception {
        JsonObject userJson = new JsonObject();
        JsonObject addressJson = new JsonObject();
        JsonObject contactDetails = new JsonObject();

        addressJson.addProperty("buildingNumber", "test");
        addressJson.addProperty("street", "test");
        addressJson.addProperty("postalCode", "test");
        addressJson.addProperty("city", "test");
        addressJson.addProperty("country", "test");

        contactDetails.addProperty("phoneNumber", "test");
        contactDetails.addProperty("secondEmail", "test");

        userJson.addProperty("email", "client@client.client");
        userJson.addProperty("password", "test");
        userJson.addProperty("name", "test");
        userJson.addProperty("surname", "test");
        userJson.addProperty("role", "CLIENT");
        userJson.add("address", addressJson);
        userJson.add("contactDetails", contactDetails);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/new/client")
                                .content(userJson.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @Order(2)
    public void createAdminTest() throws Exception {
        JsonObject userJson = new JsonObject();
        JsonObject addressJson = new JsonObject();
        JsonObject contactDetails = new JsonObject();

        addressJson.addProperty("buildingNumber", "test");
        addressJson.addProperty("street", "test");
        addressJson.addProperty("postalCode", "test");
        addressJson.addProperty("city", "test");
        addressJson.addProperty("country", "test");

        contactDetails.addProperty("phoneNumber", "test");
        contactDetails.addProperty("secondEmail", "test");

        userJson.addProperty("email", "admin@admin.admin");
        userJson.addProperty("password", "test");
        userJson.addProperty("name", "test");
        userJson.addProperty("surname", "test");
        userJson.addProperty("role", "ADMIN");
        userJson.add("address", addressJson);
        userJson.add("contactDetails", contactDetails);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/new/admin")
                                .content(userJson.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Order(3)
    @WithMockUser(authorities = "CLIENT")
    public void setServiceDetailsTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/about/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @Order(4)
    @WithMockUser(authorities = {"ADMIN"})
    public void getAcceptedVisitsTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/visits/accepted")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Order(5)
    @WithMockUser(authorities = {"ADMIN"})
    public void getClientVisitsTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/visits/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @Order(6)
    @WithMockUser(authorities = {"ADMIN"})
    public void addCarTest() throws Exception {
        JsonObject car = new JsonObject();

        car.addProperty("vinCode", "test");
        car.addProperty("licensePlate", "test");
        car.addProperty("carRegistrationDate", "2022-01-01");
        car.addProperty("modelName", "test");
        car.addProperty("brandName", "test");
        car.addProperty("version", "test");
        car.addProperty("engine", "test");


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/vehicle")
                                .content(car.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @Order(7)
    @WithMockUser(authorities = {"ADMIN"})
    public void addCarPartTest() throws Exception {
        JsonObject car = new JsonObject();

        car.addProperty("code", "test");
        car.addProperty("name", "test");
        car.addProperty("price", "test");
        car.addProperty("quantity", 1);


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/order/parts")
                                .content(car.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
