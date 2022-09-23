package mocks;

/* Из лекции по citrus:
Заглушка restServer инициализируется при старте теста и ждет нас на порту 5555.
В citrus заглушка делается под каждый тест, поднимается на портах, тест заканчивается, заглушки закрываются.
 */

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import pojo.http.UserToCourse;

import java.util.ArrayList;
import java.util.List;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class testMockGetListUsers extends TestNGCitrusSupport {
    public TestContext context;


    @Test(description = "Получение списка пользователей", enabled = true)
    @CitrusTest
    public void mockGetListUsers(){
        this.context = citrus.getCitrusContext().createTestContext();

        //При помощи http-клиента отправляем get-запрос в нашу заглушку restServer
        $(http()
                .client("restClient")
                .send()
                .get("/user/get/all")
                .fork(true) //!!! Добавлен асинхрон для работы с localhost: дожидаться ответа после взаимодействия с сервером
        );

        //Шаги взаимодействия с заглушкой.
        //1. Сначала принимаем get-запрос от клиента
        $(http().server("restServer")
                .receive()
                .get());
        //2. Отправляем хардкод-ответ по запрошенным данным в соответствии с контрактом
        $(http().server("restServer")
                .send()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body("[\n" +
                        "  {\n" +
                        "    \"name\": \"Test user\",\n" +
                        "    \"course\": \"QA\",\n" +
                        "    \"email\": \"test@test.test\",\n" +
                        "    \"age\": 23\n" +
                        "  }\n" +
                        "]"));

        //Http-клиент получает ответ и валидирует его (проверка по схеме)
        $(http()
                .client("restClient")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getJsonData(),"objectMapper"))
        );
    }

    public List<UserToCourse> getJsonData() {
        List<UserToCourse> userToCourseList = new ArrayList<>();

        UserToCourse userToCourse = new UserToCourse();
        userToCourse.setName("Test user");
        userToCourse.setCourse("QA");
        userToCourse.setEmail("test@test.test");
        userToCourse.setAge(23);
        userToCourseList.add(userToCourse);

        return userToCourseList;
    }
}
