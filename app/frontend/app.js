const API_BASE = "/api";

const el = (id) => document.getElementById(id);

const healthBadge = el("healthBadge");
const refreshHealthBtn = el("refreshHealthBtn");

const createUserForm = el("createUserForm");
const usernameInput = el("username");
const clearBtn = el("clearBtn");

const refreshUsersBtn = el("refreshUsersBtn");
const usersList = el("usersList");
const usersCount = el("usersCount");

const debugBox = el("debugBox");

function setDebug(message) {
  const ts = new Date().toISOString();
  debugBox.textContent = `[${ts}] ${message}`;
}

function setHealth(state, text) {
  healthBadge.classList.remove("ok", "warn", "err");
  if (state) healthBadge.classList.add(state);
  healthBadge.textContent = text;
}

async function checkHealth() {
  setHealth("warn", "Checking...");
  try {
    const res = await fetch(`${API_BASE}/health`, { method: "GET" });
    const text = await res.text();

    if (res.ok && text.trim().toUpperCase() === "OK") {
      setHealth("ok", "Health: OK");
      setDebug("Health check OK.");
      return true;
    }

    setHealth("warn", `Health: ${res.status}`);
    setDebug(`Health check returned status ${res.status}. Body: ${text}`);
    return false;
  } catch (err) {
    setHealth("err", "Health: error");
    setDebug(`Health check error: ${err?.message ?? err}`);
    return false;
  }
}

function renderUsers(users) {
  usersList.innerHTML = "";

  const count = Array.isArray(users) ? users.length : 0;
  usersCount.textContent = `${count} user${count === 1 ? "" : "s"}`;

  if (!users || users.length === 0) {
    const li = document.createElement("li");
    li.textContent = "No users found.";
    usersList.appendChild(li);
    return;
  }

  users.forEach((u) => {
    const li = document.createElement("li");

    const left = document.createElement("div");
    left.textContent = u.username ?? "(no username)";

    const right = document.createElement("span");
    right.className = "pill";
    right.textContent = `id: ${u.id ?? "?"}`;

    li.appendChild(left);
    li.appendChild(right);
    usersList.appendChild(li);
  });
}

async function fetchUsers() {
  try {
    const res = await fetch(`${API_BASE}/users`, { method: "GET" });
    if (!res.ok) {
      const text = await res.text();
      setDebug(`GET /users failed: ${res.status}. Body: ${text}`);
      renderUsers([]);
      return;
    }
    const data = await res.json();
    renderUsers(data);
    setDebug(`Loaded ${data.length} users.`);
  } catch (err) {
    setDebug(`GET /users error: ${err?.message ?? err}`);
    renderUsers([]);
  }
}

async function createUser(username) {
  try {
    const res = await fetch(`${API_BASE}/users`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username }),
    });

    if (!res.ok) {
      const text = await res.text();
      setDebug(`POST /users failed: ${res.status}. Body: ${text}`);
      return false;
    }

    const created = await res.json().catch(() => null);
    setDebug(`User created: ${created?.username ?? username}`);
    return true;
  } catch (err) {
    setDebug(`POST /users error: ${err?.message ?? err}`);
    return false;
  }
}

refreshHealthBtn.addEventListener("click", async () => {
  await checkHealth();
});

refreshUsersBtn.addEventListener("click", async () => {
  await fetchUsers();
});

clearBtn.addEventListener("click", () => {
  usernameInput.value = "";
  usernameInput.focus();
});

createUserForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const username = usernameInput.value.trim();
  if (!username) return;

  const ok = await createUser(username);
  if (ok) {
    usernameInput.value = "";
    await fetchUsers();
  }
});

// Initial load
(async function init() {
  setDebug("Initializing UI...");
  await checkHealth();
  await fetchUsers();
})();
