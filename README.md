# Simple Lead Tracker — Part A

A Java console application that keeps leads in an `ArrayList` for the current session.

## Run

Install a Java Development Kit (JDK 8 or later), then run these commands from this folder:

```sh
javac Lead.java LeadTracker.java
java LeadTracker
```

Choose `1` to add a lead, `2` to view all leads, or `3` to exit. Leads are lost when the application exits.

Invalid input prompts you to retry just that field, preserving previously entered details. After saving, a confirmation shows the lead's name, score, and priority. Viewing leads shows the total count and a numbered table whose columns expand to fit the saved names and emails.

## Test cases

Run the application and check these inputs through the menu:

| Input or action | Expected result |
| --- | --- |
| View before adding any leads | Helpful message explaining how to add a lead |
| Add leads with scores `0`, `49` | COLD |
| Add leads with scores `50`, `79` | WARM |
| Add leads with scores `80`, `100` | HOT |
| Empty or whitespace-only name or email | Helpful error; retry the same field without losing earlier details |
| Emails `molla.com`, `@molla.com`, `name@`, `name@@molla.com`, or `name @molla.com` | Immediate email-format error; retry email before entering the score |
| Emails `nirzon@molla.com` or `first.last+work@example.co.uk` | Accepted |
| Scores `-1`, `101` | Clear range error; retry score while preserving the name and email |
| Scores `abc`, `50.5`, blank, or an integer too large for Java's `int` | Clear integer error; retry score |
| Correct an invalid email and score, then save | Exactly one lead saved; confirmation shows its name, score, and priority |
| Add multiple leads, including long names or emails, then view | Correct total count and numbered, aligned columns; full details are shown |
| Invalid menu option | Clear message; menu remains usable |

## Decisions

Names and emails are trimmed, and whitespace-only values count as empty. I added basic email-format validation to catch obvious input mistakes: it requires nonempty parts around a single `@`, a dotted domain with nonempty segments, and no internal whitespace. The same validation is used by the console and the `Lead` constructor. This is a simple format check, not full email-standard validation, and it does not verify whether the address exists.

Priority is calculated from the score in `Lead.calculatePriority()` and is never stored separately.

With more time, I would add automated regression tests for the menu interactions.

## Resources used

OpenAI Codex was used to read the assessment, implement Part A, and run compilation and input/output checks.
