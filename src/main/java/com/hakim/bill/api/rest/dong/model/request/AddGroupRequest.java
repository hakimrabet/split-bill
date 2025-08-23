package com.hakim.bill.api.rest.dong.model.request;

import java.util.ArrayList;
import java.util.List;


import com.hakim.bill.common.RequestService;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class AddGroupRequest extends RequestService {

	@NotBlank
	private String name;

	private List<Long> members = new ArrayList<>();

	private String icon;

	private String description;

}