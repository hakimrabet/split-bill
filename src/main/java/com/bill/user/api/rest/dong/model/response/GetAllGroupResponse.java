package com.bill.user.api.rest.dong.model.response;

import java.util.List;

import com.bill.user.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GetAllGroupResponse extends ResponseService {

	private List<GroupDto> groups;

}
