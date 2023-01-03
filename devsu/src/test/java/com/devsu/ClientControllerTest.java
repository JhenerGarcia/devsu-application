package com.devsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.devsu.configuration.ApiException;
import com.devsu.controller.ClientController;
import com.devsu.entity.Client;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DevsuApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
@TestMethodOrder(OrderAnnotation.class)
class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @Test
    @Order(1)
    public void test_createClient() throws ApiException {
        Client client = new Client();
        client.setAddress("Guatemala City");
        client.setAge(25);
        client.setGender("MALE");
        client.setName("Jhener Garcia");
        client.setPassword("password");
        client.setPersonalId("98464231567");
        client.setPhone("+50259448648");
        client.setState(true);

        clientController.createClient(client);
        Assertions.assertEquals(4L, client.getId());
    }
    
    @Test
    @Order(2)
    public void test_createExistingClient() throws ApiException {
        Client client = new Client();
        client.setAddress("Guatemala City");
        client.setAge(25);
        client.setGender("MALE");
        client.setName("Jhener Garcia");
        client.setPassword("password");
        client.setPersonalId("98464231567");
        client.setPhone("+50259448648");
        client.setState(true);

        
        ApiException exceptionThrown = Assertions.assertThrows(ApiException.class, () -> {
            clientController.createClient(client);
        });

        Assertions.assertEquals("Client with personal id 98464231567 already exists.", exceptionThrown.getApiResult().getMessage());
    }

}
