package com.example.administrator.greendao3demo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/5.
 */

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String age;
    private String phone;
    @Transient
    private int tempUsageCount; // not persisted
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1982716926)
    public User(Long id, String name, String age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }
    @Generated(hash = 586692638)
    public User() {
    }


}
