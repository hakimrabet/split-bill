package com.hakim.bill.service.dong.impl;

import com.hakim.bill.model.dong.Expense;
import com.hakim.bill.model.dong.Group;
import com.hakim.bill.model.dong.Split;
import com.hakim.bill.model.dong.dao.ExpenseDao;
import com.hakim.bill.model.dong.dao.GroupDao;
import com.hakim.bill.model.user.User;
import com.hakim.bill.model.user.dao.UserDao;
import com.hakim.bill.service.dong.ExpenseService;
import com.hakim.bill.service.dong.mapper.ExpenseServiceMapper;
import com.hakim.bill.service.dong.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseDao expenseDao;

    private final GroupDao groupDao;

    private final ExpenseServiceMapper mapper;

    private final UserDao userDao;

    @Override
    public ExpenseResult addExpense(ExpenseModel model) {
        Group group = groupDao.findByGroupId(model.getGroupId());
        Expense expense = mapper.toExpense(model, group);
        for (SplitResult s : model.getSplits()) {
            User user = userDao.findById(s.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("group id not valid"));
            Split split = mapper.toSplit(s, user);

            expense.addSplit(split);
        }
        Expense expenseFromDb = expenseDao.saveAndFlush(expense);
        return mapper.toExpenseResult(expenseFromDb);
    }

    @Override
    public ExpenseResult editExpense(EditExpenseModel model) {
        Expense expense = expenseDao.findByExpenseId(model.getExpenseId())
                .orElseThrow(() -> new IllegalArgumentException("invalid expense id"));

        mapper.toExpense(expense, model);
        expense.setSplits(new ArrayList<>());
        for (SplitResult s : model.getSplits()) {
            User user = userDao.findById(s.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("group id not valid"));
            Split split = mapper.toSplit(s, user);
            expense.addSplit(split);
        }

        Expense savedExpense = expenseDao.save(expense);
        return mapper.toExpenseResult(savedExpense);
    }

    @Override
    @Transactional(readOnly = true)
    public GetAllExpenseResults getExpenseByGroupId(String groupId) {
        List<Expense> expenses = expenseDao.findAllByGroupGroupId(groupId);
        return mapper.toGetAllExpenseResults(expenses);
    }

    @Override
    @Transactional(readOnly = true)
    public UserGroupBalanceResult getUserBalance(UserGroupBalanceModel model) {
        List<Expense> expenses = expenseDao.findAllByGroupGroupId(model.getGroupId());

        Long totalCreditMap = 0L;
        Long totalDebtMap = 0L;

        for (Expense expense : expenses) {
            for (Split split : expense.getSplits()) {
                if (split.getUser()
                        .getId() == model.getUserId()) {
                    Long creditAmount = split.getCreditAmount();
                    Long debtAmount = split.getDebtAmount();

                    totalCreditMap += creditAmount;
                    totalDebtMap += debtAmount;
                }
            }
        }

        Long balance = totalCreditMap - totalDebtMap;
        UserGroupBalanceResult result = new UserGroupBalanceResult();
        result.setBalance(balance);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResult getExpenseByExpenseId(String expenseId) {
        Expense expense = expenseDao.findByExpenseId(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("expense id not found"));
        return mapper.toExpenseResult(expense);
    }

    @Override
    public void deleteExpenseById(String expenseId) {
        expenseDao.deleteByExpenseId(expenseId);
    }

    @Transactional(readOnly = true)
    public SettleResult settleGroup(String groupId) {
        List<Expense> expenses = expenseDao.findAllByGroupGroupId(groupId);

        Map<Long, Long> balances = new HashMap<>();
        for (Expense expense : expenses) {
            for (Split split : expense.getSplits()) {
                Long userId = split.getUser()
                        .getId();
                Long credit = split.getCreditAmount() == null ? 0L : split.getCreditAmount();
                Long debt = split.getDebtAmount() == null ? 0L : split.getDebtAmount();
                balances.merge(userId, credit - debt, Long::sum);
            }
        }

        Queue<Map.Entry<Long, Long>> debtors = new LinkedList<>();
        Queue<Map.Entry<Long, Long>> creditors = new LinkedList<>();

//		Queue<Pair<Long, Long>> debtors = new LinkedList<>();
//		Queue<Pair<Long, Long>> creditors = new LinkedList<>();

        for (Map.Entry<Long, Long> e : balances.entrySet()) {
            if (e.getValue() < 0) {
                debtors.add(Map.entry(e.getKey(), -e.getValue()));
            } else if (e.getValue() > 0) {
                creditors.add(Map.entry(e.getKey(), e.getValue()));
            }
        }

        List<SettleDto> instructions = new ArrayList<>();

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Map.Entry<Long, Long> debtor = debtors.poll();
            Map.Entry<Long, Long> creditor = creditors.poll();

            Long payAmount = Math.min(debtor.getValue(), creditor.getValue());

            instructions.add(new SettleDto(
                    debtor.getKey(), creditor.getKey(), payAmount
            ));

            if (debtor.getValue() > payAmount) {
                debtors.add(Map.entry(debtor.getKey(), debtor.getValue() - payAmount));
            }
            if (creditor.getValue() > payAmount) {
                creditors.add(Map.entry(creditor.getKey(), creditor.getValue() - payAmount));
            }
        }

        return mapper.toSettleResult(instructions, null);
    }

}