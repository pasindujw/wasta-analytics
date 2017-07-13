/*
 *
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.wastaanalytics.Util;

public class Constants {
    public static final String SERVER_IP = "demo.iot.wso2.com";
    public static final String TYPE_HTTPS = "https://";
    public static final String TYPE_HTTP = "http://";
    public static final String PORT_HTTPS = ":9443";
    public static final String PORT_HTTP = ":9763";
    public static final String PORTAL_LOGIN_URI = "/kiosk-app/enrollments/windows/login-agent";
    public static final String HTTPS_HOST_URL = TYPE_HTTPS + SERVER_IP + PORT_HTTPS;
    public static final String HTTP_HOST_URL = TYPE_HTTP + SERVER_IP + PORT_HTTP;
    public static final String HTTPS_LOGIN_URL = TYPE_HTTPS + SERVER_IP + PORT_HTTPS + PORTAL_LOGIN_URI;
    //public static final String URL_TO_POST = "http://www.mocky.io/v2/5960ef961000004107ac5aa5";
    public static final String URL_TO_POST = "https://demo.iot.wso2.com:9443/kiosk-app/nfc-bump/token";
    public static final String RESTRTICTION_URL = "RESTRICTION_URL";
    public static final String AGENT_PACKAGE = "org.wso2.wastaanalytics";
    public static final String TOKEN_SPITTER = "/";
    public static final String INTENT_EXTRA_URL = "url";

    public final class AppRestrictions {
        public static final String KEYWORD_URL = "url";
    }

    public final class JSONElements {
        public static final String TOKEN = "token";
        public static final String USER = "user";
        public static final String URL = "url";
    }
}
