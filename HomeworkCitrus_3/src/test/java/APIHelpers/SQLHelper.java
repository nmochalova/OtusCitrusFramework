package APIHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import javax.sql.DataSource;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class SQLHelper extends TestNGCitrusSupport {
    @Autowired
    public DataSource sqlHelper;
    public TestContext context;

    @Test(description = "SQL Helper", enabled = true)
    @CitrusTest
    public void createUserTest() {
        this.context = citrus.getCitrusContext().createTestContext();

        query(sqlHelper)
                .statement("SELECT ID FROM TEST_TABLE;")
                .extract("ID", "ID").build();


        $(echo("ID = " + context.getVariable("ID")));

//        sql(sqlHelper)
//                .statement("DELETE FROM TEST_TABLE");


    }
}
