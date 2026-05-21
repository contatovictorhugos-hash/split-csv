# SplitCSV

Utilitario Java para dividir arquivos CSV grandes em arquivos menores.

O split e feito em modo streaming: o arquivo de entrada e lido uma unica vez, e apenas um arquivo de saida fica aberto por vez. Isso reduz I/O desnecessario e evita problemas com limite de descritores de arquivos do sistema operacional.

## Funcionalidades

- Divide um CSV grande em partes menores.
- Permite informar, no momento da execucao, quantas linhas de dados devem ir em cada arquivo.
- Replica o cabecalho em todos os arquivos gerados quando `POSSUI_CABECALHO` esta habilitado.
- Mantem apenas um `BufferedWriter` aberto por vez.
- Gera arquivos com timestamp no nome para evitar sobrescrita acidental.

## Configuracao

As configuracoes principais ficam em [src/SplitCSV.java](src/SplitCSV.java):

```java
private static final boolean POSSUI_CABECALHO = true;
private static final long LINHAS_DADOS_POR_ARQUIVO_PADRAO = 10000;
private static final Charset CHARSET = StandardCharsets.ISO_8859_1;
private static final String INPUT_FILE = "input.csv";
private static final String OUTPUT_DIR = "output";
```

Campos:

- `POSSUI_CABECALHO`: quando `true`, a primeira linha do arquivo de entrada e tratada como cabecalho e replicada em cada parte gerada.
- `LINHAS_DADOS_POR_ARQUIVO_PADRAO`: valor usado quando o usuario apenas pressiona Enter na pergunta inicial.
- `CHARSET`: encoding usado para ler e escrever os arquivos.
- `INPUT_FILE`: caminho do CSV de entrada. Pode ser relativo, como `input.csv`, ou absoluto.
- `OUTPUT_DIR`: diretorio onde os arquivos divididos serao criados. Pode ser relativo, como `output`, ou absoluto.

## Como Executar

Compile:

```powershell
javac -d out src\SplitCSV.java
```

Execute:

```powershell
java -cp out SplitCSV
```

Ao iniciar, o programa solicita a quantidade de linhas de dados por arquivo:

```text
Informe as linhas de dados por arquivo [padrao 10000]:
```

Se pressionar Enter sem digitar um valor, o programa usa o valor padrao configurado em `LINHAS_DADOS_POR_ARQUIVO_PADRAO`.

## Nome dos Arquivos Gerados

Os arquivos seguem o formato:

```text
nome-original-parte-timestamp.csv
```

Exemplo:

```text
saida_parte_10-1-143021052026.csv
saida_parte_10-2-143021052026.csv
```

## Observacoes

Este projeto divide por quantidade de linhas por arquivo, nao por numero fixo de partes.

Essa abordagem evita a contagem previa de todas as linhas do CSV. Dividir exatamente em `N` partes balanceadas exigiria conhecer o total de linhas antes do split, o que adicionaria uma leitura completa extra do arquivo.

## Requisitos

- Java 8 ou superior.
