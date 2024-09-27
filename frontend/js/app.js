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
      <input type="checkbox" class="task-select" data-index="${index}">
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
  console.log("Formulário foi submetido!");

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

function deleteTask(index) {
  tasks.splice(index, 1);
  localStorage.setItem('tasks', JSON.stringify(tasks));
  renderTasks();
}

function editTask(index) {
  const task = tasks[index];
  document.getElementById('task-name').value = task.name;
  document.getElementById('task-desc').value = task.description;
  document.getElementById('task-priority').value = task.priority;
  document.getElementById('task-category').value = task.category;
  document.getElementById('task-status').value = task.status;

  tasks.splice(index, 1);
}

document.getElementById('change-status').addEventListener('click', function() {
  const selectedStatus = document.getElementById('multi-task-status').value;
  const checkboxes = document.querySelectorAll('.task-select:checked');

  checkboxes.forEach(checkbox => {
    const taskIndex = checkbox.getAttribute('data-index');
    tasks[taskIndex].status = selectedStatus;
  });

  localStorage.setItem('tasks', JSON.stringify(tasks));
  renderTasks();
});

document.getElementById('filter-tasks').addEventListener('click', function() {
  const selectedCategory = filterCategory.value.toLowerCase();
  const selectedPriority = filterPriority.value;
  const selectedStatus = filterStatus.value;

  const filteredTasks = tasks.filter(task => {
    const matchCategory = selectedCategory === '' || task.category.toLowerCase().includes(selectedCategory);
    const matchPriority = selectedPriority === '' || task.priority == selectedPriority;
    const matchStatus = selectedStatus === '' || task.status === selectedStatus;

    return matchCategory && matchPriority && matchStatus;
  });

  renderTasks(filteredTasks);
});



renderTasks();


