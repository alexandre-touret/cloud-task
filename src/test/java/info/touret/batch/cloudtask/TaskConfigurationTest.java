package info.touret.batch.cloudtask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
class TaskConfigurationTest {

    @Autowired
    private CommandLineRunner commandLineRunner;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.reset();
        mapper = new ObjectMapper();
    }

    @Test
    void shoud_create_a_commandlinerunner_succesfully() throws JsonProcessingException {
        NationalizeResponseDTO nationalizeResponseDTO = new NationalizeResponseDTO();
        nationalizeResponseDTO.setName("name");
        CountryDTO countryDTO = new CountryDTO("FR", "0.10");
        CountryDTO countryDTO2 = new CountryDTO("EN", "0.20");
        nationalizeResponseDTO.setCountries(List.of(countryDTO, countryDTO2));
        mockServer.expect(once(), requestTo(containsString("api.nationalize.io"))).andExpect(method(GET)).andRespond(withStatus(OK)
                .contentType(APPLICATION_JSON)
                .body(mapper.writeValueAsString(nationalizeResponseDTO)));
        assertNotNull(commandLineRunner);
    }
}
