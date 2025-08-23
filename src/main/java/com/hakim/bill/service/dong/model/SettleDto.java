package com.hakim.bill.service.dong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettleDto {

    private Long fromUserId;

    private Long toUserId;

    private Long amount;

}
