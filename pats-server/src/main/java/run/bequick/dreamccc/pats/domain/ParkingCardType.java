package run.bequick.dreamccc.pats.domain;

import lombok.Getter;

public enum ParkingCardType {
    USER_PERSISTENCE("用户钱包"),DISPOSABLE("一次性停车卡");

    ParkingCardType(String name) {
        this.name = name;
    }

    @Getter
    private final String name;
}
