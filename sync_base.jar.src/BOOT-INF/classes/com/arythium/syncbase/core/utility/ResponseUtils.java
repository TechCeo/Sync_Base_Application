/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.utility;
/*    */ 
/*    */ import com.arythium.syncbase.core.dto.ResponseDTO;
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResponseUtils
/*    */ {
/*    */   public static ResponseDTO createDefaultSuccessResponse() {
/* 18 */     ResponseDTO response = new ResponseDTO();
/* 19 */     response.setRespCode("00");
/* 20 */     response.setRespDescription("success");
/* 21 */     return response;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResponseDTO createResponse(Object data) {
/* 27 */     ResponseDTO response = new ResponseDTO();
/* 28 */     response.setRespCode("00");
/* 29 */     response.setRespDescription("success");
/* 30 */     response.setRespBody(data);
/* 31 */     return response;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ResponseDTO createResponse(String code, String message) {
/* 36 */     ResponseDTO response = new ResponseDTO();
/* 37 */     response.setRespCode(code);
/* 38 */     response.setRespDescription(message);
/* 39 */     return response;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ResponseDTO createResponse(String code, String message, Object body) {
/* 44 */     ResponseDTO response = new ResponseDTO();
/* 45 */     response.setRespCode(code);
/* 46 */     response.setRespDescription(message);
/* 47 */     response.setRespBody(body);
/* 48 */     return response;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ResponseDTO createDefaultFailureResponse() {
/* 53 */     ResponseDTO response = new ResponseDTO();
/* 54 */     response.setRespCode("999");
/* 55 */     response.setRespDescription("Error performing operation");
/* 56 */     return response;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ResponseDTO createFailureResponse(String errorMessage) {
/* 61 */     ResponseDTO response = new ResponseDTO();
/* 62 */     response.setRespCode("999");
/* 63 */     response.setRespDescription(errorMessage);
/* 64 */     return response;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResponseDTO createSuccessResponse(String message) {
/* 70 */     ResponseDTO response = new ResponseDTO();
/* 71 */     response.setRespCode("00");
/* 72 */     response.setRespDescription(message);
/* 73 */     return response;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResponseDTO createFailureResponse(String code, String errorMessage) {
/* 79 */     ResponseDTO response = new ResponseDTO();
/* 80 */     response.setRespCode(code);
/* 81 */     response.setRespDescription(errorMessage);
/* 82 */     return response;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Object convertResponseBodyToClassInstance(ResponseDTO response, Class to) throws IOException {
/* 87 */     ObjectMapper objectMapper = new ObjectMapper();
/* 88 */     String body = objectMapper.writeValueAsString(response.getRespBody());
/* 89 */     return objectMapper.readValue(body, to);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\utility\ResponseUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */