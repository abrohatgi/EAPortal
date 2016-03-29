/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author abhishek_rohatgi
 */
public class QueryDB {

    public static synchronized void main(String args[]) {

       // createQARatingTB();
        // insertEvalData("BestBuy", "bestbuymediastreaming", "http://www.bestbuy.ca/en-CA/category/media-streaming/20003a.aspx", "34-34-34-34-34-34", "dwdewdewdewd");
        //  insertQARatingData("How does that work ?", "kjgiuhuhuohio biuhubn biuhouhiou", "3", "bodybuildingid", "Body Building", "http://ca.bodybuilding.com/store/bsn/synth.html");
        /*   Connection c = null;
         try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:/Users/abhishek_rohatgi/NiA.db");
         } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
         }
         System.out.println("Opened database successfully");
         */
        // createEvaluationTBSQL();
        fetchQAList("usaa");

    }

    public static void createEvaluationTB() {

        String sql = "CREATE TABLE `EvaluationTB` (\n"
                + "	`ProjectName`	TEXT,\n"
                + "	`ProjectID`	TEXT,\n"
                + "	`ProjectURL`	TEXT,\n"
                + "	`DATE`	TEXT,\n"
                + "	`Results`	TEXT\n"
                + ");";

        executeSQL(sql);

    }

    public static void createQARatingTB() {

        String sql = "CREATE TABLE `QARatingTB` (\n"
                + "	`Question`	TEXT,\n"
                + "	`Response`	TEXT,\n"
                + "	`Rating`	TEXT,\n"
                + "	`ProjectID`	TEXT,\n"
                + "	`ProjectName`	TEXT,\n"
                + "	`ProjectURL`	TEXT\n"
                + ");";

        executeSQL(sql);

    }

    public static void insertEvalData(String projectName, String projectID, String projectURL, String DATE, String Results) {
        String sql = "INSERT INTO EvaluationTB (ProjectName,ProjectID,ProjectURL,DATE,Results) "
                + "VALUES ('" + projectName + "', '" + projectID + "', '" + projectURL + "', '" + DATE + "', '" + Results + "' );";

        executeSQL(sql);
    }

    public static void insertQARatingData(String Question, String Response, String Rating, String ProjectID, String ProjectName, String ProjectURL) {
        String sql = "INSERT INTO QARatingTB (Question,Response,Rating,ProjectID,ProjectName,ProjectURL) "
                + "VALUES ('" + Question + "', '" + Response + "', '" + Rating + "', '" + ProjectID + "', '" + ProjectName + "', '" + ProjectURL + "' );";

        executeSQL(sql);
    }

    public static synchronized void executeSQL(String Query) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/usr/local/Nuance/SQLDB/NiA.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = Query;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.println("Database Query Successful !");
    }

    public static String fetchResult4date(String Date) {

        System.out.println(" The date is : " + Date);

        Connection c = null;
        Statement stmt = null;
        String Results = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/usr/local/Nuance/SQLDB/NiA.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EvaluationTB WHERE DATE LIKE '" + Date + "';");
            while (rs.next()) {

                String ProjectName = rs.getString("ProjectName");
                String ProjectURL = rs.getString("ProjectURL");
                String DATE = rs.getString("DATE");
                Results = rs.getString("Results");

            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;

        }

        System.out.println("Operation done successfully");
        return Results;
    }

    public static ArrayList<String> fetchEvalData(String ProjectID) {

        ArrayList<String> dateList = new ArrayList<String>();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/usr/local/Nuance/SQLDB/NiA.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EvaluationTB WHERE ProjectID LIKE '" + ProjectID + "';");
            while (rs.next()) {

                String ProjectName = rs.getString("ProjectName");
                String ProjectURL = rs.getString("ProjectURL");
                String DATE = rs.getString("DATE");
                String Results = rs.getString("Results");

                dateList.add(DATE);

                System.out.println("ProjectName : " + ProjectName);
                System.out.println("ProjectURL : " + ProjectURL);
                System.out.println("DATE : " + DATE);
                System.out.println("Results : " + Results);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;

        }

        System.out.println("Operation done successfully");
        return dateList;
    }

    public static String fetchRating(String Question, String Response, String ProjectID) {

        Connection c = null;
        Statement stmt = null;
        String Rating = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/usr/local/Nuance/SQLDB/NiA.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM QARatingTB WHERE ProjectID LIKE '" + ProjectID + "' AND Question LIKE '" + Question + "' AND Response LIKE '" + Response + "';");
            while (rs.next()) {

                String ProjectName = rs.getString("ProjectName");
                String ProjectURL = rs.getString("ProjectURL");
                Rating = rs.getString("Rating");

                System.out.println("ProjectName : " + ProjectName);
                System.out.println("ProjectURL : " + ProjectURL);
                System.out.println("Rating : " + Rating);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Operation done successfully");

        return Rating;

    }

    public static HashMap<String, HashMap<String, String>> fetchQAList(String ProjectID) {
        Connection c = null;
        Statement stmt = null;
        

        HashMap<String, HashMap<String, String>> qaList = new HashMap<String, HashMap<String, String>>();
        //HashMap<String,String> ratedResponseList = new HashMap<String,String>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/usr/local/Nuance/SQLDB/NiA.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM QARatingTB WHERE ProjectID LIKE '" + ProjectID + "' ;");
            while (rs.next()) {

                String Question = rs.getString("Question");
                String Response = rs.getString("Response");
                String Rating = rs.getString("Rating");

                //System.out.println("Question : " + Question);
                //System.out.println("Answer : " + Response);
                //System.out.println("Rating : " + Rating);
                //Selecting Queries only with good responses

                if (Rating.contains("Relevant") || Rating.contains("Good")) {
                    if (qaList.containsKey(Question)) {
                        qaList.get(Question).put(Response, Rating);
                    } else {
                        HashMap<String, String> ratedResponseList = new HashMap<String, String>();
                        ratedResponseList.put(Response, Rating);
                        qaList.put(Question, ratedResponseList);
                    }
                }
                
                

            }
            
            /*
            int totalQuestions =0;
            int totalResponses =0;
            
            Iterator it = qaList.entrySet().iterator();
            while (it.hasNext()) {
                totalQuestions ++;
                int Responses4Question =0;
                HashMap.Entry pair = (HashMap.Entry) it.next();
                System.out.println("*************************************");
                System.out.println(" The QUESTION IS :" + pair.getKey());
                HashMap<String, String> responseList = (HashMap) pair.getValue();
                Iterator it2 = responseList.entrySet().iterator();
                while (it2.hasNext()) {
                    totalResponses++;
                    Responses4Question ++;
                    HashMap.Entry pair2 = (HashMap.Entry) it2.next();
                    System.out.println(pair2.getKey() + " = " + pair2.getValue());
                    it2.remove(); // avoids a ConcurrentModificationException
                }
                System.out.println("Responses4Question =" + Responses4Question);
                System.out.println("*************************************");
                it.remove(); // avoids a ConcurrentModificationException    
            }
            

            System.out.println("Total Questions =" + totalQuestions);
             System.out.println("Total Responses =" + totalResponses);
            */
            
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Operation done successfully");

        return qaList;
    }
    
    
     public static HashMap<String, HashMap<String, String>> fetchQAList2Write(String ProjectID) {
        Connection c = null;
        Statement stmt = null;
        

        HashMap<String, HashMap<String, String>> qaList = new HashMap<String, HashMap<String, String>>();
        //HashMap<String,String> ratedResponseList = new HashMap<String,String>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/usr/local/Nuance/SQLDB/NiA.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM QARatingTB WHERE ProjectID LIKE '" + ProjectID + "' ;");
            while (rs.next()) {

                String Question = rs.getString("Question");
                String Response = rs.getString("Response");
                String Rating = rs.getString("Rating");

                //System.out.println("Question : " + Question);
                //System.out.println("Answer : " + Response);
                //System.out.println("Rating : " + Rating);
                //Selecting Queries only with good responses

                if (Rating.contains("Relevant") || Rating.contains("Good")|| Rating.contains("Irrelevant")) {
                    if (qaList.containsKey(Question)) {
                        qaList.get(Question).put(Response, Rating);
                    } else {
                        HashMap<String, String> ratedResponseList = new HashMap<String, String>();
                        ratedResponseList.put(Response, Rating);
                        qaList.put(Question, ratedResponseList);
                    }
                }
                
                

            }
            
            /*
            int totalQuestions =0;
            int totalResponses =0;
            
            Iterator it = qaList.entrySet().iterator();
            while (it.hasNext()) {
                totalQuestions ++;
                int Responses4Question =0;
                HashMap.Entry pair = (HashMap.Entry) it.next();
                System.out.println("*************************************");
                System.out.println(" The QUESTION IS :" + pair.getKey());
                HashMap<String, String> responseList = (HashMap) pair.getValue();
                Iterator it2 = responseList.entrySet().iterator();
                while (it2.hasNext()) {
                    totalResponses++;
                    Responses4Question ++;
                    HashMap.Entry pair2 = (HashMap.Entry) it2.next();
                    System.out.println(pair2.getKey() + " = " + pair2.getValue());
                    it2.remove(); // avoids a ConcurrentModificationException
                }
                System.out.println("Responses4Question =" + Responses4Question);
                System.out.println("*************************************");
                it.remove(); // avoids a ConcurrentModificationException    
            }
            

            System.out.println("Total Questions =" + totalQuestions);
             System.out.println("Total Responses =" + totalResponses);
            */
            
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Operation done successfully");

        return qaList;
    }

}
