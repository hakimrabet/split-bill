package com.bill.user.api.rest.dong;

import com.bill.user.AbstractContainerBaseTest;
import com.bill.user.api.rest.dong.model.request.AddExpenseRequest;
import com.bill.user.api.rest.dong.model.request.EditExpenseRequest;
import com.bill.user.api.rest.dong.model.request.SplitDto;
import com.bill.user.api.rest.dong.model.response.*;
import com.bill.user.common.GeneralResponse;
import com.bill.user.common.ResultStatus;
import com.bill.user.model.dong.Expense;
import com.bill.user.model.dong.Group;
import com.bill.user.model.dong.Split;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ExpenseControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao userDao;

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
        assertThat(response.getBody().getExpense()).isNotNull();

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
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        EditExpenseRequest request = new EditExpenseRequest();
        request.setAmount(300_000L);
        request.setDescription("morgh");
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

        ResponseEntity<ExpenseResponse> response = restTemplate.exchange("/split/expense/expense-id",
                HttpMethod.PUT, entity, ExpenseResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(response.getBody().getExpense()).isNotNull();

        assertThat(response.getBody().getExpense().getExpenseId()).isNotBlank();
        assertThat(response.getBody().getExpense().getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody().getExpense().getAmount()).isEqualTo(300000L);
        assertThat(response.getBody().getExpense().getDescription()).isEqualTo("morgh");
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
    void getExpenseByGroupId() {
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<GetAllExpenseResponse> response = restTemplate.exchange("/split/expense/group-id",
                HttpMethod.GET, entity, GetAllExpenseResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(response.getBody().getExpenses()).isNotNull();
        assertThat(response.getBody().getExpenses()).hasSize(1);

        assertThat(response.getBody().getExpenses().get(0).getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody().getExpenses().get(0).getAmount()).isEqualTo(300000L);
        assertThat(response.getBody().getExpenses().get(0).getDescription()).isEqualTo("Expense 1 description");
        assertThat(response.getBody().getExpenses().get(0).getExpenseId()).isEqualTo("expense-id");
        assertThat(response.getBody().getExpenses().get(0).getType()).isEqualTo(Type.SHOP);
        assertThat(response.getBody().getExpenses().get(0).getSplits()).hasSize(3);
        assertThat(response.getBody().getExpenses().get(0).getSplits().get(0).getCreditAmount()).isEqualTo(200_000);
        assertThat(response.getBody().getExpenses().get(0).getSplits().get(0).getDebtAmount()).isEqualTo(0);
        assertThat(response.getBody().getExpenses().get(0).getSplits().get(0).getUserId()).isEqualTo(1L);
    }

    @Test
    void getUserGroupBalance1() {
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<UserGroupBalanceResponse> response = restTemplate.exchange("/split/expense/balance/group-id",
                HttpMethod.GET, entity, UserGroupBalanceResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(response.getBody().getBalance()).isEqualTo(200_000);
    }

    @Test
    void getUserGroupBalance2() {
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "2");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<UserGroupBalanceResponse> response = restTemplate.exchange("/split/expense/balance/group-id",
                HttpMethod.GET, entity, UserGroupBalanceResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(response.getBody().getBalance()).isEqualTo(-100_000);
    }

    @Test
    void getByExpenseId() {
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<ExpenseResponse> response = restTemplate.exchange("/split/expense/detail/expense-id",
                HttpMethod.GET, entity, ExpenseResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(response.getBody().getExpense().getExpenseId()).isEqualTo("expense-id");
        assertThat(response.getBody().getExpense().getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody().getExpense().getAmount()).isEqualTo(300000L);
        assertThat(response.getBody().getExpense().getDescription()).isEqualTo("Expense 1 description");
        assertThat(response.getBody().getExpense().getType()).isEqualTo(Type.SHOP);
        assertThat(response.getBody().getExpense().getSplits()).hasSize(3);

        assertThat(response.getBody().getExpense().getSplits().get(0).getCreditAmount()).isEqualTo(200000L);
        assertThat(response.getBody().getExpense().getSplits().get(0).getDebtAmount()).isEqualTo(0L);
        assertThat(response.getBody().getExpense().getSplits().get(0).getUserId()).isEqualTo(1);

        assertThat(response.getBody().getExpense().getSplits().get(1).getCreditAmount()).isEqualTo(0L);
        assertThat(response.getBody().getExpense().getSplits().get(1).getDebtAmount()).isEqualTo(100000L);
        assertThat(response.getBody().getExpense().getSplits().get(1).getUserId()).isEqualTo(2);

        assertThat(response.getBody().getExpense().getSplits().get(2).getCreditAmount()).isEqualTo(0L);
        assertThat(response.getBody().getExpense().getSplits().get(2).getDebtAmount()).isEqualTo(100000L);
        assertThat(response.getBody().getExpense().getSplits().get(2).getUserId()).isEqualTo(3);
    }

    @Test
    void delete() {
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<GeneralResponse> response = restTemplate.exchange("/split/expense/expense-id",
                HttpMethod.DELETE, entity, GeneralResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);

        Optional<Expense> expense = expenseDao.findByExpenseId("expense-id");
        assertThat(expense).isEmpty();
    }

    @Test
    void getSettleByGroup() {
        createGroupWithExpense();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<SettleResponse> response = restTemplate.exchange("/split/expense/settle/group-id",
                HttpMethod.GET, entity, SettleResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getResult().getTitle()).isEqualTo(ResultStatus.SUCCESS);

        assertThat(response.getBody().getSettles().get(0).getFromUserId()).isEqualTo(2);
        assertThat(response.getBody().getSettles().get(0).getToUserId()).isEqualTo(1);
        assertThat(response.getBody().getSettles().get(0).getAmount()).isEqualTo(100_000);

        assertThat(response.getBody().getSettles().get(1).getFromUserId()).isEqualTo(3);
        assertThat(response.getBody().getSettles().get(1).getToUserId()).isEqualTo(1);
        assertThat(response.getBody().getSettles().get(1).getAmount()).isEqualTo(100_000);

    }

    private void createGroup() {
        List<User> users = userDao.findAllByIdIn(List.of(1L, 2L, 3L));

        Group group = new Group(null, "group-id", "group-name", users.getFirst(), users,
                null, "icon", "group desc", null,
                null, null);

        groupDao.save(group);
    }

    private void createGroupWithExpense() {
        createGroup();
        List<User> users = userDao.findAllByIdIn(List.of(1L, 2L, 3L));
        Group group = groupDao.findByGroupId("group-id");

        Expense expense1 = new Expense();
        expense1.setExpenseId("expense-id");
        expense1.setAmount(300_000L);
        expense1.setDescription("Expense 1 description");
        expense1.setType(com.bill.user.model.dong.Type.SHOP);
        expense1.setGroup(group);
        expense1.setCreationDate(System.currentTimeMillis());
        expense1.setLastModificationDate(System.currentTimeMillis());

        Split split1 = new Split(null, "split-1", users.get(0), 200_000L, 0L, null);
        Split split2 = new Split(null, "split-2", users.get(1), 0L, 100_000L, null);
        Split split3 = new Split(null, "split-3", users.get(2), 0L, 100_000L, null);

        expense1.addSplit(split1);
        expense1.addSplit(split2);
        expense1.addSplit(split3);

        expenseDao.saveAndFlush(expense1);
    }
}