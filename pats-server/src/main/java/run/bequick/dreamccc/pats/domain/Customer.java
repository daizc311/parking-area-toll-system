package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
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
public class Customer {

    @Id
    private Long id;

    @Schema(name = "[三要素之一]真实姓名")
    private String realName;

    @Schema(name = "[三要素之一]身份证号")
    private String idNumber;

    @Schema(name = "[三要素之一][可用于登录]手机号")
    private String mobile;

    @Schema(name = "[可用于登录]登录名称")
    private String loginName;

    @Schema(name = "与用户关联的停车卡信息")
    @OneToMany(targetEntity = ParkingCard.class)
    private List<ParkingCard> parkingCards;

    @Schema(name = "与用户关联的车辆信息")
    @OneToMany(targetEntity = ParkingCard.class)
    private List<CarInfo> carInfos;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
