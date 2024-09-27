const form = document.getElementById('task-form');
const taskList = document.getElementById('task-list');
const filterStatus = document.getElementById('filter-status');
const filterCategory = document.getElementById('filter-category');
const filterPriority = document.getElementById('filter-priority');

let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
