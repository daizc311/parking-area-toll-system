package run.bequick.dreamccc.pats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * <h3>客户-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "customer", indexes = {
        @Index(name = "idx_customer_mobile", columnList = "mobile", unique = true),
        @Index(name = "idx_customer_loginName", columnList = "loginName", unique = true)
})
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Customer extends AbstractAuditable<AppUser, Long> {

    @Schema(name = "[三要素之一]真实姓名")
    @Column(nullable = false)
    private String realName;

    @Schema(name = "[三要素之一]身份证号")
    @Column(nullable = false)
    private String idNumber;

    @Schema(name = "[三要素之一][可用于登录]手机号")
    @Column(nullable = false)
    private String mobile;

    @Schema(name = "[可用于登录]登录名称")
    @Column(nullable = false)
    private String loginName;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private String salt;


    @Schema(name = "与用户关联的停车卡信息")
    @OneToMany(targetEntity = ParkingCard.class)
    private List<ParkingCard> parkingCards;

    @Schema(name = "与用户关联的车辆信息")
    @OneToMany(targetEntity = CarInfo.class)
    private List<CarInfo> carInfos;

}
