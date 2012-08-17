package com.qunb.freebase;

public enum FreebaseCategory {
	Geography		("Location"), 
	Animal 			("Animal"),
	Film			("Film"),
	Music			("Music Album"),
	Organization	("Organization"),
	Film_Genre		("Film genre"),
	Music_Genre		("Music genre"),
	Politician		("Politician");
	
	
	private String fbName;
	
	FreebaseCategory(String fbname){
		this.fbName = fbname;
	}
	
	public String getFbName(){
		return this.fbName;
	}
	
	public static FreebaseCategory searchCat(String name){
		for(FreebaseCategory cat: FreebaseCategory.values()){
			if(cat.getFbName().toLowerCase().equals(name.toLowerCase())){
				return cat;
			}
		}
		return null;
	}
}
