import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SplitCSV {

    // ===== CONFIG =====
    private static final boolean POSSUI_CABECALHO = true;
    private static final long LINHAS_DADOS_POR_ARQUIVO_PADRAO = 10000;
    private static final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private static final String INPUT_FILE = "input.csv";
    private static final String OUTPUT_DIR = "output";

    public static void main(String[] args) {

        Path inputPath = Paths.get(INPUT_FILE);
        Path outputDir = Paths.get(OUTPUT_DIR);
        long linhasDadosPorArquivo = lerLinhasDadosPorArquivo();

        try {

            Files.createDirectories(outputDir);

            System.out.println("=================================");
            System.out.println("Arquivo: " + INPUT_FILE);
            System.out.println("Possui cabecalho: " + (POSSUI_CABECALHO ? "Sim" : "Nao"));
            System.out.println("Linhas de dados por arquivo: " + linhasDadosPorArquivo);
            System.out.println("Diretorio saida: " + OUTPUT_DIR);
            System.out.println("=================================");

            try (BufferedReader reader = Files.newBufferedReader(inputPath, CHARSET)) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmddMMyyyy"));
                String nomeCompletoEntrada = inputPath.getFileName().toString();
                String nomeBase = nomeCompletoEntrada.contains(".")
                        ? nomeCompletoEntrada.substring(0, nomeCompletoEntrada.lastIndexOf('.'))
                        : nomeCompletoEntrada;

                System.out.println("Arquivos gerados:");

                String cabecalho = POSSUI_CABECALHO ? reader.readLine() : null;
                String linha;
                int parteAtual = 0;
                long totalDados = 0;
                long contadorNoArquivo = 0;
                BufferedWriter writer = null;

                while ((linha = reader.readLine()) != null) {
                    if (writer == null || contadorNoArquivo >= linhasDadosPorArquivo) {
                        if (writer != null) {
                            writer.close();
                        }

                        parteAtual++;
                        contadorNoArquivo = 0;
                        writer = abrirWriterParte(outputDir, nomeBase, timestamp, parteAtual, cabecalho);
                    }

                    writer.write(linha);
                    writer.newLine();

                    contadorNoArquivo++;
                    totalDados++;
                }

                if (writer != null) {
                    writer.close();
                }

                System.out.println("=================================");
                System.out.println("Total de linhas de dados: " + totalDados);
                System.out.println("Total de arquivos: " + parteAtual);
                System.out.println("Split finalizado com sucesso.");

            }

        } catch (Exception e) {
            System.err.println("Erro no split:");
            e.printStackTrace();
        }
    }

    private static long lerLinhasDadosPorArquivo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Informe as linhas de dados por arquivo [padrao "
                    + LINHAS_DADOS_POR_ARQUIVO_PADRAO + "]: ");

            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return LINHAS_DADOS_POR_ARQUIVO_PADRAO;
            }

            try {
                long valor = Long.parseLong(entrada);
                if (valor > 0) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Mostra a mensagem abaixo e solicita novamente.
            }

            System.out.println("Valor invalido. Informe um numero inteiro maior que zero.");
        }
    }

    private static BufferedWriter abrirWriterParte(
            Path outputDir,
            String nomeBase,
            String timestamp,
            int parteAtual,
            String cabecalho
    ) throws IOException {
        String nomeSaida = String.format("%s-%d-%s.csv", nomeBase, parteAtual, timestamp);
        Path outFile = outputDir.resolve(nomeSaida);

        System.out.println(" - " + nomeSaida);

        BufferedWriter writer = Files.newBufferedWriter(outFile, CHARSET);
        if (cabecalho != null) {
            writer.write(cabecalho);
            writer.newLine();
        }

        return writer;
    }
}
