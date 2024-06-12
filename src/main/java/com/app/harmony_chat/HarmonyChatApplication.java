package com.app.harmony_chat;

import com.app.harmony_chat.configs.DefineEmail;
import com.app.harmony_chat.utils.auths.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HarmonyChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarmonyChatApplication.class, args);
	}


}
