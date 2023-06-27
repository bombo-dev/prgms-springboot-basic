package com.prgms.VoucherApp.domain.voucher.controller;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;
import com.prgms.VoucherApp.domain.voucher.model.VoucherCreator;
import com.prgms.VoucherApp.domain.voucher.model.VoucherReader;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherManagementApp {

    private final VoucherCreator voucherCreator;
    private final VoucherReader voucherReader;
    private final Input input;
    private final Output output;

    public VoucherManagementApp(VoucherCreator voucherCreator, VoucherReader voucherReader, Input input, Output output) {
        this.voucherCreator = voucherCreator;
        this.voucherReader = voucherReader;
        this.input = input;
        this.output = output;
    }

    public void createVoucher() {
        VoucherType policy = getVoucherPolicyType();
        long amount = getDiscountAmount(policy);
        Voucher voucher = voucherCreator.createVoucher(policy, amount);
        output.printCreatedMsg(voucher);
    }

    public void readVouchers() {
        List<VoucherDto> vouchers = voucherReader.readVoucherList();
        output.printVoucherList(vouchers);
    }

    private VoucherType getVoucherPolicyType() {
        output.printDisplayVoucherPolicy();
        String inputVoucherPolicy = input.inputVoucherPolicy();
        return VoucherType.findByPolicy(inputVoucherPolicy);
    }

    private long getDiscountAmount(VoucherType policy) {
        output.printDisplayDiscountCondition(policy);
        return input.inputDiscountAmount(policy);
    }
}
