📝 ZG-Hero: TODO List
Este projeto é uma aplicação de gerenciamento de tarefas desenvolvida como parte da Trilha dos Desafios do programa Acelera ZG. A solução evoluiu de uma ferramenta de terminal em Java para uma aplicação web completa com integração de serviços de e-mail.

👤 Autor: Sarah Silva Lima

🚀 Funcionalidades (MVP & Backend Java)
O projeto atende aos seguintes requisitos obrigatórios:

CRUD de Tarefas: Criação, Listagem, Edição e Remoção de atividades.

Parâmetros Completos: Cada tarefa possui Nome, Descrição, Data de Término, Prioridade (1-5), Categoria e Status.

Rebalanceamento Dinâmico: Ordenação automática para que as tarefas de maior prioridade apareçam primeiro.

Sistema de Filtros: Listagem personalizada por Categoria, Prioridade ou Status.

Menu Interativo: Interface via terminal simples e intuitiva.

🔔 Sistema de Alarmes (Challenge K1-T3)
Suporte a Horários Precisos: Utilização de LocalDateTime para configuração de hora exata.

Configuração de Alarme: Opção de ativar ou desativar notificações por tarefa.

Acionamento Automático: Varredura automática ao iniciar a aplicação.

Lógica de Antecedência: Disparo de alertas para tarefas com prazo nas próximas 2 horas.

🧪 Testes e Qualidade
Testes Unitários: Implementação com JUnit 5 para garantir a qualidade do CRUD.

Metodologia TDD: Desenvolvimento da funcionalidade de edição guiado por testes.

Padrão Given/When/Then: Estrutura de testes focada na legibilidade e comportamento.

🌐 Frontend & Integrações Web
O frontend foi desenvolvido com HTML, CSS e JavaScript puros, focando em uma experiência de usuário (UX) funcional e intuitiva.

🚀 Novas Funcionalidades Web
Persistência com LocalStorage: Dados salvos automaticamente no navegador, evitando perda de informações ao fechar a página.

Atualização em Massa (Bulk Update): Seleção múltipla via checkboxes para alteração de status (TODO, DOING, DONE) de várias tarefas com um único clique.

UX Aprimorada: Ordenação automática por prioridade no navegador e feedback visual de status.

📧 Integração com Serviço de E-mail (Challenge K2-T3)
EmailJS Integration: Integração com o serviço EmailJS para notificações em tempo real sem a necessidade de backend.

Notificação Dinâmica: Envio de e-mail de confirmação para o endereço informado pelo usuário no momento da criação da tarefa.

Alerta "Caso Sandubinha": Varredura automática que dispara um e-mail de alerta caso uma tarefa vença nas próximas 24 horas (dia anterior ao término).

Segurança (Token): Utilização de Public Key e Service IDs para evitar a exposição de credenciais SMTP no código-fonte.

Feedback Visual: Notificações customizadas e elegantes integradas com a biblioteca SweetAlert2.

🛠️ Tecnologias Utilizadas
Linguagens: Java 21, JavaScript (ES6+), HTML5, CSS3.

Backend: Java Collections API, Java Stream API, Java Time API.

Testes: JUnit 5, Maven.

Integrações: EmailJS (E-mail Service), SweetAlert2 (UI Notifications).

⚙️ Como Executar
Frontend (Web)
Abra o arquivo index.html em qualquer navegador moderno.

Insira seu nome, e-mail e os dados da tarefa para receber as notificações.

Backend (Java Terminal)
Clone o repositório:

Bash
git clone https://github.com/Sarah6432/Todo-List.git
Compile e Execute:

Bash
javac main.Main.java main.Tarefa.java
java main.Main
Executar Testes Unitários (Java)
Via Terminal: mvn test

Via IDE: Navegue até src/Test/main/GerenciadorTarefasTest.java e selecione Run Test.

🧠 Detalhes Técnicos
Cálculo de Intervalo: Uso de java.time.Duration para precisão nos alarmes do sistema Java.

Lógica de Rebalanceamento: Utilização do método sort com Comparator para garantir que o nível de criticidade (5 a 1) dite a ordem de exibição.

Persistência Frontend: Implementação de lógica para evitar disparos de e-mails duplicados utilizando flags de controle no LocalStorage.
