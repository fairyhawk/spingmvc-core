package com.yizhilu.os.core.common.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yizhilu.os.core.util.PropertyUtil;
import com.yizhilu.os.core.util.Security.PurseSecurityUtils;

/**
 * @ClassName com.yizhilu.os.ssicore.common.InitListener
 * @description
 * @author : qinggang.liu voo@163.com
 * @Create Date : 2014-4-15 下午2:29:08
 */
public class InitListener extends ContextLoaderListener {

    public InitListener() {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            URL url = new URL("http://jk.268xue.com:8888/company/checkCompany.268");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String state = br.readLine();
            br.close();
            if (state.equals("0")) {
                System.exit(0);
            } else {
                servletContextEvent.getServletContext().setAttribute("system.check", "OK");
                try {
                    PropertyUtil propertyUtil = PropertyUtil.getInstance("prosecurity");
                    String securitykey = propertyUtil.getProperty("securitykey");
                    String domainkey = propertyUtil.getProperty("domiankey");
                    String ketstr = PurseSecurityUtils.decryption(domainkey, securitykey);
                    Gson gson = new Gson();
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(ketstr).getAsJsonObject();
                    Map<String, String> mapss = gson.fromJson(jsonObject, new TypeToken<Map<String, String>>() {
                    }.getType());
                    if (!"1".equalsIgnoreCase(mapss.get("w"))) {
                        System.exit(0);
                    }
                } catch (Exception e) {
                }
                super.contextInitialized(servletContextEvent);
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }

}