/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nuance.expertassistant;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author abhishek_rohatgi
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of com.nuance.expertassistant.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
       
        return "get-working";
    }

    
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
        System.out.println(" The content is :" + content);
        String[] array = content.split("\\|",-1);
            String url = array[1];
            String urlpattern = array[2];
            String depth  = array[3];
            String urlLimit = array[4];
            String title = array[5];
     
     ContentCrawler.crawlWebSite(url, depth, urlpattern, urlLimit, title);
        
        
    }
}
