# Rinha Backend

Projeto desenvolvido para comparar desempenho com outroas linguagens.

Seguindo as caracterisiticas propostas pelo desafio

Utilizar o NGINx como Load Balancer;
2 APIs no mesmo servidor;
Banco Postgresql
Limitado a 3GB de RAM e 1.5 CPU

Porém, utilizei o docker instalado na minha máquina, com isso pode ter uma variação devido ao meu processador (AMD Ryzen R5 3600) ser diferente dos processadores usados nos testes;

Foi Utilizado Quarkus na versão 3.3.2 para realizar os testes
Bem como a Biblioteca Hibernate Reactive




Variações de Testes:

Realizei testes com as configurações dentro das especificas e com valores maiores, porém em praticamente todos os casos, os resutlados foram muito semelhantes.
Indicando a existencia de algum gargalo na aplicação, visto que ela não escala, mesmo com mais recursos disponiveis.

 - Banco com 6 CPU e 6 GB RAM:
 - Requisições totais 47115, dessas 30800 foram sucesso
 - Regsitros salvos no banco: 11999



