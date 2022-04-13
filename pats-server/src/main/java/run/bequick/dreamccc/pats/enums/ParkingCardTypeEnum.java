package run.bequick.dreamccc.pats.enums;

import lombok.Getter;

public enum ParkingCardTypeEnum {
    USER_PERSISTENCE("用户钱包"),
    DISPOSABLE("一次性停车卡"),
    COUNT("计次停车卡");

    ParkingCardTypeEnum(String name) {
        this.name = name;
    }

    @Getter
    private final String name;
}
