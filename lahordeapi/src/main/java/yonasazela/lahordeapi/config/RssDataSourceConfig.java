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
        basePackages = "yonasazela.lahordeapi.rss.repositories",
        entityManagerFactoryRef = "rssEntityManagerFactory",
        transactionManagerRef = "rssTransactionManager"
)
public class RssDataSourceConfig {

    @Bean
    public DataSource rssDataSource(Environment env) {
        HikariDataSource ds = new HikariDataSource();

        ds.setJdbcUrl(env.getProperty("spring.datasource.rss.jdbc-url"));
        ds.setUsername(env.getProperty("spring.datasource.rss.username"));
        ds.setPassword(env.getProperty("spring.datasource.rss.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.rss.driver-class-name"));

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean rssEntityManagerFactory(
            @Qualifier("rssDataSource") DataSource dataSource
    ) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("yonasazela.lahordeapi.rss.entities");
        em.setPersistenceUnitName("rss");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");

        em.setJpaPropertyMap(props);

        return em;
    }

    @Bean
    public PlatformTransactionManager rssTransactionManager(
            @Qualifier("rssEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}