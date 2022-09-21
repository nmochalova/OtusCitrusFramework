package lesson.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import lesson.behavior.CreateUserBehavior;
import org.testng.annotations.Test;
import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstBehaviorTest extends TestNGCitrusSupport {
    public TestContext context;
    String name = "Nick";
    String job = "Teacher";

    @Test(description = "Получение информации о user с предварительным его созданием.", enabled = true)
    @CitrusTest
    public void CreateUserBehaviorTest(){
        this.context = citrus.getCitrusContext().createTestContext();

        //Предварительное действие: создание User
        $(applyBehavior(new CreateUserBehavior(name,job, context)));

        //Получение переменной из context и вывод ее значения в консоль
        $(echo("CreateUserBehaviorTest \"currentId\" = " + context.getVariable("currentId")));

        $(http()
                .client("restClientReqres")
                .send()
                .get("users/"+context.getVariable("currentId")) //передаем id из context в метод
        );

        //Т.е. reqres - это заглушка и она не возвращает случайный id при создании юзера, то запросить данные по id нет возможности.
        //В реальной жизни, мы бы сверяли ответ с полученным id, в случае с заглушкой же ответом будет NOT_FOUND
//        $(http()
//                .client("restClientReqres")
//                .receive()
//                .response(HttpStatus.OK)
//                .message()
//                .type("application/json")
//                .validate(jsonPath()                            //ожидаемый результат
//                        .expression("$.data.id",context.getVariable("currentId"))
//                        .expression("$.data.name",name)
//                        .expression("$.data.job",job)
//                )
//        );
    }

}
