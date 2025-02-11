package com.prgms.VoucherApp.domain.voucher.controller;


import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.VoucherCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class VoucherManagementController implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(VoucherManagementController.class);
    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    public VoucherManagementController(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                output.printVoucherCommand();
                Integer inputCommandNumber = input.inputVoucherCommand();
                VoucherCommand command = VoucherCommand.findByVoucherCommandNumber(inputCommandNumber);

                switch (command) {
                    case CREATE -> {
                        output.printVoucherPolicy();
                        String inputVoucherType = input.inputVoucherType();

                        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
                        output.printDiscountCondition(voucherType);
                        Long amount = input.inputDiscountAmount(voucherType);

                        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType, BigDecimal.valueOf(amount));

                        voucherService.save(voucherCreateRequest);
                    }

                    case FIND_ALL -> {
                        VouchersResponse findVouchers = voucherService.findAll();
                        output.printVoucherList(findVouchers.getVouchers());
                    }

                    case FIND_ONE -> {
                        String inputUUID = input.inputUUID();
                        UUID voucherId = UUID.fromString(inputUUID);

                        voucherService.findOne(voucherId)
                            .ifPresent(output::printVoucher);
                    }

                    case FIND_BY_VOUCHER_TYPE -> {
                        String inputVoucherType = input.inputVoucherType();
                        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);

                        VouchersResponse findVouchers = voucherService.findByVoucherType(voucherType);

                        output.printVoucherList(findVouchers.getVouchers());
                    }

                    case UPDATE -> {
                        String inputUUID = input.inputUUID();
                        UUID voucherId = UUID.fromString(inputUUID);

                        String inputVoucherType = input.inputVoucherType();
                        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);

                        Long inputAmount = input.inputDiscountAmount(voucherType);
                        BigDecimal amount = BigDecimal.valueOf(inputAmount);

                        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(voucherId, amount, voucherType);
                        voucherService.update(voucherUpdateRequest);
                    }

                    case DELETE -> {
                        String inputUUID = input.inputUUID();
                        UUID voucherId = UUID.fromString(inputUUID);

                        voucherService.deleteById(voucherId);
                    }

                    case EXIT -> {
                        isRunning = false;
                    }

                    default -> {
                        output.printNotImplementMsg();
                    }
                }
            } catch (RuntimeException exception) {
                logger.debug("할인권 관리 프로그램 실행 중에 발생한 예외를 처리하였습니다.", exception);
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
