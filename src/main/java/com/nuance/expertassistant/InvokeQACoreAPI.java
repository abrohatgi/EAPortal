/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import com.nuance.expertassistant.crawler4j.EAController;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author abhishek_rohatgi
 */
public class InvokeQACoreAPI {

    static String url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");

    public static void main(String args[]) {
        System.out.println(" The status is :[" + getContextID("audihelp") + "]");

    }

    public static JSONObject getProjectList() {
        url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        String localurl = url + "getProjectList";

        try {
            String jsonString = HTTPConnection.sendPost(localurl, null);
            String jsonResponse = "{\"response\":" + jsonString + "}";
            JSONObject object = new JSONObject(jsonResponse);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String callAnnotator(String projectID, String projectName, String URL) {
        return createNewProject(projectName, projectID, "LOCAL_WEB", URL, URL, "3", "300");
    }

    public static String createNewProject(String projectName, String ProjectID, String ProjectType, String URL, String CrawlPattern, String CrawlDepth, String UrlLimit) {
        url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        String htmlFilePath = LoadConfig.getValueFromFile("HTML_PATH", "https://db.tt/hVkpsluN");
        String localurl = url + "newProject";

        HashMap<String, String> paramMap = new HashMap<String, String>();

        if (ProjectType.equalsIgnoreCase("LOCAL_WEB")) {
            int depth = Integer.parseInt(CrawlDepth);
            int maxPages = Integer.parseInt(UrlLimit);

            EAController.crawlWebSite(ProjectID, URL, localurl, maxPages, depth);
            paramMap.put("projectName", projectName);
            paramMap.put("projectId", ProjectID);
            paramMap.put("projectType", "LOCAL_WEB");
            paramMap.put("url", htmlFilePath + "/" + ProjectID + "/");

            paramMap.put("crawlPattern", URL);
            paramMap.put("crawlDepth", CrawlDepth);
            paramMap.put("maxPages", UrlLimit);

            paramMap.put("cssContentSelector", "div.content");
            paramMap.put("resourceBaseUrl", URL);

        } else {

            paramMap.put("projectName", projectName);
            paramMap.put("projectId", ProjectID);
            paramMap.put("projectType", ProjectType);

            paramMap.put("url", URL);
            paramMap.put("crawlPattern", CrawlPattern);
            paramMap.put("crawlDepth", CrawlDepth);
            paramMap.put("maxPages", UrlLimit);
        }

        try {
            HTTPConnection.sendPost(localurl, paramMap);
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";

    }

    public static String getProjectStatus(String projectID) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("projectId", projectID);
        url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        String localurl = url + "getProjectStatus";
        try {
            String str = HTTPConnection.sendPost(localurl, paramMap);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deleteProject(String projectID) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("projectId", projectID);
        url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        String localurl = url + "deleteProject";
        try {
            String str = HTTPConnection.sendPost(localurl, paramMap);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO";
    }

    public static String getAnswer(String ContextID, String ProjectID, String QuestionText) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("contextId", ContextID);
        url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        // paramMap.put("projectId", ProjectID);
        paramMap.put("question", QuestionText);

        String localURL = url + "question";

        try {
            String answerString = HTTPConnection.sendPost(localURL, paramMap);
            //String jsonResponse = "{\"response\":" + jsonString + "}";
            //JSONObject object = new JSONObject(jsonResponse);
            return answerString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContextID(String ProjectID) {

        System.out.println(" The project id is :" + ProjectID);

        url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("projectId", ProjectID);
        String localurl = url + "context?";

        System.out.println(" The project URL is :" + url);

        try {
            String ContextID = HTTPConnection.sendPost(localurl, paramMap);

            System.out.println(" The context ID is :" + ContextID);

            if (ContextID != null && !ContextID.isEmpty()) {
                return ContextID;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
