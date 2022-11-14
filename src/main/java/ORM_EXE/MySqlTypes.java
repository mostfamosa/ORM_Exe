package ORM_EXE;

import com.mysql.cj.MysqlType;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MySqlTypes {
    public static Map<Class<?>, MysqlType> DEFAULT_MYSQL_TYPES = new HashMap();

    static  {
        DEFAULT_MYSQL_TYPES.put(BigDecimal.class, MysqlType.DECIMAL);
        DEFAULT_MYSQL_TYPES.put(BigInteger.class, MysqlType.BIGINT);
        DEFAULT_MYSQL_TYPES.put(Blob.class, MysqlType.BLOB);
        DEFAULT_MYSQL_TYPES.put(Boolean.class, MysqlType.BOOLEAN);
        DEFAULT_MYSQL_TYPES.put(Byte.class, MysqlType.TINYINT);
        DEFAULT_MYSQL_TYPES.put(byte[].class, MysqlType.BINARY);
        DEFAULT_MYSQL_TYPES.put(Calendar.class, MysqlType.TIMESTAMP);
        DEFAULT_MYSQL_TYPES.put(Clob.class, MysqlType.TEXT);
        DEFAULT_MYSQL_TYPES.put(Date.class, MysqlType.DATE);
        DEFAULT_MYSQL_TYPES.put(java.util.Date.class, MysqlType.TIMESTAMP);
        DEFAULT_MYSQL_TYPES.put(Double.class, MysqlType.DOUBLE);
        DEFAULT_MYSQL_TYPES.put(Duration.class, MysqlType.TIME);
        DEFAULT_MYSQL_TYPES.put(Float.class, MysqlType.FLOAT);
        DEFAULT_MYSQL_TYPES.put(InputStream.class, MysqlType.BLOB);
        DEFAULT_MYSQL_TYPES.put(Instant.class, MysqlType.TIMESTAMP);
        DEFAULT_MYSQL_TYPES.put(Integer.class, MysqlType.INT);
        DEFAULT_MYSQL_TYPES.put(LocalDate.class, MysqlType.DATE);
        DEFAULT_MYSQL_TYPES.put(LocalDateTime.class, MysqlType.DATETIME);
        DEFAULT_MYSQL_TYPES.put(LocalTime.class, MysqlType.TIME);
        DEFAULT_MYSQL_TYPES.put(Long.class, MysqlType.BIGINT);
        DEFAULT_MYSQL_TYPES.put(OffsetDateTime.class, MysqlType.TIMESTAMP);
        DEFAULT_MYSQL_TYPES.put(OffsetTime.class, MysqlType.TIME);
        DEFAULT_MYSQL_TYPES.put(Reader.class, MysqlType.TEXT);
        DEFAULT_MYSQL_TYPES.put(Short.class, MysqlType.SMALLINT);
        DEFAULT_MYSQL_TYPES.put(String.class, MysqlType.VARCHAR);
        DEFAULT_MYSQL_TYPES.put(Time.class, MysqlType.TIME);
        DEFAULT_MYSQL_TYPES.put(Timestamp.class, MysqlType.TIMESTAMP);
        DEFAULT_MYSQL_TYPES.put(ZonedDateTime.class, MysqlType.TIMESTAMP);
    }
}
