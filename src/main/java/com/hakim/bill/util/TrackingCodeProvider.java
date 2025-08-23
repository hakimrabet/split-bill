package com.hakim.bill.util;

import java.util.UUID;

public final class TrackingCodeProvider {

	private TrackingCodeProvider() {

	}

	public static String generate() {
		int uniqueID;
		do {
			uniqueID = UUID.randomUUID().toString().hashCode() & Integer.MAX_VALUE;
		}
		while (uniqueID == 0);
		return String.valueOf(uniqueID) + System.currentTimeMillis();
	}

}