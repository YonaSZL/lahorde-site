package yonasazela.lahordeapi.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "yonasazela.lahordeapi.kenshi.repositories",
        entityManagerFactoryRef = "kenshiEntityManagerFactory",
        transactionManagerRef = "kenshiTransactionManager"
)
public class KenshiDataSourceConfig {

    @Bean
    public DataSource kenshiDataSource(Environment env) {
        HikariDataSource ds = new HikariDataSource();

        ds.setJdbcUrl(env.getProperty("spring.datasource.kenshi.jdbc-url"));
        ds.setUsername(env.getProperty("spring.datasource.kenshi.username"));
        ds.setPassword(env.getProperty("spring.datasource.kenshi.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.kenshi.driver-class-name"));

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean kenshiEntityManagerFactory(
            @Qualifier("kenshiDataSource") DataSource dataSource
    ) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("yonasazela.lahordeapi.kenshi.entities");
        em.setPersistenceUnitName("kenshi");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");

        em.setJpaPropertyMap(props);

        return em;
    }

    @Bean
    public PlatformTransactionManager kenshiTransactionManager(
            @Qualifier("kenshiEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}