package com.prgms.VoucherApp.domain.voucher.model;

import java.math.BigDecimal;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final Long DISCOUNT_AMOUNT_MIN_VALUE = 0L;

    public FixedAmountVoucher(UUID getVoucherId, BigDecimal discountAmount) {
        super(getVoucherId, discountAmount);
    }


    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        if (isResultNegative(beforeAmount)) {
            return BigDecimal.ZERO;
        }

        return beforeAmount.subtract(amount);
    }

    private boolean isResultNegative(BigDecimal beforeAmount) {
        return beforeAmount.subtract(amount)
            .compareTo(BigDecimal.ZERO) < DISCOUNT_AMOUNT_MIN_VALUE;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED_VOUCHER;
    }
}
