package daoTests;

import com.epam.rd.fp.service.DBManager;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractIntegrationTest {
    @BeforeEach
    void setUp() throws  Exception{
        Class.forName("org.h2.Driver");
        deleteAllData();
        applySchema();
    }

    private void applySchema() {
        try (Connection connection = DBManager.getInstance().getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("changelogs/db.changelog-1.0.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllData() throws SQLException {
        try(Connection connection = DBManager.getInstance().getConnection();
            Statement statement = connection.createStatement()){
            statement.execute("DROP ALL OBJECTS");
        }
    }
}
