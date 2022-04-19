package run.bequick.dreamccc.pats.config;

import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import run.bequick.dreamccc.pats.common.CommentIntegrator;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@Configuration
@EnableJpaRepositories(basePackages = {"run.bequick.dreamccc.pats.repository"})
@EnableTransactionManagement
public class JpaConfiguration {

    //    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("run.bequick.dreamccc");
//        factory.setDataSource(dataSource);
//        return factory;
//    }
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.use_sql_comments", true);
            hibernateProperties.put("hibernate.integrator_provider",
                    (IntegratorProvider) () -> Collections.singletonList(CommentIntegrator.INSTANCE));

        };
    }

}
