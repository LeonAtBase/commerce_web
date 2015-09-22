package data_source;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;

public class DSF {

    public static DataSource getDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://192.168.0.54:3306/commerce");
        mysqlDataSource.setUser("guest");
        mysqlDataSource.setPassword("123");
        return mysqlDataSource;
    }
}
