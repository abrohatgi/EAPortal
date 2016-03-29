/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

/**
 *
 * @author abhishek_rohatgi
 */
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailAnalysis {

    public static void main(String[] args) {
        /*
        ArrayList<HashMap<String, String>> EvalArray = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> QueryMap = new  HashMap<String, String>();
        QueryMap.put("Query", "Hello");
         QueryMap.put("ExpectedResult", "Hey Hey");
          QueryMap.put("Found", "YES");
           QueryMap.put("Rank", "3");
           QueryMap.put("PassageLength", "3003");
           
           EvalArray.add(QueryMap);
           EvalArray.add(QueryMap);
           EvalArray.add(QueryMap);
           EvalArray.add(QueryMap);
           EvalArray.add(QueryMap);
        
        
        sendMail(EvalArray);*/
        
       
    }
    
    
    public static void mailFile(String filename , String EmailAddr)
    {
        
        final String username = "eagroupmail@gmail.com";
        final String password = "eadevuser";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            
            InternetAddress[] address = new InternetAddress[1];
            
            address[0] = new InternetAddress (EmailAddr);
         
            

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("eagroupmail@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,address);
            message.setSubject("EA Analysis-Report");
            /*message.setText("Dear Mail Crawler,"
             + "\n\n No spam to my email, please!");*/

            message.setText("Evaluation Email");

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();

        messageBodyPart = new MimeBodyPart();
        String file = "/usr/local/Nuance/SQLDB/"+filename+".xls";
        String fileName = filename+".xls";
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        System.out.println("Sending");

        Transport.send(message);

        System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
    }

    public static void sendMail(ArrayList<HashMap<String, String>> EvalArray) {
        
        
        String json = JsonWriter.objectToJson(EvalArray);
        
        //ArrayList<HashMap<String, String>> EvalArray2 = (ArrayList<HashMap<String, String>>) JsonReader.jsonToJava(json);
        
        //System.out.println(" The Eval Array Json is : [" + json +"]");
        

        String TableContent = "<tbody>";

        for (int i = 0; i < EvalArray.size(); i++) {
            System.out.println("" + EvalArray.get(i).get("Query"));
            System.out.println("" + EvalArray.get(i).get("ExpectedResult"));
            System.out.println("" + EvalArray.get(i).get("Found"));
            System.out.println("" + EvalArray.get(i).get("Rank"));
            System.out.println("" + EvalArray.get(i).get("PassageLength"));

            TableContent = TableContent + "<tr>"
                    + "<td>" + i + "</td>"
                    + "<td>" + EvalArray.get(i).get("Query") + "</td>"
                    + "<td>" + EvalArray.get(i).get("ExpectedResult") + "</td>"
                    + "<td>" + EvalArray.get(i).get("Found") + "</td>"
                    + "<td>" + EvalArray.get(i).get("Rank") + "</td>"
                    + "<td>" + EvalArray.get(i).get("PassageLength") + "</td>"
                    + "</tr>";

        }
        TableContent = TableContent + "</tbody>";

        String TableHeading = "<thread>"
                + "<tr>"
                + "<th>#</th>"
                + "<th>Query</th>"
                + "<th>Expected-Result</th>"
                + "<th>Found</th>"
                + "<th>Rank</th>"
                + "<th>Passage-Length</th>"
                + "</tr>"
                + "</thread>";

        String HTMLTable = "<table class=\"table\">" + TableHeading + TableContent + "</table>";

        String WrapperHtml = "<html>\n"
                + "  \n"
                + "  <head>\n"
                + "    <title>New Page</title>\n"
                + "    <meta name=\"viewport\" content=\"width=device-width\">\n"
                + "    <link rel=\"stylesheet\" href=\"https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\">\n"
                + "    <script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n"
                + "    <script type=\"text/javascript\" src=\"https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js\"></script>\n"
                + "  </head>\n"
                + "  \n"
                + "  <body>\n"
                + "    <div class=\"jumbotron\">\n"
                + "      <h2>Analysis Report</h2>" + HTMLTable + "    </div>\n"
                + "  </body>\n"
                + "\n"
                + "</html>";

        final String username = "eagroupmail@gmail.com";
        final String password = "eadevuser";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            
            InternetAddress[] address = new InternetAddress[1];
            //address[0] = new InternetAddress ("erdem.ozcan@nuance.com");
            address[0] = new InternetAddress ("abhishek.rohatgi@nuance.com");
            //address[2] = new InternetAddress ("john.heater@nuance.com");
            

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("eagroupmail@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,address);
            message.setSubject("EA Analysis-Report");
            /*message.setText("Dear Mail Crawler,"
             + "\n\n No spam to my email, please!");*/

            message.setContent(WrapperHtml, "text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
