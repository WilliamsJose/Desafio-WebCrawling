# Desafio Web Crawling
O desafio consiste em desenvolver uma aplicação web crawling que navegue em todas as páginas filhas a partir de uma BASE_URL (url inicial), cumprindo os requisitos estabelecidos, principalmente, sem instalar novos frameworks e utilizando apenas código Java sem alterar o **pom.xml** e **Dockerfile**. Exemplo: se a URL base foi definida como **http://exemplo.com/site/**, um link para **http://exemplo.com/site/pagina.html** deve ser visitado; já um link para **http://exemplo.com/blog/** não deve ser visitado. 

---

### Requisitos
- [x] A KEYWORD deve ser case-insensitive
- [x] A KEYWORD deve estar em qualquer conteúdo HTML, mesmo que não esteja visível
- [x] O resultado só deve ser considerado se estiver de acordo com a BASE_URL, qualquer outra URL fora do formato será desconsiderada da busca
- [x] O OUTPUT deve seguir o padrão: "Result found: URL", seguindo arquivo de exemplo "results_four.txt"
- [x] O OUTPUT deve mostrar apenas uma URL por linha
- [x] Não instalar novos frameworks
- [x] A BASE_URL, KEYWORD e MAX_RESULTS devem estar em variáveis de ambiente cada
- [x] Acessar URL apenas uma vez
- [x] A URL seguir o padrão URI especificado em java.net.URI
- [x] Melhorar performance
- [ ] A execução pode continuar de onde parou

---

### Compilação e execução

`docker build . -t axreng/backend`

*Adicionei mais uma flag **-v** no comando a seguir para especificar onde serão salvos os resultados na máquina host.
Devido à isso será necessário alteração do caminho marcado **C:\Users\willi\Desktop\results**:/root/Desktop/results para uma pasta local onde deseja salvar os resultados*

`docker run -v MAVEN_HOME:/root/.m2 -v C:\Users\willi\Desktop\results:/root/Desktop/results -e BASE_URL=http://hiring.axreng.com/ -e KEYWORD=four --rm axreng/backend`
