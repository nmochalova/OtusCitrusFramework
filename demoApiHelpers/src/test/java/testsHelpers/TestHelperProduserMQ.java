package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Test;

//JMS Helper
//ProducerMQ - выступает в роли заглушки, которая передает данные в менеджера сообщений какой-то MQ-очереди и валидирует их.
public class TestHelperProduserMQ extends TestNGCitrusTestRunner {
    public TestContext context;

    @Test(description = "Test MQ Producer", enabled = false)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        //Отправка в менеджер сообщений (MQ)
        send(action -> action
                .endpoint("mqHelperProduser")
                .messageType(MessageType.XML)
                .payload("payload"));
    }
}
