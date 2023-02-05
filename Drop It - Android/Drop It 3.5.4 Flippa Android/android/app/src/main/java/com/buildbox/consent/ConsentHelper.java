package com.buildbox.consent;

import java.util.ArrayList;
import java.util.List;

public class ConsentHelper {

    public static List<SdkConsentInfo> getSdkConsentInfos() {
        ArrayList<SdkConsentInfo> sdks = new ArrayList<>();

        sdks.add( new SdkConsentInfo("admob", "Admob", "https://policies.google.com/technologies/partner-sites"));

        return sdks;
    }

    public static String getConsentKey(String sdkId) {
        return sdkId + "_CONSENT_KEY";
    }
}
