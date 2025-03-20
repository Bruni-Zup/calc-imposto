API RESTful para Gerenciamento e Cálculo de Impostos no Brasil

Descrição
Esta API RESTful foi desenvolvida para gerenciar e calcular impostos no Brasil. Ela permite o registro de diferentes tipos de impostos, como ICMS, ISS, IPI, entre outros, e realiza cálculos com base no tipo de imposto e no valor base fornecido. A API é segura, utilizando Spring Security e JWT (JSON Web Token) para autenticação e autorização.

 Funcionalidades
 1. Gerenciamento de Tipos de Impostos
- Listar todos os tipos de impostos disponíveis.
- Cadastrar novos tipos de impostos (Acesso restrito a usuários com papel `ADMIN`).
- Obter detalhes de um tipo de imposto específico pelo ID.
- Excluir um tipo de imposto pelo ID (Acesso restrito a usuários com papel `ADMIN`).

2. Cálculo de Impostos
- Calcular o valor do imposto com base no tipo de imposto e no valor base fornecido (Acesso restrito a usuários com papel `ADMIN`).

 3. Segurança
- Autenticação e autorização com Spring Security e JWT.
- Controle de acesso: Apenas usuários autenticados podem acessar os endpoints.

 Endpoints e JSONs para Testes no Thunder Client
1. Gerenciamento de Tipos de Impostos
GET /tipos**
- Descrição: Retorna a lista de todos os tipos de impostos cadastrados.
- Exemplo de Requisição:
  - Método: `GET`
  - URL: `http://localhost:8080/tipos
- Resposta (200):
```json
[
  {
    "id": 1,
    "nome": "ICMS",
    "descricao": "Imposto sobre Circulação de Mercadorias e Serviços",
    "aliquota": 18.0
  },
  {
    "id": 2,
    "nome": "ISS",
    "descricao": "Imposto sobre Serviços",
    "aliquota": 5.0
  }
]

POST /tipos**
- Descrição: Cadastra um novo tipo de imposto. (Acesso restrito ao papel `ADMIN`).
- Exemplo de Requisição:
  - Método: `POST`
  - URL: `http://localhost:8080/tipos
  - Headers:
    - `Authorization: Bearer <TOKEN_JWT>`
  - Body:
```json
{
  "nome": "IPI",
  "descricao": "Imposto sobre Produtos Industrializados",
  "aliquota": 12.0
}

- Resposta (201):
```json
{
  "id": 3,
  "nome": "IPI",
  "descricao": "Imposto sobre Produtos Industrializados",
  "aliquota": 12.0
}
```

GET /tipos/{id}
- Descrição: Retorna os detalhes de um tipo de imposto específico pelo ID.
- Exemplo de Requisição:
  - Método: `GET`
  - URL: http://localhost:8080/tipos/1
- Resposta (200):
```json
{
  "id": 1,
  "nome": "ICMS",
  "descricao": "Imposto sobre Circulação de Mercadorias e Serviços",
  "aliquota": 18.0
}

DELETE /tipos/{id}
- Descrição: Exclui um tipo de imposto pelo ID. (Acesso restrito ao papel ADMIN).
- Exemplo de Requisição:
  - Método: DELETE
  - URL: http://localhost:8080/tipos/1
  - Headers:
    - `Authorization: Bearer <TOKEN_JWT>`
- Resposta (204):
Sem conteúdo.

2. Cálculo de Impostos

POST /calculo
- Descrição: Calcula o valor do imposto com base no tipo de imposto e no valor base. (Acesso restrito ao papel ADMIN).
- Exemplo de Requisição:
  - Método: `POST`
  - URL: `http://localhost:8080/calculo`
  - Headers:
    - `Authorization: Bearer <TOKEN_JWT>`
  - Body:
```json
{
  "tipoImpostoId": 1,
  "valorBase": 1000.0
}
```
- Resposta (200):
```json
{
  "tipoImposto": "ICMS",
  "valorBase": 1000.0,
  "aliquota": 18.0,
  "valorImposto": 180.0
}


3. Gerenciamento de Usuários

POST /user/register
- Descrição: Registra novos usuários no sistema.
- Exemplo de Requisição:
  - Método: POST
  - URL: `http://localhost:8080/user/register`
  - Body:
```json
{
  "username": "usuario123",
  "password": "senhaSegura",
  "role": "USER"
}
```
- Resposta (201):
```json
{
  "id": 1,
  "username": "usuario123",
  "role": "USER"
}


POST /user/login
- Descrição: Autentica usuários e gera um token JWT.
- Exemplo de Requisição:
  - Método: `POST`
  - URL: `http://localhost:8080/user/login`
  - Body:
```json
{
  "username": "usuario123",
  "password": "senhaSegura"
}
```
- Resposta (200):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}


Regras de Negócio
- O username deve ser único.
- A senha deve ser armazenada de forma segura utilizando BCrypt.
- Apenas usuários autenticados podem acessar os endpoints.
- Endpoints de criação, exclusão e cálculo de impostos são restritos ao papel `ADMIN`.

---

Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- JPA/Hibernate
- H2 Database*(para desenvolvimento e testes)
- JUnit (para testes unitários)

Como Executar
1. Clone o repositório.
2. Execute o comando:
mvnw spring-boot:run
3. Acesse a API em `http://localhost:8080`.
