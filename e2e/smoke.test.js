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
    // Desde el contenedor selenium, localhost apunta a sí mismo.
    // host.docker.internal apunta a tu Windows host donde está Nginx (8080).
    await driver.get("http://host.docker.internal:8080");

    // 1) Esperar a que cargue input
    const usernameInput = await driver.wait(
      until.elementLocated(byTestId("username-input")),
      10000
    );

    // 2) Escribir username único
    const username = `e2e-user-${Date.now()}`;
    await usernameInput.clear();
    await usernameInput.sendKeys(username);

    // 3) Crear usuario
    const createBtn = await driver.findElement(byTestId("create-user-btn"));
    await createBtn.click();

    // 4) Refrescar lista (por si tu UI no auto-refresca siempre)
    const refreshUsersBtn = await driver.findElement(byTestId("refresh-users-btn"));
    await refreshUsersBtn.click();

    // 5) Verificar que aparece el usuario (buscando dentro del UL)
    const usersList = await driver.findElement(byTestId("users-list"));
    await driver.wait(
      async () => {
        const text = await usersList.getText();
        return text.includes(username);
      },
      10000,
      "User was not found in the list"
    );

    // 6) Health should be OK (opcional pero muy buena comprobación)
    const healthBadge = await driver.findElement(byTestId("health-badge"));
    const healthText = await healthBadge.getText();
    if (!healthText.toLowerCase().includes("ok")) {
      throw new Error(`Health badge is not OK. Got: "${healthText}"`);
    }

    console.log("✅ E2E OK - created and listed user:", username);
  } finally {
    await driver.quit();
  }
}

run().catch((e) => {
  console.error("❌ E2E FAILED:", e);
  process.exit(1);
});
