package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResDto;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.util.List;

public interface Output {
    void printManagementMenu();

    void printCustomerCommand();

    void printVoucherCommand();

    void printDisplayVoucherPolicy();

    void printErrorMsg(String msg);

    void printDisplayDiscountCondition(VoucherType policy);

    void printCustomers(CustomersResDto customers);

    void printCustomer(CustomerResDto customer);

    void printVoucherList(List<VoucherResDto> voucher);

    void printVoucher(VoucherResDto voucher);

    void printBlackLists(CustomersResDto blacklists);

    void printFindEmpty();

    void printNotImplementMsg();
}
