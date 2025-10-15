import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Solution {

    class Transaction {

        String name;
        int time;
        int amount;
        String city;

        Transaction(String input) {
            String[] transaction = input.split(",");
            this.name = transaction[0];
            this.time = Integer.parseInt(transaction[1]);
            this.amount = Integer.parseInt(transaction[2]);
            this.city = transaction[3];
        }
    }

    public List<String> invalidTransactions(String[] transactions) {
        List<String> invalidTxnList = new ArrayList<>();
        Map<String, List<Transaction>> txnMap = new HashMap<>();
        for (String input : transactions) {
            Transaction txn = new Transaction(input);
            txnMap.putIfAbsent(txn.name, new ArrayList<>());
            txnMap.get(txn.name).add(txn);
        }
        for (String input : transactions) {
            Transaction txn = new Transaction(input);
            if (!isValid(txn, txnMap.get(txn.name))) {
                invalidTxnList.add(input);
            }
        }
        return invalidTxnList;
    }

    public boolean isValid(Transaction txn, List<Transaction> txnList) {
        if (txn.amount > 1000) {
            return false;
        }
        for (Transaction t : txnList) {
            if (!t.city.equals(txn.city) && Math.abs(t.time - txn.time) <= 60) {
                return false;
            }
        }
        return true;
    }

}