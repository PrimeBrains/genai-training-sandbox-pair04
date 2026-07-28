package com.example.api;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /** POST /expenses/reimburse — 1明細の支給額を返す */
    @PostMapping("/reimburse")
    public int reimburse(@RequestBody ReimburseRequest request) {
        return expenseService.reimburse(new ExpenseItem(request.category(), request.amount()));
    }

    /** POST /expenses/total — 明細リストの支給額合計を返す */
    @PostMapping("/total")
    public int total(@RequestBody TotalRequest request) {
        List<ExpenseItem> items = request.items().stream()
                .map(r -> new ExpenseItem(r.category(), r.amount()))
                .toList();
        return expenseService.total(items);
    }
}
