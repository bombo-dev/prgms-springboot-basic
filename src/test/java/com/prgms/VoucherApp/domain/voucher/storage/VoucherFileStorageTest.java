package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {VoucherFileStorage.class})
@ActiveProfiles(profiles = "dev")
public class VoucherFileStorageTest {

    @Autowired
    VoucherStorage storage;

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000.0), VoucherType.FIXED_VOUCHER);

        // when
        storage.save(fixedVoucher);
        Voucher findVoucher = storage.findByVoucherId(fixedVoucher.getVoucherId()).get();

        // then
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("퍼센트 비율 할인권 생성 테스트")
    void savePercentVoucher() {
        // given
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50.0), VoucherType.PERCENT_VOUCHER);

        // when
        storage.save(percentVoucher);
        Voucher findVoucher = storage.findByVoucherId(percentVoucher.getVoucherId()).get();

        // then
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(percentVoucher);
    }
}
