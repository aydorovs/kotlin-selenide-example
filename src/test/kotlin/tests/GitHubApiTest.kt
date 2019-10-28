package tests

import io.restassured.RestAssured
import org.junit.jupiter.api.*
import util.BaseFunc
import kotlin.test.assertEquals

@DisplayName("Проверка api GitHub")
@Tags(Tag("test"), Tag("test_api"))
class GitHubApiTest {

    @BeforeEach
    fun startTest() {
        BaseFunc.setUpRemote()
    }

    @AfterEach
    fun endTest() {
        BaseFunc.tearDown()
    }

    @Test
    @DisplayName("Запрос /projects с неверными параметрами")
    fun `Check status code 415 and valid values for message and documentation_url`() {
        val request = RestAssured.given().get("https://api.github.com/projects/123")

        assertEquals(415, request.statusCode)
        assertEquals(
            "If you would like to help us test the Projects API during its preview period, you must specify a custom media type in the 'Accept' header. Please see the docs for full details.",
            request.jsonPath().get("message")
        )
        assertEquals(
            "https://developer.github.com/v3/projects/#get-a-project",
            request.jsonPath().get("documentation_url")
        )
    }


    @Test
    @DisplayName("Запрос /users")
    fun `Check status code and count users`() {
        val request = RestAssured.given().get("https://api.github.com/users")

        assertEquals(200, request.statusCode)
        assertEquals(30, request.jsonPath().getList<String>("login").size)
    }
}