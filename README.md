<h1 align="center">Api Rest Bus Line POA</h1>
<p align="center">Sistema que faz integração ou download de todas as informações contidas na api: http://www.datapoa.com.br/dataset/poatransporte, e cria uma nova Api Restfull disponibilizando diferentes funcionalidades.</p>

---

**<p align="center">Sumário:</p>**
<p align="center">
<a href="#sobre">Sobre</a> |
<a href="#funcionalidades">Funcionalidades</a> |
<a href="#tecnologias">Tecnologias</a> |
<a href="#serviços-usados">Serviços usados</a> |
<a href="#imagens">Imagens</a> |
<a href="#como-usar">Como usar</a> |
<a href="#pré-requisitos">Pré-requisitos</a> |
<a href="#links">Links</a> |
<a href="#autor">Autor</a></p>

## Sobre
Esse projeto foi criado com o objetivo de implementar uma Api para gerência das linhas de ônibus de Porto Alegre e seus itinerários, consultando as linhas disponíveis e seus itinerários, bam como criando, excluindo e atualizando as linhas. Também possibilitando a consulta de linhas próximas a um ponto específico.

## Funcionalidades
As principais funcionalidades do projeto são:

✅ Consultar todas as linhas de ônibus de Porto Alegre e seus itinerários;

✅ Criar uma nova linha de ônibus e seus itinerários;

✅ Consultar, a partir do seu código, uma linha específica e seu itinerário;

✅ Consultar, a partir do seu nome, uma linha específica e seu itinerário;

✅ Atualizar uma linha específica e seu itinerário;

✅ Consultar linhas de onibus que mais se aproximam de um determinado ponto;


## Tecnologias utilizadas
* Java
* Spring Boot
* Spring Data
* JPA
* Hibernate
* Banco em memória H2

## Serviços usados
* GitHub
* Heroku (para hospedagem)

## Imagens
<p>Não há imagens no momento</p>

## Como usar
<p>As funcionalidades fornecidas pela Api são disponibilizadas pelos seguintes métodos request http:</p>

1. RequestMethod <i>GET</i> para url: http://localhost:8089/api/busLine/ - <b>Retorna lista de todas as linhas de ônibus e seus itinerários</b>

2. RequestMethod <i>GET</i> Url: http://localhost:8089/api/busLine/{id}" - <b>Retorna uma linha de ônibus específica, conforme valor do "id", e todos seus itinerários</b>

3. RequestMethod <i>GET</i> Url: http://localhost:8089/api/filterBusLine/{nome}" - <b>Retorna uma ou mais linhas de ônibus, conforme "nome" passado por parâmetro</b>

4. RequestMethod <i>POST</i> Url: http://localhost:8089/api/busLine/" - <b>Cria uma nova linha de ônibus e seus itinerários, conforme exemplo json:</b>

Exemplo:
{

    "code": "LINHA DE ÔNIBUS 45",
    "name": "LINHA 45",
    "busRoutes": [
        {
            "latitude": -30.147212063109,
            "longitude": -51.214982284567
        },
        {
            "latitude": -20.147212063109,
            "longitude": -66.214982284567
        }
    ]

}

5. RequestMethod <i>PUT</i> Url: http://localhost:8089/api/busLine/{id}" - <b>Atualiza a linha de ônibus DE "ID 1" e seus itinerários, conforme exemplo json:</b>

Exemplo:
{

    "code": "LINHA DE ÔNIBUS 45",
    "name": "LINHA 45",
    "busRoutes": [
        {
            "latitude": -30.147212063109,
            "longitude": -51.214982284567
        }
    ]

}

6. RequestMethod <i>DELETE</i> Url: http://localhost:8089/api/busLine/{id}" - <b>Exclui uma linha de ônibus e seus itinerários</b>

7. RequestMethod <i>GET</i> Url: http://localhost:8089/api/filterBusLineByRadius/?km={Valor Km}&latitude={Valor Latitute}&longitude={Valor Longitude}" - <b>Retorna todas linhas de ônibus e seus itinerários próximos a uma latitude e longitude dentro de um raio em KM passado por parâmetro url</b>

8. RequestMethod <i>POST</i> Url: http://localhost:8089/api/filterBusLineByRadius/" - <b>Retorna todas linhas de ônibus e seus itinerários próximos a uma latitude e longitude dentro de um raio em KM, passados por json, conforme exemplo:</b>

Exemplo:
{

    "latitude":"-30.129305",
    "longitude":"-51.201491",
    "km":1

}

## Pré requisitos
Para abrir o sistema, basta o uso de um navegador de sua preferência e conexão com internet.

## Links
* Repositório: https://github.com/rodrigogambarra/Api-Restful-Bus-Lines-POA
* Deploy: 
* Link para testar e acessar a documentação da Api utilizando o Swagger: http://localhost:8089/swagger-ui.html
* Link para o banco H2: http://localhost:8089/h2/

## Autor
✨ Feito por Rodrigo Gambarra!!

* rodrigo@gambarra.com.br
* <a href="linkedin.com/in/rodrigo-gambarra-2a195b151" target=”_blank”>LinkedIn</a>
* <a href="https://github.com/rodrigogambarra" target=”_blank”>GitHub</a>

