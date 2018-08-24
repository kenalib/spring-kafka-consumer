package com.example.springkafkaconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrimePayload {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("Case Number")
    private String caseNumber;
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Block")
    private String block;
    @JsonProperty("IUCR")
    private String iucr;
    @JsonProperty("Primary Type")
    private String primaryType;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Location Description")
    private String locationDescription;
    @JsonProperty("Arrest")
    private String arrest;
    @JsonProperty("Domestic")
    private String domestic;
    @JsonProperty("Beat")
    private String beat;
    @JsonProperty("District")
    private String district;
    @JsonProperty("Ward")
    private String ward;
    @JsonProperty("Community Area")
    private String communityArea;
    @JsonProperty("FBI Code")
    private String fbiCode;
    @JsonProperty("X Coordinate")
    private String xCoordinate;
    @JsonProperty("Y Coordinate")
    private String yCoordinate;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Updated On")
    private String updatedOn;
    @JsonProperty("Latitude")
    private String latitude;
    @JsonProperty("Longitude")
    private String longitude;
    @JsonProperty("Location")
    private String location;

    public CrimePayload() {
    }

    public CrimePayload(String ID, String caseNumber, String date, String block, String iucr, String primaryType, String description, String locationDescription, String arrest, String domestic, String beat, String district, String ward, String communityArea, String fbiCode, String xCoordinate, String yCoordinate, String year, String updatedOn, String latitude, String longitude, String location) {
        this.id = ID;
        this.caseNumber = caseNumber;
        this.date = date;
        this.block = block;
        this.iucr = iucr;
        this.primaryType = primaryType;
        this.description = description;
        this.locationDescription = locationDescription;
        this.arrest = arrest;
        this.domestic = domestic;
        this.beat = beat;
        this.district = district;
        this.ward = ward;
        this.communityArea = communityArea;
        this.fbiCode = fbiCode;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.year = year;
        this.updatedOn = updatedOn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getIucr() {
        return iucr;
    }

    public void setIucr(String iucr) {
        this.iucr = iucr;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getArrest() {
        return arrest;
    }

    public void setArrest(String arrest) {
        this.arrest = arrest;
    }

    public String getDomestic() {
        return domestic;
    }

    public void setDomestic(String domestic) {
        this.domestic = domestic;
    }

    public String getBeat() {
        return beat;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCommunityArea() {
        return communityArea;
    }

    public void setCommunityArea(String communityArea) {
        this.communityArea = communityArea;
    }

    public String getFbiCode() {
        return fbiCode;
    }

    public void setFbiCode(String fbiCode) {
        this.fbiCode = fbiCode;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CrimePayload: " + this.id + " " + this.caseNumber;
    }
}
