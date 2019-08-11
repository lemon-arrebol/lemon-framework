package com.lemon.mybatis.po;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author shanhonghao
 * @date 2018-10-22 11:45
 */
@Getter
@Setter
public class Demo {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id = RandomUtils.nextInt();
    @Column(name = "int_enum")
    private IntEnum intEnum;
    @Column(name = "string_enum")
    private StringEnum stringEnum;

    @Column(name = "json_arr")
    public JSONArray jsonArr;
    @Column(name = "json_obj")
    public JSONObject jsonObj;
    @Column(name = "json_simple")
    public Obj jsonSimple;
    @Column(name = "yaml_obj")
    public YamlObj yamlObj;

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Obj {
        private Integer id = RandomUtils.nextInt();
        private String name = UUID.randomUUID().toString();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class YamlObj {
        private Integer id = RandomUtils.nextInt();
        private String name = UUID.randomUUID().toString();
    }
}
