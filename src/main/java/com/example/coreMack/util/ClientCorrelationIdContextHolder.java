package com.example.coreMack.util;

public class ClientCorrelationIdContextHolder {
    private static final ThreadLocal<String> correlationIdContextHolder = new ThreadLocal<>();

    public static void setCorrelationId(String correlationId){
        String lastCorrelationId = correlationIdContextHolder.get();
        if (lastCorrelationId==null)
            correlationIdContextHolder.set(correlationId);
    }
    public static String getCorrelationId(){
        return correlationIdContextHolder.get();
    }
    public static void clearContext(){
        correlationIdContextHolder.remove();
    }
}
