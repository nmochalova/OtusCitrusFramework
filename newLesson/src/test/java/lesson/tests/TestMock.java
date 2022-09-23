package lesson.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import lesson.pojo.DataJson;
import lesson.pojo.Support;
import lesson.pojo.User;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
/*
Заглушка testServer инициализируется при старте теста и ждет нас на порту 5555. Все параллельрные теста на этот порт зафейлятся.
В citrus заглушка делается под каждый тест, поднимается на портах, тест заканчивается, заглушки закрываются.
Но существует citrus simulator, который умеет крутиться как отдельный jar-ник. В текущем курсе это не рассматривается.
 */

public class TestMock extends TestNGCitrusSupport {
    public TestContext context;

    @Test(description = "Mock-ируем reqres-сервис: получение информации о пользователе", enabled = true)
    @CitrusTest
    public void mockTestGetUser(){
        this.context = citrus.getCitrusContext().createTestContext();

        //Запрос
        $(http()
                .client("restClient")
                .send()
                .get("users/"+context.getVariable("userId"))
                .fork(true) //!!! Добавлен асинхрон для работы с localhost: дожидаться ответа после взаимодействия с сервером
        );

        //Шаги взаимодействия с заглушкой.
        //1. Сначала принимаем get-запрос от клиента
        $(http().server("restServer")
                .receive()
                .get()
        );
        //2. Отправляем хардкод-ответ по запрошенным данным в соответсвии с контрактом
        $(http().server("restServer")
                .send()
                .response()
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": ${userId},\n" +
                        "        \"email\": \"janet.weaver@reqres.in\",\n" +
                        "        \"first_name\": \"Janet\",\n" +
                        "        \"last_name\": \"Weaver\",\n" +
                        "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "    },\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}")
        );

        //Ответ
        $(http()
                .client("restClient")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getJsonData(),"objectMapper")) //метод из citrus-validation-json
        );
    }

    public User getJsonData() {
        User user = new User();

        DataJson data = new DataJson();
        data.setId(Integer.valueOf(context.getVariable("userId")));
        data.setEmail("janet.weaver@reqres.in");
        data.setFirstName("Janet");
        data.setLastName("Weaver");
        data.setAvatarURL("https://reqres.in/img/faces/2-image.jpg");
        user.setData(data);

        Support support = new Support();
        support.setUrl("https://reqres.in/#support-heading");
        support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");
        user.setSupport(support);

        return user;
    }
}
