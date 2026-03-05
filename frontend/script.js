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

    const nome = document.getElementById('nome').value.trim();
    const categoria = document.getElementById('categoria').value;
    const descricao = document.getElementById('descricao').value.trim();
    const dataHora = document.getElementById('dataHora').value;
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
        return;
    }

    if (!regexData.test(dataHora)) {
        alert("Por favor, selecione uma data e hora válidas.");
        return;
    }

    const dataInserida = new Date(dataHora);
    const agora = new Date();
    agora.setSeconds(0, 0);

    if (dataInserida < agora) {
        alert("A data de término não pode ser menor que a atual!");
        return;
    }

    const tarefa = {
        id: editandoId || Date.now(),
        nome,
        categoria,
        descricao,
        dataHora,
        prioridade: parseInt(prioridadeStr),
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
            <input type="checkbox" class="task-checkbox" data-id="${t.id}">
            <div style="flex-grow: 1;">
                <h3 style="margin:0; color: #06b6d4;">${t.nome} <small style="color: #94a3b8;">(${t.status})</small></h3>
                <p style="margin: 5px 0; font-size: 0.9rem;">${t.descricao}</p>
                <small>${t.dataHora.replace('T', ' ')} | Prioridade: ${t.prioridade} | ${t.categoria}</small>
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
    window.scrollTo(0,0);
}

document.getElementById('btn-cancel').addEventListener('click', () => {
    editandoId = null;
    taskForm.reset();
    document.getElementById('form-title').innerText = "Nova Tarefa";
    document.getElementById('btn-cancel').classList.add('hidden');
});

renderizar();