package com.bill.user.api.rest.dong;

import com.bill.user.api.rest.dong.mapper.ExpenseResourceMapper;
import com.bill.user.api.rest.dong.model.request.AddExpenseRequest;
import com.bill.user.api.rest.dong.model.request.EditExpenseRequest;
import com.bill.user.api.rest.dong.model.response.ExpenseResponse;
import com.bill.user.api.rest.dong.model.response.GetAllExpenseResponse;
import com.bill.user.api.rest.dong.model.response.UserGroupBalanceResponse;
import com.bill.user.common.GeneralResponse;
import com.bill.user.service.dong.ExpenseService;
import com.bill.user.service.dong.model.EditExpenseModel;
import com.bill.user.service.dong.model.ExpenseModel;
import com.bill.user.service.dong.model.ExpenseResult;
import com.bill.user.service.dong.model.GetAllExpenseResults;
import com.bill.user.service.dong.model.UserGroupBalanceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/split/expense")
public class ExpenseController {

	private final ExpenseService expenseService;

	private final ExpenseResourceMapper mapper;


	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody AddExpenseRequest request) {
		ExpenseModel model = mapper.toExpenseModel(request);
		ExpenseResult result = expenseService.addExpense(model);
		return ResponseEntity.ok(mapper.toExpenseResponse(result));
	}

	@PutMapping(path = "/{expenseId}", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseResponse> editExpense(@PathVariable("expenseId") String expenseId,
			@Valid @RequestBody EditExpenseRequest request) {
		EditExpenseModel model = mapper.toEditExpenseModel(expenseId, request);
		ExpenseResult response = expenseService.editExpense(model);
		return ResponseEntity.ok(mapper.toExpenseResponse(response));
	}

	@GetMapping(path = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetAllExpenseResponse> getExpenseByGroupId(@PathVariable("groupId") String groupId) {
		GetAllExpenseResults results = expenseService.getExpenseByGroupId(groupId);
		return ResponseEntity.ok(mapper.toGetAllExpenseResponse(results));
	}

	@GetMapping(path = "/balance/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserGroupBalanceResponse> getUserGroupBalance(@RequestHeader("X-User-Id") String userId,
			@PathVariable(name = "groupId") String groupId) {
		UserGroupBalanceResult result = expenseService.getUserBalance(mapper.toUserGroupBalanceModel(userId, groupId));
		UserGroupBalanceResponse response = mapper.toUserGroupBalanceResponse(result);
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/detail/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseResponse> getByExpenseId(@PathVariable("expenseId") String expenseId) {
		ExpenseResult result = expenseService.getExpenseByExpenseId(expenseId);
		ExpenseResponse response = mapper.toExpenseResponse(result);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(path = "/{expenseId}")
	public ResponseEntity<GeneralResponse> delete(@PathVariable("expenseId") String expenseId) {
		expenseService.deleteExpenseById(expenseId);
		return ResponseEntity.ok(GeneralResponse.success());
	}

}
