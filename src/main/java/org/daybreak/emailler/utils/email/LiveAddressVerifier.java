package org.daybreak.emailler.utils.email;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alan on 14-3-26.
 */
public class LiveAddressVerifier implements EmailAddressVerifier {

    private static final Logger logger = LoggerFactory.getLogger(YahooAddressVerifier.class);

    private static final Pattern SERVER_QS_PATTERN = Pattern.compile(",\"QS\":\"(.*?)\",");

    private static LiveAddressVerifier liveAddressVerifier = new LiveAddressVerifier();

    private LiveAddressVerifier() {
    }

    public static LiveAddressVerifier getInstance() {
        return liveAddressVerifier;
    }

    @Override
    public boolean verify(EmailAddress emailAddress) {
        HttpsConnectionManager httpsManager = null;
        try {
            httpsManager = new HttpsConnectionManager(true);

            HashMap<String, String> cookieData = new HashMap<>();
            HttpResponse sigupResponse = httpsManager.getHttpRequest("https://signup.live.com/signup.aspx?lic=1", null, null, cookieData);
            // 获取消息头的信息
            Header[] sigupHeaders = sigupResponse.getAllHeaders();
            for (int i = 0; i < sigupHeaders.length; i++) {
                if (sigupHeaders[i].getName().equals("Set-Cookie")) {
                    String cookieStr = sigupHeaders[i].getValue();
                    String[] cookies = cookieStr.split(";");
                    for (String cookie : cookies) {
                        int eqIndex = cookie.indexOf("=");
                        if (eqIndex > -1) {
                            String cookieName = cookie.substring(0, eqIndex);
                            String cookieValue = cookie.substring(eqIndex + 1);
                            cookieData.put(cookieName, cookieValue);
                        }
                    }
                }
            }
            HttpEntity entity = sigupResponse.getEntity();
            String sigupHtml = httpsManager.readContentFromEntity(entity);

            Matcher m = SERVER_QS_PATTERN.matcher(sigupHtml);
            String serverQS = "";
            while (m.find()) {
                serverQS = m.group(1);
                break;
            }
            serverQS = serverQS.replaceAll("\\\\u003d", "=").replaceAll("\\\\u0026", "&");

            HttpResponse response = httpsManager.postHttpRequest("https://signup.live.com/checkavail.aspx?chkavail="
                    + emailAddress + "&chkeasi=1&tk=" + System.currentTimeMillis() + "&" + serverQS,
                    "https://signup.live.com/signup.aspx?lic=1", null, null, null, cookieData);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获取消息头的信息
                Header[] headers = response.getAllHeaders();
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].getName().equals("Set-Cookie")) {
                        String cookieStr = headers[i].getValue();
                        String[] cookies = cookieStr.split(";");
                        for (String cookie : cookies) {
                            int eqIndex = cookie.indexOf("=");
                            if (eqIndex > -1) {
                                String cookieName = cookie.substring(0, eqIndex);
                                String cookieValue = cookie.substring(eqIndex + 1);
                                if ("CheckAvail".equals(cookieName) && "Error_1242".equals(cookieValue)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                logger.warn(emailAddress + " is not valid. by live verifier.");
            } else {
                return DefualtAddressVerifier.getInstance().verify(emailAddress);
            }
        } catch (IOException e) {
            logger.error("", e);
            return DefualtAddressVerifier.getInstance().verify(emailAddress);
        } finally {
            //
        }
        return false;
    }

    public static void main(String[] args) {
        EmailAddressVerifier verifier = new LiveAddressVerifier();
        System.out.println("" + verifier.verify(new EmailAddress("kliumin@live.cn")));
        System.out.println("" + verifier.verify(new EmailAddress("alanliumin@live.cn"))); // invalid
        //System.out.println("" + verifier.verify(new EmailAddress("kachun1130@hotmail.com.hk"))); // invalid
        //System.out.println("" + verifier.verify(new EmailAddress("belifeo@hotmail.com")));
        //System.out.println("" + verifier.verify(new EmailAddress("happytogetherna@hotmail.com.hk")));
    }
}
