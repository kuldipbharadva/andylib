package com.example.libusage.payment.payTm;

public class PayTmInstruction {

    // PayTm Integration
    // Ref link
    // - https://www.simplifiedcoding.net/paytm-integration-android-example/
    // - https://www.elsner.com/paytm-integration-in-android-accept-payments-with-paytm/
    //
    // Paytm Official link
    // - http://paywithpaytm.com/developer/paytm_api_doc?target=integration-guide
    // Add dependency
    // - implementation 'com.paytm:pgplussdk:1.1.2'
    // Then put PaytmIntegration class code into your project and handle callback
    // For CHECKSUMHASH param you need to call api
    // Generate CHECKSUM on our server. web part

    // Following params are mandatory
    //        Map<String, String> paramMap = new HashMap<>();
    //        paramMap.put("MID","TECHOP10964184510936");
    //        paramMap.put("ORDER_ID","1523342168787");
    //        paramMap.put("CUST_ID","test111");
    //        paramMap.put("CHANNEL_ID","WAP");
    //        paramMap.put("TXN_AMOUNT","1");
    //        paramMap.put("CALLBACK_URL","https://securegw.paytm.in/theia/paytmCallback");
    //        paramMap.put("CHECKSUMHASH","yDZks+XoWcQ4YZJ+Iod+f/b/7mi5tcGqqQELPhSLQYjGdUfcUnsegcjlsdW797gnsvy4YrHNV8HSIJmdFN0NgWbNTle8wgKFAnSH14crB3A=");
    //        paramMap.put("INDUSTRY_TYPE_ID","Retail");
    //        paramMap.put("WEBSITE","TECHweb");

    // That's it :)
    // For further information just refer above link :)
}
