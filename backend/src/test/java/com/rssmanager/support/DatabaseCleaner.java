package com.rssmanager.support;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner implements InitializingBean {
    @Autowired
    private DataSource dataSource;
    private List<String> tableNames = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws SQLException {
        try (final var connection = dataSource.getConnection()) {
            final var tableResultSet = connection.getMetaData()
                    .getTables(null, "PUBLIC", null, null);

            while (tableResultSet.next()) {
                this.tableNames.add(tableResultSet.getString("TABLE_NAME").toLowerCase());
            }
        }
    }

    public void clear() {
        try (final var connection = dataSource.getConnection();
             final var statement = connection.createStatement()) {

            statement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

            for (String tableName : tableNames) {
                statement.executeUpdate("TRUNCATE TABLE " + tableName);
                statement.executeUpdate("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
            }

            statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
