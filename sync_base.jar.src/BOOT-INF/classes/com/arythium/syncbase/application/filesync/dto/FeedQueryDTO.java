/*    */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.dto;
/*    */ 
/*    */ public class FeedQueryDTO {
/*    */   private String queryString;
/*    */   private int numOfValues;
/*    */   private ArrayList<Integer> ignoredCells;
/*    */   private ArrayList<Integer> dateCells;
/*    */   
/*  9 */   public void setQueryString(String queryString) { this.queryString = queryString; } private ArrayList<Integer> timeStampCells; private ArrayList<Integer> yearCells; private Map<Integer, String> patternCells; private ArrayList<Integer> numberCells; private ArrayList<String> cellsDefaultValue; public void setNumOfValues(int numOfValues) { this.numOfValues = numOfValues; } public void setIgnoredCells(ArrayList<Integer> ignoredCells) { this.ignoredCells = ignoredCells; } public void setDateCells(ArrayList<Integer> dateCells) { this.dateCells = dateCells; } public void setTimeStampCells(ArrayList<Integer> timeStampCells) { this.timeStampCells = timeStampCells; } public void setYearCells(ArrayList<Integer> yearCells) { this.yearCells = yearCells; } public void setPatternCells(Map<Integer, String> patternCells) { this.patternCells = patternCells; } public void setNumberCells(ArrayList<Integer> numberCells) { this.numberCells = numberCells; } public void setCellsDefaultValue(ArrayList<String> cellsDefaultValue) { this.cellsDefaultValue = cellsDefaultValue; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.application.filesync.dto.FeedQueryDTO)) return false;  com.arythium.syncbase.application.filesync.dto.FeedQueryDTO other = (com.arythium.syncbase.application.filesync.dto.FeedQueryDTO)o; if (!other.canEqual(this)) return false;  Object this$queryString = getQueryString(), other$queryString = other.getQueryString(); if ((this$queryString == null) ? (other$queryString != null) : !this$queryString.equals(other$queryString)) return false;  if (getNumOfValues() != other.getNumOfValues()) return false;  Object<Integer> this$ignoredCells = (Object<Integer>)getIgnoredCells(), other$ignoredCells = (Object<Integer>)other.getIgnoredCells(); if ((this$ignoredCells == null) ? (other$ignoredCells != null) : !this$ignoredCells.equals(other$ignoredCells)) return false;  Object<Integer> this$dateCells = (Object<Integer>)getDateCells(), other$dateCells = (Object<Integer>)other.getDateCells(); if ((this$dateCells == null) ? (other$dateCells != null) : !this$dateCells.equals(other$dateCells)) return false;  Object<Integer> this$timeStampCells = (Object<Integer>)getTimeStampCells(), other$timeStampCells = (Object<Integer>)other.getTimeStampCells(); if ((this$timeStampCells == null) ? (other$timeStampCells != null) : !this$timeStampCells.equals(other$timeStampCells)) return false;  Object<Integer> this$yearCells = (Object<Integer>)getYearCells(), other$yearCells = (Object<Integer>)other.getYearCells(); if ((this$yearCells == null) ? (other$yearCells != null) : !this$yearCells.equals(other$yearCells)) return false;  Object<Integer, String> this$patternCells = (Object<Integer, String>)getPatternCells(), other$patternCells = (Object<Integer, String>)other.getPatternCells(); if ((this$patternCells == null) ? (other$patternCells != null) : !this$patternCells.equals(other$patternCells)) return false;  Object<Integer> this$numberCells = (Object<Integer>)getNumberCells(), other$numberCells = (Object<Integer>)other.getNumberCells(); if ((this$numberCells == null) ? (other$numberCells != null) : !this$numberCells.equals(other$numberCells)) return false;  Object<String> this$cellsDefaultValue = (Object<String>)getCellsDefaultValue(), other$cellsDefaultValue = (Object<String>)other.getCellsDefaultValue(); return !((this$cellsDefaultValue == null) ? (other$cellsDefaultValue != null) : !this$cellsDefaultValue.equals(other$cellsDefaultValue)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.application.filesync.dto.FeedQueryDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $queryString = getQueryString(); result = result * 59 + (($queryString == null) ? 43 : $queryString.hashCode()); result = result * 59 + getNumOfValues(); Object<Integer> $ignoredCells = (Object<Integer>)getIgnoredCells(); result = result * 59 + (($ignoredCells == null) ? 43 : $ignoredCells.hashCode()); Object<Integer> $dateCells = (Object<Integer>)getDateCells(); result = result * 59 + (($dateCells == null) ? 43 : $dateCells.hashCode()); Object<Integer> $timeStampCells = (Object<Integer>)getTimeStampCells(); result = result * 59 + (($timeStampCells == null) ? 43 : $timeStampCells.hashCode()); Object<Integer> $yearCells = (Object<Integer>)getYearCells(); result = result * 59 + (($yearCells == null) ? 43 : $yearCells.hashCode()); Object<Integer, String> $patternCells = (Object<Integer, String>)getPatternCells(); result = result * 59 + (($patternCells == null) ? 43 : $patternCells.hashCode()); Object<Integer> $numberCells = (Object<Integer>)getNumberCells(); result = result * 59 + (($numberCells == null) ? 43 : $numberCells.hashCode()); Object<String> $cellsDefaultValue = (Object<String>)getCellsDefaultValue(); return result * 59 + (($cellsDefaultValue == null) ? 43 : $cellsDefaultValue.hashCode()); } public String toString() { return "FeedQueryDTO(queryString=" + getQueryString() + ", numOfValues=" + getNumOfValues() + ", ignoredCells=" + getIgnoredCells() + ", dateCells=" + getDateCells() + ", timeStampCells=" + getTimeStampCells() + ", yearCells=" + getYearCells() + ", patternCells=" + getPatternCells() + ", numberCells=" + getNumberCells() + ", cellsDefaultValue=" + getCellsDefaultValue() + ")"; }
/*    */ 
/*    */   
/* 12 */   public String getQueryString() { return this.queryString; }
/* 13 */   public int getNumOfValues() { return this.numOfValues; }
/* 14 */   public ArrayList<Integer> getIgnoredCells() { return this.ignoredCells; }
/* 15 */   public ArrayList<Integer> getDateCells() { return this.dateCells; }
/* 16 */   public ArrayList<Integer> getTimeStampCells() { return this.timeStampCells; }
/* 17 */   public ArrayList<Integer> getYearCells() { return this.yearCells; }
/* 18 */   public Map<Integer, String> getPatternCells() { return this.patternCells; }
/* 19 */   public ArrayList<Integer> getNumberCells() { return this.numberCells; } public ArrayList<String> getCellsDefaultValue() {
/* 20 */     return this.cellsDefaultValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\dto\FeedQueryDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */