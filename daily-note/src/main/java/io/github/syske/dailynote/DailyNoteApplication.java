package io.github.syske.dailynote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.function.Supplier;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "io.github.syske.dailynote")
//@EnableDiscoveryClient
public class DailyNoteApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DailyNoteApplication.class, args);
        run.getBean("dalyNoteApplication");
        run.close();
        try {
            Class<?> contextClass = Class.forName("java.lang.String");
            String str = (String)contextClass.newInstance();
            contextClass.getDeclaredConstructors();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Supplier<String> stringSupplier = String::new;
        String s = stringSupplier.get();

    }

}
