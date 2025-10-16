import java.util.*;

/**
 * Variant of LeetCode 1169. Invalid Transactions and then write a Unit Test to
 * verify the solution.
 * 
 * /*
 * Fraud Detection: Find Suspicious Transactions
 * 
 * A bank wants to detect fraudulent transactions. A transaction is suspicious
 * if:
 * - The same user makes two transactions within 100 seconds of each other.
 * - A single transaction amount exceeds $10,000.
 * 
 * Return a list of users who have made fraudulent transactions.
 * 
 * Example
 * 
 * Input:
 * transactions = [
 * { "userId": "Alice", "amount": 5000, "timestamp": 10050 },
 * { "userId": "Alice", "amount": 2000, "timestamp": 10200 },
 * { "userId": "Bob", "amount": 12000, "timestamp": 10400 },
 * { "userId": "Alice", "amount": 6000, "timestamp": 10700 }
 * ]
 * 
 * Output:
 * ["Alice", "Bob"]
 */

class Solution {

    class Transaction {
        String userId;
        int amount;
        int timestamp;

        Transaction(String userId, int amount, int timestamp) {
            this.userId = userId;
            this.amount = amount;
            this.timestamp = timestamp;
        }
    }

    public List<String> findSuspiciousTransactions(List<Transaction> transactions) {
        Set<String> suspiciousUsers = new HashSet<>();
        Map<String, List<Transaction>> userTransactions = new HashMap<>();
    
        // Group transactions by userId
        for (Transaction transaction : transactions) {
            userTransactions
                .computeIfAbsent(transaction.userId, k -> new ArrayList<>())
                .add(transaction);
        }
    
        // Check each user's transactions for suspicious activity
        for (Map.Entry<String, List<Transaction>> entry : userTransactions.entrySet()) {
            String userId = entry.getKey();
            List<Transaction> userTrans = entry.getValue();
    
            // Sort transactions by timestamp
            userTrans.sort(Comparator.comparingInt(t -> t.timestamp));
    
            for (int i = 0; i < userTrans.size(); i++) {
                Transaction t1 = userTrans.get(i);
    
                // Check if the transaction amount exceeds $10,000
                if (t1.amount > 10000) {
                    suspiciousUsers.add(userId);
                }
    
                // Check for transactions within 100 seconds
                for (int j = i + 1; j < userTrans.size(); j++) {
                    Transaction t2 = userTrans.get(j);
                    if (t2.timestamp - t1.timestamp <= 100) {
                        suspiciousUsers.add(userId);
                    } else {
                        break; // No need to check further as the list is sorted
                    }
                }
            }
        }
    
        return new ArrayList<>(suspiciousUsers);
    }