package com.dian.Merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.dian.Merchant"})
public class MerchantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantApplication.class, args);
	}

}
