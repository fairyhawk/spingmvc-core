package com.yizhilu.os.core.common.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * @ClassName com.yizhilu.os.ssicore.common.InitListener
 * @description
 * @author : qinggang.liu voo@163.com
 * @Create Date : 2014-4-15 下午2:29:08
 */
public class InitListener extends ContextLoaderListener {

    // 读取配置文件类
   // public static PropertyUtil propertyUtil = PropertyUtil.getInstance("project");

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
                super.contextInitialized(servletContextEvent);
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }

}