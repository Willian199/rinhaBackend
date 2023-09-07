# Rinha Backend

Projeto desenvolvido para comparar desempenho com outras linguagens.

Projetos usados como base:

https://github.com/brunoborges/rinha-backend-2023-q3-java

https://github.com/viniciusfcf/rinha-de-backend-2023-q3

Seguindo as caracterisiticas propostas pelo desafio

https://github.com/zanfranceschi/rinha-de-backend-2023-q3

- Utilizar o NGINX como Load Balancer;
- 2 APIs no mesmo servidor;
- Banco PostgreSQL;
- Limitado a 3GB de RAM e 1.5 CPU.

Porém, utilizei o Docker instalado na minha máquina, com isso pode haver uma variação devido ao meu processador (AMD Ryzen R5 3600) ser diferente dos processadores usados nos testes.

Foi utilizado o Quarkus na versão 3.3.2 com Hibernate Reactive.

Variações de Testes:

Realizei testes com as configurações dentro das especificações e com valores maiores, porém em praticamente todos os casos, os resultados foram muito semelhantes, indicando a existência de algum gargalo na aplicação, visto que ela não escala mesmo com mais recursos disponíveis. Inclusive, pode-se identificar que as primeiras 26 mil requisições ocorrem sem erro, porém a partir desse momento, a quantidade de usuários aumenta muito e a aplicação simplesmente morre.

Primeiro Teste:
 - Banco com 0.6 CPU e 1.6 GB de RAM;
 - 2x API com 0.35 CPU e 0.6 GB de RAM cada;
 - NGINX com 0.2 CPU e 0.2 GB de RAM;
 - Requisições totais 47,517, das quais 31,804 foram executadas com sucesso;
 - Registros salvos no banco: 12,401;
 - Ao repetir o teste, o resultado foi praticamente o mesmo.

Segundo Teste:
 - Banco com 6 CPU e 6 GB de RAM;
 - 2x API com 0.35 CPU e 0.6 GB de RAM cada;
 - Requisições totais 47,115, das quais 30,800 foram executadas com sucesso;
 - Registros salvos no banco: 11,999.

Terceiro Teste:
 - Banco com 0.6 CPU e 1.6 GB de RAM;
 - 2x API com 2 CPU e 2 GB de RAM cada;
 - NGINX com 0.2 CPU e 0.2 GB de RAM;
 - Requisições totais 47,236, das quais 31,181 foram executadas com sucesso;
 - Registros salvos no banco: 12,130.

Quarto Teste
 - Banco com 2 CPU e 6 GB de RAM;
 - 4x API com 2 CPU e 2 GB de RAM cada;
 - NGINX com 1 CPU e 1 GB de RAM;
 - Requisições totais 47,125, das quais 30,847 foram executadas com sucesso;
 - Registros salvos no banco: 12,015.


 Quinto Teste
 - Banco com 0.6 CPU e 1.6 GB de RAM;
 - 2x API com 0.35 CPU e 0.6 GB de RAM cada;
 - NGINX com 0.2 CPU e 0.2 GB de RAM;
 - Requisições totais 47,456, das quais 31,672 foram executadas com sucesso;
 - Registros salvos no banco: 12,342;
 - Foi utilizado o Quarkus na versão 3.2.5, porém apresentou o mesmo gargalo;
 - Usando o Quarkus 3.1.0, gerou praticamente os mesmos resultados.

Considerando que outros participantes conseguiram resultados melhores, mesmo usando PostgreSQL e NGINX, pode-se assumir que o Quarkus está sendo o causador do gargalo.

