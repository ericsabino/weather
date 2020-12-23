package br.com.ems.weather.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfiguration implements StringEncryptor {

    @Bean("encryptorBean")
    public StringEncryptor stringEncryptor() {
        return new JasyptConfiguration();
    }

    @Override
    public String encrypt(String s) {
        return null;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

        textEncryptor.setPassword("kexvvsqr!!11dw");
        return textEncryptor.decrypt(encryptedMessage);
    }
}


