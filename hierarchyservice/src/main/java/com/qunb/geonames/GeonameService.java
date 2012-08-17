package com.qunb.geonames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

public class GeonameService {
	public static void main(String[] args) throws IOException, JSONException{
		String geoname = "berlin";
		Map<String, RelationType> map_relation = new HashMap<String, RelationType>();
		map_relation.put("parent", RelationType.IS_COMPOSED_OF);
		map_relation.put("children", RelationType.COMPOSE);
		Map<String, List<String>> result = GeoName.findRelation(geoname, map_relation.values());
		
		for (String relation : result.keySet()) {
			System.out.println(relation + " of "+ geoname+":");
			for (String geo : result.get(relation)) {
				System.out.println("\t"+geo);
			}
		}
	}
	
	public enum RelationType {
		IS_COMPOSED_OF, COMPOSE;
	}

}
