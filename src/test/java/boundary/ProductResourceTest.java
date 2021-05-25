package boundary;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import entity.Product;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ProductResourceTest {

    static final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig());

    @Test
    @Order(1)
    public void testCreateProduct() {
        Product product = Product
                .builder()
                .title("product_title")
                .description("product_description")
                .build();
        given()
                .when()
                .header("Content-Type", "application/json")
                .body(jsonb.toJson(product))
                .post("/v1/products")
                .then()
                .statusCode(201);
    }


    @Test
    @Order(2)
    public void testGetAll() {
        given()
                .when()
                .header("Content-Type", "application/json")
                .get("/v1/products")
                .then()
                .statusCode(200);
    }
}