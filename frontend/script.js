let tarefas = [];
let filtroAtual = 'ALL';
let editandoId = null;

const taskForm = document.getElementById('task-form');
const taskContainer = document.getElementById('task-container');

taskForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const nome = document.getElementById('nome').value.trim();
    const categoria = document.getElementById('categoria').value;
    const descricao = document.getElementById('descricao').value.trim();
    const dataHora = document.getElementById('dataHora').value;
<<<<<<< HEAD
    const prioridadeStr = document.getElementById('prioridade').value.trim();
    const status = document.getElementById('status').value;
    const alarmes = document.getElementById('alarmes').value;

    const regexNome = /^[A-Za-z0-9À-ÖØ-öø-ÿ\s]{3,}$/;
    const regexPrioridade = /^[1-5]$/;
    const regexData = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}$/;

    if (!regexNome.test(nome)) {
        alert("O nome da tarefa deve ter pelo menos 3 caracteres e não conter símbolos especiais.");
        return;
    }

    if (!regexPrioridade.test(prioridadeStr)) {
        alert("Prioridade inválida! Escolha um valor entre 1 e 5.");
=======
    const prioridade = document.getElementById('prioridade').value.trim();
    const status = document.getElementById('status').value;
    const alarmes = document.getElementById('alarmes').value;

    const regexPrioridade = /^[1-5]$/;
    
    const regexData = /^\d{4}-\d{2}-\d{2}(T\d{2}:\d{2})?$/;

    if (!nome || !dataHora || !prioridade) {
        alert("Por favor, preencha os campos obrigatórios.");
        return;
    }

    if (!regexPrioridade.test(prioridade)) {
        alert("Prioridade inválida! Insira um número de 1 a 5.");
>>>>>>> 16709304b850fd441410ab222490108eaea14e05
        return;
    }

    if (!regexData.test(dataHora)) {
<<<<<<< HEAD
        alert("Por favor, selecione uma data e hora válidas.");
=======
        alert("Formato de data inválido!");
>>>>>>> 16709304b850fd441410ab222490108eaea14e05
        return;
    }

    const dataInserida = new Date(dataHora);
<<<<<<< HEAD
    const agora = new Date();
    agora.setSeconds(0, 0);

    if (dataInserida < agora) {
=======
    const dataHoje = new Date();
    dataHoje.setHours(0, 0, 0, 0);
    dataInserida.setHours(0, 0, 0, 0);

    if (dataInserida < dataHoje) {
>>>>>>> 16709304b850fd441410ab222490108eaea14e05
        alert("A data de término não pode ser menor que a atual!");
        return;
    }

    const tarefa = {
        id: editandoId || Date.now(),
        nome,
        categoria,
        descricao,
        dataHora,
<<<<<<< HEAD
        prioridade: parseInt(prioridadeStr),
=======
        prioridade: parseInt(prioridade),
>>>>>>> 16709304b850fd441410ab222490108eaea14e05
        status,
        alarmes
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
<<<<<<< HEAD
            <input type="checkbox" class="task-checkbox" data-id="${t.id}">
            <div style="flex-grow: 1;">
                <h3 style="margin:0; color: #06b6d4;">${t.nome} <small style="color: #94a3b8;">(${t.status})</small></h3>
                <p style="margin: 5px 0; font-size: 0.9rem;">${t.descricao}</p>
                <small>${t.dataHora.replace('T', ' ')} | Prioridade: ${t.prioridade} | ${t.categoria}</small>
=======
            <div class="task-info">
                <h3>${t.nome} <small>(${t.categoria})</small></h3>
                <p>${t.descricao}</p>
                <small>Prazos: ${t.dataHora.replace('T', ' ')} | Prioridade: ${t.prioridade} | Status: <strong>${t.status}</strong></small>
>>>>>>> 16709304b850fd441410ab222490108eaea14e05
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