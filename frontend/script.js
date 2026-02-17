let tarefas = [];
let filtroAtual = 'ALL';
let editandoId = null;

const taskForm = document.getElementById('task-form');
const taskContainer = document.getElementById('task-container');

taskForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const tarefa = {
        id: editandoId || Date.now(),
        nome: document.getElementById('nome').value,
        categoria: document.getElementById('categoria').value,
        descricao: document.getElementById('descricao').value,
        dataHora: document.getElementById('dataHora').value,
        prioridade: document.getElementById('prioridade').value,
        status: document.getElementById('status').value,
        alarmes: document.getElementById('alarmes').value
    };

    if (editandoId) {
        tarefas = tarefas.map(t => t.id === editandoId ? tarefa : t);
        editandoId = null;
        document.getElementById('form-title').innerText = "Nova Tarefa";
        document.getElementById('btn-cancel').classList.add('hidden');
    } else {
        tarefas.push(tarefa);
    }

    taskForm.reset();
    renderizar();
});

function renderizar() {
    taskContainer.innerHTML = '';

    const listaFiltrada = tarefas
        .filter(t => filtroAtual === 'ALL' || t.status === filtroAtual)
        .sort((a, b) => b.prioridade - a.prioridade);

    listaFiltrada.forEach(t => {
        const div = document.createElement('div');
        div.className = 'task-card';
        div.innerHTML = `
            <div class="task-info">
                <h3>${t.nome} <small>(${t.categoria})</small></h3>
                <p>${t.descricao}</p>
                <small>Prazo: ${t.dataHora} | Prioridade: ${t.prioridade} | Status: <strong>${t.status}</strong></small>
            </div>
            <div class="task-actions">
                <button class="btn-edit" onclick="prepararEdicao(${t.id})">Editar</button>
                <button class="btn-delete" onclick="removerTarefa(${t.id})">Excluir</button>
            </div>
        `;
        taskContainer.appendChild(div);
    });
}

function removerTarefa(id) {
    tarefas = tarefas.filter(t => t.id !== id);
    renderizar();
}

function prepararEdicao(id) {
    const t = tarefas.find(task => task.id === id);
    if (!t) return;

    document.getElementById('nome').value = t.nome;
    document.getElementById('categoria').value = t.categoria;
    document.getElementById('descricao').value = t.descricao;
    document.getElementById('dataHora').value = t.dataHora;
    document.getElementById('prioridade').value = t.prioridade;
    document.getElementById('status').value = t.status;
    document.getElementById('alarmes').value = t.alarmes;

    editandoId = id;
    document.getElementById('form-title').innerText = "Editando Tarefa";
    document.getElementById('btn-cancel').classList.remove('hidden');
    window.scrollTo(0, 0);
}

function filtrarTarefas(status) {
    filtroAtual = status;
    document.querySelectorAll('.btn-filter').forEach(btn => {
        btn.classList.toggle('active', btn.innerText.toUpperCase() === status || (status === 'ALL' && btn.innerText === 'Todas'));
    });
    renderizar();
}

document.getElementById('btn-cancel').addEventListener('click', () => {
    editandoId = null;
    taskForm.reset();
    document.getElementById('form-title').innerText = "Nova Tarefa";
    document.getElementById('btn-cancel').classList.add('hidden');
});