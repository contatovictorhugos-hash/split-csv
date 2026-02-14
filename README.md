Split CSV Utility
Um utilitário simples e eficiente em Java para dividir arquivos CSV volumosos em partes menores e iguais, mantendo a integridade das linhas.

Funcionamento
O script lê um arquivo de entrada, calcula o número total de linhas e distribui o conteúdo uniformemente entre 10 arquivos de saída (configurável). Caso a divisão não seja exata, o código distribui o restante das linhas entre os primeiros arquivos automaticamente.

Principais Funcionalidades
Divisão Proporcional: Garante que todos os arquivos tenham quase o mesmo tamanho.

Suporte a Encoding: Configurado para ISO-8859-1, ideal para lidar com caracteres especiais da língua portuguesa em arquivos CSV legados.

Gerenciamento Automático: Cria o diretório de saída caso ele não exista.

Configuração
Antes de rodar, é necessário definir os caminhos dos arquivos diretamente nas constantes do código:

Java
// Modifique estas linhas no arquivo SplitCSV.java
private static final int NUM_PARTES = 10;
private static final String INPUT_FILE = "C:/caminho/seu_arquivo.csv";
private static final String OUTPUT_DIR = "C:/caminho/saida";
Pré-requisitos
Java 8 ou superior.

Uma IDE (IntelliJ IDEA, Eclipse, VS Code) ou Terminal.

Como Executar
Clone o repositório:

Bash
git clone https://github.com/seu-usuario/split-csv.git
Abra o projeto no IntelliJ IDEA.

Configure as variáveis INPUT_FILE e OUTPUT_DIR no arquivo SplitCSV.java.

Execute a classe main.

Detalhes Técnicos
O algoritmo utiliza as seguintes classes da biblioteca NIO do Java para garantir performance:

Files.lines: Para contagem rápida de linhas.

BufferedReader / BufferedWriter: Para leitura e escrita eficiente em buffer, evitando consumo excessivo de memória RAM.