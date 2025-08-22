package com.bill.user.api.rest.dong.model.response;

import java.util.List;

import com.bill.user.common.ResponseService;
import com.bill.user.service.dong.model.SettleDto;
import lombok.Data;

@Data
public class SettleResponse extends ResponseService {

	private List<SettleDto> settles;

}
