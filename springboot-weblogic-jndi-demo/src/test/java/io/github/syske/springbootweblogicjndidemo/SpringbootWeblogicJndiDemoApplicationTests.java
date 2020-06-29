package io.github.syske.springbootweblogicjndidemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootWeblogicJndiDemoApplicationTests {
        @Value("${spring.datasource.jndi-name}")
        private String JNDI_NAME;
    @Test
    public void contextLoads() throws NamingException {
            JndiObjectFactoryBean bean = new JndiObjectFactoryBean();

            Properties properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "t3://10.189.130.88:6201");
            //weblogic账号
            properties.put(Context.SECURITY_PRINCIPAL, "weblogic");
            //weblogic密码
            properties.put(Context.SECURITY_CREDENTIALS, "ydjy%*88_2019");//

            bean.setJndiEnvironment(properties);

            bean.setResourceRef(true);
            bean.setJndiName(JNDI_NAME);
            bean.setProxyInterface(DataSource.class);
            bean.setLookupOnStartup(false);
            bean.afterPropertiesSet();
            DataSource dataSource = (DataSource)bean.getObject();
            //打印出jndi数据源
            System.err.println(dataSource);
        }

        @Test
        public void test() throws NamingException, SQLException {
                Hashtable ht = new Hashtable();
                ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
                ht.put(Context.PROVIDER_URL, "t3://127.0.0.1:9901");

                Context context=new InitialContext(ht);

                //注意：lookup 中的参数 是你在weblogic中配置的JNDI名称

                DataSource  ds = (DataSource) context.lookup("sxlssds");  //配置的JNID名
                Connection conn = ds.getConnection("weblogic", "weblogic10");  //登陆weblogic的用户名、密码
                java.sql.Statement stmt=conn.createStatement();
                String sql="SELECT * FROM dual";
                ResultSet rs=stmt.executeQuery(sql);
                while (rs.next()){
                        System.out.println(rs.getInt(1));
                }
        }

}
