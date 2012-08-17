package com.qunb.geonames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.qunb.geonames.GeonameService.RelationType;


public class GeoName {
	private String name;
	private String id;
	private String parentId;
	private List<String> childrenIds;
	
	public GeoName(String name) throws JSONException{
		this.name = name;
		this.id = GeonamesHandler.searchId(name);
		this.parentId = GeonamesHandler.getParent(this.id);
		this.childrenIds = GeonamesHandler.getChildren(this.id);
	}

	
	public String getName(){
		return this.name;
	}
	public String getId(){
		return this.id;
	}
	public String getParent(){
		return this.parentId;
	}
	public List<String> getChildren(){
		return this.childrenIds;
	}
	
	public static Map<String,List<String>> findRelation(String name,Collection<RelationType> relations) throws JSONException{
		GeoName geo = new GeoName(name);
		
		Map<String,List<String>> relationMap = new HashMap<String,List<String>>();
		if(relations.contains(RelationType.IS_COMPOSED_OF)){
			List<String> parent = new ArrayList<String>();
			parent.add(geo.getParent());
			relationMap.put("Parent", parent);
		}
		
		if(relations.contains(RelationType.COMPOSE)){
			relationMap.put("Children", geo.getChildren());
		}
		return relationMap;
	}

}
