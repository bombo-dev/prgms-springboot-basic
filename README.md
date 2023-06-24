# SpringBoot Basic Weekly Mission

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 📌 과제 설명

**Spring을 사용하여 바우처 관리 애플리케이션 만들기!**

## 📌 기능 요구 사항

### 1차 PR 기능 요구사항

- [X] 스프링 부트 애플리케이션으로 만들기
- [X] 프로그램이 시작하면 같이 지원가능한 명령어 알려주기
    - [X] beryx:text-io 사용하기
    - [X] 명령어를 입력 받는다. - Input
        - [X] exit 명령어를 입력받으면 프로그램이 종료된다.
        - [X] create 명령어를 입력받으면 voucher를 생성하는 창으로 이동한다.
        - [X] list 명령어를 입력받으면 생성된 voucher를 조회한다.
    - [X] 명령어 출력 및 Voucher 리스트를 출력한다. - Output
    - [X] 지원가능한 명령어를 Enum 으로 관리한다. - Command
- [X] Voucher는 여러가지 종류로 확장이 가능하다. - Voucher
    - [X] Voucher는 할인을 하는 기능을 가지고 있다. - Voucher#discount()
    - [X] 고정된 금액을 할인하는 Voucher - FixedAmountVoucher
    - [X] 비율로 할인을 하는 Voucher - PercentDiscountVoucher
- [X] Voucher를 생성 할 수 있다. - VoucherCreator#createVoucher()
    - [X] Voucher는 메모리에 저장한다. - VoucherMemoryStorage#save(Voucher voucher)
- [X] Voucher를 조회 할 수 있다. - VoucherReader#readVoucherList()

---

### 2차 PR 기능 요구사항

- [ ] 적절한 로그를 기록하고 logback 설정을 해서 에러는 파일로 기록된다.
- [ ] 실행가능한 jar 파일을 생성한다.
- [ ] profile을 사용하여, dev 프로필에서 파일로 관리되도록 해본다.
