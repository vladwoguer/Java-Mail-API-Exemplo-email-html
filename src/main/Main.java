package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

    public static void main(String[] args) {

        final String username = "mock@gmail.com"; //TODO put real email before run
        final String password = "mock"; //TODO put real email before run
        String from = "mock@gmail.com"; //TODO put real email before run
        String to = "mock@gmail.com"; //TODO put real email before run
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);

            // Email que você quer que apareça como tendo enviado
            message.setFrom(new InternetAddress(from));

            // Para quem vai enviar
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Assunto
            message.setSubject("Teste de email html");

            try (BufferedReader br = new BufferedReader(new FileReader("teste.html"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String htmlContent = sb.toString();
                message.setContent(htmlContent, "text/html");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Transport.send(message);
            System.out.println("Mensagem enviada");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
