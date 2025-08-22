package com.bill.user.service.dong.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettleResult {

	private Long fromUserId;

	private Long toUserId;

	private Long amount;

}
