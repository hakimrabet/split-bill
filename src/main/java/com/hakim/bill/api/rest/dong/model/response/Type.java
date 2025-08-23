package com.hakim.bill.api.rest.dong.model.response;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

	PAYMENT(0), BUDGET(1), SHOP(2);

	private final int value;

	@JsonCreator
	public static Type fromValue(int i) {
		return Stream.of(values())
			.filter(data -> data.value == i)
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("undefined value found for type " + i));
	}

	@JsonValue
	public int getValue() {
		return value;
	}

}
