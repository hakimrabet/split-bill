package com.bill.user.api.rest.dong.model.response;

import com.bill.user.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GroupResponse extends ResponseService {

	private GroupDto group;

}
