function firstNonRepeatingChar(input) {
    if (typeof input !== "string") {
        throw new TypeError("Input must be a string.");
    }

    const counts = new Map();

    for (const character of input) {
        if (
            (character >= "a" && character <= "z") ||
            (character >= "A" && character <= "Z") ||
            (character >= "0" && character <= "9")
        ) {
            const key = character.toLowerCase();
            counts.set(key, (counts.get(key) || 0) + 1);
        }
    }

    for (const character of input) {
        if (counts.get(character.toLowerCase()) === 1) {
            return character;
        }
    }

    return null;
}

console.log("Question 1: First non-repeating character");
console.log("Find the first character that appears once, ignoring case, and return its original form.");
console.log('Input: "swiss" | Answer:', firstNonRepeatingChar("swiss"));
console.log('Input: "aAB" | Answer:', firstNonRepeatingChar("aAB"));
console.log('Input: "aa!1bb" | Answer:', firstNonRepeatingChar("aa!1bb"));
console.log('Input: "aabb" | Answer:', firstNonRepeatingChar("aabb"));
console.log('Input: "" | Answer:', firstNonRepeatingChar(""));
console.log('Input: "  !? " | Answer:', firstNonRepeatingChar("  !? "));
console.log('Input: "Z" | Answer:', firstNonRepeatingChar("Z"));

try {
    console.log("Input: 123 | Answer:", firstNonRepeatingChar(123));
} catch (error) {
    console.log("Input: 123 | Answer:", error.name + ": " + error.message);
}

function findTwoSum(numbers, target) {
    const seen = new Map();

    for (let i = 0; i < numbers.length; i++) {
        const needed = target - numbers[i];

        if (seen.has(needed)) {
            return [seen.get(needed), i];
        }

        seen.set(numbers[i], i);
    }

    return null;
}

console.log("\nQuestion 2: Two sum");
console.log("Return the indices of two different elements whose sum equals the target, or null if no pair exists.");
console.log("Input: [2, 7, 11, 15], target: 9 | Answer:", findTwoSum([2, 7, 11, 15], 9));
console.log("Input: [3, 2, 4], target: 6 | Answer:", findTwoSum([3, 2, 4], 6));
console.log("Input: [3, 3], target: 6 | Answer:", findTwoSum([3, 3], 6));
console.log("Input: [1, 2, 3], target: 10 | Answer:", findTwoSum([1, 2, 3], 10));
console.log("Input: [], target: 0 | Answer:", findTwoSum([], 0));
console.log("Input: [3], target: 6 | Answer:", findTwoSum([3], 6));
console.log("Input: [0, 0], target: 0 | Answer:", findTwoSum([0, 0], 0));
console.log("Input: [-3, 4, 3], target: 0 | Answer:", findTwoSum([-3, 4, 3], 0));

function countLeadsByStatus(leads) {
    const counts = new Map();

    for (const lead of leads) {
        counts.set(lead.status, (counts.get(lead.status) || 0) + 1);
    }

    return Object.fromEntries(counts);
}

console.log("\nQuestion 3: Count leads by status");
console.log("Return the number of leads in each status without changing the original array.");

const leads = [
    { name: "Alice", status: "NEW" },
    { name: "Bob", status: "CONTACTED" },
    { name: "Charlie", status: "NEW" },
    { name: "David", status: "QUALIFIED" },
];
const originalLeads = JSON.stringify(leads);

console.log("Input:", JSON.stringify(leads), "| Answer:", countLeadsByStatus(leads));
console.log("Input: Were the original leads preserved? | Answer:", JSON.stringify(leads) === originalLeads);
console.log("Input: [] | Answer:", countLeadsByStatus([]));

const newStatusLeads = [
    { name: "Alice", status: "FOLLOW_UP" },
];
console.log("Input:", JSON.stringify(newStatusLeads), "| Answer:", countLeadsByStatus(newStatusLeads));
