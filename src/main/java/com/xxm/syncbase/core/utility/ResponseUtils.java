package com.xxm.syncbase.core.utility;

import com.xxm.syncbase.core.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ResponseUtils {
    public ResponseUtils() {
    }

    public static ResponseDTO createDefaultSuccessResponse() {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode("00");
        response.setRespDescription("success");
        return response;
    }

    public static ResponseDTO createResponse(Object data) {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode("00");
        response.setRespDescription("success");
        response.setRespBody(data);
        return response;
    }

    public static ResponseDTO createResponse(String code, String message) {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode(code);
        response.setRespDescription(message);
        return response;
    }

    public static ResponseDTO createResponse(String code, String message, Object body) {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode(code);
        response.setRespDescription(message);
        response.setRespBody(body);
        return response;
    }

    public static ResponseDTO createDefaultFailureResponse() {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode("999");
        response.setRespDescription("Error performing operation");
        return response;
    }

    public static ResponseDTO createFailureResponse(String errorMessage) {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode("999");
        response.setRespDescription(errorMessage);
        return response;
    }

    public static ResponseDTO createSuccessResponse(String message) {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode("00");
        response.setRespDescription(message);
        return response;
    }

    public static ResponseDTO createFailureResponse(String code, String errorMessage) {
        ResponseDTO response = new ResponseDTO();
        response.setRespCode(code);
        response.setRespDescription(errorMessage);
        return response;
    }

    public static Object convertResponseBodyToClassInstance(ResponseDTO response, Class to) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(response.getRespBody());
        return objectMapper.readValue(body, to);
    }
}
