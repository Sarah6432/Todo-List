import { SERVICE_ID, TEMPLATE_ID, PUBLIC_KEY } from './config.js';

export function enviarEmail(tarefa) {
    const templateParams = {
        to_email: tarefa.email,
        task_name: tarefa.nome,
        category: tarefa.categoria,
        due_date: tarefa.dataHora.replace('T', ' às '),
        description: tarefa.descricao || "Sem descrição informada."
    };

    return emailjs.send(SERVICE_ID, TEMPLATE_ID, templateParams, PUBLIC_KEY)
        .then(() => {
           Swal.fire({
               title: 'E-mail Enviado!',
               icon: 'success',
               background: '#1e293b', color: '#f8fafc', confirmButtonColor: '#8b5cf6', timer: 2500
           });
        })
        .catch(() => {
           Swal.fire({ title: 'Erro de Conexão', icon: 'error', background: '#1e293b', color: '#f8fafc' });
        });
}