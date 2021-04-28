package io.github.syske.weblogic;

import io.github.syske.dao.model.ServerRunrecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;

/**
 * @program: weblogic-monitor
 * @description: 获取weblogic服务器运行数据
 * @author: syske
 * @create: 2020-01-17 14:35
 */
public class WeblogicMonitorUtil {

    private static Logger logger = LoggerFactory.getLogger(WeblogicMonitorUtil.class);
    private static final String RUNTIMESERVICEMBEAN = "com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";

    /**
     * 初始化服务器连接
     *
     * @param protocol   协议方式，如t3
     * @param hostname   主机地址
     * @param portString 服务端口
     * @param username   用户名
     * @param password   密码
     * @throws IOException
     * @throws MalformedURLException
     * @throws MalformedObjectNameException
     */
    public static MBeanServerConnection initConnection(String protocol, String hostname, String portString,
                                                       String username, String password) throws IOException,
            MalformedURLException {
        Integer portInteger = Integer.valueOf(portString);
        int port = portInteger.intValue();
        String jndiroot = "/jndi/";
        String mserver = "weblogic.management.mbeanservers.runtime";
        JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port,
                jndiroot + mserver);

        Hashtable<String, String> h = new Hashtable<String, String>();
        h.put(Context.SECURITY_PRINCIPAL, username);
        h.put(Context.SECURITY_CREDENTIALS, password);
        h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
                "weblogic.management.remote");
        return JMXConnectorFactory.connect(serviceURL, h).getMBeanServerConnection();
    }


    /**
     * web应用程序运行时组件
     *
     * @param serverRuntime
     * @throws AttributeNotFoundException
     * @throws InstanceNotFoundException
     * @throws MBeanException
     * @throws ReflectionException
     * @throws IOException
     */
    public void getWebAppComponentRuntime(ObjectName serverRuntime, MBeanServerConnection mBeanServerConnection)
            throws AttributeNotFoundException, InstanceNotFoundException,
            MBeanException, ReflectionException, IOException {
        // 获取web应用程序运行时组件
        ObjectName[] applicationRuntimes = getAttribute(serverRuntime,
                "ApplicationRuntimes", mBeanServerConnection);
        for (int i = 0; i < applicationRuntimes.length; i++) {
            ObjectName applicationRuntime = applicationRuntimes[i];
            ObjectName[] componentRuntimes = getAttribute(applicationRuntime,
                    "ComponentRuntimes", mBeanServerConnection);
            for (int j = 0; j < componentRuntimes.length; j++) {
                ObjectName componentRuntime = componentRuntimes[j];
                String componentType = getAttribute(componentRuntime, "Type", mBeanServerConnection);
                // web应用程序运行时组件 WebAppComponentRuntime
                if (componentType.equals("WebAppComponentRuntime")) {
                    // 单元名 Name
                    String name = getAttribute(componentRuntime, "Name", mBeanServerConnection);
                    // 部署状态 DeploymentState
                    Integer deploymentState = getAttribute(componentRuntime,
                            "DeploymentState", mBeanServerConnection);
                    // 当前打开的Session数 OpenSessionsCurrentCount
                    Integer openSessionsCurrentCount = getAttribute(
                            componentRuntime, "OpenSessionsCurrentCount", mBeanServerConnection);
                    // Web应用程序名称
                    // 打开的Session最高数 OpenSessionsHighCount
                    Integer openSessionsHighCount = getAttribute(
                            componentRuntime, "OpenSessionsHighCount", mBeanServerConnection);
                    // 打开的Session的总数 SessionsOpenedTotalCount
                    Integer sessionsOpenedTotalCount = getAttribute(
                            componentRuntime, "SessionsOpenedTotalCount", mBeanServerConnection);
                    // 应用状态 Status
                    String status = getAttribute(componentRuntime, "Status", mBeanServerConnection);

                    // TODO
                    logger.info("name　= " + name + ", status = "
                            + deploymentState);
                    logger.info("openSessionsCurrentCount:{}", openSessionsCurrentCount);
                    logger.info("openSessionsHighCount:{}", openSessionsHighCount);
                    logger.info("sessionsOpenedTotalCount:{}", sessionsOpenedTotalCount);
                    logger.info("status:{}", status);
                }
            }
        }
    }


    /**
     * 获取weblogic属性参数
     *
     * @param objectName
     * @param name
     * @param mBeanServerConnection
     * @param <T>
     * @return
     */
    private static <T> T getAttribute(ObjectName objectName, String name, MBeanServerConnection mBeanServerConnection) {
        Object obj = null;
        try {
            obj = mBeanServerConnection.getAttribute(objectName, name);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
        return (T) obj;
    }


    public static void test() throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        JmxWeblogic weblogic = new JmxWeblogic();
        // initConnection();
        String protocol = "t3";
        String hostname = "127.0.0.1";
        String portString = "8201";
        String username = "weblogic";
        String password = "weblogic2019";
        MBeanServerConnection mBeanServerConnection = initConnection(protocol, hostname, portString, username, password);

        ObjectName runtimeService = new ObjectName(RUNTIMESERVICEMBEAN);
        ObjectName serverRuntime = getAttribute(runtimeService, "ServerRuntime", mBeanServerConnection);


        weblogic.getServerRunrecord(serverRuntime, mBeanServerConnection);
    }


    /**
     * 获取服务运行记录信息
     *
     * @param hostname
     * @param portString
     * @param username
     * @param password
     * @return
     */
    public static ServerRunrecord getServerInfo(String hostname, String portString,
                                                String username, String password) {
        final String protocol = "t3";
        MBeanServerConnection mBeanServerConnection = null;
        JmxWeblogic weblogic = new JmxWeblogic();
        try {
            mBeanServerConnection = initConnection(protocol, hostname, portString, username, password);
            ObjectName runtimeService = new ObjectName(RUNTIMESERVICEMBEAN);
            ObjectName serverRuntime = getAttribute(runtimeService, "ServerRuntime", mBeanServerConnection);

            return weblogic.getServerRunrecord(serverRuntime, mBeanServerConnection);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取服务器状态信息失败：", e);
        } catch (ReflectionException e) {
            e.printStackTrace();
            logger.error("获取服务器状态信息失败：", e);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            logger.error("获取服务器状态信息失败：", e);
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
            logger.error("获取服务器状态信息失败：", e);
        } catch (MBeanException e) {
            e.printStackTrace();
            logger.error("获取服务器状态信息失败：", e);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            logger.error("获取服务器状态信息失败：", e);
        }

        return new ServerRunrecord();
    }


}
