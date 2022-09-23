package APIHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

//HTTP Helper - Авторизация пользователя
public class HTTPHelper extends TestNGCitrusSupport {
    private TestContext context;
    private String email = "eve.holt@reqres.in";
    private String password = "cityslicka";
    private String token = "QpwL5tke4Pnpja7X4";

    @Test(description = "Авторизация пользователя", enabled = true)
    @CitrusTest
    public void createUserTest() {
        this.context = citrus.getCitrusContext().createTestContext();

        $(http()
                .client("restClientReqres")
                .send()
                .post("login")
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"email\": \"" + email + "\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        "}")
        );

        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .validate(jsonPath()
                        .expression("$.token",token))   //валидация отдельных полей: проверяем совпадение полей
                .extract(fromBody()
                        .expression("$.token","token"))  //извлекаем token
        );

        $(echo("token = ${token}")); //выводим token в консоль
        context.setVariable("token","${token}"); //сетим token в контекст
    }
}
