package com.bill.user.api.rest.dong.model.response;

import com.bill.user.common.ResponseService;
import com.bill.user.service.dong.model.SettleDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SettleResponse extends ResponseService {

    private List<SettleDto> settles;

}
