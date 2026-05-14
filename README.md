# 📝 ZG-Hero: TODO List

Este projeto é uma aplicação de gerenciamento de tarefas desenvolvida como parte da Trilha dos Desafios do programa Acelera ZG. A solução evoluiu de uma ferramenta de terminal em Java para uma aplicação web completa com integração de serviços de e-mail.

**👤 Autor:** Sarah Silva Lima

---

## 🏗️ Implementação de Princípios SOLID
O projeto foi refatorado para elevar o padrão arquitetural, focando nos princípios **SOLID** para garantir baixo acoplamento e alta coesão:

* **S - Single Responsibility Principle (Responsabilidade Única):** A lógica de notificação foi extraída do `GerenciadorTarefas` para classes especializadas, garantindo que cada classe tenha apenas um motivo para mudar.
* **O - Open/Closed Principle (Aberto/Fechado):** O sistema agora permite a adição de novos métodos de alerta (E-mail, SMS, Push) apenas criando novas implementações da interface, sem modificar o código central de monitoramento.
* **D - Dependency Inversion Principle (Inversão de Dependência):** O `GerenciadorTarefas` não depende mais de implementações concretas. Ele depende da interface `Notificador`, que é injetada via construtor.
* **Desacoplamento de Testes:** Graças à Inversão de Dependência, os testes unitários utilizam **Mocks** (objetos simulados) para validar se os alertas são disparados no tempo correto sem a necessidade de saídas físicas no console.

---

## 🎨 Design Patterns Aplicados
Para solucionar problemas de acoplamento e facilitar a expansão do software, foi aplicado o seguinte padrão:

* **Strategy Pattern (Estratégia):** Implementado no sistema de filtros. O `GerenciadorTarefas` não possui mais uma lógica fixa de filtragem (switch-case). Em vez disso, ele recebe um objeto que implementa `FiltroStrategy` (ex: `FiltroPorCategoria`, `FiltroPorStatus`). Isso permite criar novos tipos de filtros sem nunca mais precisar alterar a classe de lógica principal.

---

## ✨ Refatoração e Clean Code
Além do SOLID e Patterns, o código aplica as melhores práticas de **Clean Code**:

* **Eliminação de Strings Mágicas:** Substituição de textos soltos pelo Enum `StatusTarefa`.
* **Tell, Don't Ask:** A classe `Tarefa` é responsável por calcular seus próprios minutos restantes e validar seu estado de conclusão.
* **Encapsulamento Defensivo:** Proteção da lista de tarefas com `Collections.unmodifiableList` e cópias defensivas em coleções mutáveis.
* **DRY (Don't Repeat Yourself):** Reuso de lógica de entrada de dados (Scanner) através de métodos auxiliares na classe `Main`.
* **Testes BDD com Spock:** Escrita de testes legíveis utilizando a estrutura *Given/When/Then*.

---

## 🚀 Funcionalidades (MVP & Backend Java)
* **CRUD Completo:** Criação, Listagem, Edição e Remoção de atividades.
* **Rebalanceamento Dinâmico:** Ordenação automática por prioridade (1-5).
* **Sistema de Filtros Especializado:** Busca refinada utilizando estratégias dinâmicas.
* **Monitoramento de Alarmes:** Thread de background que verifica prazos e dispara alertas configuráveis.

---

## 🌐 Frontend & Integrações Web
Interface intuitiva desenvolvida com foco em UX e persistência de dados local.

* **LocalStorage:** Persistência automática no navegador para evitar perda de dados.
* **Bulk Update:** Atualização de status em massa para múltiplas tarefas.
* **Integração EmailJS:** Notificações automáticas enviadas para o e-mail do usuário.
* **Alerta "Caso Sandubinha":** Lógica preventiva que dispara e-mails para tarefas vencendo em 24h.

---

## 🛠️ Tecnologias Utilizadas
* **Linguagens:** Java 21, Groovy (Testes), JavaScript (ES6+), HTML5, CSS3.
* **Backend:** Java Stream API, Time API, Collections.
* **Testes:** JUnit 5, Spock Framework.
* **Integrações:** EmailJS, SweetAlert2.

---

## ⚙️ Como Executar

### Frontend (Web)
1. Abra o arquivo `index.html` em qualquer navegador moderno.

### Backend (Java Terminal)
1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/Sarah6432/Todo-List.git](https://github.com/Sarah6432/Todo-List.git)
    ```
2.  **Compile os arquivos (incluindo as estratégias de filtro):**
    ```bash
    javac main/*.java
    ```
3.  **Execute a aplicação:**
    ```bash
    java main.Main
    ```

### Executar Testes Unitários
* Via Terminal: `mvn test` ou `./gradlew test`.
* Via IDE: Execute a classe `GerenciadorTarefasSpec.groovy`.

---

## 🧠 Detalhes Técnicos
* **Gestão de Tempo:** Uso de `java.time.Duration` para precisão milimétrica nos alarmes.
* **Mocking:** Validação de comportamento entre objetos sem efeitos colaterais em ambiente de teste.
* **Polimorfismo:** Uso intensivo de interfaces para garantir que o sistema seja flexível e extensível.
