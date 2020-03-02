package com.finfamily.utils;


public class Password {
    private int id;

    private String basePassword;

    private String newPassword;

    public int getIdUsuario() {
        return id;
    }

    public void setIdUsuario(int idUsuario) {
        this.id = idUsuario;
    }

    public String getBasePassword() {
        return basePassword;
    }

    public void setBasePassword(String basePassword) {
        this.basePassword = basePassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
