package org.wso2.wastaanalytics.Util;

/**
 * Created by pasindu on 7/7/17.
 */

public class Constants {
    public static final String SERVER_IP = "192.168.8.100";
    public static final String TYPE_HTTPS = "https://";
    public static final String PORT_HTTPS = ":9443";
    public static final String PORTAL_LOGIN_URI = "/portal/login";
    public static final String PORTAL_LOGOUT_URI = "/portal/logout";

    public static final String HOST_URL = TYPE_HTTPS + SERVER_IP + PORT_HTTPS +"/";
    public static final String HTTPS_LOGIN_URL = TYPE_HTTPS + SERVER_IP + PORT_HTTPS + PORTAL_LOGIN_URI;
    public static final String URL_TO_POST = HOST_URL;

}
