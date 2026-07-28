package com.example.api;

public record ReimburseRequest(ExpenseItem.Category category, int amount) {}
