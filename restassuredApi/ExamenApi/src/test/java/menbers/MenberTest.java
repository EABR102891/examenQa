package menbers;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testng.AssertJUnit;
import org.testng.Assert;

import java.io.File;
import java.util.function.Supplier;

import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.assertEquals;

public class MenberTest {



    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:5002/api";
    }
    private Response safeRequest(Supplier<Response> request) {
        try { return request.get(); } catch (Exception e) {
            System.err.println("Error during request: " + e.getMessage());
            return new ResponseBuilder().setStatusCode(500).build();
        }
    }

    @Test
    public void testGetSingleMember() {
        Response response = safeRequest(() -> get("/members/1"));
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test public void testGetSingleAuthor() {
        Response response = safeRequest(() -> get("/authors/2"));
        assertEquals(200, response.getStatusCode());
    }

    @Test public void testCreateMember() {
        String json = "{\n" +
                "        \"id\":20,\n" +
                "        \"name\": \"Ryan\",\n" +
                "        \"gender\":\"Male\"\n" +
                "}";
        Response response = safeRequest(() -> given()
                .header("Content-Type", "application/json")
                .body(json)
                .post("/members"));
        assertEquals(200, response.getStatusCode());
    }

    @Test public void testUpdateMember() {
        String json = "{ \"name\": \"Jane Doe\", \"role\": \"admin\" }";
        Response response = safeRequest(() -> given()
                .header("Content-Type", "application/json")
                .body(json)
                .put("/members/4"));
        assertEquals(200, response.getStatusCode());
    }

    @Test public void testPatchMember() {
        String json = "{ \"role\": \"super-admin\" }";
        Response response = safeRequest(() -> given()
                .header("Content-Type", "application/json")
                .body(json) .patch("/members/4"));
        assertEquals(200, response.getStatusCode());
    }

    @Test public void testDeleteMember() {
        Response response = safeRequest(() -> delete("/members/40"));
        assertEquals(200, response.getStatusCode());
    }

    @Test public void testUploadFile() {
        File file = new File("path/to/your/image.png");
        Response response = safeRequest(() -> given()
                .multiPart("file", file)
                .post("/upload"));
        assertEquals(201, response.getStatusCode());
    }

    @Test public void testDownloadFile() {
        Response response = safeRequest(() -> get("/download?name=Yey.jpg"));
        assertEquals(200, response.getStatusCode()); // Validar la integridad del archivo si es necesario

    }
}

