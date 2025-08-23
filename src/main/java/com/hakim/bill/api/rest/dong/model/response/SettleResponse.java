package com.hakim.bill.api.rest.dong.model.response;

import com.hakim.bill.common.ResponseService;
import com.hakim.bill.service.dong.model.SettleDto;
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
