const { Builder, By, until } = require("selenium-webdriver");

function byTestId(id) {
  return By.css(`[data-testid="${id}"]`);
}

async function run() {
  const driver = await new Builder()
    .usingServer("http://localhost:4444/wd/hub")
    .forBrowser("chrome")
    .build();

  try {
    // From the Selenium container, localhost points to itself.
    // host.docker.internal points to the Windows host where Nginx runs (8080).
    await driver.get("http://host.docker.internal:8080");

    // 1) Wait for input to load
    const usernameInput = await driver.wait(
      until.elementLocated(byTestId("username-input")),
      10000
    );

    // 2) Reset users to keep the test deterministic
    const resetUsersBtn = await driver.findElement(byTestId("reset-users-btn"));
    await resetUsersBtn.click();

    const usersCount = await driver.findElement(byTestId("users-count"));
    await driver.wait(
      async () => (await usersCount.getText()).trim() === "0 users",
      10000,
      "Reset did not complete"
    );

    // 3) Use a unique username
    const username = `e2e-user-${Date.now()}`;
    await usernameInput.clear();
    await usernameInput.sendKeys(username);

    // 4) Create user
    const createBtn = await driver.findElement(byTestId("create-user-btn"));
    await createBtn.click();

    // 5) Refresh list (in case the UI does not always auto-refresh)
    const refreshUsersBtn = await driver.findElement(byTestId("refresh-users-btn"));
    await refreshUsersBtn.click();

    // 6) Verify the user is listed
    const usersList = await driver.findElement(byTestId("users-list"));
    await driver.wait(
      async () => {
        const text = await usersList.getText();
        return text.includes(username);
      },
      10000,
      "User was not found in the list"
    );

    // 7) Health should be OK (optional but a good check)
    const healthBadge = await driver.findElement(byTestId("health-badge"));
    const healthText = await healthBadge.getText();
    if (!healthText.toLowerCase().includes("ok")) {
      throw new Error(`Health badge is not OK. Got: "${healthText}"`);
    }

    console.log("OK - E2E created and listed user:", username);
  } finally {
    await driver.quit();
  }
}

run().catch((e) => {
  console.error("E2E FAILED:", e);
  process.exit(1);
});
