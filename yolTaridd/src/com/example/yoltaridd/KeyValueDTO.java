package com.example.yoltaridd;

public class KeyValueDTO {
@Override
	public String toString() {
		return value;
	}
String key;
String value;

public KeyValueDTO(String key, String value) {
	super();
	this.key = key;
	this.value = value;
}
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
}
