import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SplitCSV {

    // ===== CONFIG =====
    private static final int NUM_PARTES = 10;
    private static final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private static final String INPUT_FILE = "";
    private static final String OUTPUT_DIR = "";

    public static void main(String[] args) {

        Path inputPath = Paths.get(INPUT_FILE);
        Path outputDir = Paths.get(OUTPUT_DIR);

        try {

            Files.createDirectories(outputDir);

            long totalLinhas = Files.lines(inputPath, CHARSET).count();
            long linhasPorArquivo = totalLinhas / NUM_PARTES;
            long resto = totalLinhas % NUM_PARTES;

            System.out.println("=================================");
            System.out.println("Arquivo: " + INPUT_FILE);
            System.out.println("Total de linhas: " + totalLinhas);
            System.out.println("Arquivos: " + NUM_PARTES);
            System.out.println("Linhas base por arquivo: " + linhasPorArquivo);
            System.out.println("Extras distribuídos: " + resto);
            System.out.println("Diretório saída: " + OUTPUT_DIR);
            System.out.println("=================================");

            try (BufferedReader reader = Files.newBufferedReader(inputPath, CHARSET)) {

                BufferedWriter[] writers = new BufferedWriter[NUM_PARTES];

                for (int i = 0; i < NUM_PARTES; i++) {
                    Path outFile = outputDir.resolve("saida_parte_" + (i + 1) + ".csv");
                    writers[i] = Files.newBufferedWriter(outFile, CHARSET);
                }

                String linha;
                int arquivoAtual = 0;
                long contadorNoArquivo = 0;
                long limiteAtual = linhasPorArquivo + (arquivoAtual < resto ? 1 : 0);

                while ((linha = reader.readLine()) != null) {

                    writers[arquivoAtual].write(linha);
                    writers[arquivoAtual].newLine();

                    contadorNoArquivo++;

                    if (contadorNoArquivo >= limiteAtual && arquivoAtual < NUM_PARTES - 1) {
                        arquivoAtual++;
                        contadorNoArquivo = 0;
                        limiteAtual = linhasPorArquivo + (arquivoAtual < resto ? 1 : 0);
                    }
                }

                for (BufferedWriter bw : writers) {
                    if (bw != null) bw.close();
                }

                System.out.println("Split finalizado com sucesso.");

            }

        } catch (Exception e) {
            System.err.println("Erro no split:");
            e.printStackTrace();
        }
    }
}