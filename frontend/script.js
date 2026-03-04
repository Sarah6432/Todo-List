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
        return;
    }

    if (!regexData.test(dataHora)) {
        alert("Formato de data inválido!");
        return;
    }

    const dataInserida = new Date(dataHora);
    const dataHoje = new Date();
    dataHoje.setHours(0, 0, 0, 0);
    dataInserida.setHours(0, 0, 0, 0);

    if (dataInserida < dataHoje) {
        alert("A data de término não pode ser menor que a atual!");
        return;
    }

    const tarefa = {
        id: editandoId || Date.now(),
        nome,
        categoria,
        descricao,
        dataHora,
        prioridade: parseInt(prioridade),
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
            <div class="task-info">
                <h3>${t.nome} <small>(${t.categoria})</small></h3>
                <p>${t.descricao}</p>
                <small>Prazo: ${t.dataHora.replace('T', ' ')} | Prioridade: ${t.prioridade} | Status: <strong>${t.status}</strong></small>
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