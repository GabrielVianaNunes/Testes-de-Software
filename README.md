# 📌 Cadastro de Clientes — Aplicação Web com Testes de Unidade e Integração  
**Disciplina:** Testes de Software – Prof. Reinaldo Jr  
**Aluno:** *[Seu Nome Aqui]*  
**Entrega:** N2 – Aplicação Web com Testes Automatizados

---

## 📖 1. Descrição Geral do Projeto

Este projeto consiste no desenvolvimento de uma aplicação web completa para **cadastro de clientes**, integrando tecnologias de:

- **Front-end:** HTML5, CSS3 e JavaScript  
- **Back-end:** Java + Spring Boot  
- **Persistência:** PostgreSQL com JPA/Hibernate  
- **Testes Automatizados:**  
  - **JUnit (testes de unidade)**  
  - **Selenium WebDriver (testes de integração pela interface)**  

A aplicação permite executar todas as operações CRUD:

- Cadastrar cliente  
- Listar clientes  
- Editar cliente  
- Excluir cliente  

E os testes asseguram que:

- A lógica de negócio funciona corretamente (JUnit).  
- A interface realmente envia dados para o back-end (Selenium).  
- O banco de dados é atualizado corretamente.

---

## 🖥️ 2. Guia de Utilização da Aplicação

### ▶️ Como rodar o back-end

1. Tenha o PostgreSQL instalado e rodando.
2. Crie o banco:

```sql
CREATE DATABASE cadastro_clientes;
Crie a tabela:

sql
Copiar código
CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(30) NOT NULL,
    cidade VARCHAR(60) NOT NULL
);
Configure o arquivo src/main/resources/application.properties:

properties
Copiar código
spring.datasource.url=jdbc:postgresql://localhost:5432/cadastro_clientes
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
server.port=8081
Inicie a aplicação no NetBeans abrindo a classe:

CadastroClientesApplication.java → Botão direito → Run File

🌐 3. Interface Web
Após iniciar o projeto, abra no navegador:

👉 http://localhost:8081/index.html

A interface permite:

Preencher os 4 campos obrigatórios

Salvar um cliente

Editar um cliente existente

Excluir um cliente

Visualizar a lista completa

A interface também mostra validações e mensagens visuais de sucesso/erro.

🧪 4. Instruções para Execução dos Testes
✔️ 4.1 Testes de Unidade (JUnit)
Os testes de unidade validam:

Funcionamento do ClienteService

Regras de atualização

Manipulação de dados simulados com Mockito

Para executar somente os testes de unidade:

No NetBeans:
👉 Clicar com o direito em ClienteServiceTest.java → Test File

Ou via Maven:

bash
Copiar código
mvn -Dtest=ClienteServiceTest test
✔️ 4.2 Testes de Integração (Selenium WebDriver)
Os testes Selenium realizam:

Insert via interface

Update via interface

Delete via interface

E verificam no banco de dados com ClienteRepository

Pré-requisitos:

Ter o Google Chrome instalado

Parar qualquer execução ativa da aplicação (botão vermelho do NetBeans)

Deixar a porta configurada no application.properties:

ini
Copiar código
server.port=8081
Para rodar os testes Selenium:

No NetBeans:
👉 Clique com o direito no arquivo ClienteSeleniumIT.java → Test File

Ou via Maven:

bash
Copiar código
mvn failsafe:integration-test -Dit.test=ClienteSeleniumIT
O Selenium irá:

Subir o Spring Boot automaticamente

Abrir o Chrome

Preencher os campos

Clicar nos botões

Conferir no banco se os dados foram inseridos/alterados/excluídos

Os resultados aparecem no terminal.

🧰 5. Tecnologias Utilizadas
Camada	Tecnologia
Linguagem	Java 21
Framework Web	Spring Boot 4.0
Persistência	JPA / Hibernate
Banco de Dados	PostgreSQL 16
Front-end	HTML5, CSS3, JavaScript
Gerenciador de Build	Maven
Testes de Unidade	JUnit 5 + Mockito
Testes de Integração	Selenium WebDriver + WebDriverManager
IDE	NetBeans 22

🎥 6. Vídeo Demonstrativo
URL do vídeo (YouTube ou Google Drive):

👉 https://

O vídeo deve mostrar:

Inserção de um cliente

Atualização de um cliente

Exclusão de um cliente

Funcionamento das validações

Execução dos testes automatizados

📁 7. Estrutura do Projeto
swift
Copiar código
cadastro-clientes/
 ├── src/main/java/com/example/cadastro_clientes/
 │    ├── controller/
 │    ├── service/
 │    ├── repository/
 │    ├── model/
 │    └── CadastroClientesApplication.java
 ├── src/main/resources/
 │    ├── static/
 │    │    ├── index.html
 │    │    ├── style.css
 │    │    └── script.js
 │    └── application.properties
 ├── src/test/java/com/example/cadastro_clientes/
 │    ├── service/ClienteServiceTest.java
 │    └── selenium/ClienteSeleniumIT.java
 ├── pom.xml
 └── README.md
