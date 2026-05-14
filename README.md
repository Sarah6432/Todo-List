# 📝 ZG-Hero: TODO List

Este projeto é uma aplicação de gerenciamento de tarefas desenvolvida como parte da Trilha dos Desafios do programa Acelera ZG. A solução evoluiu de uma ferramenta de terminal em Java para uma aplicação web completa com integração de serviços de e-mail.

**👤 Autor:** Sarah Silva Lima

---

## 🏛️ Arquitetura MVC (Model-View-Controller)
O projeto foi reestruturado seguindo o padrão **MVC**, separando as responsabilidades para facilitar a manutenção e futuras migrações para frameworks:

* **Model:** Contém as entidades de negócio (`Tarefa`), enums (`StatusTarefa`) e contratos (`Notificador`). Representa o estado e as regras da aplicação.
* **View:** Responsável pela interface com o usuário. No Java, é representada pela classe `Main` (CLI) e `ConsoleNotificador`.
* **Controller:** O `GerenciadorTarefas` atua como o cérebro, intermediando a comunicação entre a View e o Model, processando comandos e gerenciando o fluxo de dados.

---

## 🏗️ Implementação de Princípios SOLID
O projeto aplica os princípios **SOLID** para garantir baixo acoplamento e alta coesão:

* **S - Single Responsibility Principle:** Divisão clara entre quem gerencia tarefas (Controller), quem é a tarefa (Model) e quem notifica (View).
* **O - Open/Closed Principle:** Extensibilidade via interfaces para novos métodos de alerta e filtros.
* **D - Dependency Inversion Principle:** O Controller depende da abstração `Notificador`, permitindo trocar a forma de alerta sem alterar a lógica central.

---

## 🎨 Design Patterns Aplicados
* **Strategy Pattern (Estratégia):** Utilizado no sistema de filtros. Cada critério de busca (Categoria, Status, Prioridade) é uma estratégia independente que implementa a interface `FiltroStrategy`.

---

## ✨ Refatoração e Clean Code
* **Eliminação de Strings Mágicas:** Uso do Enum `StatusTarefa`.
* **Tell, Don't Ask:** A lógica de tempo e estado reside no objeto `Tarefa`.
* **Encapsulamento Defensivo:** Uso de `Collections.unmodifiableList` para proteger os dados do Model.
* **Testes BDD com Spock:** Documentação viva do comportamento do sistema em Groovy.

---

## 🌐 Frontend & Integrações Web
* **LocalStorage:** Persistência de dados no navegador.
* **Bulk Update:** Atualização de status em massa.
* **Integração EmailJS:** Notificações de e-mail em tempo real.
* **Alerta "Caso Sandubinha":** Notificação preventiva para prazos de 24h.

---

## 🛠️ Tecnologias Utilizadas
* **Linguagens:** Java 21, Groovy (Testes), JavaScript (ES6+), HTML5, CSS3.
* **Backend:** Java Stream API, Time API, Collections API.
* **Testes:** JUnit 5, Spock Framework.

---

## ⚙️ Como Executar

### Estrutura de Pastas
Certifique-se de que os arquivos estão organizados nos pacotes:
`main.model`, `main.controller` e `main.view`.

### Backend (Java Terminal)
1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/Sarah6432/Todo-List.git](https://github.com/Sarah6432/Todo-List.git)
    ```
2.  **Compile o projeto respeitando os pacotes:**
    ```bash
    javac main/model/*.java main/controller/*.java main/view/*.java
    ```
3.  **Execute a aplicação:**
    ```bash
    java main.view.Main
    ```

### Executar Testes Unitários
* Execute via IDE ou terminal (`mvn test`) a classe `GerenciadorTarefasSpec.groovy`.

---

## 🧠 Detalhes Técnicos
* **Gestão de Tempo:** Uso de `java.time.Duration`.
* **Mocking:** Uso de Mocks no Spock para isolar testes do Controller.
* **Daemon Threads:** Monitoramento de alarmes em background.
