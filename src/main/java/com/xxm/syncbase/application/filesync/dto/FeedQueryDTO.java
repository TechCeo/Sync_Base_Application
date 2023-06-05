package com.xxm.syncbase.application.filesync.dto;


import java.util.ArrayList;
import java.util.Map;

public class FeedQueryDTO {
    private String queryString;
    private int numOfValues;
    private ArrayList<Integer> ignoredCells;
    private ArrayList<Integer> dateCells;
    private ArrayList<Integer> timeStampCells;
    private ArrayList<Integer> yearCells;
    private Map<Integer, String> patternCells;
    private ArrayList<Integer> numberCells;
    private ArrayList<String> cellsDefaultValue;

    public FeedQueryDTO() {
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public int getNumOfValues() {
        return numOfValues;
    }

    public void setNumOfValues(int numOfValues) {
        this.numOfValues = numOfValues;
    }

    public ArrayList<Integer> getIgnoredCells() {
        return ignoredCells;
    }

    public void setIgnoredCells(ArrayList<Integer> ignoredCells) {
        this.ignoredCells = ignoredCells;
    }

    public ArrayList<Integer> getDateCells() {
        return dateCells;
    }

    public void setDateCells(ArrayList<Integer> dateCells) {
        this.dateCells = dateCells;
    }

    public ArrayList<Integer> getTimeStampCells() {
        return timeStampCells;
    }

    public void setTimeStampCells(ArrayList<Integer> timeStampCells) {
        this.timeStampCells = timeStampCells;
    }

    public ArrayList<Integer> getYearCells() {
        return yearCells;
    }

    public void setYearCells(ArrayList<Integer> yearCells) {
        this.yearCells = yearCells;
    }

    public Map<Integer, String> getPatternCells() {
        return patternCells;
    }

    public void setPatternCells(Map<Integer, String> patternCells) {
        this.patternCells = patternCells;
    }

    public ArrayList<Integer> getNumberCells() {
        return numberCells;
    }

    public void setNumberCells(ArrayList<Integer> numberCells) {
        this.numberCells = numberCells;
    }

    public ArrayList<String> getCellsDefaultValue() {
        return cellsDefaultValue;
    }

    public void setCellsDefaultValue(ArrayList<String> cellsDefaultValue) {
        this.cellsDefaultValue = cellsDefaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedQueryDTO)) return false;

        FeedQueryDTO that = (FeedQueryDTO) o;

        if (getNumOfValues() != that.getNumOfValues()) return false;
        if (getQueryString() != null ? !getQueryString().equals(that.getQueryString()) : that.getQueryString() != null)
            return false;
        if (getIgnoredCells() != null ? !getIgnoredCells().equals(that.getIgnoredCells()) : that.getIgnoredCells() != null)
            return false;
        if (getDateCells() != null ? !getDateCells().equals(that.getDateCells()) : that.getDateCells() != null)
            return false;
        if (getTimeStampCells() != null ? !getTimeStampCells().equals(that.getTimeStampCells()) : that.getTimeStampCells() != null)
            return false;
        if (getYearCells() != null ? !getYearCells().equals(that.getYearCells()) : that.getYearCells() != null)
            return false;
        if (getPatternCells() != null ? !getPatternCells().equals(that.getPatternCells()) : that.getPatternCells() != null)
            return false;
        if (getNumberCells() != null ? !getNumberCells().equals(that.getNumberCells()) : that.getNumberCells() != null)
            return false;
        return getCellsDefaultValue() != null ? getCellsDefaultValue().equals(that.getCellsDefaultValue()) : that.getCellsDefaultValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getQueryString() != null ? getQueryString().hashCode() : 0;
        result = 31 * result + getNumOfValues();
        result = 31 * result + (getIgnoredCells() != null ? getIgnoredCells().hashCode() : 0);
        result = 31 * result + (getDateCells() != null ? getDateCells().hashCode() : 0);
        result = 31 * result + (getTimeStampCells() != null ? getTimeStampCells().hashCode() : 0);
        result = 31 * result + (getYearCells() != null ? getYearCells().hashCode() : 0);
        result = 31 * result + (getPatternCells() != null ? getPatternCells().hashCode() : 0);
        result = 31 * result + (getNumberCells() != null ? getNumberCells().hashCode() : 0);
        result = 31 * result + (getCellsDefaultValue() != null ? getCellsDefaultValue().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "FeedQueryDTO{" +
                "queryString='" + queryString + '\'' +
                ", numOfValues=" + numOfValues +
                ", ignoredCells=" + ignoredCells +
                ", dateCells=" + dateCells +
                ", timeStampCells=" + timeStampCells +
                ", yearCells=" + yearCells +
                ", patternCells=" + patternCells +
                ", numberCells=" + numberCells +
                ", cellsDefaultValue=" + cellsDefaultValue +
                '}';
    }
}
