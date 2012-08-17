package com.qunb.geonames;

import static org.junit.Assert.*;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class GeonamesHandlerTest {
	
	@Test
	public void testGetParent() throws JSONException{
		String childId = "2657896";
		String parent =GeonamesHandler.getParent(childId);
		String expected = "7287650";
		assertEquals(expected,parent);
	}
	
	@Test
	public void testGetChildren() throws JSONException{
		String parentId = "3175395";
		List<String> childrent =GeonamesHandler.getChildren(parentId);
		int expected = 20;
		assertEquals(expected,childrent.size());
	}
	
	@Test
	public void testSearchName() throws JSONException{
		String geoId = "3175395";
		String name = GeonamesHandler.searchName(geoId);
		String expected = "Italy";
		assertEquals(expected,name);
	}

}
