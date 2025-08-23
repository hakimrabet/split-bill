package com.hakim.bill.model.dong;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

	PAYMENT(0), BUDGET(1), SHOP(2);

	private final int value;

}
