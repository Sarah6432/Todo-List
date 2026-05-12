📝 ZG-Hero: TODO List
Este projeto é uma aplicação de gerenciamento de tarefas desenvolvida como parte da Trilha dos Desafios do programa Acelera ZG. A solução evoluiu de uma ferramenta de terminal em Java para uma aplicação web completa com integração de serviços de e-mail.

👤 Autor: Sarah Silva Lima

✨ Refatoração e Clean Code
Recentemente, o projeto passou por uma "faxina" técnica para elevar o padrão de qualidade do código, aplicando princípios de Clean Code:

Substituição de textos fixos por Enums (StatusTarefa), garantindo segurança em tempo de compilação e evitando erros de digitação.

Princípio da Responsabilidade Única (SRP): A classe Main foi simplificada para focar apenas na interação com o usuário, enquanto a lógica de captura e validação de dados foi movida para métodos auxiliares.

Tell, Don't Ask: A lógica de cálculo de tempo foi movida para dentro da classe Tarefa, permitindo que o objeto responda sobre seu próprio estado em vez de ter seus dados processados externamente.

DRY (Don't Repeat Yourself): Centralização da lógica de entrada de dados para evitar repetição de código entre as funções de cadastro e edição.

Testes Automatizados com Spock: Implementação de testes utilizando o framework Spock (Groovy), adotando o padrão BDD (Given/When/Then) para documentar o comportamento esperado do sistema.

🚀 Funcionalidades (MVP & Backend Java)
O projeto atende aos seguintes requisitos obrigatórios:

CRUD de Tarefas: Criação, Listagem, Edição e Remoção de atividades.

Parâmetros Completos: Cada tarefa possui Nome, Descrição, Data de Término, Prioridade (1-5), Categoria e Status.

Rebalanceamento Dinâmico: Ordenação automática para que as tarefas de maior prioridade apareçam primeiro.

Sistema de Filtros: Listagem personalizada por Categoria, Prioridade ou Status.

Menu Interativo: Interface via terminal simples e intuitiva.

🔔 Sistema de Alarmes
Suporte a Horários Precisos: Utilização de LocalDateTime para configuração de hora exata.

Configuração de Alarme: Opção de ativar ou desativar notificações por tarefa.

Acionamento Automático: Varredura automática ao iniciar a aplicação.

Lógica de Antecedência: Disparo de alertas para tarefas com prazo nas próximas 2 horas.

🧪 Testes e Qualidade
Testes Unitários: Implementação com JUnit 5 e Spock para garantir a qualidade do CRUD.

Metodologia TDD: Desenvolvimento da funcionalidade de edição guiado por testes.

Padrão BDD: Estrutura de testes focada na legibilidade e comportamento (Specification).

🌐 Frontend & Integrações Web
O frontend foi desenvolvido com HTML, CSS e JavaScript puros, focando em uma experiência de usuário (UX) funcional e intuitiva.

🚀 Novas Funcionalidades Web
Persistência com LocalStorage: Dados salvos automaticamente no navegador, evitando perda de informações ao fechar a página.

Atualização em Massa (Bulk Update): Seleção múltipla via checkboxes para alteração de status (TODO, DOING, DONE) de várias tarefas com um único clique.

UX Aprimorada: Ordenação automática por prioridade no navegador e feedback visual de status.

📧 Integração com Serviço de E-mail (Challenge K2-T3)
EmailJS Integration: Integração com o serviço EmailJS para notificações em tempo real sem a necessidade de backend.

Notificação Dinâmica: Envio de e-mail de confirmação para o endereço informado pelo usuário.

Alerta "Caso Sandubinha": Varredura automática que dispara um e-mail de alerta caso uma tarefa vença nas próximas 24 horas.

Segurança (Token): Utilização de Public Key e Service IDs para evitar a exposição de credenciais SMTP no código-fonte.

Feedback Visual: Notificações customizadas e elegantes integradas com a biblioteca SweetAlert2.

🛠️ Tecnologias Utilizadas
Linguagens: Java 21, Groovy (Testes), JavaScript (ES6+), HTML5, CSS3.

Backend: Java Collections API, Java Stream API, Java Time API.

Testes: JUnit 5, Spock Framework.

Integrações: EmailJS (E-mail Service), SweetAlert2 (UI Notifications).

⚙️ Como Executar
Frontend (Web)
Abra o arquivo index.html em qualquer navegador moderno.

Backend (Java Terminal)

Clone o repositório:
git clone https://github.com/Sarah6432/Todo-List.git
Compile e Execute:

Bash:
javac main/Main.java main/Tarefa.java main/GerenciadorTarefas.java main/StatusTarefa.java
java main.Main

Executar Testes Unitários
Via Terminal: mvn test ou ./gradlew test (dependendo do seu gerenciador de dependências).

🧠 Detalhes Técnicos
Cálculo de Intervalo: Uso de java.time.Duration para precisão nos alarmes do sistema Java.

Lógica de Rebalanceamento: Utilização do método sort com Comparator para garantir que o nível de criticidade (5 a 1) dite a ordem de exibição.

Persistência Frontend: Implementação de lógica para evitar disparos de e-mails duplicados utilizando flags de controle no LocalStorage.
