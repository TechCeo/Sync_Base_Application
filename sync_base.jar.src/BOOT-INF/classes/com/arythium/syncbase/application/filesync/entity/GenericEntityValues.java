/*    */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.entity;
/*    */ 
/*    */ 
/*    */ public class GenericEntityValues {
/*    */   ArrayList<String> stringArrayList;
/*    */   
/*  7 */   public String toString() { return "GenericEntityValues(stringArrayList=" + getStringArrayList() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object<String> $stringArrayList = (Object<String>)getStringArrayList(); return result * 59 + (($stringArrayList == null) ? 43 : $stringArrayList.hashCode()); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.application.filesync.entity.GenericEntityValues; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.application.filesync.entity.GenericEntityValues)) return false;  com.arythium.syncbase.application.filesync.entity.GenericEntityValues other = (com.arythium.syncbase.application.filesync.entity.GenericEntityValues)o; if (!other.canEqual(this)) return false;  Object<String> this$stringArrayList = (Object<String>)getStringArrayList(), other$stringArrayList = (Object<String>)other.getStringArrayList(); return !((this$stringArrayList == null) ? (other$stringArrayList != null) : !this$stringArrayList.equals(other$stringArrayList)); } public void setStringArrayList(ArrayList<String> stringArrayList) { this.stringArrayList = stringArrayList; }
/*    */   
/*    */   public ArrayList<String> getStringArrayList() {
/* 10 */     return this.stringArrayList;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\entity\GenericEntityValues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */