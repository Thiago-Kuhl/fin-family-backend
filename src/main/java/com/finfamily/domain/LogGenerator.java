package com.finfamily.domain;

import java.io.*;

public class LogGenerator {

    public void AcessLog(String usuario, String data, boolean status) throws IOException{
        try {

            String username = System.getProperty("user.name");

            File directory = new File("/home/" + username + "/finfamily/logs/");

            if (! directory.exists()){
                directory.mkdirs();

                BufferedWriter writer = new BufferedWriter(new FileWriter("/home/" + username + "/finfamily/logs/authorization.log", true));
                writer.write(usuario + " logged at " + data + " with status success: " + status);
                writer.newLine();
                writer.close();
            }
            else{
                BufferedWriter writer = new BufferedWriter(new FileWriter("/home/" + username + "/finfamily/logs/authorization.log", true));
                writer.write(usuario + " logged at " + data + " with status success: " + status);
                writer.newLine();
                writer.close();
            }
        }
        catch (IOException erro) {
            System.err.println("Erro ao gravar no arquivo");
            System.err.println(erro);
        }
    }
}
