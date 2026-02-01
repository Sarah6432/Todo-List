📝 ZG-Hero: TODO List Java

Este projeto é uma aplicação de gerenciamento de tarefas (Backend) desenvolvida como parte da trilha K1-T3 do programa Acelera ZG. A aplicação permite o gerenciamento completo de tarefas através do terminal, com foco em lógica de priorização e organização por categorias.
👤 Autor

    Sarah Silva Lima

🚀 Funcionalidades (MVP)

O projeto atende aos seguintes requisitos obrigatórios:

    CRUD de Tarefas: Criação, Listagem e Remoção de atividades.

    Parâmetros Completos: Cada tarefa possui Nome, Descrição, Data de Término, Prioridade (1-5), Categoria e Status.

    Rebalanceamento Dinâmico: Ao inserir uma nova tarefa, a lista é automaticamente ordenada para que as tarefas de maior prioridade apareçam primeiro.

    Sistema de Filtros: Listagem personalizada por Categoria, Prioridade ou Status.

    Menu Interativo: Interface via terminal simples e intuitiva.

🛠️ Tecnologias Utilizadas

    Java 21
    Java Collections API: Utilização de ArrayList para armazenamento.

    Java Stream API: Filtros eficientes e manipulação de dados.

    Java Time API: Gerenciamento de datas com LocalDate.

📋 Como Executar
Pré-requisitos

    Java JDK instalado (versão 11 ou superior recomendada).

    Um terminal ou IDE (IntelliJ, VS Code, Eclipse).

Passo a Passo

    Clone este repositório:
    Bash

    git clone https://github.com/Sarah6432/Todo-List.git

    Navegue até a pasta do projeto:
    Bash

    cd Todo-List

    Compile os arquivos Java:
    Bash

    javac Main.java Tarefa.java

    Execute a aplicação:
    Bash

    java Main

🧠 Lógica de Rebalanceamento

Para cumprir o requisito de rebalanceamento, foi utilizado o método sort da interface List combinado com um Comparator. A lógica garante que, independentemente da ordem de inserção, a exibição sempre priorize o nível de criticidade da tarefa (5 a 1).
