/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.dto;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResponseDTO
/*    */ {
/*    */   private String respCode;
/*    */   private String respDescription;
/*    */   private Object respBody;
/*    */   
/*    */   public void setRespCode(String respCode) {
/* 13 */     this.respCode = respCode; } public void setRespDescription(String respDescription) { this.respDescription = respDescription; } public void setRespBody(Object respBody) { this.respBody = respBody; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.dto.ResponseDTO)) return false;  com.arythium.syncbase.core.dto.ResponseDTO other = (com.arythium.syncbase.core.dto.ResponseDTO)o; if (!other.canEqual(this)) return false;  Object this$respCode = getRespCode(), other$respCode = other.getRespCode(); if ((this$respCode == null) ? (other$respCode != null) : !this$respCode.equals(other$respCode)) return false;  Object this$respDescription = getRespDescription(), other$respDescription = other.getRespDescription(); if ((this$respDescription == null) ? (other$respDescription != null) : !this$respDescription.equals(other$respDescription)) return false;  Object this$respBody = getRespBody(), other$respBody = other.getRespBody(); return !((this$respBody == null) ? (other$respBody != null) : !this$respBody.equals(other$respBody)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.dto.ResponseDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $respCode = getRespCode(); result = result * 59 + (($respCode == null) ? 43 : $respCode.hashCode()); Object $respDescription = getRespDescription(); result = result * 59 + (($respDescription == null) ? 43 : $respDescription.hashCode()); Object $respBody = getRespBody(); return result * 59 + (($respBody == null) ? 43 : $respBody.hashCode()); } public String toString() { return "ResponseDTO(respCode=" + getRespCode() + ", respDescription=" + getRespDescription() + ", respBody=" + getRespBody() + ")"; }
/*    */ 
/*    */   
/* 16 */   public String getRespCode() { return this.respCode; }
/* 17 */   public String getRespDescription() { return this.respDescription; } public Object getRespBody() {
/* 18 */     return this.respBody;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\dto\ResponseDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */