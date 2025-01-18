# To-DoList
Este projeto é uma API para gerenciar tarefas (To-DoList), construída com Spring Boot. Inclui funcionalidades para criar, atualizar, listar e excluir tarefas, além de integração com autenticação e documentação Swagger.

---

## 🚀 Pré-requisitos

- [Java 21 (JDK)](https://www.oracle.com/br/java/technologies/downloads/#jdk21-windows)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop)
- [Git](https://git-scm.com/downloads) 

---

## 📥 Clonando o repositório

```bash
git clone https://github.com/joaoygorr/To-DoList
cd To-DoList
```

## 🖥️ Executando Manualmente

1. Compile o projeto:

    No diretório do projeto, execute:
   ```bash
   mvn clean install
   ```
2. Execute o servidor Spring Boot:

   Após a compilação, rode:
    ```bash
    mvn spring-boot:run
    ```
3. Acesse a API:

   A API estará disponível em http://localhost:8080.

## 🐳 Executando com Docker

1. Certifique-se de ter o Docker instalado.

    Rode o comando na pasta raíz da aplicação: 
    ```bash
    docker-compose up -d
    ```

## 🧪 Executando os Testes

1. Para rodar os testes do projeto, execute o seguinte comando:
    
    ```bash
       mvn test
    ```
## 📖 Swagger (Documentação da API)
A documentação da API está disponível via **Swagger**. Após iniciar o servidor acesse:
[Documentação](http://localhost:8080/apiswagger-ui.html)
