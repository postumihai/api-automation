import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTests {

    @BeforeAll
    static void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
//https://jsonplaceholder.typicode.com/posts
    @Test @Order(1) @Tag("positive")
    @DisplayName("GET /posts")
    void getAllPosts(){
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(100))
                .body("[0].id", equalTo(1));
    }

    @Test @Order(2) @Tag("positive")
    @DisplayName("GET /posts/1")
    void getPostById(){
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(1))
                .body("userId", notNullValue())
                .body("title",not(emptyOrNullString()))
                .body("body",not(emptyOrNullString()));
    }

    @Test @Order(3) @Tag("positive")
    @DisplayName("GET /posts/1/comments")
    void getCommentsForPost(){
        given()
                .when()
                .get("/posts/1/comments")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].postId", equalTo(1))
                .body("[0].email", containsString("@"));

    }
    @Test @Order(4) @Tag("positive")
    @DisplayName("GET /comments?postId=1")
    void getComments(){
        given()
                .queryParam("postId",1)
                .when()
                .get("comments")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(1)));
    }

    @Test @Order(5) @Tag("positive")
    @DisplayName("POST /posts")
    void createPost(){
        String body = """
                {
                    "title": "foo",
                    "body": "bar",
                    "userId": 1
                  }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(101))
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId",equalTo(1));
    }
    @Test @Order(6) @Tag("positive")
    @DisplayName("PUT /posts/1")
    void createPut(){
        String body = """
                {
                    "id":1,
                    "title": "updated title",
                    "body": "updated body",
                    "userId": 1
                  }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("title", equalTo("updated title"))
                .body("body", equalTo("updated body"))
                .body("userId",equalTo(1));
    }

    @Test @Order(7) @Tag("positive")
    @DisplayName("PATCH /posts/1")
    void createPatch() {
        String body = """
                {
                    "title": "PATCHED title"
                  }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("title", equalTo("PATCHED title"));
    }

    @Test @Order(8) @Tag("positive")
    @DisplayName("DELETE /posts/1")
    void createDelete() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test @Order(9) @Tag("negative")
    @DisplayName("GET /posts")
    void getWrongPosts(){
        given()
                .when()
                .get("/postssss")
                .then()
                .statusCode(404);
    }

    @Test @Order(10) @Tag("negative")
    @DisplayName("GET /posts")
    void getWrongPosts2(){
        given()
                .when()
                .get("/postssss")
                .then()
                .statusCode(404);
    }

    @Test @Order(11) @Tag("negative")
    @DisplayName("GET /posts")
    void getWrongPosts3(){
        given()
                .when()
                .get("/postssss")
                .then()
                .statusCode(404);
    }

    @Test @Order(12) @Tag("negative")
    @DisplayName("GET /posts")
    void getWrongPosts6(){
        given()
                .when()
                .get("/postssss")
                .then()
                .statusCode(404);

    }
}
