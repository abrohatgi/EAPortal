/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import com.nuance.expertassistant.db.QueryDB;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

/**
 *
 * @author abhishek_rohatgi
 */
public class ReadExcelFile {

    public static void main(String[] args) {

        //ReadExcelFile ref = new ReadExcelFile();
        //ref.retrieveAnswersandWrite("usaa");

        // ref.readExcelFile("bestbuy", "myfile.xls");
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> evaluateFromRatingDB(String ProjectID) {
        HashMap<String, ArrayList<HashMap<String, String>>> EvalMap = new HashMap<String, ArrayList<HashMap<String, String>>>();
        HashMap<String, HashMap<String, String>> QAList = new HashMap<String, HashMap<String, String>>();

        try {
            QAList = QueryDB.fetchQAList(ProjectID);
            Iterator it = QAList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair1 = (Map.Entry) it.next();

                HashMap<String, String> ratedResponses = (HashMap<String, String>) pair1.getValue();

                String Question = pair1.getKey().toString();

                Iterator it2 = ratedResponses.entrySet().iterator();

                ArrayList<HashMap<String, String>> responseArray4Question = new ArrayList<HashMap<String, String>>();

                while (it2.hasNext()) {
                    Map.Entry pair2 = (Map.Entry) it2.next();
                    System.out.println(Question + " = " + pair2.getKey());

                    /**
                     * ***********************************
                     */
                    HashMap<String, String> queryAnalysis = new HashMap<String, String>();

                    System.out.println(" The QueryList String is " + Question);
                    System.out.println(" The ExpectedResultList String is " + pair2.getKey());

                    queryAnalysis.put("Query", Question);
                    queryAnalysis.put("ExpectedResult", pair2.getKey().toString());
                    queryAnalysis.put("UserRating", pair2.getValue().toString());
                    queryAnalysis.put("GoldResponses", String.valueOf(ratedResponses.size()));
                    queryAnalysis.put("Found", "NO");
                    queryAnalysis.put("Rank", "-1");
                    queryAnalysis.put("PassageLength", "-1");
                    queryAnalysis.put("numResponses", "-1");

                    ArrayList<Integer> resultArray = new ArrayList<Integer>();

                    resultArray = retrieveAnswers(ProjectID, Question, pair2.getKey().toString());

                    System.out.println("Result Array" + resultArray.toString());

                    if (resultArray.get(0) == 1) {
                        queryAnalysis.put("Found", "YES");
                        queryAnalysis.put("Rank", resultArray.get(1).toString());
                        queryAnalysis.put("PassageLength", resultArray.get(2).toString());
                        queryAnalysis.put("numResponses", resultArray.get(3).toString());

                    }

                    responseArray4Question.add(queryAnalysis);

                    /**
                     * ************************************
                     */
                }

                System.out.println("*************************************");
                System.out.println("**************EVALUTE****************");
                System.out.println("*************************************");

                for (int i = 0; i < responseArray4Question.size(); i++) {
                    System.out.println("" + responseArray4Question.get(i).get("Query"));
                    System.out.println("" + responseArray4Question.get(i).get("ExpectedResult"));
                    System.out.println("" + responseArray4Question.get(i).get("UserRating"));
                    System.out.println("" + responseArray4Question.get(i).get("GoldResponses"));
                    System.out.println("" + responseArray4Question.get(i).get("Found"));
                    System.out.println("" + responseArray4Question.get(i).get("Rank"));
                    System.out.println("" + responseArray4Question.get(i).get("PassageLength"));

                }

                EvalMap.put(Question, responseArray4Question);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("*************************************");
        System.out.println("**************Print EvalMap****************");
        System.out.println("*************************************");

        Iterator it = EvalMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<HashMap<String, String>> responseArray = (ArrayList<HashMap<String, String>>) pair.getValue();

            for (int i = 0; i < responseArray.size(); i++) {

                System.out.println("" + responseArray.get(i).get("Query"));
                System.out.println("" + responseArray.get(i).get("ExpectedResult"));
                System.out.println("" + responseArray.get(i).get("Found"));
                System.out.println("" + responseArray.get(i).get("Rank"));
                System.out.println("" + responseArray.get(i).get("PassageLength"));
            }
        }

        System.out.println("*************************************");
        System.out.println("**************Print[ed] EvalMap****************");
        System.out.println("*************************************");
        
        return EvalMap;
    }

    public ArrayList<HashMap<String, String>> readExcelFile(String ProjectID, String filename) {
        try {
            Workbook workbook = Workbook.getWorkbook(new File("/usr/local/Nuance/MultiModalFramework/uploadedFiles/" + filename));
            Sheet sheet = workbook.getSheet(0);

            ArrayList<HashMap<String, String>> EvalArray = new ArrayList<HashMap<String, String>>();

            System.out.println(" The number of rows are :" + sheet.getRows());
            System.out.println(" The number of coloumn are :" + sheet.getColumns());

            int numrows = sheet.getRows();
            //int numcols = sheet.getColumns();

            for (int i = 1; i < numrows; i++) {

                HashMap<String, String> queryAnalysis = new HashMap<String, String>();

                System.out.println(" The QueryList String is " + sheet.getCell(0, i).getContents());
                System.out.println(" The ExpectedResultList String is " + sheet.getCell(1, i).getContents());

                queryAnalysis.put("Query", sheet.getCell(0, i).getContents());
                queryAnalysis.put("ExpectedResult", sheet.getCell(1, i).getContents());
                queryAnalysis.put("Found", "NO");
                queryAnalysis.put("Rank", "-1");
                queryAnalysis.put("PassageLength", "-1");

                ArrayList<Integer> resultArray = new ArrayList<Integer>();

                resultArray = retrieveAnswers(ProjectID, sheet.getCell(0, i).getContents(), sheet.getCell(1, i).getContents());

                System.out.println("Result Array" + resultArray.toString());

                if (resultArray.get(0) == 1) {
                    queryAnalysis.put("Found", "YES");
                    queryAnalysis.put("Rank", resultArray.get(1).toString());
                    queryAnalysis.put("PassageLength", resultArray.get(2).toString());
                }

                EvalArray.add(queryAnalysis);

            }

            System.out.println("*************************************");
            System.out.println("**************EVALUTE****************");
            System.out.println("*************************************");

            for (int i = 0; i < EvalArray.size(); i++) {
                System.out.println("" + EvalArray.get(i).get("Query"));
                System.out.println("" + EvalArray.get(i).get("ExpectedResult"));
                System.out.println("" + EvalArray.get(i).get("Found"));
                System.out.println("" + EvalArray.get(i).get("Rank"));
                System.out.println("" + EvalArray.get(i).get("PassageLength"));

            }

            return EvalArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean retrieveAnswersandWrite(String projectID, String  filename) {

        int row = 1;

        HashMap<String, HashMap<String, String>> qaList = new HashMap<String, HashMap<String, String>>();
        qaList = QueryDB.fetchQAList2Write(projectID);

        try {

            WritableWorkbook workbook = Workbook.createWorkbook(new File("/usr/local/Nuance/SQLDB/"+filename+".xls"));
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);

            Label label = new Label(0, 0, "Question");
            sheet.addCell(label);
            label = new Label(1, 0, "Answer");
            sheet.addCell(label);
            label = new Label(2, 0, "Score");
            sheet.addCell(label);
             label = new Label(3, 0, "Confidence");
            sheet.addCell(label);
            label = new Label(4, 0, "User-Rating");
            sheet.addCell(label);
            label = new Label(5, 0, "Rank");
            sheet.addCell(label);
            label = new Label(6, 0, "Found");
            sheet.addCell(label);
            

           

            Iterator it = qaList.entrySet().iterator();
            while (it.hasNext()) {
               
                
                HashMap.Entry pair = (HashMap.Entry) it.next();
                System.out.println("*************************************");
                System.out.println(" The QUESTION IS :" + pair.getKey());
                HashMap<String, String> responseList = (HashMap) pair.getValue();

                String currentQuestion = pair.getKey().toString();

                // Invoking QA Core to fetch Responses
                String contextID = InvokeQACoreAPI.getContextID(projectID).replaceAll("\n", "");
                System.out.println(" The contextID is :[" + contextID + "]");
                String answerString = InvokeQACoreAPI.getAnswer(contextID, projectID, currentQuestion);
                JSONArray jsonArray = new JSONArray(answerString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject job = jsonArray.getJSONObject(i);
                    String answerText = job.getJSONObject("answer").getString("text");
                    String evidence = html2text(job.getJSONObject("answer").getJSONObject("evidence").getString("text"));
                    Double confidence = job.getJSONObject("answer").getDouble("confidence");
                    Double score = job.getJSONObject("answer").getDouble("score");
                    int Rank = i;
                    String Found = "YES";
                    String Rating = "Not Rated";
                    
                    if(responseList.containsKey(answerText))
                    {
                        Rating = responseList.get(answerText);
                        responseList.remove(answerText);
                    }
                    
                    label = new Label(0,row, currentQuestion);
                    sheet.addCell(label);
                    label = new Label(1, row, answerText);
                    sheet.addCell(label);
                    label = new Label(2, row, String.valueOf(score));
                    sheet.addCell(label);
                    label = new Label(3, row,String.valueOf(confidence));
                    sheet.addCell(label);
                    label = new Label(4, row, Rating);
                    sheet.addCell(label);
                    label = new Label(5, row, String.valueOf(Rank));
                    sheet.addCell(label);
                    label = new Label(6, row, Found);
                    sheet.addCell(label);
                   
                    
                    row ++;
                    
  
                }
                
                 Iterator it2 = responseList.entrySet().iterator();
                 while (it2.hasNext()) {
                    
                    HashMap.Entry pair2 = (HashMap.Entry) it2.next();
                    System.out.println(pair2.getKey() + " = " + pair2.getValue());
                    
                    String answerText = pair2.getKey().toString();
                    String evidence = "null";
                    Double confidence = 0.0;
                    Double score = 0.0;
                    int Rank = -1;
                    String Found = "NO";
                    String Rating = pair2.getValue().toString();
                    
                     label = new Label(0,row, currentQuestion);
                    sheet.addCell(label);
                    label = new Label(1, row, answerText);
                    sheet.addCell(label);
                    label = new Label(2, row, String.valueOf(score));
                    sheet.addCell(label);
                    label = new Label(3, row,String.valueOf(confidence));
                    sheet.addCell(label);
                    label = new Label(4, row, Rating);
                    sheet.addCell(label);
                    label = new Label(5, row, String.valueOf(Rank));
                    sheet.addCell(label);
                    label = new Label(6, row, Found);
                    sheet.addCell(label);
                    
                    
                    row ++;
                    
                    it2.remove(); // avoids a ConcurrentModificationException
                }
                
                
                
                

              
                System.out.println("*************************************");
                it.remove(); // avoids a ConcurrentModificationException    
            }

            workbook.write();
            workbook.close();
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Cannot Access the workbook !");
            return false;

        }
        
       

    }

    public ArrayList<Integer> retrieveAnswers(String projectID, String question, String expectedAnswer) {

        ArrayList<Integer> analysis = new ArrayList<Integer>();

        analysis.add(0);
        analysis.add(-1);
        analysis.add(-1);
        analysis.add(-1);

        try {

            String contextID = InvokeQACoreAPI.getContextID(projectID).replaceAll("\n", "");

            System.out.println(" The contextID is :[" + contextID + "]");

            String answerString = InvokeQACoreAPI.getAnswer(contextID, projectID, question);
            // System.out.println(" The retrieved answer is :" + answerString);
            JSONArray jsonArray = new JSONArray(answerString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                String answerText = job.getJSONObject("answer").getString("text");
                String evidence = html2text(job.getJSONObject("answer").getJSONObject("evidence").getString("text"));
                int confidence = job.getJSONObject("answer").getInt("confidence");
                Double score = job.getJSONObject("answer").getDouble("score");

                System.out.println("ANSWER TEXT (" + i + "):[" + answerText + "]");
                System.out.println("EVIDENCE TEXT (" + i + "):[" + evidence + "]");
                System.out.println("CONFIDENCE (" + i + "):[" + confidence + "]");
                System.out.println("SCORE (" + i + "):[" + score + "]");

                if (evidence.contains(expectedAnswer) || answerText.contains(expectedAnswer)) {
                    analysis.clear();
                    analysis.add(1);
                    analysis.add(i);
                    analysis.add(evidence.length() + answerText.length());
                    analysis.add(jsonArray.length());

                    System.out.println("Analysis is " + analysis.toString());

                    return analysis;
                }

                System.out.println("*********************************************");
            }

        } catch (Exception e) {

            System.out.println("Exception :" + e.getMessage());
            e.printStackTrace();
            return analysis;
        }

        return analysis;

    }

    public String html2text(String html) {

        return Jsoup.parse(html).text();

    }

}
