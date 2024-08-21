# Microserviço de Loja de Vinhos

Este projeto é um microserviço Spring Boot que gerencia dados de uma loja de vinhos.
## Visão Geral

O microserviço consome dados de dois endpoints externos:
- Lista de Produtos: https://rgr3viiqdl8sikgv.public.blob.vercelstorage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json
- Lista de Clientes e Compras: https://rgr3viiqdl8sikgv.public.blob.vercelstorage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json

Com base nesses dados, o serviço oferece quatro endpoints principais para análise e recomendação.

## Requisitos

- Java 11 ou superior
- Spring Boot
- Maven

## Estrutura do Projeto

O projeto segue uma estrutura padrão MVC usando Spring Boot:

## Endpoints Implementados

1. **GET: /compras**
    - Retorna uma lista de compras ordenadas de forma crescente por valor.
    - Inclui: nome do cliente, CPF do cliente, dados do produto, quantidade da compra e valor total.

2. **GET: /maior-compra/{ano}**
    - Retorna a maior compra do ano especificado.
    - Inclui: nome do cliente, CPF do cliente, dados do produto, quantidade da compra e valor total.

3. **GET: /clientes-fieis**
    - Retorna o Top 3 clientes mais fiéis (com mais compras recorrentes e maiores valores).

4. **GET: /recomendacao/cliente/{cpf}**
    - Retorna uma recomendação de vinho baseada nos tipos de vinho que o cliente mais compra.

## Principais Classes

- `WineShopController`: Gerencia as requisições HTTP.
- `WineShopServiceImpl`: Implementa a lógica de negócios.
- `DataServiceImpl`: Responsável por carregar e fornecer os dados dos endpoints externos.
- `Produto`, `Cliente`, `Compra`: Modelos de dados.
- `CompraFielDto`, `ClienteFielDto`: DTOs para transferência de dados entre outros...

## Como Executar

1. Clone o repositório:
   ```
   git clone [https://github.com/DougNunes91/wine-shop]
   ```

2. Navegue até o diretório do projeto:
   ```
   cd [wine-shop]
   ```

3. Compile o projeto:
   ```
   mvn clean install
   ```

4. Execute a aplicação:
   ```
   java -jar target/wineshop-0.0.1-SNAPSHOT.jar
   ```

5. A aplicação estará disponível em `http://localhost:8080`

## Testando os Endpoints

Você pode usar ferramentas como Postman ou cURL para testar os endpoints. Exemplos:

```
GET http://localhost:8080/compras
GET http://localhost:8080/maior-compra/2022
GET http://localhost:8080/clientes-fieis
GET http://localhost:8080/recomendacao/cliente/1
```

## Contribuindo

Contribuições são bem-vindas! Email: douglas.vnunes91@gmail.com
