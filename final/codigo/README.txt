- Pastas e arquivos:
    fetch_data.rb: Arquivo de extração de dados das bases do StackOverflow Careers e do Glassdoor
    webservice.jar: Binário executável da aplicação final
    webservice: Pasta contendo o código fonte da aplicação final

 - Para rodar a aplicação final, basta executar o seguinte comando no terminal (é necessário ter Java 8 instalado):
java -jar webservice.jar \
    --http-server.port=8080 \
    --database-sql.username=<usuário do mysql> \
    --database-sql.password=<senha do mysql>

- Após a inicialização do programa, um conjunto de URLs ficará disponível para acesso a partir do endereço http://localhost:8080, sendo algumas delas:

    - Interface gráfica na raiz (http://localhost:8080)

    - Lista de empresas cadastradas:
        /companies

    - Busca de empresas baseada nas avaliações de cada empresa:
        /companies/search
        Parâmetros:
            - name: Nome ou parte do nome da empresa
            - overall(true/false): Indica se o ranking 'overall' deve ser considerado na ordenação dos resultados
            - cultureAndValues(true/false): Indica se o ranking 'cultureAndValues' deve ser considerado na ordenação dos resultados
            - seniorLeadership(true/false): Indica se o ranking 'seniorLeadership' deve ser considerado na ordenação dos resultados
            - compensationAndBenefits(true/false): Indica se o ranking 'compensationAndBenefits' deve ser considerado na ordenação dos resultados
            - careerOpportunities(true/false): Indica se o ranking 'careerOpportunities' deve ser considerado na ordenação dos resultados
            - workLifeBalance(true/false): Indica se o ranking 'workLifeBalance' deve ser considerado na ordenação dos resultados
            - recommendToFriend(true/false): Indica se o ranking 'recommendToFriend' deve ser considerado na ordenação dos resultados
            - limit: Número máximo de resultados a serem retornados
        Exemplo:
            http://localhost:8080/offers/search?limit=10&name=micro&overall=true&compensationAndBenefits=true

    - Lista de habilidades cadastradas:
        /skills

    - Lista de usuários cadastrados:
        /users

    - Lista de ofertas de emprego recomendadas para determinado usuário:
        /users/recommendations/{id}
        Onde {id} é o Id do usuário
        Exemplo:
            http://localhost:8080/users/recommendations/1

    - Lista de ofertas de emprego cadastradas:
        /offers

    - Lista de usuários mais indicados para determinada oferta de emprego:
        /offers/recommended/{id}
        Onde {id} é o Id da oferta
        Exemplo:
            http://localhost:8080/offers/recommended/1

    - Busca de ofertas de emprego com base nas avaliações das empresas que postaram cada oferta:
        /offers/search
        Parâmetros:
            - skills: Cada oferta deve exigir ao menos uma habilidade dessa lista
            - overall(true/false): Indica se o ranking 'overall' deve ser considerado na ordenação dos resultados
            - cultureAndValues(true/false): Indica se o ranking 'cultureAndValues' deve ser considerado na ordenação dos resultados
            - seniorLeadership(true/false): Indica se o ranking 'seniorLeadership' deve ser considerado na ordenação dos resultados
            - compensationAndBenefits(true/false): Indica se o ranking 'compensationAndBenefits' deve ser considerado na ordenação dos resultados
            - careerOpportunities(true/false): Indica se o ranking 'careerOpportunities' deve ser considerado na ordenação dos resultados
            - workLifeBalance(true/false): Indica se o ranking 'workLifeBalance' deve ser considerado na ordenação dos resultados
            - recommendToFriend(true/false): Indica se o ranking 'recommendToFriend' deve ser considerado na ordenação dos resultados
            - limit: Número máximo de resultados a serem retornados
        Exemplo:
            http://localhost:8080/offers/search?limit=10&skills=java&skills=javascript&overall=true&compensationAndBenefits=true

    - Lista de ofertas postadas por determinada empresa:
        /offers/company/{id}
        Onde {id} é o Id da empresa
        Exemplo:
            http://localhost:8080/offers/company/1

    - Avaliar positivamente uma oferta de emprego:
        /users/like/{userId}?offerId={offerId}
        Onde {userId} é o Id do usuário e {offerId} é o Id da oferta de emprego.
        Exemplo:
            http://localhost:8080/users/like/1?offerId=9000

    - Avaliar negativamente uma oferta de emprego:
        /users/dislike/{userId}?offerId={offerId}
        Onde {userId} é o Id do usuário e {offerId} é o Id da oferta de emprego.
        Exemplo:
            http://localhost:8080/users/like/1?offerId=9002

    - Zerar as avaliações de ofertas de emprego de um usuário:
        /users/unrate_all/{userId}
        Onde {userId} é o Id do usuário.
        Exemplo:
            http://localhost:8080/users/unrate_all/1

    - Ver ofertas de empregos avaliadas pelo usuário:
        /users/ratings/{id}
        Onde {id} é o Id do usuário.
        Exemplo:
            http://localhost:8080/users/ratings/1

    - Ver o perfil de escolhas do usuário construido a partir das ofertas de emprego avaliadas por ele:
        /users/profile/{id}
        Onde {id} é o Id do usuário.
        Exemplo:
            http://localhost:8080/users/profile/1

    - Recomendação de ofertas com base nas avaliações do usuário (implementação 1):
        /users/recommend/{id}/offers_by_ratings/v1
        Onde {id} é o Id do usuário
        Exemplo:
            http://localhost:8080/recommend/1/offers_by_ratings/v1

    - Recomendação de ofertas com base nas avaliações do usuário (implementação 2):
        /users/recommend/{id}/offers_by_ratings/v2
        Onde {id} é o Id do usuário
        Exemplo:
            http://localhost:8080/recommend/1/offers_by_ratings/v2

Exemplo 1:
==========
http://localhost:8080/users/unrate_all/1
http://localhost:8080/users/ratings/1
http://localhost:8080/users/recommend/1/offers_by_ratings
http://localhost:8080/users/like/1?offerId=4180
http://localhost:8080/users/like/1?offerId=1477
http://localhost:8080/users/like/1?offerId=3902
http://localhost:8080/users/ratings/1
http://localhost:8080/users/recommend/1/offers_by_ratings

Exemplo 2:
==========
http://localhost:8080/users/unrate_all/1
http://localhost:8080/users/ratings/1
http://localhost:8080/users/recommend/1/offers_by_ratings/v1
http://localhost:8080/users/recommend/1/offers_by_ratings/v2
http://localhost:8080/users/like/1?offerId=9000
http://localhost:8080/users/like/1?offerId=9001
http://localhost:8080/users/dislike/1?offerId=9002
http://localhost:8080/users/like/1?offerId=9003
http://localhost:8080/users/ratings/1
http://localhost:8080/users/recommend/1/offers_by_ratings/v1
http://localhost:8080/users/recommend/1/offers_by_ratings/v2

