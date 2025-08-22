package com.bill.user.api.rest.dong;

import com.bill.user.AbstractContainerBaseTest;
import com.bill.user.api.rest.dong.model.request.AddExpenseRequest;
import com.bill.user.api.rest.dong.model.request.SplitDto;
import com.bill.user.api.rest.dong.model.response.ExpenseResponse;
import com.bill.user.api.rest.dong.model.response.Type;
import com.bill.user.common.ResultStatus;
import com.bill.user.model.dong.Group;
import com.bill.user.model.dong.dao.ExpenseDao;
import com.bill.user.model.dong.dao.GroupDao;
import com.bill.user.model.user.User;
import com.bill.user.model.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExpenseControllerIT extends AbstractContainerBaseTest {

    @Autowired
    ExpenseDao expenseDao;

    @BeforeEach
    void setUp() {
        expenseDao.deleteAll();
    }

    @Test
    void addExpense() {
        createGroup();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        AddExpenseRequest request = new AddExpenseRequest();
        request.setAmount(300_000L);
        request.setDescription("joje");
        request.setGroupId("group-id");
        request.setType(Type.SHOP);

        SplitDto dto1 = new SplitDto();
        dto1.setUserId(1L);
        dto1.setExpenseId("expense-id");
        dto1.setCreditAmount(200_000L);
        dto1.setDebtAmount(0L);

        SplitDto dto2 = new SplitDto();
        dto2.setUserId(2L);
        dto2.setExpenseId("expense-id");
        dto2.setCreditAmount(0L);
        dto2.setDebtAmount(100_000L);

        SplitDto dto3 = new SplitDto();
        dto3.setUserId(1L);
        dto3.setExpenseId("expense-id");
        dto3.setCreditAmount(0L);
        dto3.setDebtAmount(100_000L);

        request.setSplits(List.of(dto1, dto2, dto3));

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ExpenseResponse> response = restTemplate.exchange("/split/expense",
                HttpMethod.POST, entity, ExpenseResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(response.getBody().getExpense()).isEqualTo(ResultStatus.SUCCESS);

        assertThat(response.getBody().getExpense().getExpenseId()).isNotBlank();
        assertThat(response.getBody().getExpense().getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody().getExpense().getAmount()).isEqualTo(300000L);
        assertThat(response.getBody().getExpense().getDescription()).isEqualTo("joje");
        assertThat(response.getBody().getExpense().getType()).isEqualTo(Type.SHOP);
        assertThat(response.getBody().getExpense().getSplits()).hasSize(3);


        assertThat(response.getBody().getExpense().getSplits().get(0).getCreditAmount()).isEqualTo(200000L);
        assertThat(response.getBody().getExpense().getSplits().get(0).getDebtAmount()).isEqualTo(0L);

        assertThat(response.getBody().getExpense().getSplits().get(1).getCreditAmount()).isEqualTo(0L);
        assertThat(response.getBody().getExpense().getSplits().get(1).getDebtAmount()).isEqualTo(100000L);

        assertThat(response.getBody().getExpense().getSplits().get(2).getCreditAmount()).isEqualTo(0L);
        assertThat(response.getBody().getExpense().getSplits().get(2).getDebtAmount()).isEqualTo(100000L);
    }

    @Test
    void editExpense() {
    }

    @Test
    void getExpenseByGroupId() {
    }

    @Test
    void getUserGroupBalance() {
    }

    @Test
    void getByExpenseId() {
    }

    @Test
    void delete() {
    }

    @Test
    void getSettleByGroup() {
    }

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao userDao;

    private void createGroup() {
        List<User> users = userDao.findAllByIdIn(List.of(1L, 2L, 3L));

        Group group = new Group(null, "group-id", "group-name", users.getFirst(), users,
                null, "icon", "group desc", null,
                null, null);

        groupDao.save(group);
    }
}