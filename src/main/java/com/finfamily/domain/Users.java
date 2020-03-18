package com.finfamily.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Embeddable
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden=true)
    @JsonProperty
    private int id;

    @JsonProperty
    private String full_name;

    @JsonProperty
    private String nickname;

    @JsonProperty
    private String cpf;

    @JsonProperty
    private String birthday;

    @JsonProperty
    private String phone_area_code;

    @JsonProperty
    private String phone_area_number;

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private String created_at;

    @JsonProperty
    private String updated_at;

//    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<GroupParticipants> groupParticipants;

    @OneToOne(mappedBy = "users", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private PersonalWallet personalWallet;

    public Users( int id, String full_name, String nickname,String cpf, String birthday, String phone_area_code, String phone_area_number,
                 String email , String password, String created_at, String updated_at) {
        this.id = id;
        this.full_name = full_name;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Users(){}

}