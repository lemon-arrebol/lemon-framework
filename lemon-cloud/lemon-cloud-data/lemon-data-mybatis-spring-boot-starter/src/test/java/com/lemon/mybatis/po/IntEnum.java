package com.lemon.mybatis.po;

/**
 * @author shanhonghao
 * @date 2018-10-22 11:45
 */
public enum IntEnum implements BaseIntEnum {
    S1(1, "s1"),
    S2(2, "s2"),
    ;

    public final int value;
    public final String description;

    IntEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
