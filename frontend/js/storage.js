export function carregarTarefas() {
    return JSON.parse(localStorage.getItem('tarefas_zg')) || [];
}

export function salvarTarefas(tarefas) {
    localStorage.setItem('tarefas_zg', JSON.stringify(tarefas));
}