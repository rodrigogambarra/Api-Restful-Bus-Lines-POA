<h1 align="center">Api Restful Bus Line POA</h1>
<p align="center">Sistema que faz download de todas as informações contidas na api: http://www.datapoa.com.br/dataset/poatransporte, e cria uma nova Api Restfull disponibilizando diferentes funcionalidades.</p>

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
Esse projeto foi criado com o objetivo de implementar uma Api que disponibiliza métodos de gerência sobre as linhas de ônibus de Porto Alegre e seus itinerários.

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

1. RequestMethod <i>GET</i> para url: http://localhost:8080/api/busLine/ - <b>Retorna lista de todas as linhas de ônibus e seus itinerários</b>

2. RequestMethod <i>GET</i> Url: http://localhost:8080/api/busLine/{id}" - <b>Retorna uma linha de ônibus específica, conforme valor do "id", e todos seus itinerários</b>

3. RequestMethod <i>GET</i> Url: http://localhost:8080/api/busLine/{nome}" - <b>Retorna uma ou mais linhas de ônibus, conforme "nome" passado por parâmetro</b>

4. RequestMethod <i>POST</i> Url: http://localhost:8080/api/busLine/" - <b>Cria uma nova linha de ônibus e seus itinerários, conforme exemplo json:</b>

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

5. RequestMethod <i>PUT</i> Url: http://localhost:8080/api/busLine/" - <b>Atualiza a linha de ônibus DE "ID 1" e seus itinerários, conforme exemplo json:</b>

Exemplo:
{

    "idBusLine":1,
    "code": "LINHA DE ÔNIBUS 45",
    "name": "LINHA 45",
    "busRoutes": [
        {
            "latitude": -30.147212063109,
            "longitude": -51.214982284567
        }
    ]

}

6. RequestMethod <i>DELETE</i> Url: http://localhost:8080/api/busLine/{id}" - <b>Exclui uma linha de ônibus e seus itinerários</b>

7. RequestMethod <i>GET</i> Url: http://localhost:8080/api/filterBusLineByRadius/" - <b>Retorna todas linhas de ônibus e seus itinerários próximos a uma latitude e longitude dentro de um raio em KM também passado por json, conforme exemplo:</b>

Exemplo:
{


}

8. RequestMethod <i>GET</i> Url: http://localhost:8080/api/filterBusLineByRadius/" - <b>Retorna todas linhas de ônibus e seus itinerários próximos a uma latitude e longitude dentro de um raio em KM, passados por json, conforme exemplo:</b>

Exemplo:
{


}


## Pré requisitos
Para abrir o sistema, basta o uso de um navegador de sua preferência e conexão com internet.

## Links
* Repositório: https://github.com/rodrigogambarra/Api-Restful-Bus-Lines-POA
* Deploy: 
* Link para testar e acessar a documentação da Api utilizando o Swagger: xxx/swagger-ui.html

## Autor
✨ Feito por Rodrigo Gambarra!!

* rodrigo@gambarra.com.br
* <a href="linkedin.com/in/rodrigo-gambarra-2a195b151" target=”_blank”>LinkedIn</a>
* <a href="https://github.com/rodrigogambarra" target=”_blank”>GitHub</a>

