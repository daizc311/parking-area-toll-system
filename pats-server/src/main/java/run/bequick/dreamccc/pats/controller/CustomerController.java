package run.bequick.dreamccc.pats.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.param.CustomerLoginParam;
import run.bequick.dreamccc.pats.param.CustomerRegisterParam;
import run.bequick.dreamccc.pats.param.ThreeFactor;
import run.bequick.dreamccc.pats.service.data.CustomerDService;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final RedisTemplate<String, ThreeFactor> verifyThreeFactorTemplate;
    private final CustomerDService customerDService;

    @PostMapping("/free/registerThreeFactor")
    public DrResponse<String> registerThreeFactor(@RequestBody @Validated ThreeFactor threeFactor) {

        // TODO 由于没钱调接口，直接返回OK了
        String key = UUID.fastUUID().toString(true);
        ValueOperations<String, ThreeFactor> valueOperations = verifyThreeFactorTemplate.opsForValue();
        // 验证信息5分钟内有效
        valueOperations.set(key, threeFactor, 5, TimeUnit.MINUTES);

        return DrResponse.data(key);
    }

    @PostMapping("/free/register")
    public DrResponse<Customer> register(@RequestBody @Validated CustomerRegisterParam customerRegisterParam) {

        String loginName = customerRegisterParam.getLoginName().trim();
        String password = customerRegisterParam.getPassword().trim();
        String threeFactorNum = customerRegisterParam.getThreeFactor().trim();
        ThreeFactor threeFactor = verifyThreeFactorTemplate.opsForValue().get(threeFactorNum);
        if (Objects.isNull(threeFactor)) {
            throw new BusinessException("三要素验证尚未完成，无法注册用户");
        }
        Customer customer = customerDService.saveCustomer(threeFactor, loginName, password);

        return DrResponse.data(customer);
    }

    @PostMapping("/free/login")
    public DrResponse<String> login(@RequestBody @Validated CustomerLoginParam customerLoginParam, HttpServletRequest httpServletRequest) {

        Customer customer = customerDService.login(customerLoginParam.getLoginName().trim(), customerLoginParam.getPassword().trim());

        log.info("签发Token");

        DateTime dateTime = new DateTime();
        Calendar accessTokenTime = dateTime.toCalendar();
        accessTokenTime.add(Calendar.SECOND, 1800);
        Algorithm algorithm = Algorithm.HMAC256("HMAC256");
        String accessToken = JWT.create()
                .withSubject(customer.getRealName())
                .withExpiresAt(accessTokenTime.getTime())
                .withIssuer(httpServletRequest.getRequestURL().toString())
                .withClaim("userType", "CUSTOMER")
                .withClaim("userId", customer.getId())
                .withClaim("roles", Collections.singletonList("CUSTOMER"))
                .sign(algorithm);

        return DrResponse.data(accessToken);
    }
}
