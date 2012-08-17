package com.qunb.freebase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qunb.geonames.GeonameService.RelationType;

public class FreebaseService {
	
	public static void main(String[] args) throws Exception {
		String uri = "http://www.freebase.com/view/en/berlin";
		
		Map<String, RelationType> map_relation = new HashMap<String, RelationType>();
		map_relation.put("parent", RelationType.IS_COMPOSED_OF);
		map_relation.put("children", RelationType.COMPOSE);
		Map<String, List<String>> result = FreebaseHandler.findRelation(uri, map_relation.values());
		
		for (String relation : result.keySet()) {
			System.out.println(relation + " of "+ uri+":");
			for (String fb : result.get(relation)) {
				System.out.println("\t"+fb);
			}
		}
	}
	
	public enum RelationType {
		IS_COMPOSED_OF, COMPOSE;
	}
}
