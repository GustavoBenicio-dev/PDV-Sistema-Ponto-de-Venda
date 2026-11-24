# 🛒 PDV – Sistema de Ponto de Venda

Trabalho da **Atividade Avaliativa A3** da disciplina de **Algoritmos e Programação** (semestre 2026.2).
Projeto escolhido: **Projeto 1 – Ponto de Venda (PDV)**.

O PDV é um programa que funciona como o **caixa de uma loja**: ele guarda os produtos, registra as vendas, controla o estoque, imprime o recibo e calcula a comissão do vendedor e o imposto.

Ele foi feito em **Java** e roda no **terminal** (aquela tela preta onde a gente digita).

---

## 📋 O que o sistema faz

- **Cadastra** produtos, clientes e vendedores.
- **Registra vendas.** Toda venda tem um cliente e um vendedor.
- **Controla o estoque.** Não deixa vender mais do que existe e, quando a venda termina, desconta do estoque sozinho.
- **Mostra o recibo** com cada produto, o total, a data e o nome do vendedor.
- **Calcula a comissão** de 5% do vendedor sobre cada venda.
- **Calcula o imposto** de 25% sobre as vendas.
- **Mostra relatórios** do dia ou de um período, com o que foi vendido, o total, o imposto e a comissão de cada vendedor.

---

## ▶️ Como usar

### O que precisa ter no computador
- O **Java (JDK)** instalado.
- O arquivo `PDV.java` baixado.

### Passo a passo no terminal
Abra o terminal na pasta onde está o arquivo e digite:

```
javac PDV.java
java PDV
```

- `javac PDV.java` → prepara o programa (compila).
- `java PDV` → abre o programa.

### Usando o VS Code
1. Abra a pasta do projeto no VS Code.
2. Instale a extensão **Extension Pack for Java**.
3. Abra o arquivo `PDV.java` e clique em **Run** (▶️) em cima da função `main`.

> 💡 O programa já começa com **3 produtos, 2 clientes e 2 vendedores de exemplo**, para você poder testar na hora, sem cadastrar nada.

---

## 🧭 Como funciona o menu

Quando o programa abre, aparece este menu:

| Opção | O que faz |
|-------|-----------|
| **1 – Cadastros** | Abre outro menu para cadastrar e listar produtos, clientes e vendedores |
| **2 – Nova venda** | Faz uma venda do começo ao fim e mostra o recibo |
| **3 – Relatório do dia** | Mostra as vendas de hoje, com imposto e comissão |
| **4 – Relatório por período** | Mostra as vendas entre duas datas que você escolher |
| **0 – Sair** | Fecha o programa |

### Como fazer uma venda (passo a passo)
1. No menu principal, digite **2**.
2. Escolha o **cliente** pelo código.
3. Escolha o **vendedor** pelo código.
4. Digite o **código do produto** e a **quantidade**. Repita para colocar mais produtos.
5. Quando terminar, digite **0**.
6. O sistema desconta o estoque e mostra o **recibo**.

### Como pedir um relatório por período
Na opção **4**, digite as datas assim: **ano-mês-dia**.
Exemplo: `2026-11-16`.

---

## 💰 Regras de cálculo

| Item | Regra |
|------|-------|
| Comissão do vendedor | **5%** do valor de cada venda |
| Imposto | **25%** do valor das vendas |

**Exemplo:** uma venda de **R$ 66,00** gera **R$ 3,30** de comissão (5%) e **R$ 16,50** de imposto (25%).

---

## 🗂️ Como o código está organizado

Tudo está em um único arquivo, o `PDV.java`, dividido em partes pequenas chamadas **funções**. Cada função faz uma tarefa.

| Parte | O que tem | Quem cuidou |
|-------|-----------|-------------|
| **Cadastros** | Cadastrar e listar produtos, clientes e vendedores; buscar pelo código | João Wictor |
| **Venda** | Montar o carrinho, conferir o estoque, finalizar a venda | Gustavo |
| **Dados, recibo e cálculos** | As "fichas" de dados, leitura do que o usuário digita, total da venda e recibo | Áron |
| **Relatórios e menu** | Relatório por período e o menu principal | Fabius |

### As "fichas" que guardam os dados
- **Produto:** código, nome, valor e estoque
- **Pessoa:** código e nome (serve para cliente e para vendedor)
- **Item:** um produto dentro de uma venda, com a quantidade
- **Venda:** número, data, cliente, vendedor e a lista de itens

### O que foi usado na programação
- **Funções** (cada tarefa em um bloquinho)
- **Condicionais** (`if`, `else` e `switch`) para tomar decisões
- **Repetições** (`for` e `do-while`) para repetir passos
- **Listas** (`ArrayList`) para guardar vários produtos, clientes e vendas

---

## ✅ Testes feitos

Todos os testes abaixo foram feitos e funcionaram como esperado:

| Teste | O que acontece |
|-------|----------------|
| Cadastrar produto novo | Aparece "Produto cadastrado!" |
| Cadastrar código repetido | O sistema avisa que o código já existe |
| Venda normal | O recibo aparece e o estoque diminui |
| Pedir mais do que tem no estoque | O sistema avisa quanto ainda tem disponível |
| Produto ou cliente que não existe | O sistema avisa que não encontrou |
| Terminar a venda sem nenhum produto | A venda é cancelada |
| Relatório do dia | Mostra o total, o imposto e a comissão certos |
| Relatório de período sem vendas | Avisa que não há vendas |

---

## ⚠️ O que ainda não tem (limites do projeto)

- Os dados ficam **só na memória**: quando o programa fecha, o que foi cadastrado e vendido **se perde**.
- Funciona só no **terminal**, sem telas com botões.
- Não tem cancelamento ou devolução de venda.
- Não tem senha nem controle de quem pode usar.

Essas são ideias para melhorar o projeto no futuro.

---

## 📎 Outros arquivos do projeto

- **Relatório técnico (PDF):** explica o projeto em detalhes, com manual e testes.
- **Pitch (PowerPoint):** apresentação curta do projeto.

---

## 👥 Equipe

| Nome | Curso |
|------|-------|
| João Wictor de Araújo Almeida | Ciências da Computação |
| Gustavo Brandão de Moura Benicio | Ciência da Computação |
| Áron Guilherme Falcão de Lima | ADS |
| Fabius Maximus Araújo Santos | ADS |

**Disciplina:** Algoritmos e Programação
**Professores:** André Nascimento, Arthur Almeida