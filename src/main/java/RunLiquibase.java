import com.epam.rd.fp.service.DBManager;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RunLiquibase {
    public static void main(String[] args) throws ParseException {
//        DBManager dbManager = DBManager.getInstance();
//        try (Connection connection = dbManager.getConnection("jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass")) {
//            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
//            Liquibase liquibase = new Liquibase("changelogs/db.changelog-1.0.xml", new ClassLoaderResourceAccessor(), database);
//            liquibase.update(new Contexts(), new LabelExpression());
//        } catch (SQLException | LiquibaseException e) {
//            e.printStackTrace();
//        }
        DateFormat df = new SimpleDateFormat("dd.MM.yy");
        String dateStr = "30.05.21";
        Date date = df.parse(dateStr);
        System.out.println(df.format(date));
        System.out.println(df.format(date).equals(dateStr));
    }
}
