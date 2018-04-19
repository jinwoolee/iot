package org.iptime.iothome.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable=false, length=20)
    private String userId;
    private String name;
    private String password;
    private String email;
    
    public User() {
        
    }
    public User(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void update(User updateUser) {
        this.name = updateUser.name;
        this.email = updateUser.email;
        this.password = updateUser.password;
    }
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", email=" + email + "]";
    }
}