package com.example.PHONGTROSPRING.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RechargeRequest {

    @NotNull(message = "Số tiền nạp không được để trống")
    @DecimalMin(value = "50000", message = "Số tiền nạp phải lớn hơn hoặc bằng 50,000")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
