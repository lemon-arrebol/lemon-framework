package com.lemon.mybatis.mapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

public interface LFMySQLMapper<T> extends Mapper<T>, IdsMapper<T>, InsertListMapper<T> {

}