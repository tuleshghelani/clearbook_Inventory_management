package com.accounting.enums;

public enum StockMovementType {
    PURCHASE_ORDER("PO"),
    SALES_ORDER("SO"),
    STOCK_ADJUSTMENT("ADJ"),
    OPENING_BALANCE("OB");
    
    private final String code;
    
    StockMovementType(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
} 