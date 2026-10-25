# Simple Lead Tracker — Part A

A Java console application that keeps leads in an `ArrayList` for the current session.

## Run

Install a Java Development Kit (JDK 8 or later), then run these commands from this folder:

```sh
javac Lead.java LeadTracker.java
java LeadTracker
```

Choose `1` to add a lead, `2` to view all leads, or `3` to exit. Leads are lost when the application exits.

## Test cases

Run the application and check these inputs through the menu:

| Input or action | Expected result |
| --- | --- |
| View before adding any leads | Helpful message explaining how to add a lead |
| Add leads with scores `0`, `49` | COLD |
| Add leads with scores `50`, `79` | WARM |
| Add leads with scores `80`, `100` | HOT |
| Empty or whitespace-only name or email | Immediate error for that field, before prompting for the next field; no lead added |
| Scores `-1`, `101` | Clear range error; no lead added |
| Scores `abc`, `50.5`, blank, or an integer too large for Java's `int` | Clear integer error; no lead added |
| Add multiple leads, then view | Every lead's name, email, score, and priority appears |
| Invalid menu option | Clear message; menu remains usable |

## Decisions

Names and emails are trimmed, and whitespace-only values count as empty. Email format validation is not required by Part A, so any nonempty email is accepted.

Priority is calculated from the score in `Lead.calculatePriority()` and is never stored separately.

With more time, I would add automated regression tests for the menu interactions.

## Resources used

OpenAI Codex was used to read the assessment, implement Part A, and run compilation and input/output checks.
