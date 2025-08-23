package com.hakim.bill.api.rest.dong.model.request;

import java.util.List;

import com.hakim.bill.common.RequestService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class AddGroupMemberRequest extends RequestService {

	private List<Long> members;

}