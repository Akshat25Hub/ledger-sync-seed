# Task 1: Track Flow Teardown

## 1. Permissions & Sync Stage
* **Permission Request Experience:** The app prompts for SMS and email read permissions cleanly. The rationale is clear, but granting it requires trust since financial data is sensitive.
* **Sync Behavior:** Upon granting permissions, the sync progress indicator runs through the message corpus smoothly.
* *[Insert screenshot of the permission request and sync screen here]*

## 2. Transaction List & Accuracy
* **Observed Output:** The resulting transaction list successfully captures standard debits and credits from bank channels.
* **Missed / Incorrect Transactions:**
    1. *Example 1:* A peer-to-peer UPI transfer was categorized under general spend instead of transfer/micro.
    2. *Example 2:* A promotional SMS mentioning a loan offer was incorrectly parsed as an incoming transaction amount.
* *[Insert screenshot of the transaction list here]*

## 3. Trust Analysis
* **Where I Trusted It:** Standard bank SMS alerts (like debit/credit notifications with clear merchant names and exact amounts) inspire high trust.
* **Where I Didn't Trust It:** Ambiguous notifications or promotional messages where numbers can be misinterpreted as transaction values.

## 4. Three Things I'd Change & Why
1. **Manual Override / Correction:** Allow users to manually recategorize or correct misclassified transactions.
2. **Parser Guardrails:** Stricter validation on promotional vs. transactional keywords to avoid false positives.
3. **Sync Transparency:** Show a detailed breakdown of skipped messages (e.g., why a promotional SMS was ignored) so the user knows nothing important was accidentally dropped.