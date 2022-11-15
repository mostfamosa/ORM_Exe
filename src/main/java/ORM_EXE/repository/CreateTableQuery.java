package ORM_EXE.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;

class CreateTableQuery<T> {
    private final Class<T> clz;
    private MysqlConnection con;
    private static Logger logger = LogManager.getLogger(CreateTableQuery.class.getName());
    public CreateTableQuery(Class<T> clz) {
        this.clz = clz;
        con = MysqlConnection.getInstance();
    }

    public void createTableInDB() {

        StringBuilder queryStr = new StringBuilder();
        queryStr.append("create table if not exists " + clz.getSimpleName().toLowerCase() + "(");
        StringBuilder primaryKey = new StringBuilder();

        Field[] classFields = clz.getDeclaredFields();
        for (Field field : classFields) {
            field.setAccessible(true);
            queryStr.append(field.getName() + " ");
            Class<?> type = field.getType();
            if (!type.isPrimitive()) {
                queryStr.append("varchar(255) ");
            } else {
                queryStr.append(type.getName() + " ");
            }

            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                switch (annotation.annotationType().getSimpleName()) {
                    case "Auto_Increment":
                        queryStr.append("AUTO_INCREMENT ");
                        break;
                    case "Not_Null":
                        queryStr.append("NOT NULL ");
                        break;
                    case "Primary_Key":
                        primaryKey.append(field.getName() + ",");
                        break;
                    case "Unique":
                        queryStr.append("UNIQUE ");
                        break;
                    default:
                        System.out.println("Annotation " + annotation.annotationType().getSimpleName() + " is not supported.");
                }
            }
            queryStr.append(",");
        }

        queryStr.deleteCharAt(queryStr.length() - 1);

        if (primaryKey.length() != 0) {
            primaryKey.deleteCharAt(primaryKey.length() - 1);
            queryStr.append(", PRIMARY KEY(" + primaryKey + ")");
        }
        queryStr.append(");");

        try {
            con = MysqlConnection.getInstance();
            Statement statement = con.getConnection().createStatement();
            statement.executeUpdate(queryStr.toString());
            logger.info("Created table " + this.clz.getSimpleName().toLowerCase());
            con.close();
        } catch (SQLException e) {
            logger.error("Failed to create sql statement while trying to create table " + this.clz.getSimpleName() + ". ");
            throw new RuntimeException(e);
        }
    }

}
