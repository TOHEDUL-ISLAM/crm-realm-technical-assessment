# CRM Realm Technical Assessment

The work is split into three folders:

- [java/](java/) contains the console Lead Tracker.
- [javascript/solutions.js](javascript/solutions.js) contains the three functions and their examples.
- [sql/queries.sql](sql/queries.sql) contains the two SQL queries.

## How to run

You need a JDK (Java 8 or later) and Node.js. Run these commands from the main project folder.

For Java:

```sh
javac java/Lead.java java/LeadTracker.java
java -cp java LeadTracker
```

Choose `1` to add a lead, `2` to view leads, or `3` to exit. Leads stay in memory and are lost when the program closes. While adding a lead, type `/cancel` at any prompt to return to the menu without saving.

For JavaScript:

```sh
node javascript/solutions.js
```

Each function is followed by hardcoded examples and edge cases. The terminal prints the question, input, and answer. These are manual checks, with no automatic pass/fail report. Compare the answers with the cases below. No packages need to be installed.

## How the Java application developed

I kept lead details in `Lead` and the menu in `LeadTracker`. An `ArrayList` holds the leads while the program runs. Priority is calculated from the score rather than saved separately, so the two cannot disagree.

The first version only checked whether an email was empty. That would accept a value like `example.com`, even though it was only a domain. I added a basic check for an address like `alex@example.com`. It catches obvious format mistakes but does not confirm that the address exists. Spaces around names and emails are trimmed, and spaces alone count as empty input.

Another issue appeared when all fields were empty. The program tried converting the score first, so only the score error appeared. Validation now happens one field at a time. A mistake prompts the user to retry that field while keeping the details already entered correctly.

Then there was the question of choosing Add by accident. `/cancel` gives the user a way back without completing the form or saving a partial lead. It works at all three prompts, including retries, and keeps existing leads. A save confirmation and a numbered table make it easier to review what was added.

## JavaScript approach

**First non-repeating character:** The starting idea was frequency counting, like a counting array in C++. A `Map` holds the counts. The first loop counts using lowercase keys; the second reads the original string to find the first count of one. This preserves the original order and case. I chose to count only English letters and digits, ignoring spaces, punctuation, and other characters so formatting does not become the answer.

**Two sum:** The idea was to find the number that completes the target. Modulo was considered first, but it gives a remainder. The needed value is `target - current`. A `Map` remembers earlier numbers and their indices, avoiding repeated searches. Checking before storing the current element prevents using the same element twice. Inputs are assumed to be integers, as stated in the question.

**Count leads by status:** One loop reads each lead's status and increases its count in a `Map`, which is then converted into an object. New statuses are handled automatically, and the original leads are not changed. Statuses are assumed to be strings.

To include statuses with zero leads, I would initialize known statuses with zero before counting. New statuses could still be added as they appear.

## SQL

The queries use PostgreSQL. They assume `leads` already exists with `id`, `name`, `email`, and a non-null integer `score` from 0 to 100.

The first query selects scores of 80 or higher. The second uses `CASE` and `COUNT` to return HOT, WARM, and COLD totals in one row, including zero for empty groups. SQL is separate from the Java application.

## Cases to check

### Java: Lead Tracker

| Check | Input or action | Result |
| --- | --- | --- |
| COLD boundaries | Score `0` or `49` | **COLD** |
| WARM boundaries | Score `50` or `79` | **WARM** |
| HOT boundaries | Score `80` or `100` | **HOT** |
| Required fields | Blank name or email | Message and retry of that field |
| Email format | `example.com` | Ask for an address like `alex@example.com` |
| Score range | `-1` or `101` | Ask for a score from 0 to 100 |
| Score type | `abc` or blank | Ask for a whole number |
| Empty list | View before adding a lead | Helpful message explaining how to add one |
| Cancel | `/cancel` during entry or a retry | Return to the menu; no partial lead saved; existing leads kept |

### JavaScript

**1. First non-repeating character**

| Input | Result |
| --- | --- |
| `"swiss"` | `"w"` |
| `"aAB"` | `"B"` — original case preserved |
| `"aa!1bb"` | `"1"` — punctuation ignored, digit counted |
| `"aabb"` | `null` — no unique character |
| `""` | `null` — empty input |
| `123` without quotes | `TypeError` — input must be a string |

**2. Two sum**

| Array | Target | Result |
| --- | --- | --- |
| `[2, 7, 11, 15]` | `9` | `[0, 1]` |
| `[3, 3]` | `6` | `[0, 1]` — different elements with equal values |
| `[1, 2, 3]` | `10` | `null` — no pair |
| `[3]` | `6` | `null` — cannot use the same element twice |
| `[]` | `0` | `null` |
| `[0, 0]` | `0` | `[0, 1]` |
| `[-3, 4, 3]` | `0` | `[0, 2]` |

**3. Count leads by status**

| Check | Result |
| --- | --- |
| Statuses `NEW`, `CONTACTED`, `NEW`, `QUALIFIED` | `{ NEW: 2, CONTACTED: 1, QUALIFIED: 1 }` |
| Empty array | `{}` |
| One lead with the new status `FOLLOW_UP` | `{ FOLLOW_UP: 1 }` |
| Compare the original leads before and after counting | Unchanged |

### SQL: PostgreSQL

These cases were checked against both queries. Counts are shown in **HOT / WARM / COLD** order.

| Scores in the table | HOT scores returned | Counts |
| --- | --- | --- |
| `0, 49, 50, 79, 80, 100` | `80, 100` | `2 / 2 / 2` |
| Empty table | No rows | `0 / 0 / 0` |
| `80, 85, 100` | `80, 85, 100` | `3 / 0 / 0` |

## With more time

- Add a small user interface with a form to enter leads and a table to view them. This would make the tracker easier to use without knowing the console commands.
- Use [Jest](https://jestjs.io/docs/getting-started) to turn the JavaScript examples into automated tests, especially for empty inputs, duplicate numbers, and new statuses. This would make it easier to check that later changes still work.
- Save and load leads from a local file so they are still available after closing the application.

## Resources used

- [MDN: Map](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map) and [Object.fromEntries](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/fromEntries) were consulted while reviewing how the JavaScript functions store counts and turn them into an object.
- [W3Schools: SQL CASE](https://www.w3schools.com/sql/sql_case.asp) was consulted while reviewing the conditions used in the priority-count query.
- OpenAI Codex helped discuss the approaches, write and refine the code and README, generate test cases, and check the results. The solutions were revised through the examples and decisions described above.
