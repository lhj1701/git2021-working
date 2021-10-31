package com.example.sales.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 엔티티모델
public class Product {
	private int id;
	private String code;
	private String name;
	private int unitPrice;
}