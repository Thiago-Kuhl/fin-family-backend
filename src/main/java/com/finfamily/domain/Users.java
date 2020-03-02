package com.finfamily.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Entity
@Embeddable
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String full_name;

    private String cpf;

    private Date birthday;

    private String phone_area_code;

    private String phone_area_number;

    private String email;

    private String password;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<GroupParticipants> groupParticipants;

    @OneToOne(mappedBy = "users", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private PersonalWallet personalWallet;

    public Users(int id, String full_name, String cpf, Date birthday, String phone_area_code, String phone_area_number,
                 String email , String password, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.full_name = full_name;
        this.cpf = cpf;
        this.birthday = birthday;
        this.phone_area_code = phone_area_code;
        this.phone_area_number = phone_area_number;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone_area_code() {
        return phone_area_code;
    }

    public void setPhone_area_code(String phone_area_code) {
        this.phone_area_code = phone_area_code;
    }

    public String getPhone_area_number() {
        return phone_area_number;
    }

    public void setPhone_area_number(String phone_area_number) {
        this.phone_area_number = phone_area_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Users(){}

}