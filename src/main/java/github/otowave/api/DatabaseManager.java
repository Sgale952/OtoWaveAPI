package github.otowave.api;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

    static void setupConnection() {
        final HikariDataSource hds = new HikariDataSource();
        hds.setDriverClassName("org.mariadb.jdbc.Driver");
        hds.setJdbcUrl("jdbc:mariadb://localhost:3306/otowave");
        hds.setUsername("user");
        hds.setPassword("passwrd");

        hds.addDataSourceProperty("cachePrepStmts", "true");
        hds.addDataSourceProperty("prepStmtCacheSize", "250");
        hds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hds.addDataSourceProperty("useServerPrepStmts", "true");
        hds.addDataSourceProperty("useLocalSessionState", "true");
        hds.addDataSourceProperty("rewriteBatchedStatements", "true");
        hds.addDataSourceProperty("cacheResultSetMetadata", "true");
        hds.addDataSourceProperty("cacheServerConfiguration", "true");
        hds.addDataSourceProperty("elideSetAutoCommits", "true");
        hds.addDataSourceProperty("maintainTimeStats", "false");
    }
}
