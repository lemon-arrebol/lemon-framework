package com.lemon.mybatis.po;

/**
 * @author shanhonghao
 * @date 2018-10-22 11:45
 */
public enum StringEnum implements BaseStringEnum {
    S1("s1", "s1"),
    S2("s2", "s2"),
    ;

    public final String value;
    public final String description;

    StringEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
