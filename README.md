# Prova Tecnica

Especificação:
1) Parte 1
    Integração com api http://www.datapoa.com.br/dataset/poatransporte, neste site realizar a
    integração com as operações de linhas de ônibus e itinerário.
    Operações (Integração)
        - Listar as linhas de ônibus -
            http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o
        - Listar itinerário de uma determinada unidade de transporte -
            http://www.poatransporte.com.br/php/facades/process.php?a=il&p=5566
2) Parte 2
    Criar API para filtrar as linhas de ônibus por nome.
3) Parte 3
    Criar um CRUD de linhas e itinerários, onde seja possível realizar consultas e cadastros
    desses itens. Para o cadastro deve ser realizada a consulta para verificar se a linha ou o
    itinerário já está cadastrada ou se tem alguma diferença de dados (linha ou itinerário), caso
    tenha deve ser atualizada ou caso não tenha deve ser cadastrada no sistema.
4) Parte 4
    Criar uma operação que faça o filtro de linhas por meio de um raio. Exemplo: passando uma
    latitude, longitude e um raio em KM, trazer todas as linhas dentro do raio informado.
5) Documentação da API.
    Junto com o teste, justifique o porquê utilizou o determinado framework, determinada base
    de dados e exemplos de request.

Gerar o Jar:

    mvn clean package

Rodar o sistema:

    java -jar target/provatecnica-1.0-SNAPSHOT.jar

Rodar com Docker:

    sudo docker build -t provatecnica:1.0.0 .
    sudo docker run --name provatecnica -p 8089:8089 -d provatecnica:1.0.0
    sudo docker logs --tail 200 -f provatecnica

Link da Especificação dos métodos no Swagger:

    http://localhost:8089/swagger-ui.html
