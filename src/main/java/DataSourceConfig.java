import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mariadb://172.24.80.146:3306/otowave");
        dataSourceBuilder.username("otoadm");
        dataSourceBuilder.password("b5T85(CwL#:#");
        dataSourceBuilder.type(HikariDataSource.class);
        return dataSourceBuilder.build();
    }
}
