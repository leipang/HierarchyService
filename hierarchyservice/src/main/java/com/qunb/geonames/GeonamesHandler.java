package com.qunb.geonames;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeonamesHandler {
	private static String[] user = {"jbtheard","jbtheardgmail","cvinceyqunb","jbtheard01","jbtheard02","jbtheard03","jbtheard04","jbtheard05","jbtheard06","jbtheard07","jbtheard08","jbtheard09","jbtheard10","jbtheard11","jbtheard12","jbtheard13","jbtheard14","jbtheard15","jbtheard16","jbtheard17","jbtheard18","jbtheard19","jbtheard20","jbtheard21","jbtheard22","jbtheard23","jbtheard24","jbtheard25","jbtheard26","jbtheard27","jbtheard28","jbtheard29","jbtheard30","jbtheard31","jbtheard32","jbtheard33","leipang"};
	private static int userNb = 0;
	
	public static String getParent(String id) throws JSONException{
           String strUrl = "http://api.geonames.org/hierarchyJSON?geonameId="+id+"&username="
    				+ user[userNb];
            String callService = RestHandler.callSvc(strUrl);
            if(callService.contains("limit")){
            	userNb++;
            	if(userNb>=user.length){
            		userNb=0;
            	}
            	return getParent(id);
            }
            JSONObject result = new JSONObject(callService);
            JSONArray parents = (JSONArray) result.get("geonames");
            String geo = parents.getJSONObject(parents.length()-1).get("name").toString();
            for(int i = parents.length()-1;i>=0;i--){
            	JSONObject tmp = parents.getJSONObject(i);
            	if(!tmp.get("name").toString().equals(geo)){
            		return tmp.get("geonameId").toString();
            	}
            }
		return parents.getJSONObject(0).get("geonameId").toString();
	}
	
	public static List<String> getChildren(String id) throws JSONException{
		List<String> childrenId = new ArrayList<String>();
		String strUrl = "http://api.geonames.org/childrenJSON?geonameId="+id+"&username="
				+ user[userNb];
		String callService = RestHandler.callSvc(strUrl);
        if(callService.contains("limit")){
        	userNb++;
        	if(userNb>=user.length){
        		userNb=0;
        	}
        	return getChildren(id);
        }
        JSONObject result = new JSONObject(callService);
        JSONArray children = (JSONArray) result.get("geonames");
        for(int i = 0;i<children.length();i++){
        	JSONObject tmp = children.getJSONObject(i);
        	childrenId.add(tmp.get("geonameId").toString());
        }
        return childrenId;
	}
	
	public static String searchId(String name) throws JSONException{
		String strUrl = "http://api.geonames.org/searchJSON?q="+name+"&maxRows=1&username="
				+ user[userNb];
		String callService = RestHandler.callSvc(strUrl);
        if(callService.contains("limit")){
        	userNb++;
        	if(userNb>=user.length){
        		userNb=0;
        	}
        	return searchId(name);
        }
        JSONObject result = new JSONObject(callService);
        JSONArray geo = (JSONArray) result.get("geonames");
        return geo.getJSONObject(0).get("geonameId").toString();
	}
	
	public static String searchName(String id) throws JSONException{
		String strUrl = "http://api.geonames.org/getJSON?geonameId="+id+"&username="
				+ user[userNb];
		String callService = RestHandler.callSvc(strUrl);
        if(callService.contains("limit")){
        	userNb++;
        	if(userNb>=user.length){
        		userNb=0;
        	}
        	return searchName(id);
        }
        JSONObject result = new JSONObject(callService);
        return result.get("name").toString();
	}
	
	private static String checkId(String id) throws JSONException{
		 String strUrl = "http://api.geonames.org/hierarchyJSON?geonameId="+id+"&username="
 				+ user[userNb];
         String callService = RestHandler.callSvc(strUrl);
         if(callService.contains("limit")){
         	userNb++;
         	if(userNb>=user.length){
         		userNb=0;
         	}
         	return checkId(id);
         }
         JSONObject result = new JSONObject(callService);
         JSONArray parents = (JSONArray) result.get("geonames");
         String geo = parents.getJSONObject(parents.length()-1).get("name").toString();
         String tmpgeo = parents.getJSONObject(parents.length()-1).get("geonameId").toString();
         for(int i = parents.length()-1;i>=0;i--){
         	JSONObject tmp = parents.getJSONObject(i);
         	if(tmp.get("name").toString().equals(geo)){
         		tmpgeo = tmp.get("geonameId").toString();
         	}
         }
         return tmpgeo;
	}
}
