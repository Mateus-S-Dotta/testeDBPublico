# testeDB

    Olá, eu sou Mateus e estou realizando este teste para DBSERVER.
    Sendo bastante sincero, foi bastante difícil. Literalmente, aprendi a utilizar Sring Boot e lidar com certas questões do Java.
    De fato, Spring Boot e Java não são um monstro de 7 cabeças, não é a coisa mais difícil do mundo.
    Mas com certeza não é a mais simples.
    Não posso negar a capacidade da ferramenta. É muito útil, pois muitos passos são cortados e isso pode acelerar muito o desenvolvimento,
apesar que às vezes não seja tão simples implementar. No geral, as requisições são bastante rápidas.
    Pareceu-me uma ferramenta onde, ou você sabe o que está fazendo, ou ficara mal feito. No meu caso, eu iniciei o processo de aprendizado.
    Era bastante perceptivel que algumas requisições poderiam ser passadas no Repository, como por exemplo, a rota de editção de livros.
    Acredito que poderia-se reduzir a uma única query, onde não precisaria pegar o livro com o Id para depois equalizar os alugueis do meu 
objeto novo com o livro pego.
    No geral, fico bastante satisfeito com o resultado. Acredito que tenha muito a melhorar no projeto, mais do que posso imaginar,
porém, penso que ficou em um bom padrão para quem está na minha situação, aprendendo a ferramenta e conciliando com o estágio.
    Muito obrigado pela oportunidade. Sempre busco aprender mais sobre as coisas, e este teste foi muito proveitoso para isso.
    Provavelmente irei utilizar mais estas novas ferramentas que aprendi, se for gratuita a questão do Deploy.
    Com express, consegui fazer deploy em uma API REST integrada ao banco de dados SQLite da CloudFlare.
    Contudo, como utilizei o plano gratuito da CloudFlare, não tive que criar banco de dados.
    Novamente, reitero, muito obrigado. Espero que gostem do projeto. Abaixo, fica a documentação, com explicação de cada um das rotas.

    ROTAS: Introdução
    Deixo claro que aqui ficam também minhas observações pedagógicas. Para mim, este projeto foi um teste e um aprendizado,
portanto, vou dizer o como fiz e como acredito que seja uma boa forma de se construir, mas muito provavelmente há formas melhores de
realizar o que eu descrever.

    O projeto contava com 4 principais entidades: livros, autores, alugueis e locatários.
    A relação para cada um foi:
    Aluguéis<One To Many> Locatários
    Livros<Many To Many> Aluguéis
    Autores<Many To Many> Livros

    Seguindo as seguintes regras:
    AUTORES só podem ser excluidos se não tiverem livros
    LIVROS só podem ser excluidos se não tiverem em nenhuma locação
    USUÁRIO só pode ser excluido se não tiverem nenhuma locação
    (Sei que a regra não era essa, mas para mim, não faz sentido haver a possibilidade de se deixar uma locação
com o campo locatário vazio)

    ROTAS: Authors

        /authors/:id
        Pesquisa um autor pelo Id

        /authors/:id/books
        Pesquisa os livros de um autor

        /authors
        Pesquisa todos os autores

        /authors(post)
        Cadastra um novo autor

        /authors/:id(put)
        Altera um autor

        /authors/:id(delete)
        Exclui um autor
        
    ROTAS: Book
    Em ordem que se aparece no arquivo BookController

        /book/:id
        Pesquisa um livro pelo id

        /book/findByUser/:id
        Pesquisa todos os livros que um locatário alugou

        /book(get)
        Pesquisa todos os livros
        Parametros: nome e ativo
        nome: pesquisa todos os livros com aquele nome
        ativo: pesquisa os livros ativos (valor de "ativo" = true) e os livros inativos (valor de "ativo" = false)

        /book(post)
        Cadastra um livro

        /book/:id(put)
        Edita um livro

        /book/:id(delete)
        Deleta um livro

    ROTAS: Rent

        /rent/:id
        Pesquisa um aluguel pelo id

        /rent(get)
        Pesquisa todos os alugueis
        Parametros: ativo e locatarioId
        ativo: pesquisa as locações ativas (para valor = true) e pesquisa as locações inativas (para valor = false)
        locatarioId: combina com o parametro acima. Ira pesquisar as locações ativas ou inativas (a partir do valor de "ativo") de um locatário especifico

        /rent(post)
        Cadastra uma nova locação

        /rent/:id(put)
        Pode alterar uma locação

        NÃO DEIXEI ATIVO NENHUMA ROTA DE DELEÇÃO DE ALUGUÉL
        Pois para mim não faz sentido. Pode simplesmente um usuário ou um hacker apagar toda a existência da empresa, praticamente

    ROTAS: User
        
        /user/:id
        Pesquisa todo o locatário pelo Id

        /user
        Pesquisa todos os locatários

        /user(post)
        Cria um novo locatário

        /user/:id(put)
        Altera um locatário

        /user/:id(delete)
        Deleta um locatário