let tarefas = JSON.parse(localStorage.getItem('tarefas_zg')) || [];
let editandoId = null;

const taskForm = document.getElementById('task-form');
const taskContainer = document.getElementById('task-container');

function salvarNoStorage() {
    localStorage.setItem('tarefas_zg', JSON.stringify(tarefas));
    renderizar();
}

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
    salvarNoStorage();
});

function executarAcaoEmMassa() {
    const selecionados = document.querySelectorAll('.task-checkbox:checked');
    const novoStatus = document.getElementById('bulk-status').value;

    if (selecionados.length === 0) {
        alert("Selecione ao menos uma tarefa!");
        return;
    }

    const idsParaMudar = Array.from(selecionados).map(cb => parseInt(cb.dataset.id));

    tarefas = tarefas.map(t => {
        if (idsParaMudar.includes(t.id)) {
            return { ...t, status: novoStatus };
        }
        return t;
    });

    salvarNoStorage();
    alert("Status das tarefas selecionadas atualizado!");
}

function renderizar() {
    taskContainer.innerHTML = '';
    
    const listaOrdenada = [...tarefas].sort((a, b) => b.prioridade - a.prioridade);

    listaOrdenada.forEach(t => {
        const div = document.createElement('div');
        div.className = 'task-card';
        div.style.background = "#1e293b";
        div.style.padding = "15px";
        div.style.marginBottom = "10px";
        div.style.borderRadius = "8px";
        div.style.display = "flex";
        div.style.alignItems = "center";
        div.style.gap = "15px";
        div.style.color = "white";

        div.innerHTML = `
            <div class="task-info">
                <h3>${t.nome} <small>(${t.categoria})</small></h3>
                <p>${t.descricao}</p>
                <small>Prazo: ${t.dataHora} | Prioridade: ${t.prioridade} | Status: <strong>${t.status}</strong></small>
            </div>
            <div>
                <button onclick="prepararEdicao(${t.id})" style="background: #eab308; padding: 5px 10px; border-radius: 4px; border:none; cursor:pointer; margin-right: 5px;">Editar</button>
                <button onclick="removerTarefa(${t.id})" style="background: #ef4444; padding: 5px 10px; border-radius: 4px; border:none; color:white; cursor:pointer;">Excluir</button>
            </div>
        `;
        taskContainer.appendChild(div);
    });
}

function removerTarefa(id) {
    if(confirm("Deseja excluir esta tarefa?")) {
        tarefas = tarefas.filter(t => t.id !== id);
        salvarNoStorage();
    }
}

function prepararEdicao(id) {
    const t = tarefas.find(task => task.id === id);
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
    window.scrollTo(0,0);
}

document.getElementById('btn-cancel').addEventListener('click', () => {
    editandoId = null;
    taskForm.reset();
    document.getElementById('form-title').innerText = "Nova Tarefa";
    document.getElementById('btn-cancel').classList.add('hidden');
});

renderizar();