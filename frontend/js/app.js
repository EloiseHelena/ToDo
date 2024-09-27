const form = document.getElementById('task-form');
const taskList = document.getElementById('task-list');
const filterStatus = document.getElementById('filter-status');
const filterCategory = document.getElementById('filter-category');
const filterPriority = document.getElementById('filter-priority');

let tasks = JSON.parse(localStorage.getItem('tasks')) || [];

function renderTasks(tasksToRender = tasks) {
  taskList.innerHTML = '';
  tasksToRender.forEach((task, index) => {
    const li = document.createElement('li');
    li.innerHTML = `
      <strong>${task.name}</strong> - ${task.description} <br>
      Prioridade: ${task.priority} | Categoria: ${task.category} | Status: ${task.status}
      <button onclick="editTask(${index})">Editar</button>
      <button onclick="deleteTask(${index})">Excluir</button>
    `;
    taskList.appendChild(li);
  });
}

form.addEventListener('submit', function(e) {
  e.preventDefault();

  const task = {
    name: document.getElementById('task-name').value,
    description: document.getElementById('task-desc').value,
    priority: document.getElementById('task-priority').value,
    category: document.getElementById('task-category').value,
    status: document.getElementById('task-status').value
  };

  tasks.push(task);
  localStorage.setItem('tasks', JSON.stringify(tasks));
  renderTasks();
  form.reset();
});

