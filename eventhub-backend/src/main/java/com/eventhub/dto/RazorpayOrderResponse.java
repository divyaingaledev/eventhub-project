
package com.eventhub.dto;

public class RazorpayOrderResponse {

    private String orderId;
    private Long bookingId;
    private String bookingReference;
    private Long amount;
    private String currency;
    private String keyId;

    public RazorpayOrderResponse() {
    }

    public RazorpayOrderResponse(
            String orderId,
            Long bookingId,
            String bookingReference,
            Long amount,
            String currency,
            String keyId) {

        this.orderId = orderId;
        this.bookingId = bookingId;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.currency = currency;
        this.keyId = keyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}

