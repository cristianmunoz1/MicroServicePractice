package com.example.demo.controller;

import com.example.demo.dto.SpaceShip;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@RestController
public class MicroserviceController {

    //Siempre tiene un logger, es muy importante el logger siempre para uno mantener la información.
    private static final Logger LOGGER= LoggerFactory.getLogger(MicroserviceController.class);

    //Vamos a crear nuestro primer endpoint

    @GetMapping("/spaceship")
    public String spaceShip(){
        LOGGER.info("Me acaban de llevar para recibir la nave");
        return "Gracias por la nave";
    }

    @RequestMapping("/greetings/{name}")
    public String greetings(@PathVariable("name") String name){
    LOGGER.info("Me acaban de pedir que salude a la persona {}", name);
    return String.format("¡¡¡Bienvenido %s!!!", name);
    }

    @GetMapping("/covid/{state}")
    public String getCovidStateData(@PathVariable("state") String state) throws IOException {
        LOGGER.info("Me acaban de pedir información del estado {}", state);

        String apiPage = String.format("https://api.covidtracking.com/v1/states/%s/current.json", state);

        LOGGER.info("API page to call: ", apiPage);

        URL url = new URL(apiPage);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(responseStream);

        LOGGER.info("Response JSON: " + root.toString());

        return root.toString();
    }

    @PostMapping("/submitSpaceship")
    public SpaceShip receiveSpaceship(@RequestBody SpaceShip spaceShip){

        LOGGER.info("Recibida la nave: "+ spaceShip);
        LOGGER.info("Los ocupantes de la nave son: ");
        spaceShip.getOccupants().forEach(t -> {
            LOGGER.info("Nombre: " + t.getName() + ", edad: " + t.getAge());
        });

        spaceShip.setMessage("He sido recibida y enviada de vuelta");

        return spaceShip;
    }
}
