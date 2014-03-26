/**
 * ©2013-2015 Alan L. Rights Reserved.
 */
package org.daybreak.emailler.utils.email;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alan
 */
public class HttpsConnectionManager {

    // 连接池里的最大连接数 
    public static final int MAX_TOTAL_CONNECTIONS = 100;

    // 每个路由的默认最大连接数  
    public static final int MAX_ROUTE_CONNECTIONS = 50;

    // 连接超时时间 
    public static final int CONNECT_TIMEOUT = 300000; // 5min

    // 套接字超时时间
    public static final int SOCKET_TIMEOUT = 300000;// 3min

    // 连接池中 连接请求执行被阻塞的超时时间
    public static final int CONN_MANAGER_TIMEOUT = 300000;// 3min

    // http连接相关参数
    //private static final HttpParams parentParams;

    // http线程池管理器
    private static final PoolingHttpClientConnectionManager connection_manager;

    // http客户端
    private HttpClient httpClient;

    private static List<HttpHost> proxy_host_list = new ArrayList<>();

    private static ExecutorService executor = Executors.newCachedThreadPool();

    // HTTP头
    public static String HEAD_X_REQUESTED_WITH = "X-Requested-With";

    private static final Logger logger = LoggerFactory.getLogger(HttpsConnectionManager.class);

    private static final X509TrustManager tm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string)
                throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string)
                throws java.security.cert.CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    /**
     * 初始化
     */
    static {
        ConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        connection_manager = new PoolingHttpClientConnectionManager(r);

        connection_manager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connection_manager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        try {
            URL fileUrl = HttpsConnectionManager.class.getClassLoader().getResource("/proxy-list.data");
            if (fileUrl != null) {
                String proxyListString = FileUtils.readFileToString(new File(fileUrl.toURI()));
                String[] proxyLines = proxyListString.split("\n");
                for (final String proxyLine : proxyLines) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String[] proxyLineArray = proxyLine.split("\t");
                                HttpHost proxy = new HttpHost(proxyLineArray[1], Integer.parseInt(proxyLineArray[2]));

                                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
                                CloseableHttpClient client = HttpClients.custom()
                                        .setConnectionManager(connection_manager)
                                        .setRoutePlanner(routePlanner)
                                        .build();

                                HttpGet testGet = new HttpGet("https://sa.edit.yahoo.com/registration?.pd=&intl=hk&origIntl=&done=&wl=&wlcr=&_asdk_embedded=&create_alias=&.scrumb=&src=&last=&partner=yahoo_default&domain=yahoo.com&yahooid=&lang=zh-Hant-HK");
                                RequestConfig.Builder testRequestConfigBuilder = RequestConfig.custom()
                                        .setConnectionRequestTimeout(8000)
                                        .setSocketTimeout(8000)
                                        .setConnectTimeout(8000);
                                testGet.setConfig(testRequestConfigBuilder.build());

                                HttpResponse response = client.execute(testGet);
                                int statusCode = response.getStatusLine().getStatusCode();
                                if (statusCode == 200) {
                                    proxy_host_list.add(proxy);
                                }
                            } catch (Exception e) {
                                logger.warn(e.getMessage());
                            }
                        }
                    };
                    executor.execute(task);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public HttpsConnectionManager(boolean useProxy) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connection_manager);
        httpClientBuilder.setUserAgent("Mozilla/5.0 (Windows NT 5.1; rv:26.0) Gecko/20100101 Firefox/26.0");
        httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
            @Override
            public void process(
                    final HttpRequest request,
                    final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip, deflate");
                }
            }
        });
        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true));

        if (useProxy) {
            boolean proxySetted = false;
            if (!proxy_host_list.isEmpty()) {
                int tryCount = 5;
                while (tryCount > 0 && !proxySetted) {
                    try {
                        HttpHost proxy = proxy_host_list.get((int)(Math.random() * (proxy_host_list.size() - 1)));
                        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
                        httpClientBuilder.setRoutePlanner(routePlanner);
                        httpClient = httpClientBuilder.build();

                        HttpGet testGet = new HttpGet("https://sa.edit.yahoo.com/registration?.pd=&intl=hk&origIntl=&done=&wl=&wlcr=&_asdk_embedded=&create_alias=&.scrumb=&src=&last=&partner=yahoo_default&domain=yahoo.com&yahooid=&lang=zh-Hant-HK");
                        RequestConfig.Builder testRequestConfigBuilder = RequestConfig.custom()
                                .setConnectionRequestTimeout(5000)
                                .setSocketTimeout(5000)
                                .setConnectTimeout(5000);
                        testGet.setConfig(testRequestConfigBuilder.build());

                        HttpResponse response = httpClient.execute(testGet);
                        int statusCode = response.getStatusLine().getStatusCode();
                        if (statusCode == 200) {
                            proxySetted = true;
                        }
                    } catch (Exception e) {
                        logger.warn(e.getMessage());
                    }
                    tryCount--;
                }
            }

            if (!proxySetted) {
                SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(
                        ProxySelector.getDefault());
                httpClientBuilder.setRoutePlanner(routePlanner);
                httpClient = httpClientBuilder.build();
            }
        } else {
            httpClient = httpClientBuilder.build();
        }
    }

    public HttpResponse getHttpRequest(String url, String referer,
                                       List<NameValuePair> parameters, Map<String, String> cookieData) throws IOException {
        return getHttpRequest(url, referer, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", parameters, cookieData);
    }

    /**
     * GET请求
     *
     * @param url
     * @param parameters
     * @param cookieData
     * @return
     */
    public HttpResponse getHttpRequest(String url, String referer, String accept,
                                       List<NameValuePair> parameters, Map<String, String> cookieData) throws IOException {
        logger.debug("------------------------------------------------------------------------");
        if (parameters != null && parameters.size() > 0) {
            String paramURL = URLEncodedUtils.format(parameters, HTTP.UTF_8);
            if (url.indexOf("?") > -1) {
                url = url + "&" + paramURL;
            } else {
                url = url + "?" + paramURL;
            }
        }
        logger.debug("GET URL: " + url);

        HttpGet httpGet = new HttpGet(url);

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT);
        httpGet.setConfig(requestConfigBuilder.build());

        // set header
        httpGet.setHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        httpGet.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        httpGet.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        httpGet.setHeader(HttpHeaders.CONNECTION, "keep-alive");
        httpGet.setHeader(HttpHeaders.REFERER, referer);
        httpGet.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 5.1; rv:26.0) Gecko/20100101 Firefox/26.0");

        if (cookieData != null) {
            boolean first = true;
            StringBuilder cookie = new StringBuilder();
            for (Map.Entry<String, String> me : cookieData.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    cookie.append("; ");
                }
                cookie.append(me.getKey()).append("=").append(me.getValue());
            }
            httpGet.setHeader("Cookie", cookie.toString());
        }

        if (logger.isDebugEnabled()) {
            if (parameters != null) {
                logger.debug(" + Request parameters: ");
                for (NameValuePair param : parameters) {
                    logger.debug("   - " + param.getName() + " : " + param.getValue());
                }
            }
            logger.debug(" + Request headers: ");
            for (Header header : httpGet.getAllHeaders()) {
                logger.debug("   - " + header.getName() + " : " + header.getValue());
            }
        }

        HttpResponse response = httpClient.execute(httpGet);
        if (logger.isDebugEnabled()) {
            logger.debug(" + Response headers: ");
            for (Header header : response.getAllHeaders()) {
                logger.debug("   - " + header.getName() + " : " + header.getValue());
            }
        }
        logger.debug("***********************************************************************");
        return response;
    }

    /**
     * 返回GET请求响应字符串
     *
     * @param url
     * @param parameters
     * @param cookieData
     * @return
     */
    public String getHttpRequestAsString(String url, String referer,
                                         List<NameValuePair> parameters, Map<String, String> cookieData) throws IOException {
        HttpResponse response = getHttpRequest(url, referer, parameters, cookieData);
        HttpEntity entity = response.getEntity();
        String responseContent = readContentFromEntity(entity);
        logger.info("GET: " + url);
        if (responseContent.length() > 300) {
            logger.info(" + Response content(0-300):\n" + responseContent.substring(0, 100));
        } else {
            logger.info(" + Response content:\n" + responseContent);
        }
        //logger.debug(" + Response content (ALL):\n" + responseContent);
        return responseContent;
    }

    /**
     * 新建一个HttpPost对象
     *
     * @param url
     * @param referer
     * @param parameters
     * @param cookieData
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public HttpPost createHttpPost(String url, String referer, List<NameValuePair> parameters, String content,
                                   String contentType, Map<String, String> cookieData) throws IOException {

        HttpPost httpPost = new HttpPost(url);

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT);
        httpPost.setConfig(requestConfigBuilder.build());

        // set header
        httpPost.setHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.setHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        httpPost.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        httpPost.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        httpPost.setHeader(HttpHeaders.CONNECTION, "keep-alive");
        if (StringUtils.isEmpty(contentType)) {
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        } else {
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, contentType + ";charset=UTF-8");
        }
        httpPost.setHeader(HttpHeaders.REFERER, referer);
        httpPost.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 5.1; rv:26.0) Gecko/20100101 Firefox/26.0");

        if (cookieData != null) {
            boolean first = true;
            StringBuilder cookie = new StringBuilder();
            for (Map.Entry<String, String> me : cookieData.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    cookie.append("; ");
                }
                cookie.append(me.getKey()).append("=").append(me.getValue());
            }
            httpPost.setHeader("Cookie", cookie.toString());
        }

        if (parameters != null) {
            UrlEncodedFormEntity uef = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setEntity(uef);
        } else if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(contentType)) {
            StringEntity se = new StringEntity(content, ContentType.create(contentType, "UTF-8"));
            httpPost.setEntity(se);
        }

        return httpPost;
    }

    /**
     * 从流中将字符串读出
     *
     * @param is
     * @return
     * @throws java.io.IOException
     */
    public String readStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    /**
     * POST请求
     *
     * @param url
     * @param parameters
     * @param cookieData
     * @return
     */
    public HttpResponse postHttpRequest(String url, String referer, List<NameValuePair> parameters,
                                        String content, String contentType, Map<String, String> cookieData) throws IOException {
        logger.debug("------------------------------------------------------------------------");
        logger.debug("POST URL: " + url);

        HttpPost httpPost = createHttpPost(url, referer, parameters, content, contentType, cookieData);

        if (logger.isDebugEnabled()) {
            if (parameters != null) {
                logger.debug(" + Request parameters: ");

                for (NameValuePair param : parameters) {
                    logger.debug("   - " + param.getName() + " : " + param.getValue());
                }
            }
            logger.debug(" + Request headers: ");
            for (Header header : httpPost.getAllHeaders()) {
                logger.debug("   - " + header.getName() + " : " + header.getValue());
            }
        }

        HttpResponse response = httpClient.execute(httpPost);
        if (logger.isDebugEnabled()) {
            logger.debug(" + Response headers: ");
            for (Header header : response.getAllHeaders()) {
                logger.debug("   - " + header.getName() + " : " + header.getValue());
            }
        }
        logger.debug("***********************************************************************");
        return response;
    }

    /**
     * 返回POST请求响应字符串
     *
     * @param url
     * @param parameters
     * @param cookieData
     * @return
     */
    public String postHttpRequestAsString(String url, String referer, List<NameValuePair> parameters,
                                          Map<String, String> cookieData) throws IOException {
        HttpResponse response = postHttpRequest(url, referer, parameters, null, null, cookieData);
        HttpEntity entity = response.getEntity();
        String responseContent = readContentFromEntity(entity);
        logger.info("POST: " + url);
        if (responseContent.length() > 300) {
            logger.info(" + Response content(0-300):\n" + responseContent.substring(0, 100));
        } else {
            logger.info(" + Response content:\n" + responseContent);
        }
        //logger.debug(" + Response content(ALL):\n" + responseContent);
        return responseContent;
    }

    /**
     * 返回POST请求响应字符串
     *
     * @param url
     * @param referer
     * @param parameters
     * @param cookieData
     * @return
     * @throws java.io.IOException
     */
    public String postHttpRequestAsString(String url, String referer, List<NameValuePair> parameters,
                                          String content, String contentType, Map<String, String> cookieData) throws IOException {
        HttpResponse response = postHttpRequest(url, referer, parameters, content, contentType, cookieData);
        HttpEntity entity = response.getEntity();
        String responseContent = readContentFromEntity(entity);
        logger.info("POST: " + url);
        if (responseContent.length() > 300) {
            logger.info(" + Response content(0-300):\n" + responseContent.substring(0, 100));
        } else {
            logger.info(" + Response content:\n" + responseContent);
        }
        //logger.debug(" + Response content(ALL):\n" + responseContent);
        return responseContent;
    }

    /**
     * 从response返回的实体中读取页面代码
     *
     * @param httpEntity Http实体
     * @return 页面代码
     * @throws org.apache.http.ParseException
     * @throws java.io.IOException
     */
    public String readContentFromEntity(HttpEntity httpEntity) throws ParseException, IOException {
        String html;
        Header header = httpEntity.getContentEncoding();
        if (header != null && "gzip".equals(header.getValue())) {
            html = EntityUtils.toString(new GzipDecompressingEntity(httpEntity));
        } else {
            html = EntityUtils.toString(httpEntity);
        }
        return html;
    }
}
