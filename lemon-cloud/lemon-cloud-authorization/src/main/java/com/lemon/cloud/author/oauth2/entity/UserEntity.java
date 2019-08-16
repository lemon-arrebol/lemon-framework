package com.lemon.cloud.author.oauth2.entity;

import javax.persistence.*;

/**
 * @param
 * @author lemon
 * @description
 * @return
 * @date 2019-08-15 14:31
 */
@Entity
@Table(name = "user", schema = "oauth2", catalog = "")
public class UserEntity {
    private int id;
    private String username;
    private String password;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
