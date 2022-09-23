package lesson.homework.mocks;

/* Из лекции по citrus:
Заглушка restServer инициализируется при старте теста и ждет нас на порту 5555.
В citrus заглушка делается под каждый тест, поднимается на портах, тест заканчивается, заглушки закрываются.
 */

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import lesson.homework.pojo.Course;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class testMockGetListCourses extends TestNGCitrusSupport {
    public TestContext context;


    @Test(description = "Получение списка курсов", enabled = true)
    @CitrusTest
    public void mockGetListCourses(){
        this.context = citrus.getCitrusContext().createTestContext();

        //При помощи http-клиента отправляем get-запрос в нашу заглушку restServer
        $(http()
                .client("restClient")
                .send()
                .get("/cource/get/all")
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
                        "    \"name\": \"QA java\",\n" +
                        "    \"price\": 15000\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"name\": \"Java\",\n" +
                        "    \"price\": 12000\n" +
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

    public List<Course> getJsonData() {
        List<Course> courses = new ArrayList<>();

        Course course1 = new Course();
        course1.setName("QA java");
        course1.setPrice(15000);
        courses.add(course1);

        Course course2 = new Course();
        course2.setName("Java");
        course2.setPrice(12000);
        courses.add(course2);

        return courses;
    }
}
