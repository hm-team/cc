package hanmi.util.jdbc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * Created by renyangze on 2016/11/22.
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final String key = "0002000200020002";

    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        try {
            Des des = new Des();
            String username = props.getProperty("jdbc.username");
            if (username != null) {
                props.setProperty("jdbc.username", des.Decrypt(username, des.hex2byte(key)));
            }

            String password = props.getProperty("jdbc.password");
            if (password != null) {
                props.setProperty("jdbc.password", des.Decrypt(password, des.hex2byte(key)));
            }

            String url = props.getProperty("jdbc.url");
            if (url != null) {
                props.setProperty("jdbc.url", des.Decrypt(url, des.hex2byte(key)));
            }

            String driverClassName = props.getProperty("jdbc.driverClassName");
            if(driverClassName != null){
                props.setProperty("jdbc.driverClassName", des.Decrypt(driverClassName, des.hex2byte(key)));
            }

            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }
}
