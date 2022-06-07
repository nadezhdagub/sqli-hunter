package com.example;

import java.util.regex.*;

public class RegexObj {

	public RegexObj(String regexPattern, String description){
		this.regexPattern = Pattern.compile(regexPattern);
		this.description = description;
	}
	
	// the pattern to compile
	private Pattern regexPattern;

	public Pattern getRegexPattern() {
		return regexPattern;
	}

	public void setRegexPattern(Pattern regexPattern) {
		this.regexPattern = regexPattern;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

}
