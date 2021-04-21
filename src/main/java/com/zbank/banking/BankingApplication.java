package com.zbank.banking;

import com.zbank.banking.entity.Customer;
import com.zbank.banking.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@Bean
	CommandLineRunner demoCustomersInitializer(CustomerRepository customerRepository) {
		return args -> {
			List<Customer> demoCustomers =  Arrays.asList(
					new Customer("1", "John W"),
					new Customer("2", "Alex S"),
					new Customer("3", "Sam A"),
					new Customer("4", "Adel H")
			);
			customerRepository.saveAll(demoCustomers);
		};
	}
}