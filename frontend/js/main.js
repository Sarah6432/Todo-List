import { carregarTarefas, salvarTarefas } from './storage.js';
import { enviarEmail } from './email.js';
import { taskForm, taskContainer } from './config.js';

let tarefas = carregarTarefas();
let editandoId = null;

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
                <small>📅 ${t.dataHora.replace('T', ' ')} | ⭐ Prioridade: ${t.prioridade} | 📁 ${t.categoria}</small>
            </div>
            <div>
                <button onclick="prepararEdicao(${t.id})" style="background: #eab308; padding: 5px 10px; border-radius: 4px; border:none; cursor:pointer; margin-right: 5px;">Editar</button>
                <button onclick="removerTarefa(${t.id})" style="background: #ef4444; padding: 5px 10px; border-radius: 4px; border:none; color:white; cursor:pointer;">Excluir</button>
            </div>
        `;
        taskContainer.appendChild(div);
    });
}

function salvarNoStorage() {
    salvarTarefas(tarefas);
    renderizar();
}

function verificarAlertasPrazo() {
    const hoje = new Date();
    const amanha = new Date(hoje);
    amanha.setDate(hoje.getDate() + 1);
    const amanhaStr = amanha.toISOString().split('T')[0];

    tarefas.forEach(t => {
        const dataTarefa = t.dataHora.split('T')[0];
        if (dataTarefa === amanhaStr && !t.alertaEnviado) {
            enviarEmail(t);
            t.alertaEnviado = true;
            salvarTarefas(tarefas);
        }
    });
}

taskForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email_usuario').value.trim();
    const categoria = document.getElementById('categoria').value;
    const descricao = document.getElementById('descricao').value.trim();
    const dataHora = document.getElementById('dataHora').value;
    const prioridadeStr = document.getElementById('prioridade').value.trim();
    const status = document.getElementById('status').value;
    const alarmes = document.getElementById('alarmes').value;

    const regexNome = /^[A-Za-z0-9À-ÖØ-öø-ÿ\s]{3,}$/;
    if (!regexNome.test(nome)) {
        Swal.fire({ icon: 'warning', title: 'Nome inválido', background: '#1e293b', color: '#f8fafc' });
        return;
    }

    const tarefa = {
        id: editandoId || Date.now(),
        nome,
        email,
        categoria,
        descricao,
        dataHora,
        prioridade: parseInt(prioridadeStr),
        status,
        alarmes,
        alertaEnviado: false
    };

    if (editandoId) {
        tarefas = tarefas.map(t => t.id === editandoId ? tarefa : t);
        editandoId = null;
        document.getElementById('form-title').innerText = "Nova Tarefa";
        document.getElementById('btn-cancel').classList.add('hidden');
    } else {
        tarefas.push(tarefa);
        enviarEmail(tarefa);
    }

    taskForm.reset();
    salvarNoStorage();
});

function prepararEdicao(id) {
    const t = tarefas.find(task => task.id === id);
    if (!t) return;

    document.getElementById('nome').value = t.nome;
    document.getElementById('email_usuario').value = t.email;
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

function removerTarefa(id) {
    Swal.fire({
        title: 'Tem certeza?',
        text: "Essa ação não pode ser desfeita!",
        icon: 'warning',
        showCancelButton: true,
        background: '#1e293b',
        color: '#f8fafc',
        confirmButtonColor: '#ef4444',
        cancelButtonColor: '#475569',
        confirmButtonText: 'Sim, excluir!'
    }).then((result) => {
        if (result.isConfirmed) {
            tarefas = tarefas.filter(t => t.id !== id);
            salvarNoStorage();
        }
    });
}

function executarAcaoEmMassa() {
    const selecionados = document.querySelectorAll('.task-checkbox:checked');
    const novoStatus = document.getElementById('bulk-status').value;

    if (selecionados.length === 0) {
        Swal.fire({ icon: 'info', title: 'Atenção', text: 'Selecione ao menos uma tarefa!', background: '#1e293b', color: '#f8fafc' });
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
}

document.getElementById('btn-cancel').addEventListener('click', () => {
    editandoId = null;
    taskForm.reset();
    document.getElementById('form-title').innerText = "Nova Tarefa";
    document.getElementById('btn-cancel').classList.add('hidden');
});

window.prepararEdicao = prepararEdicao;
window.removerTarefa = removerTarefa;
window.executarAcaoEmMassa = executarAcaoEmMassa;

renderizar();
verificarAlertasPrazo();