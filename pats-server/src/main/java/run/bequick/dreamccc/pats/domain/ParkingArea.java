package run.bequick.dreamccc.pats.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <h3>停车场</h3>
 *
 * @author Daizc
 */
public class ParkingArea {

    @Id
    private Long id;

    private BigDecimal amount;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
