package com.qunb.freebase;

import static com.freebase.json.JSON.a;
import static com.freebase.json.JSON.o;

import com.freebase.json.JSON;

public class FreebaseQuery {

	public static JSON getChildrenQuery(String uri, FreebaseCategory cat) {
		uri = FreebaseHandler.getFreeBaseId(uri);
		JSON query;
		switch (cat) {
		case Geography:
			query = a(o("/location/location/containedby", a(o("id", uri)),
					"mid", null));
			break;
		case Animal:
			query = a(o(
					"/biology/organism_classification/higher_classification",
					a(o("id", uri)), "mid", null));
			break;
		case Film:
			query = a(o("!pd:/film/film/genre",
					a(o("!index", null, "id", uri)), "mid", null, "sort",
					"!pd:/film/film/genre.!index"));
			break;
		case Music:
			query = a(o("!pd:/music/album/genre",
					a(o("!index", null, "id", uri)), "mid", null, "sort",
					"!pd:/music/album/genre.!index"));
			break;
		case Organization:
			query = a(o("!pd:/organization/organization/child",
					a(o("!index", null, "id", uri)), "child",
					a(o("mid", null, "limit", 1)), "limit", 60, "sort",
					"!pd:/organization/organization/child.!index", "type",
					"/organization/organization_relationship"));
			break;
		case Film_Genre:
			query = a(o("/media_common/media_genre/parent_genre",
					a(o("id", uri)), "mid", null));
			break;
		case Music_Genre:
			query = a(o("/music/genre/parent_genre", a(o("id", uri)), "mid",
					null));
			break;
		default:
			query = null;
		}
		return query;
	}

	public static JSON getParentQuery(String uri, FreebaseCategory cat) {
		uri = FreebaseHandler.getFreeBaseId(uri);
		JSON query;
		switch (cat) {
		case Geography:
			query = a(o("/location/location/contains", a(o("id", uri)), "mid",
					null));
			break;
		case Animal:
			query = a(o(
					"/biology/organism_classification/lower_classifications",
					a(o("id", uri)), "mid", null));
			break;
		case Film:
			query = a(o("!pd:/film/film/genre",
					a(o("!index", null, "id", uri)), "mid", null, "sort",
					"!pd:/film/film/genre.!index"));
			break;
		case Music:
			query = a(o("!pd:/music/album/genre",
					a(o("!index", null, "id", uri)), "mid", null, "sort",
					"!pd:/music/album/genre.!index"));
			break;
		case Politician:
			query = a(o("!pd:/government/politician/party",
					a(o("!index", null, "id", uri)), "party",
					a(o("mid", null, "limit", 1)), "limit", 60, "sort",
					"!pd:/government/politician/party.!index", "type",
					"/government/political_party_tenure"));
			break;
		case Organization:
			query = a(o("!pd:/organization/organization/parent",
					a(o("!index", null, "id", uri)), "parent",
					a(o("mid", null, "limit", 1)), "limit", 60, "sort",
					"!pd:/organization/organization/parent.!index", "type",
					"/organization/organization_relationship"));
			break;
		case Film_Genre:
			query = a(o("/media_common/media_genre/child_genres",
					a(o("id", uri)), "mid", null));
			break;
		case Music_Genre:
			query = a(o("/music/genre/subgenre", a(o("id", uri)), "mid", null));
			break;
		default:
			query = null;
		}
		return query;
	}

}
