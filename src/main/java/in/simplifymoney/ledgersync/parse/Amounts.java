package in.simplifymoney.ledgersync.parse;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rupee amounts as banks write them.
 *
 * Handles the prefixes we see in practice - "Rs.", "Rs ", "INR " - and strips
 * the thousands separators before handing back a BigDecimal.
 */
public final class Amounts {

    private Amounts() {}

    // Updated pattern to handle both decimals (.00) and integers (like Rs.5)
    private static final Pattern AMOUNT =
            Pattern.compile("(?:Rs\\.?|INR)\\s*([0-9,]+(?:\\.[0-9]{1,2})?)");

    private static final Pattern BALANCE = Pattern.compile(
            "(?:Avl\\s*Bal|Available\\s*Balance|BalAvl|Avl\\s*Limit)\\s*:?\\s*"
                    + "(?:Rs\\.?|INR)\\s*([0-9,]+\\.[0-9]{2})",
            Pattern.CASE_INSENSITIVE);

    /** The transaction amount: the first rupee figure in the message, ignoring balance */
    public static BigDecimal first(String body) {
        // If there's a stated balance, we should ensure we match the amount *before* the balance part
        // Or cleaner: let's find all matches and pick the first one that doesn't belong to the balance.
        Matcher m = AMOUNT.matcher(body);
        BigDecimal statedBal = statedBalance(body);

        while (m.find()) {
            BigDecimal val = toDecimal(m.group(1));
            // If this amount matches the stated balance, it's likely the balance, not the txn amount
            if (statedBal != null && val.compareTo(statedBal) == 0) {
                continue;
            }
            return val;
        }
        return null;
    }

    /** The balance the bank quoted, if it quoted one. */
    public static BigDecimal statedBalance(String body) {
        Matcher m = BALANCE.matcher(body);
        if (!m.find()) return null;
        return toDecimal(m.group(1));
    }

    private static BigDecimal toDecimal(String raw) {
        return new BigDecimal(raw.replace(",", "")).setScale(2);
    }
}