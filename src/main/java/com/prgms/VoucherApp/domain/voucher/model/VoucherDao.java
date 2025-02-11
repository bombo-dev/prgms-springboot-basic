package com.prgms.VoucherApp.domain.voucher.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherDao {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType type);

    void update(Voucher voucher);

    void deleteById(UUID id);
}
