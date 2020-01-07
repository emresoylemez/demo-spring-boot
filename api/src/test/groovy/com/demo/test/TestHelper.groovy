//package com.demo.test
//
//import com.demo.client.paymentapi.*
//import com.demo.enums.VerifyEnrollmentStatus
//import com.demo.model.*
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.springframework.http.ResponseEntity
//
//class TestHelper {
//    public static final String TRACE_ID = "traceId"
//    static final String PAYMENT_ID = "570b3944-b6ff-4774-a9c8-765ab17b27de"
//    static final String SUCCESS_PAYMENT_ID = "650b3944-b6ff-4774-a9c8-765ab17b27ca"
//    static final String FAILURE_PAYMENT_ID = "650b3944-b6ff-4774-a9c8-765ab17b28ab"
//    static final String PAYMENT_DATE_TIME_STRING = "2019-01-01T12:00:00.000Z"
//    static final String SERVICE_ACCESS_TOKEN_VALUE = "serviceAccessTokenValue"
//    static final String CUSTOMER_ACCESS_TOKEN = "9d04e534-6117-43f1-a9d5-99eb26133090"
//    static final String SELLING_LOCATION = "cd39ab0b-f441-47c8-9206-938453ddc766"
//    static final String STORE_ID = "2001"
//    static final String SUCCESS = "success"
//    static final String POS_ID = "123"
//
//    static validPppsAuthoriseRequest(boolean isReversal) {
//        PppsAuthoriseRequest.builder()
//                .customerId("customerId")
//                .paymentDateTime(PAYMENT_DATE_TIME_STRING)
//                .paymentReference("paymentReference")
//                .paymentCardToken("paymentCardToken")
//                .authorizationAmount("1.05")
//                .paymentId(PAYMENT_ID)
//                .customerAccessToken(CUSTOMER_ACCESS_TOKEN)
//                .cscPresent(true)
//                .reverseAuthorization(isReversal)
//                .paymentTerminalType("paymentTerminalType")
//                .enrolmentStatus("enrolmentStatus")
//                .transactionReferenceNumber("transactionReferenceNumber")
//                .posId(POS_ID)
//                .paymentContext(
//                PppsAuthoriseRequest.PaymentContext.builder()
//                        .storeId(STORE_ID)
//                        .productInstance("productInstance")
//                        .productInstance("transactionMode")
//                        .region("region")
//                        .businessId("businessId")
//                        .language("language")
//                        .currencyCode("GBP")
//                        .channelId("channelId")
//                        .build()
//        )
//                .build()
//    }
//
//    def static validPppsAuthoriseRequest = validPppsAuthoriseRequest(false)
//
//    def static validPppsReverseAuthorisationRequest = PppsReverseAuthorisationRequest.builder()
//            .customerAccessToken(CUSTOMER_ACCESS_TOKEN)
//            .paymentId(PAYMENT_ID)
//            .build();
//
//    def static validPppsReverseAuthorisationResponse = PppsReverseAuthorisationResponse.builder()
//            .responseCode("SUCCESS")
//            .build();
//
//    def static validPppsAuthoriseReversalRequest = validPppsAuthoriseRequest(true)
//
//    def static validPppsAuthoriseResponse = PppsAuthoriseResponse.builder()
//            .cardSecurityResponse("cardSecurityResponse")
//            .responseDescription("responseDescription")
//            .responseCode("responseCode")
//            .authorizationCode("authorizationCode")
//            .avsAddressResponse("avsAddressResponse")
//            .paymentReference("paymentReference")
//            .build()
//
//    def static pollWaitResponseEntity = ResponseEntity.ok(
//            PollWaitResponse.builder()
//                    .status("success")
//                    .id("id")
//                    .paymentId("paymentId")
//                    .reason("reason")
//                    .build())
//
//    def static chargeConfirmationPaymentResponse = ChargeConfirmationPaymentResponse.builder()
//            .paymentId("paymentId")
//            .cardTransactionId("cardTransactionId")
//            .details(
//            ChargeConfirmationPaymentResponse.Details.builder()
//                    .authorisationCode("authorisationCode")
//                    .maskedPrimaryAccountNumber("maskedPrimaryAccountNumber")
//                    .expiryMonth("10")
//                    .expiryYear("20")
//                    .cardType("Master")
//                    .build()
//    )
//            .build()
//
//    def static validGetAssurenceResponse = GetAssuranceResponse.builder()
//            .status("Success")
//            .completedAssurances(GetAssuranceResponse.CompletedAssurances.builder()
//            .threeDS(GetAssuranceResponse.ThreeDS.builder().xid("xid").result("NotEnrolled").build())
//            .card(new GetAssuranceResponse.Card("6b66a458-16e0-40b6-915e-bf373baba110"))
//            .saleAmount(GetAssuranceResponse.SaleAmount.builder().amount(2).currency("GBP").build())
//            .build())
//            .pendingAssurances()
//            .failedAssurances()
//            .assuranceId("assurenceId")
//            .build()
//    def static validGetAssurenceResponseEnrolled = GetAssuranceResponse.builder()
//            .status("Success")
//            .completedAssurances()
//            .pendingAssurances(GetAssuranceResponse.PendingAssurances.builder()
//            .threeDS(GetAssuranceResponse.ThreeDS.builder()
//            .acsUrl("acsUrl_string")
//            .paReq("paReq_string")
//            .status("Pending").build())
//            .build())
//            .failedAssurances()
//            .assuranceId("assurenceId")
//            .build()
//
//    def static validVerifyEnrollmentRequest = VerifyEnrollmentRequest.builder()
//            .paymentDateTime(PAYMENT_DATE_TIME_STRING)
//            .paymentCardToken("paymentCardToken")
//            .paymentId(PAYMENT_ID)
//            .assuranceAmount("1.05")
//            .currencyCode("GBP")
//            .customerAccessToken("customerAccessToken")
//            .paymentReference("transactionReferenceNumber")
//            .build()
//
//    def static validVerifyEnrollmentResponse = VerifyEnrollmentResponse.builder()
//            .enrollmentStatus(VerifyEnrollmentStatus.NOT_ENROLLED)
//            .msgID("assurenceId")
//            .responseCode("200")
//            .responseDescription("Success")
//            .successful(true)
//            .build()
//
//    def static validVerifyEnrollmentResponseEnrolled = VerifyEnrollmentResponse.builder()
//            .acsUrl("acsUrl_string")
//            .enrollmentStatus(VerifyEnrollmentStatus.ENROLLED)
//            .msgID("assurenceId")
//            .pareq("paReq_string")
//            .responseCode("200")
//            .responseDescription("Success")
//            .successful(true)
//            .build()
//
//    def static validGetAssurenceResponse3DS = GetAssuranceResponse.builder()
//            .status("Success")
//            .completedAssurances(
//            GetAssuranceResponse.CompletedAssurances.builder()
//                    .threeDS(GetAssuranceResponse.ThreeDS.builder()
//                    .cavv("cavv").xid("xid").result("Authenticated").build())
//                    .card(new GetAssuranceResponse.Card("6b66a458-16e0-40b6-915e-bf373baba110"))
//                    .saleAmount(GetAssuranceResponse.SaleAmount.builder().amount(2).currency("GBP").build())
//                    .build())
//            .pendingAssurances()
//            .failedAssurances()
//            .assuranceId("assurenceId")
//            .build()
//
//    def static validProcessParesRequest = ProcessParesRequest.builder()
//            .customerAccessToken("customerAccessToken")
//            .paymentId(PAYMENT_ID)
//            .assuranceId("assuranceId")
//            .paRes("paRes")
//            .xid("xid")
//            .build()
//
//    def static validProcessParesResponse = ProcessParesResponse.builder()
//            .cavv("cavv")
//            .xid("xid")
//            .paResStatus("Authenticated")
//            .successful(true)
//            .responseCode("200")
//            .responseDescription("Success")
//            .build()
//
//    def static validSettlePaymentRequest = SettlePaymentRequest.builder()
//            .paymentId(PAYMENT_ID)
//            .paymentAmount("1.05")
//            .currencyCode("GBP")
//            .transactionTime(PAYMENT_DATE_TIME_STRING)
//            .build()
//
//    def static validTillRefundRequest = TillRefundRequest.builder()
//            .paymentId(SUCCESS_PAYMENT_ID)
//            .amount("1.05")
//            .currency("GBP")
//            .transactionTime(PAYMENT_DATE_TIME_STRING)
//            .storeId(STORE_ID)
//            .paymentDateTime(PAYMENT_DATE_TIME_STRING)
//            .transactionReferenceNumber("transactionReferenceNumber")
//            .paymentCardToken("paymentCardToken")
//            .customerAccessToken(CUSTOMER_ACCESS_TOKEN)
//            .posId(POS_ID)
//            .build()
//
//    def static inValidTillRefundRequest = TillRefundRequest.builder()
//            .paymentId(FAILURE_PAYMENT_ID)
//            .amount("1.05")
//            .currency("GBP")
//            .transactionTime(PAYMENT_DATE_TIME_STRING)
//            .storeId(STORE_ID)
//            .paymentDateTime(PAYMENT_DATE_TIME_STRING)
//            .transactionReferenceNumber("transactionReferenceNumber")
//            .paymentCardToken("paymentCardToken")
//            .customerAccessToken(CUSTOMER_ACCESS_TOKEN)
//            .posId(POS_ID)
//            .build()
//
//    def static validTillRefundResponse = TillRefundResponse.builder()
//            .status(SUCCESS)
//            .build()
//
//    static AgentRefundRequest buildAgentRefundRequest (paymentId) {
//
//
//        AgentRefundRequest.builder()
//                .transactionTime(PAYMENT_DATE_TIME_STRING)
//                .paymentId(paymentId).amount(
//                AgentRefundRequest.Amount.builder().value("1.05").currency("GBP").build()
//        ).build()
//    }
//
//    static String getAsJson(Object obj) {
//        new ObjectMapper().writer().writeValueAsString(obj)
//    }
//
//}
