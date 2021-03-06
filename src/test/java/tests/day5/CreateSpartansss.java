package tests.day5;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.Spartan;

import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateSpartansss {

    @BeforeAll
    public static void setUp() {
        baseURI = "http://3.92.227.9:8000/";
    }

    /**
     * create a new spartan  object
     * by passing valid gender, name, phone from file
     * verify response body
     *  success = A Spartan is Born!
     */
    @Test
    public void postANewSpartannn(){
        /// create the File that we want to send
        File jsonFile = new File("src/test/resources/spartan.json");

        given().log().all()
                .contentType(ContentType.JSON).
                // include the file in the response
                body(jsonFile).
            when().post("/api/spartans").
                prettyPeek().
            then().statusCode(201).
                    body("success", equalTo("A Spartan is Born!"));
    }

    /**
     * create a new spartan  object
     * by passing valid gender, name, phone from file
     * verify response body
     *  success = A Spartan is Born!
     */
    @Test
    public void postANewSpartannnn(){
        Map<String, String> spartan = new HashMap<>();
        spartan.put("gender", "Female");
        spartan.put("name", "Rosa");
        spartan.put("phone", "202323232232");

        given().log().all().
                contentType(ContentType.JSON).
                body(spartan).
            when().post("/api/spartans").
                    prettyPeek().
            then().statusCode(201);

    }

    /*
    Create a new Spartan
   verify code 201
    get the id from the response
    get the spartan info using the id
    verify same spartan if is returned
     */
    @Test
    public void postANewSpartannnnn(){
        String name = "Tabriz";
        String gender = "Male";
        int phone = 2022022022;

        Map<String, Object> spartan = new HashMap<>();
        spartan.put("name", name);
        spartan.put("gender", gender);
        spartan.put("phone", phone);

        Response response = given().log().everything().
                                contentType(ContentType.JSON).
                                 body(spartan).
                             when().post("/api/spartans");
        response.prettyPeek().
                then().statusCode(201);
        int id = response.path("data.id");
        System.out.println(id);

        given().pathParam("id", id)
                .when().get("/api/spartans/{id}").
                    prettyPeek().
                then().statusCode(200).
                    body("name", equalTo(name)).
                    body("gender", equalTo(gender)).
                    body("phone", equalTo(phone));
    }

    /**
     * Create a new spartan
     * verify status code 201
     */
    @Test
    public void postANewSpartannnnnn(){
        Spartan spartan = new Spartan("Male", "Nerrro Prime", 2022020022);

        spartan.toString();
        given().log().everything().
                contentType(ContentType.JSON).
                body(spartan).
        when().post("/api/spartans").
                prettyPeek().
        then().statusCode(201);
    }


    /**
     * get single spartan using id
     * verify info
     */
    @Test
    public void getANewSpartannnnnn(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 482).
                        when().get("/api/spartans/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
        // get the json body and convert into Spartan object
        Spartan spartan = response.as(Spartan.class);

        System.out.println(spartan.toString());

    }

    /**
     * Create a new spartan by sending a request the spartan API
     * pass the given spartan parameter as the request body.
     * return the response object. response must contain json payload.
     * @param spartan
     * @return response
     */
    public Response createSpartan(Spartan spartan){
        Response response = given().contentType(ContentType.JSON).and().accept(ContentType.JSON)
                .body(spartan).post("api/spartans");
        response.then().statusCode(201);
        return response.jsonPath().get();
    }

    public static void main(String[] args) {
        CreateSpartansss obj = new CreateSpartansss();
        Spartan s = new Spartan("Male","UUUvasia",22345032);
        System.out.println(obj.createSpartan(s));

    }
}



