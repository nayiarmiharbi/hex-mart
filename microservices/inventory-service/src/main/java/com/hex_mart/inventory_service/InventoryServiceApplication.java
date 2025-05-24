package com.hex_mart.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hex_mart.inventory_service.model.Inventory;
import com.hex_mart.inventory_service.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_13");
			inventory1.setQuantity(100);
			
			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iphone_13_red");
			inventory2.setQuantity(0);
			
			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("iphone_14");
			inventory3.setQuantity(200);
			
			Inventory inventory4 = new Inventory();
			inventory4.setSkuCode("iphone_14_red");
			inventory4.setQuantity(0);
			
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
			inventoryRepository.save(inventory4);
		};
	}
}
