package com.comsince.github;

import com.alipay.remoting.RemotingAddressParser;
import com.alipay.remoting.Url;
import com.alipay.remoting.config.Configs;
import com.alipay.remoting.util.StringUtils;
import com.comsince.github.protocol.PushProtocol;

import java.lang.ref.SoftReference;
import java.util.Properties;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午10:36
 **/
public class PushAddressParser implements RemotingAddressParser {
    @Override
    public Url parse(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("Illegal format address string [" + url
                    + "], should not be blank! ");
        }
        Url parsedUrl = this.tryGet(url);
        if (null != parsedUrl) {
            return parsedUrl;
        }
        String ip = null;
        String port = null;
        Properties properties = null;

        int size = url.length();
        int pos = 0;
        for (int i = 0; i < size; ++i) {
            if (COLON == url.charAt(i)) {
                ip = url.substring(pos, i);
                pos = i;
                // should not end with COLON
                if (i == size - 1) {
                    throw new IllegalArgumentException("Illegal format address string [" + url
                            + "], should not end with COLON[:]! ");
                }
                break;
            }
            // must have one COLON
            if (i == size - 1) {
                throw new IllegalArgumentException("Illegal format address string [" + url
                        + "], must have one COLON[:]! ");
            }
        }

        for (int i = pos; i < size; ++i) {
            if (QUES == url.charAt(i)) {
                port = url.substring(pos + 1, i);
                pos = i;
                if (i == size - 1) {
                    // should not end with QUES
                    throw new IllegalArgumentException("Illegal format address string [" + url
                            + "], should not end with QUES[?]! ");
                }
                break;
            }
            // end without a QUES
            if (i == size - 1) {
                port = url.substring(pos + 1, i + 1);
                pos = size;
            }
        }

        if (pos < (size - 1)) {
            properties = new Properties();
            while (pos < (size - 1)) {
                String key = null;
                String value = null;
                for (int i = pos; i < size; ++i) {
                    if (EQUAL == url.charAt(i)) {
                        key = url.substring(pos + 1, i);
                        pos = i;
                        if (i == size - 1) {
                            // should not end with EQUAL
                            throw new IllegalArgumentException(
                                    "Illegal format address string [" + url
                                            + "], should not end with EQUAL[=]! ");
                        }
                        break;
                    }
                    if (i == size - 1) {
                        // must have one EQUAL
                        throw new IllegalArgumentException("Illegal format address string [" + url
                                + "], must have one EQUAL[=]! ");
                    }
                }
                for (int i = pos; i < size; ++i) {
                    if (AND == url.charAt(i)) {
                        value = url.substring(pos + 1, i);
                        pos = i;
                        if (i == size - 1) {
                            // should not end with AND
                            throw new IllegalArgumentException("Illegal format address string ["
                                    + url
                                    + "], should not end with AND[&]! ");
                        }
                        break;
                    }
                    // end without more AND
                    if (i == size - 1) {
                        value = url.substring(pos + 1, i + 1);
                        pos = size;
                    }
                }
                properties.put(key, value);
            }
        }
        parsedUrl = new Url(url, ip, Integer.parseInt(port), properties);
        this.initUrlArgs(parsedUrl);
        Url.parsedUrls.put(url, new SoftReference<Url>(parsedUrl));
        return parsedUrl;
    }

    @Override
    public String parseUniqueKey(String url) {
        return null;
    }

    @Override
    public String parseProperty(String url, String propKey) {
        return null;
    }

    @Override
    public void initUrlArgs(Url url) {
        int connTimeout = Configs.DEFAULT_CONNECT_TIMEOUT;

        url.setConnectTimeout(connTimeout);

        byte protocol = PushProtocol.PROTOCOL_CODE;

        url.setProtocol(protocol);



        int connNum = Configs.DEFAULT_CONN_NUM_PER_URL;

        url.setConnNum(connNum);

        boolean connWarmup = false;
        url.setConnWarmup(connWarmup);
    }

    /**
     * try get from cache
     *
     * @param url
     * @return
     */
    private Url tryGet(String url) {
        SoftReference<Url> softRef = Url.parsedUrls.get(url);
        return (null == softRef) ? null : softRef.get();
    }
}
