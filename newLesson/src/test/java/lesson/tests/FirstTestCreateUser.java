package lesson.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import lesson.pojo.CreateUserResponse;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.JsonSupport.json;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstTestCreateUser extends TestNGCitrusSupport {
    public TestContext context;
    String name = "Nick";
    String job = "Teacher";

    @Test(description = "Создание пользователя", enabled = true)
    @CitrusTest
    public void createUserTest() {
        this.context = citrus.getCitrusContext().createTestContext();

        $(http()
                .client("restClientReqres")
                .send()
                .post("users")
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}")
        );

        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getResponseData(name,job), "objectMapper")) //проверка json с json - полностью
                .validate(json()
                        .ignore("$.createdAt"))          //валидация отдельных полей: игнорирование поля createdAt
                .validate(jsonPath()
                        .expression("$.name",name)   //валидация отдельных полей: проверяем совпадение полей
                        .expression("$.job",job))
                .extract(fromBody()                          //извлечение значения полей из json в отдельные переменные
                        .expression("$.id","currendId")
                        .expression("$.createdAt","createdAt"))
        );

        $(echo("currendId = ${currendId} and createdAt = ${createdAt}"));
    }

    private CreateUserResponse getResponseData(String name, String job) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(name);
        createUserResponse.setJob(job);
        createUserResponse.setId("@isNumber()@"); //citrus validation matcher - проверка, что id есть number
        createUserResponse.setCreatedAt("unknown");

        return createUserResponse;
    }
}
