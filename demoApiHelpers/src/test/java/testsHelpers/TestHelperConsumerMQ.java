package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Test;

//JMS Helper
//ConsumerMQ - выступает в роли заглушки, которая принимает данные от менеджера сообщений из какой-то MQ-очереди и валидирует их.
public class TestHelperConsumerMQ extends TestNGCitrusTestRunner {
    public TestContext context;

    @Test(description = "Test MQ Consumer", enabled = false)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        receive(action -> action
                .endpoint("mqHelperConsumer")
                .messageType(MessageType.XML)  //XML мапим на xsd-схему
                .xsd("schemaName")
                .schemaValidation(true) //true - валидация по схеме, false - без валидации
                .payload("payload")
        );
    }
}
