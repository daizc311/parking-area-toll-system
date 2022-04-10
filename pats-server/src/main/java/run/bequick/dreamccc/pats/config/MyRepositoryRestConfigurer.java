package run.bequick.dreamccc.pats.config;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import run.bequick.dreamccc.pats.domain.*;

@Component
public class MyRepositoryRestConfigurer implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setDefaultPageSize(10)
                .setReturnBodyForPutAndPost(true)
                .exposeIdsFor(
                        AppRole.class,
                        AppUser.class,
                        CarInfo.class,
                        CarParkingLogDO.class,
                        CarParkingStatus.class,
                        Customer.class,
                        ParkingCard.class,
                        ParkingCardAmountLogDO.class,
                        ParkingSetting.class
                );
    }
}
