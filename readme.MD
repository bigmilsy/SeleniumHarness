# Dale's Automated Test Harness
## A demonstration website that uses my own test harness in the form of a DHTML webpages. The test harness talks to a Java backend system that will fire up automated tests using selenium’s web driver.

**SAMPLE OUTPUT**<br /><br />

START TEST SUITE<br />
---Pre Execution Checks:<br />
Does Selenium Web Driver Load?...PASS<br />
Can Test Harness Talk to the DB?...PASS<br />
Can Test Harness Talk to the API?...PASS<br /><br />

---Regression Tests:<br />
TDD: MYSQL TEST: Insert Toy...PASS<br />
TDD: MYSQL TEST: Update Toy...PASS<br />
TDD: MYSQL TEST: Delete Toy...PASS<br />
TDD: API TEST: Create Toy...PASS<br />
TDD: API TEST: Select Toy...PASS<br />
TDD: API TEST: Update Toy...PASS<br />
TDD: API TEST: Delete Toy...PASS<br />
BDD: WEB CHECK: Page body...PASS<br />
BDD: WEB: Create more toys...PASS<br />
BDD: WEB: Select created toy...PASS<br />
BDD: WEB: Delete created toy...PASS<br /><br />

---Post Execution Clean:<br />
Quit Selenium Web Driver...OK<br />