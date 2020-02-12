package com.keycorp.domain;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.FormatterClosedException;

public class LogGenerator {

    public void AcessLog(String usuario, String data, boolean status) throws IOException{
        try {

            String username = System.getProperty("user.name");

            File directory = new File("/home/" + username + "/keycorp/logs/");

            if (! directory.exists()){
                directory.mkdirs();

                BufferedWriter writer = new BufferedWriter(new FileWriter("/home/" + username + "/keycorp/logs/authorization.log", true));
                writer.write(usuario + " logged at " + data + " with status success: " + status);
                writer.newLine();
                writer.close();
            }
            else{
                BufferedWriter writer = new BufferedWriter(new FileWriter("/home/" + username + "/keycorp/logs/authorization.log", true));
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
