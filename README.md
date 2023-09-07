# Rinha Backend

Projeto desenvolvido para comparar desempenho com outroas linguagens.

Projetos usados como base:

https://github.com/brunoborges/rinha-backend-2023-q3-java

https://github.com/viniciusfcf/rinha-de-backend-2023-q3

Seguindo as caracterisiticas propostas pelo desafio

https://github.com/zanfranceschi/rinha-de-backend-2023-q3

- Utilizar o NGINx como Load Balancer;
- 2 APIs no mesmo servidor;
- Banco Postgresql
- Limitado a 3GB de RAM e 1.5 CPU

Porém, utilizei o docker instalado na minha máquina, com isso pode ter uma variação devido ao meu processador (AMD Ryzen R5 3600) ser diferente dos processadores usados nos testes;

Foi Utilizado Quarkus na versão 3.3.2 para realizar os testes
Bem como a Biblioteca Hibernate Reactive

Variações de Testes:

Realizei testes com as configurações dentro das especificas e com valores maiores, porém em praticamente todos os casos, os resutlados foram muito semelhantes.
Indicando a existencia de algum gargalo na aplicação, visto que ela não escala, mesmo com mais recursos disponiveis.
Inclusive pode se identificar que as primeiras 26 mil requisições ocorrem sem erro, porém a partir desse momento, a quantida de usuários aumenta muito e aplicação simplesmente morre.

Primeiro Teste
 - Banco com 0.6 CPU e 1.6 GB RAM:
 - 2x API com 0.35 CPU e 0.6 GB de RAM cada
 - Nginx com 0.2 CPU e 0.2 GB de RAM
 - Requisições totais 47517, dessas 31804 foram executadas com sucesso
 - Regsitros salvos no banco: 12401
 - Ao repetir o teste, o resultado foi praticamente o mesmo.

Segundo Teste
 - Banco com 6 CPU e 6 GB RAM:
 - 2x API com 0.35 CPU e 0.6 GB de RAM cada
 - Requisições totais 47115, dessas 30800 foram executadas com sucesso
 - Regsitros salvos no banco: 11999

Terceiro Teste
 - Banco com 0.6 CPU e 1.6 GB RAM:
 - 2x API com 2 CPU e 2 GB de RAM cada
 - Nginx com 0.2 CPU e 0.2 GB de RAM
 - Requisições totais 47236, dessas 31181 foram executadas com sucesso
 - Regsitros salvos no banco: 12130

Quarto Teste
 - Banco com 2 CPU e 6 GB RAM:
 - 4x API com 2 CPU e 2 GB de RAM cada
 - Nginx com 1 CPU e 1 GB de RAM
 - Requisições totais 47125, dessas 30847 foram executadas com sucesso
 - Regsitros salvos no banco: 12015


 Quinto Teste
 - Banco com 0.6 CPU e 1.6 GB RAM:
 - 2x API com 0.35 CPU e 0.6 GB de RAM cada
 - Nginx com 0.2 CPU e 0.2 GB de RAM
 - Requisições totais 47456, dessas 31672 foram executadas com sucesso
 - Regsitros salvos no banco: 12342
 - Foi utilizado o Quarkus na versão 3.2.5, porém apresentou o mesmo gargalo.
  - Usando o Quarkus 3.1.0 gerou praticamente os mesmos resultados.

Considerando que outros participantes conseguiram resultados melhores, mesmo usando postgresql e NGix, pode-se assumir que o Quarkus está sendo o causador do gargalo.

