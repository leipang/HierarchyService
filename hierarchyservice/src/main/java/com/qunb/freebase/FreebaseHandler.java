package com.qunb.freebase;

import static com.freebase.json.JSON.a;
import static com.freebase.json.JSON.o;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.freebase.api.Freebase;
import com.freebase.json.JSON;
import com.qunb.freebase.FreebaseService.RelationType;

public class FreebaseHandler {
	
	public static String getFreeBaseId(String uri){
		int position=uri.indexOf("view");
		String Id = uri.substring(position+4);
		return Id;
	}
	
	public static FreebaseCategory findField(String uri){
		uri = getFreeBaseId(uri);
		JSON query = a(o("type", "/type/type","name",null,"instance",o("id",uri,"limit",0)));
		Freebase freebase = Freebase.getFreebase();
		JSON result = freebase.mqlread(query);
		for(int i =0;i<result.get("result").array().size();i++){
			String fld = result.get("result").get(i).get("name").toString().replaceAll("\"", "");
			if(FreebaseCategory.searchCat(fld)!=null){
				return FreebaseCategory.searchCat(fld);
			}
		}
		return null;
	}
	
	public static JSON getParent(String uri,FreebaseCategory cat){
		JSON parent_query = FreebaseQuery.getParentQuery(uri, cat);
		Freebase freebase = Freebase.getFreebase();
		JSON result = freebase.mqlread(parent_query);
		return result;
	}
	
	public static JSON getChildren(String uri,FreebaseCategory cat){
		JSON children_query = FreebaseQuery.getChildrenQuery(uri,cat);
		Freebase freebase = Freebase.getFreebase();
		JSON result = freebase.mqlread(children_query);
		return result;
	}
	
	public static List<String> toParentList(JSON parents,FreebaseCategory cat){
		List<String> list = new ArrayList<String>();
		if(cat.equals(FreebaseCategory.Organization)){
			for (int i=0;i<parents.get("result").array().size();i++) {
				String child = parents.get("result").get(i).get("parent").get(0).get("mid").toString().replace("\\", "");
				list.add(i,child);
			}
		}
		else if(cat.equals(FreebaseCategory.Politician)){
			for (int i=0;i<parents.get("result").array().size();i++) {
				String child = parents.get("result").get(i).get("party").get(0).get("mid").toString().replace("\\", "");
				list.add(i,child);
			}
		}
		else{
			for (int i=0;i<parents.get("result").array().size();i++) {
				String child = parents.get("result").get(i).get("mid").toString().replace("\\", "");
				list.add(i,child);
			}
		}
		return list;
	}
	
	// convert JSON to List<String>
	public static List<String> toChildrenList(JSON children,FreebaseCategory cat){
		List<String> list = new ArrayList<String>();
		if(cat.equals(FreebaseCategory.Organization)){
			for (int i=0;i<children.get("result").array().size();i++) {
				String child = children.get("result").get(i).get("child").get(0).get("mid").toString().replace("\\", "");
				list.add(i,child);
			}
		}
		else{
			for (int i=0;i<children.get("result").array().size();i++) {
				String child = children.get("result").get(i).get("mid").toString().replace("\\", "");
				list.add(i,child);
			}
		}
		return list;
	}
	
	public static Map<String, List<String>> findRelation(String uri,Collection<RelationType> relations){
		FreebaseCategory cat = findField(uri);
		Map<String, List<String>> output = new HashMap<String, List<String>>();
		if(cat==null){
			output = null;
			return output;
		}
		if(relations.contains(RelationType.IS_COMPOSED_OF)){
			output.put("Parent", toParentList(getParent(uri,cat),cat));
		}
		if(!(cat.equals(FreebaseCategory.Film)||cat.equals(FreebaseCategory.Music)||cat.equals(FreebaseCategory.Politician ))&&relations.contains(RelationType.COMPOSE)){
			output.put("Children", toChildrenList(getChildren(uri,cat),cat));
		}
		return output;
	}
}
